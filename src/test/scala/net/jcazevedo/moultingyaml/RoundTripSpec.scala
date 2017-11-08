package net.jcazevedo.moultingyaml

import java.io.File
import java.net.URLDecoder

import scala.io.Source

import org.specs2.mutable.Specification

import net.jcazevedo.moultingyaml.defaultParser._

class RoundTripSpec extends Specification {
  def getResourceURL(resource: String): String =
    URLDecoder.decode(getClass.getResource(resource).getFile, "UTF-8")

  "The parsing of YAMLs" should {
    "work in a round trip fashion" in {
      val files = new File(getResourceURL("/examples")).listFiles()
      forall(files) { file =>
        val yamls = Source.fromFile(file).mkString.parseYamls
        forall(yamls) { innerYaml =>
          innerYaml.prettyPrint.parseYaml mustEqual innerYaml
        }
      }
    }
  }
}
