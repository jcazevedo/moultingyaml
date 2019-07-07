# MoultingYAML

[![Join the chat at https://gitter.im/jcazevedo/moultingyaml](https://badges.gitter.im/jcazevedo/moultingyaml.svg)](https://gitter.im/jcazevedo/moultingyaml?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/jcazevedo/moultingyaml.svg?branch=master)](https://travis-ci.org/jcazevedo/moultingyaml)
[![Coverage Status](https://coveralls.io/repos/github/jcazevedo/moultingyaml/badge.svg?branch=master)](https://coveralls.io/github/jcazevedo/moultingyaml?branch=master)
[![License](https://img.shields.io/dub/l/vibe-d.svg)](https://raw.githubusercontent.com/jcazevedo/moultingyaml/master/LICENSE.md)

[![Maven Central](https://img.shields.io/maven-central/v/net.jcazevedo/moultingyaml_2.10.svg?label=latest%20release%20for%202.10)](https://maven-badges.herokuapp.com/maven-central/net.jcazevedo/moultingyaml_2.10)
[![Maven Central](https://img.shields.io/maven-central/v/net.jcazevedo/moultingyaml_2.11.svg?label=latest%20release%20for%202.11)](https://maven-badges.herokuapp.com/maven-central/net.jcazevedo/moultingyaml_2.11)
[![Maven Central](https://img.shields.io/maven-central/v/net.jcazevedo/moultingyaml_2.12.svg?label=latest%20release%20for%202.12)](https://maven-badges.herokuapp.com/maven-central/net.jcazevedo/moultingyaml_2.12)
[![Maven Central](https://img.shields.io/maven-central/v/net.jcazevedo/moultingyaml_2.12.svg?label=latest%20release%20for%202.13)](https://maven-badges.herokuapp.com/maven-central/net.jcazevedo/moultingyaml_2.13)

MoultingYAML is a Scala wrapper for [SnakeYAML][snakeyaml] based on
[spray-json][spray-json].

Its basic idea is to provide a simple immutable model of the [YAML][yaml]
language, built on top of SnakeYAML models, as well as a type-class based
serialization and deserialization of custom objects.

## Installation

MoultingYAML's latest release is `0.4.1` and is built against Scala 2.13, 2.12,
2.11 and 2.10.

To use it in an existing SBT project, add the following dependency to your
`build.sbt`:

```scala
libraryDependencies += "net.jcazevedo" %% "moultingyaml" % "0.4.1"
```

## Usage

In order to use MoultingYAML, bring all relevant elements into scope with:

```scala
import net.jcazevedo.moultingyaml._
import net.jcazevedo.moultingyaml.DefaultYamlProtocol._ // if you don't supply your own protocol
```

You can then parse a YAML string into its Abstract Syntax Tree (AST)
representation with:

```scala
val source = """- Mark McGwire
               |- Sammy Sosa
               |- Ken Griffey""".stripMargin
val yamlAst = source.parseYaml
```

It is also possible to print a YAML AST back to a String using the `prettyPrint`
method:

```scala
val yaml = yamlAst.prettyPrint
```

If more fine-grained control over the printed yaml is needed, it is possible to
use the configurable `print` method.  For example, to enclose everything in
double quotes:

```scala
val yaml = yamlAst.print(scalarStyle = DoubleQuoted)
```

YAML provide different scalar styles to choose from, controlled by the argument
`scalarStyle` of the `print` method. The possible values for `scalarStyle` are
`Plain`, `SingleQuoted`, `DoubleQuoted`, `Literal` and `Folded`. Refer to the
[YAML specification](http://yaml.org/spec/current.html#id2532386) for details on
each representation.

In addition, YAML also has flow styles, in order to be able to use explicit
indicators instead of indentation to denote scope. The flow style is controlled
by the `flowStyle` argument of the `print` method. The possible values for
`flowStyle` are `Flow`, `Block` and `Auto`. `Block` style uses indentation,
whereas `Flow` style relies on explicit indicators to denote scope. The `Auto`
flow style attempts to combine both the `Block` and `Flow` style within the same
document.

Scala objects can be converted to a YAML AST using the pimped `toYaml` method:

```scala
val yamlAst = List(1, 2, 3).toYaml
```

Convert a YAML AST to a Scala object with the `convertTo` method:

```scala
val myList = yamlAst.convertTo[List[Int]]
```

In order to support calling the `toYaml` and `convertTo` methods for an object of
type `T`, you need to have implicit values in scope that provide `YamlFormat[T]`
instances for `T` and all types used by `T` (directly or indirectly). You
normally do that through a YamlProtocol.

### YamlProtocol

YamlProtocols follow the same design as [spray-json][spray-json]'s
JsonProtocols, which in turn are based on [SJSON][sjson]'s. It's a type-class
based approach that connects an existing type `T` with the logic of how to
(de)serialize its instances to and from YAML.

A YamlProtocol is a bunch of implicit values of type `YamlFormat[T]`, where
each `YamlFormat[T]` contains the logic of how to convert instances of `T` to
and from YAML.

MoultingYAML comes with a `DefaultYamlProtocol`, which already covers all of
Scala's value types as well as the most important reference and collection
types. The following are types already taken care of by the
`DefaultYamlProtocol`:

* `Byte`, `Short`, `Int`, `Long`, `Float`, `Double`, `Char`, `Unit`, `Boolean`
* `String`, `Symbol`
* `BigInt`, `BigDecimal`
* `Option`, `Either`, `Tuple1` - `Tuple7`
* `List`, `Array`
* `immutable.{Map, Iterable, Seq, IndexedSeq, LinearSeq, Set, Vector}`
* `collection.{Iterable, Seq, IndexedSeq, LinearSeq, Set}`

When you want to convert types not covered by the `DefaultYamlProtocol`, you
need to provide a `YamlFormat[T]` for your custom types.

### Prodiving YamlFormats for Case Classes

If your custom type `T` is a case class then augmenting the
`DefaultYamlProtocol` with a `YamlFormat[T]` can be done using the `yamlFormatX`
helpers, where `X` stands for the number of fields in the case class:

```scala
case class Color(name: String, red: Int, green: Int, blue: Int)

object MyYamlProtocol extends DefaultYamlProtocol {
  implicit val colorFormat = yamlFormat4(Color)
}

import MyYamlProtocol._
import net.jcazevedo.moultingyaml._

val yaml = Color("CadetBlue", 95, 158, 160).toYaml
val color = yaml.convertTo[Color]
```

Example combining custom YamlFormats:

```scala
case class Color(name: String, red: Int, green: Int, blue: Int)
case class Palette(name: String, colors: Option[List[Color]] = None)

object PaletteYamlProtocol extends DefaultYamlProtocol {
  implicit val colorFormat = yamlFormat4(Color)
  implicit val paletteFormat = yamlFormat2(Palette)
}

import PaletteYamlProtocol._
import net.jcazevedo.moultingyaml._

val yaml = """name: My Palette
             |colors:
             |- name: color 1
             |  red: 1
             |  green: 1
             |  blue: 1
             |- name: color 2
             |  red: 2
             |  green: 2
             |  blue: 2
             |""".stripMargin.parseYaml
val palette = yaml.convertTo[Palette]
```

If you explicitly declare the companion object for your case class the notation
above will stop working. You'll have to explicitly refer to the companion
object's `apply` method to fix this:

```scala
case class Color(name: String, red: Int, green: Int, blue: Int)
object Color

object MyYamlProtocol extends DefaultYamlProtocol {
  implicit val colorFormat = yamlFormat4(Color.apply)
}
```

If your case class has a type parameter the `yamlFormat` methods can also help
you. However, there is a little more boilerplate required as you need to add
context bounds for all type parameters and explicitly refer to the case classes
apply method as in this example:

```scala
case class NamedList[A](name: String, items: List[A])

object MyYamlProtocol extends DefaultYamlProtocol {
  implicit def namedListFormat[A: YamlFormat] = yamlFormat2(NamedList.apply[A])
}
```

### NullOptions

As in [spray-json][spray-json], the `NullOptions` trait supplies an alternative
rendering mode for optional case class members. Normally optional members that
are undefined (`None`) are not rendered at all. By mixing in this trait into
your custom YamlProtocol you can enforce the rendering of undefined members as
`null`. (Note that this only affects YAML writing, MoultingYAML will always read
missing optional members as well as `null` optional members as `None`)

### Providing YamlFormats for other Types

To provide (de)serialization logic for types that aren't case classes, one has
to define the `write` and `read` methods of `YamlFormat`. Here is one example:

```scala
class Color(val name: String, val red: Int, val green: Int, val blue: Int)

object MyYamlProtocol extends DefaultYamlProtocol {
  implicit object ColorYamlFormat extends YamlFormat[Color] {
    def write(c: Color) =
      YamlArray(
        YamlString(c.name),
        YamlNumber(c.red),
        YamlNumber(c.green),
        YamlNumber(c.blue))

    def read(value: YamlValue) = value match {
      case YamlArray(
        Vector(
          YamlString(name),
          YamlNumber(red: Int),
          YamlNumber(green: Int),
          YamlNumber(blue: Int))) =>
        new Color(name, red, green, blue)
      case _ => deserializationError("Color expected")
    }
  }
}

import MyYamlProtocol._

val yaml = new Color("CadetBlue", 95, 158, 160).toYaml
val color = yaml.convertTo[Color]
```

This serializes `Color` instances as a YAML array. Another way would be to
serialize `Color`s as YAML mappings, which are called YAML objects in
MoultingYAML:

```scala
object MyYamlProtocol extends DefaultYamlProtocol {
  implicit object ColorYamlFormat extends YamlFormat[Color] {
    def write(c: Color) = YamlObject(
      YamlString("name") -> YamlString(c.name),
      YamlString("red") -> YamlNumber(c.red),
      YamlString("green") -> YamlNumber(c.green),
      YamlString("blue") -> YamlNumber(c.blue)
    )
    def read(value: YamlValue) = {
      value.asYamlObject.getFields(
        YamlString("name"),
        YamlString("red"),
        YamlString("green"),
        YamlString("blue")) match {
        case Seq(
          YamlString(name),
          YamlNumber(red: Int),
          YamlNumber(green: Int),
          YamlNumber(blue: Int)) =>
          new Color(name, red, green, blue)
        case _ => deserializationError("Color expected")
      }
    }
  }
}
```

## Credits

Most of MoultingYAML's type-class (de)serialization code was inspired by
[spray-json][spray-json], by **Mathias Doenitz**. [spray-json][spray-json] was,
in turn, inspired by the [SJSON][sjson] library by **Debasish Ghosh**. Both
deserve credits here.

## License

MoultingYAML is licensed under the [MIT](http://opensource.org/licenses/MIT)
license. See `LICENSE.md` for details.

## Contributions

Feedback and contributions to the project are very welcome. Use
[GitHub's issue tracker](https://github.com/jcazevedo/moultingyaml/issues) to
report any issues you might have when using the project. Submit code
contributions via
[GitHub's pull requests](https://github.com/jcazevedo/moultingyaml/pulls).

[sjson]: https://github.com/debasishg/sjson
[snakeyaml]: https://bitbucket.org/asomov/snakeyaml
[spray-json]: https://github.com/spray/spray-json
[yaml]: http://yaml.org/
