;;Put on Github!
(ns lho
  (:use compojure)
  (:import (java.util Calendar)))

(defn head []
  [:head (include-css "/public/css/base.css")
   ])

(defn footer []
  (include-js "/public/javascript/jquery-1.3.2.js"))

;think about multimethod to accept head and footer args
(defn page [& body]
  (html (:html4 doctype)
        (conj (head)
              [:body body]
              (footer))))

(defn html-form [action & form-body]
     [:form {:method "post" :action action}
      (when (coll? form-body) form-body)
      [:input {:type "submit" :name "submit" :value "Submit"}]])

(defn months [] ['January 'February 'March 'April 'May 'June 'July 'August
                 'September 'October 'November 'December])
(defn days [] (range 1 32))
(defn years [] 
  (range (.get (Calendar/getInstance) Calendar/YEAR) 
         (+ 3 (.get (Calendar/getInstance) Calendar/YEAR)))
  )

  
(defn date-form []
  [:fieldset [:legend "When?"]
   [:select {:name "month"} (select-options (months))]
   [:select {:name "day"} (select-options (days))]
   [:select {:name "year"} (select-options (years))]
   ])


(defroutes elho
  (GET "/"
    (page [:h1 "Wanna hang out?"] 
          [:pre (str params)] (html-form "/joe" (date-form)))
    )
  (POST "/joe"
    (page [:h1 "Sounds good."] [:pre (str params)] (html-form "/joe"))
    )
  (ANY "*"
    (page-not-found)))

(run-server {:port 3000}
  "/*" (servlet elho))
