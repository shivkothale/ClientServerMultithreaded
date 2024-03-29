package primeService.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import primeService.client.ClientMenu;
import primeService.util.MyLogger;
import primeService.util.MyLogger.DebugLevel;

public class PrimeClientWorker{
    
    private Socket socket;
    private boolean closing = false;
    private BufferedReader serverReader;
    private BufferedWriter serverWriter;
    private BufferedReader userInputReader;
    private PrintWriter userInputWriter;
    private String clientUserName;
    MyLogger logger=MyLogger.getLogger();
    public PrimeClientWorker(Socket socketIn) {
       
        try {
            socket=socketIn;
            serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            userInputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            userInputWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            
             Thread closingMessageThread = new Thread(this::listenForServerClosingMessage);
            closingMessageThread.start();
        } catch (IOException e) {
            closeEverything();
            e.printStackTrace();
        }
        
    }
    

    public PrimeClientWorker() {
    }


    public void closeEverything() {
        try {
            logger.writeMessage("INFO: Closing socket and all readers and writers", DebugLevel.INFO);
            logger.closeWriter();   
            if (userInputWriter != null) {
                userInputWriter.close();
            }
            if (userInputReader != null) {
                userInputReader.close();
            }
            if (serverWriter != null) {
                serverWriter.close();
            }
            if (serverReader != null) {
                serverReader.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    public void chechPrimeFromServer() {
        String name;
        try {
            Scanner sc=new Scanner(System.in);
            logger.writeMessage("\nINFO: Starting client socket", DebugLevel.INFO);
            BufferedReader serverResponseReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(!socket.isClosed() && socket.isConnected()){
                
                boolean flag=true;
                String response=null;
                
                while(flag){
                    ClientMenu menu=new ClientMenu();
                    menu.displayMenu(); 
                    logger.writeMessage("\nINFO: Displaying client menu", DebugLevel.INFO);
                    int key=sc.nextInt();
                    logger.writeMessage("\nDEBUG: User Input for menu input is:"+ key, DebugLevel.DEBUG);
                    
                    switch (key) {
                        case 1:
                           
                            name=setClientName(sc);
                            clientUserName=name;
                            logger.writeMessage("\nDEBUG: User Input for client username is:"+ name, DebugLevel.DEBUG);
                            logger.writeMessage("\nINFO: User Input for client username is:"+ name, DebugLevel.INFO);
                            break;
                        case 2:
                            
                            String number=checkPrimeQuery(sc);
                            String message="<primeQuery><clientName>"+clientUserName+"</clientName><isPrime>"+number+"</isPrime></primeQuery>";
                            logger.writeMessage("\nDEBUG: User Input for integer to check is:"+ number, DebugLevel.DEBUG);
                            logger.writeMessage("\nDEBUG: User Input query is:"+ message, DebugLevel.DEBUG);
                            logger.writeMessage("\nINFO: User Input for number is:"+ number, DebugLevel.INFO);
                            userInputWriter.write(message); 
                            userInputWriter.write("\n");
                            userInputWriter.flush();
                            response=serverResponseReader.readLine();
                            System.out.println(response);
                            break;
                        case 3:
                            System.out.println(response);
                            logger.writeMessage("\nDEBUG: Response for the query is:"+ response, DebugLevel.DEBUG);     
                            logger.writeMessage("\nINFO: Response for the query is:"+ response, DebugLevel.INFO);
                            break;
                        case 4:
                            logger.writeMessage("\nINFO: Closing the client", DebugLevel.INFO);
                            logger.closeWriter();
                            closing = true;
                            closeEverything();
                            serverResponseReader.close();
                            System.exit(0);
                            break;

                }
                
            
            }
            sc.close();
            serverResponseReader.close();
            }
        }catch (IOException e){
            closeEverything();
        }

    }

    private String checkPrimeQuery(Scanner sc) {
        System.out.println("Enter the number to check");
        int name=sc.nextInt();
        logger.writeMessage("\nDEBUG: User Iput for integer is :" +name, DebugLevel.DEBUG);
        return String.valueOf(name);
    }

    private String setClientName(Scanner sc) {
        System.out.println("Enter Client Name");
        String name=sc.next();
        logger.writeMessage("\nDEBUG: User Iput for integer is :" +name, DebugLevel.DEBUG);
        return name;

    }
    public void listenForServerClosingMessage() {
        try {
            String message;
            while (!closing && (message = serverReader.readLine()) != null) {
                if (message.equals("ServerClosing")) {
                    logger.writeMessage("INFO: Closing the client", DebugLevel.INFO);
                    System.out.println("Server is closing. Closing client connection...");
                    closing = true;
                    closeEverything();
                    break; // Exit the loop when the server closing message is received
                }
            }
        // Close resources after the loop
        serverReader.close();
        System.exit(0);

        } catch (IOException e) {
            //e.printStackTrace();
            logger.closeWriter();
        }
    }
   


    
    
}
