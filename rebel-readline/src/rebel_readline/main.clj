(ns rebel-readline.main
  (:gen-class)
  (:require
   [rebel-readline.clojure.main :as main]))

(defn -main [& args]
  (apply main/-main args))
