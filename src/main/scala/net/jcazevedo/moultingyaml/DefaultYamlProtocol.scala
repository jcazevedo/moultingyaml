package net.jcazevedo.moultingyaml

trait DefaultYamlProtocol
  extends BasicFormats
  with StandardFormats
  with CollectionFormats
  with ProductFormats
  with AdditionalFormats

object DefaultYamlProtocol extends DefaultYamlProtocol
