(ns task
  "Some help on tasks")

;;
;; Helpers
;;
(defn- print-tasks []
  (let [tasks (->> (ns-publics 'task)
                   vals
                   (mapv meta)
                   (remove #(= '-main (:name %))))
        max-task-width (apply max (map #(-> % :name str count) tasks))
        fmt (format "%%-%ds  %%s" max-task-width)]
    (run! #(println (format fmt (:name %) (get % :short-doc "")))
          tasks)))

(defn- rresolve [namespace v]
  (requiring-resolve (symbol (str namespace) (str v))))

(defn- main [namespace args]
  (apply (rresolve namespace "-main") args))

(defn- main-doc [namespace]
  (let [m (meta (rresolve namespace "-main"))]
    (:doc m)))

;;
;; Tasks
;;
(defn import-vars
  ;; TODO maybe short-doc would got on apply-import-vars/main?
  {:short-doc "[gen-code|check]"}
  [& args]
  (main 'apply-import-vars args))
(alter-meta! #'import-vars assoc :doc (main-doc 'apply-import-vars))

(defn test-clj [& args]
  (main 'clj-tests args))
(alter-meta! #'test-clj assoc :doc (@(requiring-resolve 'clj-tests/usage-help)))

(defn test-ci []
  (main 'ci-tests []))

(defn test-libs [cmd & libs]
  (apply main 'libs-tests cmd libs))
(alter-meta! #'test-libs assoc :doc
             @(requiring-resolve 'libs-tests/docopt-usage))


(defn -main [& args]
  (println )
  (when-not (seq args)
    (print-tasks)))

