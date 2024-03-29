package primeService.driver;

import primeService.client.ClientDriver;
import primeService.server.ServerDriver;
import primeService.util.MyLogger;
import primeService.util.MyLogger.DebugLevel;

public class PrimeDriver {
    public static void main(String[] args) {
        MyLogger logger=MyLogger.getLogger();
        logger.setDebugValue(Integer.parseInt(args[2]));
        logger.startFileWriter("log.txt");
        if(!args[0].isEmpty() && args[1].isEmpty() && !args[2].isEmpty() && !args[3].isEmpty()){
            ServerDriver serverDriver=new ServerDriver(Integer.parseInt(args[0]));
            logger.writeMessage("Created the instance of server Driver and starting the server on port :"+args[0], DebugLevel.INFO);
            serverDriver.startServer();
        }else if(!args[0].isEmpty() && !args[1].isEmpty() && !args[2].isEmpty() && !args[3].isEmpty()){
            ClientDriver clientDriver=new ClientDriver(Integer.parseInt(args[0]),args[1]);
            logger.writeMessage("Created the instance of client Driver and starting the client on port"+args[0]+"and on host: "+args[1], DebugLevel.INFO);
            clientDriver.connectToServer();
        }
        // System.out.println("out");
    }
}
