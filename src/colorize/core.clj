(ns colorize.core
  "A set of functions to wrap strings in ansi-colors.")

(def ansi-colors {:black 0
                  :red 1
                  :green 2
                  :yellow 3
                  :blue 4
                  :magenta 5
                  :cyan 6
                  :white 7
                  :default 9})

(def ansi-extra {:reset 0
                 :bold 1
                 :italic 3
                 :underline 4
                 :blink 5
                 :inverse 7
                 :strikethrough 9})

(defn ansi [string]
  (str \u001b \[ string \m))

(defn extra [string v]
  (if (keyword? v)
    (str (ansi (v ansi-extra))
         string
         (if-not (= :reset v)
           (ansi (:reset ansi-extra))))))

(defn bg [string v]
  (if (keyword? v)
    (str (ansi (str 4 (v ansi-colors)))
         string
         (extra "" :reset))))

(defn fg [string v]
  (if (keyword? v)
    (str (ansi (str 3 (v ansi-colors)))
         string
         (extra "" :reset))))

(defn color [string options]
  (reduce (fn [cur [k v]]
            (condp = k
              :bg (bg cur v)
              :fg (fg cur v)
              (if (and (ansi-extra k) v)
                (extra cur k)
                cur))) string options))

(defmacro ^{:private true} create-fns []
  (apply list 'do
         (for [k (keys ansi-colors)]
           `(do (defn ~(symbol (name k)) [& ss#]
                  (apply str (map (fn [s#] (fg s# ~k)) ss#)))
                (defn ~(symbol (str (name k) "-bg")) [& ss#]
                  (apply str (map (fn [s#] (bg s# ~k)) ss#)))))
         (for [k (keys ansi-extra)]
           `(defn ~(symbol (name k)) [& ss#]
              (apply str (map (fn [s#] (extra s# ~k)) ss#))))))

(create-fns)
