(ns ^:no-doc rewrite-clj.zip.edit
  "This ns exists to preserve compatability for rewrite-clj v0 clj users who were using an internal API.
   This ns does not work for cljs due to namespace collisions."
  (:refer-clojure :exclude [replace])
  (:require [rewrite-clj.zip.editz]))

(set! *warn-on-reflection* true)

#_{:import-vars/import
   {:from [[rewrite-clj.zip.editz
            replace
            edit
            splice
            prefix
            suffix]]}}
