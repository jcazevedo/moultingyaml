package net.jcazevedo.moultingyaml

/**
 * Provides all the predefined YamlFormats.
 */
trait DefaultYamlProtocol
  extends BasicFormats
  with StandardFormats
  with CollectionFormats
  with ProductFormats
  with AdditionalFormats
  with CollectionReaders
  with CollectionWriters

object DefaultYamlProtocol extends DefaultYamlProtocol
