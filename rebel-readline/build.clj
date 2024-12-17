(ns build
  (:require [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]))

(def lib 'com.ambrosebs/rebel-readline)
(def class-dir "target/classes")
(def version "0.1.5-SNAPSHOT")
(def basis (delay (b/create-basis {:project "deps.edn"})))
(def uber-file (format "target/%s-%s-standalone.jar" (name lib) version))
(def jar-file (format "target/%s-%s.jar" (name lib) version))
(def main-ns 'rebel-readline.main)
(def src-dirs ["src" "resources"])
(def java-dirs ["java"])

(defn clean [_]
  (b/delete {:path "target"}))

(defn compile-java [{:keys [native]}]
  (b/javac (cond-> {:src-dirs java-dirs
                    :class-dir class-dir
                    :basis @basis}
             (not native) (assoc :javac-opts ["--release" "8"]))))

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
    (compile-java {:native native})
    (b/compile-clj {:basis basis
                    :ns-compile [main-ns]
                    :class-dir class-dir})
    (b/uber {:class-dir class-dir
             :uber-file uber-file
             :basis basis
             :main main-ns})))

(defn jar [_]
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis basis
                :src-dirs ["src"]})
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file}))

(defn deploy [opts]
  (jar opts)
  ((requiring-resolve 'deps-deploy.deps-deploy/deploy)
    (merge {:installer :remote
                       :artifact jar-file
                       :pom-file (b/pom-path {:lib lib :class-dir class-dir})}
                    opts))
  opts)
