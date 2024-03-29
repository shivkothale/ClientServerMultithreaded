package primeService.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

import primeService.socket.PrimeServerSocket;
import primeService.util.MyLogger;
import primeService.util.MyLogger.DebugLevel;

public class ServerDriver {
    private ServerSocket serverSocket;
    public ServerDriver(int port){
        try {
            serverSocket=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startServer(){
        MyLogger logger=MyLogger.getLogger();
        PrimeServerSocket primeServerSocket=new PrimeServerSocket(serverSocket);
        logger.writeMessage("Started the serverSocket", DebugLevel.INFO);
        primeServerSocket.startServer();
        
        
    }
    
}
