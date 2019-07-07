package net.jcazevedo.moultingyaml

import org.scalatest.{ FlatSpec, Matchers }

class AdditionalFormatsSpec extends FlatSpec with Matchers {

  case class Container[A](inner: Option[A])

  object ReaderProtocol extends DefaultYamlProtocol {
    implicit def containerReader[A: YamlFormat] = lift {
      new YamlReader[Container[A]] {
        def read(value: YamlValue) = value match {
          case YamlObject(fields) if fields.contains(YamlString("content")) =>
            Container(Some(fields(YamlString("content")).convertTo[A]))

          case _ => deserializationError("Unexpected format: " + value.toString)
        }
      }
    }
  }

  object WriterProtocol extends DefaultYamlProtocol {
    implicit def containerWriter[A: YamlFormat] = lift {
      new YamlWriter[Container[A]] {
        def write(obj: Container[A]) =
          YamlObject(YamlString("content") -> obj.inner.toYaml)
      }
    }
  }

  {
    val obj = Container(Some(Container(Some(List(1, 2, 3)))))
    val yaml =
      """content:
        |  content:
        |  - 1
        |  - 2
        |  - 3
        |""".stripMargin

    import ReaderProtocol._

    "A lifted YamlReader" should "properly read a Container[Container[List[Int]]] from YAML" in {
      yaml.parseYaml.convertTo[Container[Container[List[Int]]]] should ===(obj)
    }

    it should "throw a DeserializationException if trying to write with it" in {
      a[UnsupportedOperationException] should be thrownBy {
        obj.toYaml
      }
    }
  }

  {
    val obj = Container(Some(Container(Some(List(1, 2, 3)))))
    val yaml =
      """content:
        |  content:
        |  - 1
        |  - 2
        |  - 3
        |""".stripMargin

    import WriterProtocol._

    "A lifted YamlWriter" should "properly write a Container[Container[List[Int]]] to YAML" in {
      obj.toYaml.prettyPrint should ===(yaml)
    }

    it should "throw a DeserializationException if trying to read with it" in {
      a[UnsupportedOperationException] should be thrownBy {
        yaml.parseYaml.convertTo[Container[Container[List[Int]]]]
      }
    }
  }

  {
    case class Foo(id: Long, name: String, foos: Option[List[Foo]] = None)

    object FooProtocol extends DefaultYamlProtocol {
      implicit val fooProtocol: YamlFormat[Foo] = lazyFormat(yamlFormat3(Foo))
    }

    "The lazyFormat wrapper" should "enable recursive format definitions" in {
      val obj =
        Foo(1, "a", Some(List(
          Foo(2, "b", Some(List(
            Foo(3, "c")))),
          Foo(4, "d"))))

      val yaml = """id: 1
                     |name: a
                     |foos:
                     |- id: 2
                     |  name: b
                     |  foos:
                     |  - id: 3
                     |    name: c
                     |- id: 4
                     |  name: d
                     |""".stripMargin

      import FooProtocol._
      obj.toYaml.prettyPrint should ===(yaml)
    }
  }
}
