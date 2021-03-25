(ns outdated
  (:require [clojure.java.io :as io]
            [helper.env :as env]
            [helper.shell :as shell]
            [helper.status :as status]))

(defn check-clojure []
  (status/line :info "Checking Clojure deps")
  (shell/command-no-exit ["clojure" "-M:outdated"]))

(defn check-nodejs []
  (status/line :info "Checking Node.js deps")
  (when (not (.exists (io/file "node_modules")))
    (status/line :detail "node_modules, not found, installing.")
    (shell/command ["npm" "install"]))

  (let [{:keys [:exit]} (shell/command-no-exit ["npm" "outdated"])]
    (when (zero? exit)
      (status/line :detail "All Node.js dependencies seem up to date.")
      (status/line :detail "(warning: deps are only checked against installed ./node_modules)"))))

(defn -main []
  (env/assert-min-versions)
  (check-clojure)
  (check-nodejs)
  (status/line :detail "\nDone."))

