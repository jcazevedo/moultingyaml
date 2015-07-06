package net.jcazevedo.moultingyaml

import java.io.File
import java.net.URLDecoder
import org.specs2.mutable.Specification
import scala.io.Source

class RoundTripSpec extends Specification {
  def getResourceURL(resource: String): String =
    URLDecoder.decode(getClass.getResource(resource).getFile, "UTF-8")

  "The parsing of YAMLs" should {
    "work in a round trip fashion" in {
      val files = new File(getResourceURL("/examples")).listFiles()
      forall(files) { file =>
        val yaml = Source.fromFile(file).mkString.parseYaml
        yaml.prettyPrint.parseYaml mustEqual yaml
      }
    }
  }
}
