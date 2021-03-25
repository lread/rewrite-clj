(ns helper.env
  (:require [clojure.edn :as edn]
            [clojure.string :as string]
            [helper.shell :as shell]
            [helper.status :as status]
            [version-clj.core :as ver]))

(defn get-os []
  (let [os-name (string/lower-case (System/getProperty "os.name"))]
    (condp #(re-find %1 %2) os-name
      #"win" :win
      #"mac" :mac
      #"(nix|nux|aix)" :unix
      #"sunos" :solaris
      :unknown)))

(defn- assert-clojure-min-version
  "Asserts minimum version of Clojure version"
  []
  (let [min-version "1.10.1.697"
        version
        (->> (shell/command ["clojure" "-Sdescribe"] {:out :string})
             :out
             edn/read-string
             :version)]
    (when (< (ver/version-compare version min-version) 0)
      (status/fatal (str "A minimum version of Clojure " min-version " required.\nFound version: " version)))))

(defn- assert-babashka-min-version
  ;; TODO: this won't work as we are using bb features above min version in requires, etc.
  "Asserts minimum version of Babashka"
  []
  (let [min-version "0.2.3"
        version (System/getProperty "babashka.version")]
    (when (< (ver/version-compare version min-version) 0)
      (status/fatal (str "A minimum version of Babashka " min-version " required.\nFound version: " version)))))

(defn assert-min-versions[]
  (assert-babashka-min-version)
  (assert-clojure-min-version))
