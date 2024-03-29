package primeService.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import primeService.client.Client;
import primeService.server.AllPrimeQueries;
import primeService.server.ServerMenu;
import primeService.util.MyLogger;
import primeService.util.MyLogger.DebugLevel;

public class PrimeServerSocket {
     private ServerSocket serverSocket;
     private Socket socket;
     private  AllPrimeQueries allPrimeQueries;
    MyLogger logger=MyLogger.getLogger();
     public PrimeServerSocket(ServerSocket serverSocketIn) {
        serverSocket = serverSocketIn;
        allPrimeQueries=new AllPrimeQueries();
    }
   
    public void startServer(){
       
        logger.writeMessage("Server Menu Displayed using a thread that continuously runs", DebugLevel.INFO);
        Thread menuThread = new Thread(this::printMenu);
        menuThread.start();

        try{
            while(!serverSocket.isClosed()){
                socket=serverSocket.accept();
                System.out.println("A new client is connected");
                logger.writeMessage("\nINFO: New Client accepted on the server port", DebugLevel.INFO);
                PrimeServerWorker primeServerWorker=new PrimeServerWorker(socket);
                allPrimeQueries.getQueries().add(primeServerWorker);
                logger.writeMessage("\nINFO: Starting the new client thread", DebugLevel.INFO);
                Thread thread=new Thread(primeServerWorker);
                thread.start();
            }          
                    //printMenu();
        }catch(IOException exception){
            logger.writeMessage("\nINFO: Caught a IOException check the error with debug value", DebugLevel.INFO);
            closeServerSocket();
        }catch(NullPointerException e){
            System.out.println("Null Pointer");
            closeServerSocket();
        }
        
    }
    public void printMenu(){
            while(true){
                ServerMenu menu=new ServerMenu();
            menu.displayMenu();
            Scanner sc =new Scanner(System.in);
            int key=sc.nextInt();
            boolean flag=true;
                logger.writeMessage("\nINFO: Getting inputs on server to test the server menu ", DebugLevel.INFO);
                switch (key) {
                    case 1:
                        System.out.println("Enter the username to check queries");
                        String name=sc.next();
                        logger.writeMessage("\nDEBUG: Name:"+name, DebugLevel.DEBUG);
                        getcurrentClientandQuery(name);
                        break;
                    case 2:
                        getAllClientQuries();
                        break;
                    case 3:
                        closeServerSocket();
                        System.exit(0);
        }   
            }
            
}
public void getcurrentClientandQuery(String name) {
    AllPrimeQueries allPrimeQueries=new AllPrimeQueries();
    for(PrimeServerWorker c:allPrimeQueries.getQueries()){
        if(c.clientUserName.equals(name)){
            logger.writeMessage("DEBUG: query : "+c.toString(), DebugLevel.DEBUG);
            System.out.println(c.toString());
            break;
        }
    }
    
}

public void getAllClientQuries() {
    AllPrimeQueries allPrimeQueries=new AllPrimeQueries();
    for(PrimeServerWorker c:allPrimeQueries.getQueries()){
            logger.writeMessage("DEBUG: query : "+c.toString(), DebugLevel.DEBUG);
            System.out.println(c.toString());
    }
}
public void closeAllCLients(){
    AllPrimeQueries allPrimeQueries=new AllPrimeQueries();
    for(PrimeServerWorker c:allPrimeQueries.getQueries()){
            logger.writeMessage("INFO: Closing connection for "+c.clientUserName, DebugLevel.DEBUG);
           c.closeEverything();
    }
}
   
    public void closeServerSocket(){
        broadcastClosingMessage();
        try{
            if(serverSocket!=null){
                serverSocket.close();
                logger.closeWriter();
                System.exit(0);
            }
            if(socket!=null){
                socket.close();
            }
            logger.closeWriter();
        }catch (IOException e){
            e.printStackTrace();
            logger.closeWriter();
        }
    }

    public void broadcastClosingMessage() {
         
        AllPrimeQueries allPrimeQueries=new AllPrimeQueries();
        for (PrimeServerWorker worker :allPrimeQueries.getQueries()) {
            logger.writeMessage("INFO: send ServerClosed message to client :"+worker.clientUserName, DebugLevel.INFO);
            worker.sendClosingMessage();
        }
    }
}
