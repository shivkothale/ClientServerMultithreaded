package primeService.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import primeService.server.AllPrimeQueries;
import primeService.util.CheckPrime;
import primeService.util.MyLogger;
import primeService.util.MyLogger.DebugLevel;

public class PrimeServerWorker implements Runnable{
    
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    public String clientUserName;
    public List<Integer>query=new ArrayList<>();
    MyLogger logger=MyLogger.getLogger();
    
    @Override
    public String toString() {
        return clientUserName + ": query=" + query + "]";
    }


    public PrimeServerWorker(Socket socketIn) {

        try{
            socket=socketIn;
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            logger.writeMessage("INFO: New Thread initialized", DebugLevel.INFO);
            //clientUserName=bufferedReader.readLine();
        }catch (IOException e){
            closeEverything();
        }
    }
    
    
    public void closeEverything() {
        logger.writeMessage("INFO: PrimeServerWorker closing socket, bufferedReader, bufferedWriter", DebugLevel.INFO);
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
                bufferedWriter = null; // Set to null after closing
            }
            if (socket != null) {
                socket.close();
            }
            logger.closeWriter();
        } catch (IOException e) {
            e.printStackTrace();
            logger.closeWriter();
        }
    }


    @Override
    public void run() {
        String message;
        logger.writeMessage("INFO: Running new client",DebugLevel.INFO);
        try{
        while((message = bufferedReader.readLine()) != null ){
            
                //message=bufferedReader.readLine(); 
                if(message!=null){
                    logger.writeMessage("DEBUG: Message Passed from client:"+message, DebugLevel.INFO);
                int clientNameStart = message.indexOf("<clientName>") + "<clientName>".length();
                int clientNameEnd = message.indexOf("</clientName>");
                int numberStart = message.indexOf("<isPrime>") + "<isPrime>".length();
                int numberEnd = message.indexOf("</isPrime>");
                clientUserName = message.substring(clientNameStart, clientNameEnd);
                logger.writeMessage("INFO: Client Username set to :"+clientUserName, DebugLevel.INFO);
                String num = message.substring(numberStart, numberEnd);
                logger.writeMessage("DEBUG: Client Username passed :"+clientUserName, DebugLevel.DEBUG);
                logger.writeMessage("DEBUG: Client Integer to check passed :"+num, DebugLevel.DEBUG);
                    int number=Integer.parseInt(num);
                    query.add(number);
                    if(number<=3){
                        bufferedWriter.write("<primeQueryResponse><intValue>" + number + "</intValue><isPrime>Invalid</isPrime></primeQueryResponse>");
                        logger.writeMessage("INFO: <primeQueryResponse><intValue>" + number + "</intValue><isPrime>Invalid</isPrime></primeQueryResponse>", DebugLevel.INFO);
                        logger.writeMessage("DEBUG: <primeQueryResponse><intValue>" + number + "</intValue><isPrime>Invalid</isPrime></primeQueryResponse>", DebugLevel.DEBUG);
                         bufferedWriter.write("\n");
                        bufferedWriter.flush();
                    }else{
                        CheckPrime checkPrime=new CheckPrime();
                        boolean check=checkPrime.checkPrime(number);
                        bufferedWriter.write("<primeQueryResponse><intValue>" + number + "</intValue><isPrime>" + (check ? "Yes" : "No") + "</isPrime></primeQueryResponse>");
                         bufferedWriter.write("\n");
                        bufferedWriter.flush();
                        logger.writeMessage("INFO: <primeQueryResponse><intValue>" + number + "</intValue><isPrime>" + (check ? "Yes" : "No") + "</isPrime></primeQueryResponse>", DebugLevel.INFO);
                        logger.writeMessage("DEBUG: <primeQueryResponse><intValue>" + number + "</intValue><isPrime>" + (check ? "Yes" : "No") + "</isPrime></primeQueryResponse>", DebugLevel.DEBUG);
                    }
                    System.out.println();
                    //if (socket.isConnected()) {
                       
                    //}    
                }
                            
            
        } 
        } catch (IOException e) {
               closeEverything();
            }
    }
     
    public void sendClosingMessage() {
        try {
            if (socket != null && !socket.isClosed() && !socket.isOutputShutdown()) {
                logger.writeMessage("INFO: ServerClosed Message Sent", DebugLevel.INFO);
                bufferedWriter.write("ServerClosing");
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            logger.closeWriter();
        }
        
    }
}