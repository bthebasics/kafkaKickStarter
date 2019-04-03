import org.slf4j.Logger
import org.slf4j.LoggerFactory
import grizzled.slf4j.Logging
/*
class loggingDemo {
  val logger = LoggerFactory.getLogger(classOf[loggingDemo])
  logger.info("hello world")
}
*/

object loggingDemo extends  App with Logging{

  //val p = new loggingDemo
  logger.error("Hello, world")
}
