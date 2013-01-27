(ns main
  (:gen-class)
  (:use [clojure.tools.cli :only [cli]]
        (ring.middleware [keyword-params :only [wrap-keyword-params]]
                         [params :only [wrap-params]])
        [org.httpkit.server :only [run-server]]))

(defn to-int [s] (cond
                  (string? s) (Integer/parseInt s)
                  (instance? Integer s) s
                  (instance? Long s) (.intValue ^Long s)))

;; ~20k string
(def const-str (apply str (repeat 200 "http-kit is a http server & client written from scrach for high performance clojure web applications, support async and websocket")))

(defn handler [req]
  (let [length (to-int (or (-> req :params :length) 1024))]
    {:status 200
     :headers {"Content-Type" "text/plain"}
     :body (subs const-str 0 (max (min 10240 length) 1))}))

(defn -main [& args]
  (run-server (-> handler wrap-keyword-params wrap-params)
              {:port 8000})
  (println (str "Server started. listen at 0.0.0.0@8000")))
