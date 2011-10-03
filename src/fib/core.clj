(ns fib.core
  (:use [lamina.core]
        [aleph.http]))

(defn fib-seq []
  ((fn rfib [a b]
     (cons a (lazy-seq (rfib b (+ a b)))))
       0 1))

(defn response []
  (str (reduce + (take 40 (fib-seq)))))

(defn better-living-thru-fib [channel request]
  (enqueue channel {:status 200
                    :headers {"Content-Type" "text/plain"}
                    :body (response)}))

(start-http-server better-living-thru-fib {:port 1337})

