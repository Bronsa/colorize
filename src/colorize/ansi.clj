(ns colorize.ansi
  "A set of functions to wrap strings in ansi-colors.")

(def ansi-position
  {:bg 4
   :fg 3})

(def ansi-colors
  {:black   0
   :red     1
   :green   2
   :yellow  3
   :blue    4
   :magenta 5
   :cyan    6
   :white   7
   :default 9})

(def ansi-extra
  {:reset         0
   :bold          1
   :italic        3
   :underline     4
   :blink         5
   :inverse       7
   :strikethrough 9})

(defn ansi-char [code]
  (str \u001b \[ code \m))

(defn extra [string v]
  {:pre [(find ansi-extra v)]}
  (str (ansi-char (ansi-extra v)) string))

(defn color [string type color]
  {:pre [(find ansi-position type)
         (find ansi-colors color)]}
  (str (ansi-char (str (ansi-position type)
                       (ansi-colors color)))
       string))

(defn colorize [string opts]
  (str (reduce (fn [cur [k v]]
                 (case k
                   (:bg :fg)
                   (color cur k v)
                   :extra (reduce extra cur v)))
               string opts)
       (extra nil :reset)))
