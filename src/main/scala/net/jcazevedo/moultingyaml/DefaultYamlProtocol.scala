package net.jcazevedo.moultingyaml

trait DefaultYamlProtocol
  extends BasicFormats
  with StandardFormats
  with CollectionFormats
  with ProductFormats

object DefaultYamlProtocol extends DefaultYamlProtocol
