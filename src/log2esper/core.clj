(ns log2esper.core
  (:import [org.jeromq ZMQ])
  (:require (cheshire [core :as c])))

(def ctx (ZMQ/context 1))

(defn listen-to-logstash
  [num-events]
  (let [s (.socket ctx ZMQ/SUB)]
    (.subscribe s "")
    (.connect s "tcp://127.0.01:5555")
    (dotimes [_ num-events]
      (println (c/parse-string (String. (.recv s)))))
    (.close s)))

