(defproject com.ambrosebs/rebel-readline "0.1.5-SNAPSHOT"
  :description "Terminal readline library for Clojure dialects"
  :url "https://github.com/frenchy64/rebel-readline-graal"
  :license {:name "Eclipse Public License"
            :distribution :repo
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :scm {:name "git"
        :url "https://github.com/frenchy64/rebel-readline-graal"
        :dir ".."}

  :dependencies [[org.jline/jline-reader "3.24.1"]
                 [org.jline/jline-terminal "3.24.1"]
                 ;; deprecated, replaced by JNI provider. neither seems to compile on higher versions
                 [org.jline/jline-terminal-jansi "3.24.1"]
                 [dev.weavejester/cljfmt "0.13.0"]
                 [compliment/compliment "0.6.0"]]
  :aot [rebel-readline.line-reader-class]
  :profiles {:dev {:source-paths ["src" "dev"]
                   :main rebel-dev.main}
             :uberjar {:dependencies [[com.github.clj-easy/graal-build-time "1.0.5"]]
                       :aot :all
                       :uberjar-name "rebel-readline-standalone.jar"
                       :main rebel-readline.main}})
