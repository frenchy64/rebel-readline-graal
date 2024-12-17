(ns build
  (:require [clojure.tools.build.api :as b]))

(def lib 'com.ambrosebs/rebel-readline)
(def class-dir "target/classes")
(def version "0.1.5-SNAPSHOT")
(def basis (delay (b/create-basis {:project "deps.edn"})))
(def uber-file (format "target/%s-standalone.jar" (name lib)))
(def main-ns 'rebel-readline.main)
(def src-dirs ["src" "resources"])
(def java-dirs ["java"])

(defn clean [_]
  (b/delete {:path "target"}))

(defn compile-java [_]
  (b/javac {:src-dirs java-dirs
            :class-dir class-dir
            :basis @basis
            :javac-opts ["-source" "8" "-target" "8"]}))

(defn prep [_]
  (compile-java nil))

(defn uber [{:keys [native]}]
  (let [basis (if native
                (b/create-basis {:project "deps.edn"
                                 :aliases [:native-deps]})
                @basis)]
    (clean nil)
    (b/copy-dir {:src-dirs src-dirs
                 :target-dir class-dir})
    (compile-java nil)
    (b/compile-clj {:basis basis
                    :ns-compile [main-ns]
                    :class-dir class-dir})
    (b/uber {:class-dir class-dir
             :uber-file uber-file
             :basis basis
             :main main-ns})))
