(ns log2esper.core
  (:use log2esper.esper)
  (:import [org.jeromq ZMQ])
  (:require (cheshire [core :as c])))

(def log-data-event
  (new-event "LogDataEvent"
    {"user" :string
     "severity" :string
     "state" :string}))

(def esp-service (create-service "logAnalysis"
                                 (configuration log-data-event)))

(defn send-event
  [service event event-type]
  (.sendEvent (.getEPRuntime service) event event-type))

(def ctx (ZMQ/context 1))

(defn listen-to-logstash
  [num-events service statement listener]
  (let [s (.socket ctx ZMQ/SUB)
        stmt (new-statement service statement)
        _ (add-listener stmt (create-listener listener))]
    (.subscribe s "")
    (.connect s "tcp://127.0.01:5555")
    (dotimes [_ num-events]
      (let [msg (c/parse-string (String. (.recv s)))]
        (println msg)
        (send-event service {"user" "1" 
                             "severity" (msg "severity")
                             "state" "select"} 
                    "LogDataEvent")))
    (destroy-statement stmt)
    (.close s)))

(def log-statement
  "SELECT * FROM LogDataEvent where severity = 'INFO'")

(defn log-handler
  [new-events]
  (println "log-handler"))

(defn demo []
  (listen-to-logstash 3 esp-service log-statement log-handler))
