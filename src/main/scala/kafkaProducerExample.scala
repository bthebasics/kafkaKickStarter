import java.util

import org.apache.kafka.clients.consumer._
import org.apache.kafka.common.TopicPartition
//import org.apache.log4j.Logger
import java.util._

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

object kafkaProducerExample {

  // kafka-console-consumer.bat --bootstrap-server 127.0.0.1:9092 --topic first_topic  to check records being produced

  def main(args: Array[String]): Unit = {

    val bootstrapServer = "127.0.0.1:9092"

    val topic = "test1"
    val topicList:List[String] = new util.ArrayList[String]
    topicList.add(topic)

    // Set the Producer properties
    val producerProperties: Properties = new Properties()
    producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer)
    producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
    producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringSerializer")

    //create the producer
    val producer = new KafkaProducer[String, String](producerProperties)

    //create producer record
    val record = new ProducerRecord[String,String]("first_topic","hello world" )
    //send the data -asyncronus
    producer.send(record)

    //flush data
    producer.flush()

    //flush and close producer
    producer.close()


  }

}
