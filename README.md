# colorize

A simple library to color string output with ansi-codes.

## Installation

In Leiningen:

```clojure
:dependencies [[bronsa/colorize "0.2.0"]]
```

## Usage

```clojure
(require '[colorize.ansi :refer [colorize]])
(colorize "hey" {:fg :red :bg black :extra [:bold :underline]})
```

## License

Copyright ® 2013 Bronsa

Distributed under the Eclipse Public License, the same as Clojure.
