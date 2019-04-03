import java.util
import java.util._

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.TopicPartition
import org.slf4j.LoggerFactory

import scala.util.control.Breaks
import grizzled.slf4j.Logger


object consumerAssignSeek {

  def main(args : Array[String]) {

    val logger = Logger[this.type]

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

    //Assign and seek are mostly used to replay data or fetch a specific message

    // assign
    val partitionToReadFrom = new TopicPartition(topic, 0)
    val topicPartList = new util.ArrayList[TopicPartition]
    topicPartList.add(partitionToReadFrom)

    consumer.assign(topicPartList)
    //seek

    consumer.seek(partitionToReadFrom, 15L)

    val numberOfMessagesToRead = 5
    var keepOnReading = true
    var numberOfMessagesSoFar = 0
    val looper = new Breaks


    // poll for new data
    while (keepOnReading) {
      val records: ConsumerRecords[String,String] = consumer.poll(100) // new in kafka 2.0.0
      import scala.collection.JavaConversions._

      looper.breakable {
        for (rec <- records) {
          println("Key: " + rec.key + "  Value: " + rec.value())
          println("Partition: " + rec.partition() + "  Offset: " + rec.offset())
          numberOfMessagesSoFar += 1
          if (numberOfMessagesSoFar >= numberOfMessagesToRead) {
            keepOnReading = false
            looper.break()

          }
        }
      }
        //TODO : processing of data


    }
    logger.info("finished application")

    // end of while

  }

}
