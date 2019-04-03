import java.time.Duration
import java.util
import java.util._

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecord, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import org.slf4j.Logger

object consumerDemo {

  def main(args : Array[String]) {

    println("consumer demo")

    val Logger = LoggerFactory.getLogger(consumerDemo.getClass.getName)
    Logger.info("Logging data") // not working

    // check status of the consumer-group
    //kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group my-fourth-app --describe

    val bootStrapServer = "127.0.0.1:9092"
    val groupId = "my-fourth-app"
    val topic = "first_topic"

    val topicList: List[String] = new ArrayList[String]
    topicList.add(topic)

    val consumerProperties = new Properties()

        // Check the properties from Kafka documentation - https://kafka.apache.org/documentation/#consumerconfigs

   /* consumerProperties.put("bootstrap.servers","127.0.0.1:9092")
    consumerProperties.put("key.deserializer","")
    consumerProperties.put("value.deserializer","")
    consumerProperties.put("group.id","")
    */

    consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092")
    consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
    consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
    consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG,groupId)
    consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest") // or latest or none

    //create consumer

    val consumer = new KafkaConsumer[String, String](consumerProperties)

    //subscribe to topic

    consumer.subscribe(topicList)
    // OR consumer.subscribe(Arrays.asList(topic)

    // poll for new data
    while (true) {
      val records: ConsumerRecords[String,String] = consumer.poll(100) // new in kafka 2.0.0
      import scala.collection.JavaConversions._

      for ( rec <- records){
        println("Key: " + rec.key + "  Value: " + rec.value() )
        println("Partition: " + rec.partition() + "  Offset: " + rec.offset() )

      }
        //TODO : processing of data


    } // end of while

  }

}
