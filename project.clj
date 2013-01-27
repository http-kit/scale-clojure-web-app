(defproject scale-clojure-web-application "0.1"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-swank "1.4.4"]]
  :aot [main]
  :main main
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/tools.cli "0.2.1"]
                 [ring/ring-core "1.1.3"]
                 [org.clojure/tools.cli "0.2.1"]
                 [http-kit "2.0-rc1"]])
