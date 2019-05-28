package com.muc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import org.apache.commons.lang3.StringUtils;;
import java.util.HashSet;
public class ServerWorker extends Thread{
    private final Socket clientSocket;
    private final Server server;
    private String login = null;
    private OutputStream outputStream;
    private HashSet<String> topicSet=new HashSet<>();

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }
    //collect the workerlist and catch exceptions
    @Override
    public void run(){
        try{
            handleClientSocket();
        }catch(IOException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
//

    /**
     * allow for a max of 3 connections at a time
     * when someone closes the connection they logoff
     * when someone joins they're counted in the connections
     * allows for people to talk to each other
     * @throws IOException
     * @throws InterruptedException
     */

    private void handleClientSocket()throws IOException, InterruptedException {
        this.outputStream = clientSocket.getOutputStream();
        InputStream inputStream=clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line=reader.readLine())!=null){
            String[] tokens= line.split(" ");
            if (tokens != null && tokens.length > 0) {

                String cmd = tokens[0];

                if ("logoff".equals(cmd) || "quit".equalsIgnoreCase(line)) {
                    handeLogoff();
                    break;
                } else if ("login".equalsIgnoreCase(cmd)){
                    handleLogin(outputStream, tokens);
                }else if ("msg".equalsIgnoreCase(cmd)){
                    String[] tokensMsg = StringUtils.split(line, null, 3);
                    handleMessage(tokens);
                }else if ("join".equalsIgnoreCase(cmd)){
                    handleJoin(tokens);
                }else if ("leave".equalsIgnoreCase(cmd)){
                    handleLeave(tokens);
                }
                else {
                    String msg = "unknown" + cmd + "\n";
                    outputStream.write(msg.getBytes());
                }
// String msg = "You typed: " + line;
// outputStream.write(msg.getBytes());
            }
        }
        clientSocket.close();


    }

    /**
     * handles connection closure
     * @param tokens
     */
    private void handleLeave(String[] tokens){
        if(tokens.length > 1){
            String topic = tokens[1];
            topicSet.remove(topic);
        }
    }

    /**
     * checks to make sure tokens are handled appropriately
     * @param topic
     * @return
     */
    public boolean isMemberOfTopic(String topic){
        return topicSet.contains(topic);
    }

    /**
     * allows for the connection of worker if they aren't over the limit
     * @param tokens
     */
    private void handleJoin(String[] tokens){
        if(tokens.length > 1){
            String topic = tokens[1];
            topicSet.add(topic);

        }
    }
    // format:"msg" "login" msg...

    /**
     * sends message to the different people
     * @param tokens = people
     * @throws IOException
     */
    private void handleMessage(String[] tokens) throws IOException{
        String sendTo=tokens[1];
        String body = tokens[2];

        boolean isTopic = sendTo.charAt(0)=='#';

        List<ServerWorker> workerList = server.getWorkerList();
        for(ServerWorker worker : workerList){
            if(isTopic){
                if(worker.isMemberOfTopic(sendTo)){
                    String outMsg = "msg" + login + " " + body + "\n";
                    worker.send(outMsg);
                }
            }
            if(sendTo.equalsIgnoreCase(worker.getLogin())){
                String outMsg = "msg" + login + " " + body + "\n";
                worker.send(outMsg);
            }
        }
    }

    /**
     * if connection is closed it prints worker as offline
     * @throws IOException
     */
    private void handeLogoff()throws IOException{
        server.removeWorker(this);
        List<ServerWorker> workerList = server.getWorkerList();
// send other online users current
        String onlineMsg= "offline: " + login +"\n";
        for(ServerWorker worker : workerList){
            if (!login.equals(worker.getLogin())){
                worker.send(onlineMsg);
            }
        }
        clientSocket.close();
    }
    public String getLogin(){
        return login;
    }

    /**
     * creates parameters to sign in with
     * prints logged in workers as online
     * prints error if the login is incorrect or malfunctioning
     * @param outputStream
     * @param tokens
     * @throws IOException
     */
    public void handleLogin(OutputStream outputStream, String[] tokens)throws IOException{
        if (tokens.length == 3){
            String login = tokens[1];
            String password = tokens[2];

            if((login.equals("guest") && password.equals("guest")) || (login.equals("kyle") && password.equals("yes"))) {
                String msg = "ok login\n";
                outputStream.write(msg.getBytes());
                this.login=login;
                System.out.println("user logged in succesfully: " + login);

                List<ServerWorker> workerList = server.getWorkerList();
// send current user all other online logins
                for(ServerWorker worker : workerList) {
                    if(!login.equals(worker.getLogin())) {
                        if (worker.getLogin() != null) {
                            String msg2 = "online: " + worker.getLogin();
                            send(msg2);
                        }
                    }
                }
// send other online users current
                String onlineMsg= "online: " + login +"\n";
                for(ServerWorker worker : workerList){
                    if (!login.equals(worker.getLogin())){
                        worker.send(onlineMsg);
                    }
                }
            }
        }else{
            String msg ="error login";
            outputStream.write(msg.getBytes());
            System.err.println("login failed for " + login);
        }
    }



    private void send(String msg)throws IOException{
        if(login!= null) {
            outputStream.write(msg.getBytes());//
        }
    }

}



