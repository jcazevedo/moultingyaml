package net.jcazevedo.moultingyaml

import scala.util.{ Try, Success, Failure }

/**
 * Provides the YamlFormats for the non-collection standard Scala types
 * (Options, Eithers and Tuples).
 */
trait StandardFormats {

  implicit def optionFormat[A: YF] = new YF[Option[A]] {

    def write(option: Option[A]) = option match {
      case Some(x) => x.toYaml
      case None => YamlNull
    }

    def read(value: YamlValue) = value match {
      case YamlNull => None
      case x => Some(x.convertTo[A])
    }
  }

  implicit def eitherFormat[A: YF, B: YF] = new YF[Either[A, B]] {

    def write(either: Either[A, B]) = either match {
      case Right(a) => a.toYaml
      case Left(b) => b.toYaml
    }

    def read(value: YamlValue) =
      (Try(value.convertTo[A]), Try(value.convertTo[B])) match {
        case (Success(a), Failure(_)) => Left(a)
        case (Failure(_), Success(b)) => Right(b)
        case (Success(_), Success(_)) =>
          deserializationError("Ambiguous Either value: can be read as both " +
            "Left and Right values")
        case (Failure(ea), Failure(eb)) =>
          deserializationError("Could not read Either value: " + ea.getMessage
            + " and " + eb.getMessage)
      }
  }

  implicit def tuple2Format[A: YF, B: YF] =
    new YamlFormat[(A, B)] {

      def write(t: (A, B)) =
        YamlArray(t._1.toYaml, t._2.toYaml)

      def read(value: YamlValue) = value match {
        case YamlArray(Seq(a, b)) =>
          (a.convertTo[A], b.convertTo[B])
        case x =>
          deserializationError("Expected Tuple2 as YamlArray, but got " + x)
      }
    }

  implicit def tuple3Format[A: YF, B: YF, C: YF] =
    new YamlFormat[(A, B, C)] {

      def write(t: (A, B, C)) =
        YamlArray(t._1.toYaml, t._2.toYaml, t._3.toYaml)

      def read(value: YamlValue) = value match {
        case YamlArray(Seq(a, b, c)) =>
          (a.convertTo[A], b.convertTo[B], c.convertTo[C])
        case x =>
          deserializationError("Expected Tuple3 as YamlArray, but got " + x)
      }
    }

  implicit def tuple4Format[A: YF, B: YF, C: YF, D: YF] =
    new YamlFormat[(A, B, C, D)] {

      def write(t: (A, B, C, D)) =
        YamlArray(t._1.toYaml, t._2.toYaml, t._3.toYaml, t._4.toYaml)

      def read(value: YamlValue) = value match {
        case YamlArray(Seq(a, b, c, d)) =>
          (a.convertTo[A], b.convertTo[B], c.convertTo[C], d.convertTo[D])
        case x =>
          deserializationError("Expected Tuple4 as YamlArray, but got " + x)
      }
    }

  implicit def tuple5Format[A: YF, B: YF, C: YF, D: YF, E: YF] = {
    new YamlFormat[(A, B, C, D, E)] {

      def write(t: (A, B, C, D, E)) =
        YamlArray(t._1.toYaml, t._2.toYaml, t._3.toYaml, t._4.toYaml,
          t._5.toYaml)

      def read(value: YamlValue) = value match {
        case YamlArray(Seq(a, b, c, d, e)) =>
          (a.convertTo[A], b.convertTo[B], c.convertTo[C], d.convertTo[D],
            e.convertTo[E])
        case x =>
          deserializationError("Expected Tuple5 as YamlArray, but got " + x)
      }
    }
  }

  implicit def tuple6Format[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF] = {
    new YamlFormat[(A, B, C, D, E, F)] {

      def write(t: (A, B, C, D, E, F)) =
        YamlArray(t._1.toYaml, t._2.toYaml, t._3.toYaml, t._4.toYaml,
          t._5.toYaml, t._6.toYaml)

      def read(value: YamlValue) = value match {
        case YamlArray(Seq(a, b, c, d, e, f)) =>
          (a.convertTo[A], b.convertTo[B], c.convertTo[C], d.convertTo[D],
            e.convertTo[E], f.convertTo[F])
        case x =>
          deserializationError("Expected Tuple6 as YamlArray, but got " + x)
      }
    }
  }

  implicit def tuple7Format[A: YF, B: YF, C: YF, D: YF, E: YF, F: YF, G: YF] = {
    new YamlFormat[(A, B, C, D, E, F, G)] {

      def write(t: (A, B, C, D, E, F, G)) =
        YamlArray(t._1.toYaml, t._2.toYaml, t._3.toYaml, t._4.toYaml,
          t._5.toYaml, t._6.toYaml, t._7.toYaml)

      def read(value: YamlValue) = value match {
        case YamlArray(Seq(a, b, c, d, e, f, g)) =>
          (a.convertTo[A], b.convertTo[B], c.convertTo[C], d.convertTo[D],
            e.convertTo[E], f.convertTo[F], g.convertTo[G])
        case x =>
          deserializationError("Expected Tuple7 as YamlArray, but got " + x)
      }
    }
  }
}
