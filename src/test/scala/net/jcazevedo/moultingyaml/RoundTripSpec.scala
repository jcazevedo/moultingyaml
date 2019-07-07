package net.jcazevedo.moultingyaml

import java.io.File
import java.net.URLDecoder

import org.scalatest.{ FlatSpec, Inspectors, Matchers }
import scala.io.Source

class RoundTripSpec extends FlatSpec with Matchers with Inspectors {
  def getResourceURL(resource: String): String =
    URLDecoder.decode(getClass.getResource(resource).getFile, "UTF-8")

  "The parsing of YAMLs" should "work in a round trip fashion" in {
    val files = new File(getResourceURL("/examples")).listFiles()
    forAll(files) { file =>
      val yamls = Source.fromFile(file).mkString.parseYamls
      forAll(yamls) { innerYaml =>
        innerYaml.prettyPrint.parseYaml should ===(innerYaml)
      }
    }
  }
}
