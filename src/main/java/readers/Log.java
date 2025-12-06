package readers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Log {

    public static Logger logger(){
        return LogManager.getLogger(Thread.currentThread().getStackTrace()[3].getClassName());
    }

    public static void info(String ... message){

        logger().info(String.join(" " , message));
    }
    public static void error(String ... message){
        logger().error(String.join(" " , message));
    }
    public static void warn(String ... message){
        logger().warn(String.join(" " , message));
    }
    public static void debug(String ... message){
        logger().debug(String.join(" " , message));
    }
}
