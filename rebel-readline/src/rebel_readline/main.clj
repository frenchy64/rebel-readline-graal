(ns rebel-readline.main
  (:gen-class))

(when *compile-files*
  (require 'rebel-readline.clojure.main
           'cljfmt.core
           'compliment.core))

(when (= "buildtime" (System/getProperty "org.graalvm.nativeimage.imagecode"))
  (require 'rebel-readline.clojure.main
           'cljfmt.core
           'compliment.core))

(defn -main [& args]
  (locking clojure.lang.RT/REQUIRE_LOCK
    (require 'rebel-readline.clojure.main))
  (apply (resolve 'rebel-readline.clojure.main/-main) args))
