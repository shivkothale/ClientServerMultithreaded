package primeService.client;


import primeService.socket.PrimeClientSocket;

public class ClientDriver{
    private int port;
    private String host;
    public ClientDriver(int portIn, String hostIn) {
        port=portIn;
        host=hostIn;
    }
    public void connectToServer(){
            PrimeClientSocket primeClientSocket=new PrimeClientSocket(host,port);
            primeClientSocket.chechPrimeFromServer();

       

    }
    
}
