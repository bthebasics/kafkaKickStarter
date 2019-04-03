import java.util
//import org.apache.log4j.Logger
import java.util._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord, Producer}
import java.util.Properties
import java.util.Scanner
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.connect.json.JsonSerializer
//Ref : http://wpcertification.blogspot.com/2016/12/sending-and-receiving-json-messages-in.html

case class Contact(contactId:Int, firstName: String, lastName: String)

object kafkaProducerJson {

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
    producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,"org.apache.kafka.connect.json.JsonSerializer")

    //create the producer
    //val producer = new KafkaProducer[String, String](producerProperties)

    val producer = new KafkaProducer(producerProperties)

    import com.fasterxml.jackson.databind.ObjectMapper
    val objectMapper = new ObjectMapper

    val contact = new Contact(1, "amit", "shah")
    val jsonNode = objectMapper.valueToTree(contact)


    //create producer record
    val record = new ProducerRecord[String,JsonNode](topic, jsonNode)
    //send the data -asyncronus
    producer.send(record)

    //flush data
    producer.flush()

    //flush and close producer
    producer.close()


  }

}
