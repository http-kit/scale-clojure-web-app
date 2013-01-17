(ns main
  (:gen-class)
  (:use [clojure.tools.cli :only [cli]]
        (ring.middleware [keyword-params :only [wrap-keyword-params]]
                         [params :only [wrap-params]])
        [me.shenfeng.http.server :only [run-server]]))

(defn to-int [s] (cond
                  (string? s) (Integer/parseInt s)
                  (instance? Integer s) s
                  (instance? Long s) (.intValue ^Long s)))

;; [a..z]+
(def string (apply str
                   (map char
                        (take 10241
                              (apply concat (repeat (range (int \a) (int \z))))))))

(defn handler [req]
  (let [length (to-int (or (-> req :params :length) 1024))
        length (max (min 10240 length) 1)]
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (str "User Agent: "
                (get-in req [:headers "user-agent"])
                "\nRequested String count: " length
                "\nString: \n"
                (subs string 0 length))}))

(defn app [] (-> handler wrap-keyword-params wrap-params))

(defonce server (atom nil))

(defn -main [& args]
  (let [[options _ banner]
        (cli args
             ["-p" "--port" "Port to listen" :default 8000 :parse-fn to-int]
             ["--worker" "Http worker thread count" :default 4 :parse-fn to-int])]
    (when (:help options) (println banner) (System/exit 0))
    (reset! server (run-server (app) {:port (:port options)
                                      :worker-name-prefix "w"
                                      :thread (:worker options)}))
    (println (str "Server started. listen at 0.0.0.0@" (:port options)))))
