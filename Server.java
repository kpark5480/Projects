package com.muc;

import com.muc.ServerWorker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * create an arraylist of all people that are logging on
 * add members to the list when they connect with different logins

 */
public class Server extends Thread{
    private final int serverPort;
    private ArrayList<ServerWorker> workerList= new ArrayList<>();
    public Server(int serverPort){
        this.serverPort=serverPort;
    }
    public List<ServerWorker> getWorkerList(){
        return workerList;
    }
    @Override
    public void run(){
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (true) {

                System.out.println("About to accept client connection...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted client connection from" + clientSocket);
                ServerWorker worker = new ServerWorker( this,clientSocket);
                workerList.add(worker);
                worker.start();

            }
        }

        catch(IOException e){
            e.printStackTrace();
        }
    }
    //remove worker when they logoff
    public void removeWorker(ServerWorker serverWorker){
        workerList.remove(serverWorker);
    }
}
