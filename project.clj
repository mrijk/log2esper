(defproject log2esper "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.espertech/esper "4.9.0" :exclusions [log4j]]
                 [com.rmoquin.bundle/jeromq "0.2.0"]
                 [cheshire "5.3.1"]]
  :main log2esper.core)
