(ns clj-watch
  (:require [helper.env :as env]
            [helper.shell :as shell]
            [helper.status :as status]))

(defn -main
  "Run Clojure tests in watch mode

   Specify any additional kaocha args (ex. --focus, --skip etc) on the command line."
  [& args]
  (env/assert-min-versions)
  (status/line :info "launching kaocha watch on clojure sources")
  (shell/command (concat ["clojure" "-M:test-common:kaocha" "--watch"] args)))
