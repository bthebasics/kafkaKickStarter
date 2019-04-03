import java.util

import org.slf4j.{Logger, LoggerFactory}
import org.apache.kafka.clients.producer.{Callback, RecordMetadata}
//import org.apache.log4j.Logger
import java.util._

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

object kafkaProducerExampleCallback {

  def main(args: Array[String]): Unit = {

    val Logger = LoggerFactory.getLogger(kafkaProducerExampleCallback.getClass)
    val bootstrapServer = "127.0.0.1:9092"

    val topic = "first_topic"
    val topicList:List[String] = new util.ArrayList[String]
    topicList.add(topic)

    // Set the Producer properties
    val producerProperties: Properties = new Properties()

    producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")

    //create the producer
    val producer = new KafkaProducer[String, String](producerProperties)

    val i = 0;
    for ( i <- (1 to 10 )) {
      println(i)
      val record = new ProducerRecord[String,String]("first_topic","hello world" + i.toString())
      //send the data -asyncronus
      producer.send(record, new Callback {
        override def onCompletion(metadata: RecordMetadata, exception: Exception): Unit = {
          if ( exception == null) {
            Logger.info("hello")
            // record was successfully sent
            println("Received new Metadta : Topic : " + metadata.topic() + " Partition: " + metadata.partition() + " Offset: " + metadata.offset())
          } else {
            exception.printStackTrace()
          }


        }
      })
    }

    //create producer record


    //flush data
    producer.flush()

    //flush and close producer
    producer.close()


  }

}
