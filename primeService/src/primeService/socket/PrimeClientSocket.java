package primeService.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import primeService.util.MyLogger;
import primeService.util.MyLogger.DebugLevel;

public class PrimeClientSocket {
    private Socket socket=null;   
    MyLogger logger=MyLogger.getLogger(); 

    public PrimeClientSocket(String host, int port) {
        logger.writeMessage("INFO: Starting new client at host:"+host+" port: "+port, DebugLevel.INFO);
        logger.writeMessage("DEBUG: PrimeClientSocet -> Host:"+host+" port: "+port, DebugLevel.DEBUG);
        try {
            socket=new Socket(host,port);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            closeEverything(socket);
            e.printStackTrace();
        }
       
    }

    private void closeEverything(Socket socket) {
        try{
            if(socket!=null){
                socket.close();
                logger.closeWriter();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void chechPrimeFromServer() {
        PrimeClientWorker primeClientWorker=new PrimeClientWorker(socket);
        primeClientWorker.chechPrimeFromServer();
    }
    
}
