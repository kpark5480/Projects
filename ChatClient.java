package com.muc;
import org.apache.commons.lang3.StringUtils;;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;

public class ChatClient {
    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader bufferedIn;

    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private ArrayList<MessageListener> messageListeners = new ArrayList<>();
    public ChatClient(String serverName, int serverPort){
        this.serverName=serverName;
        this.serverPort=serverPort;


    }

    /**
     * takes ServerWorker and turns in into a client side list
     * @param args
     * @throws IOException
     */
    public static void main(String[] args)throws IOException{
        ChatClient client = new ChatClient("localhost",8818);
        client.addUserStatusListener(new UserStatusListener() {
            @Override
            public void onLine(String login) {
                System.out.println("ONLINE: " + login);
            }

            @Override
            public void offline(String login) {
                System.out.println("OFFLINE: " + login);
            }
        });
        client.addMessageListener((new MessageListener() {
            @Override
            public void onMessage(String fromLogin, String msgBody) {
                System.out.println("You got a message from " + fromLogin + " ====>" + msgBody);
            }
        }));
    if(!client.connect()){
        System.err.println("Connect failed.");
        }else{
        System.out.println("Connect successful");
       if( client.login("guest", "guest")){
           System.out.println("login succesful");
           client.msg("KYLE", "hello World!");
       }else{
           System.err.println("login failed");
       }
client.logoff();
    }
    }

    /**
     * sends messages to worker in chatclient
     * @param sendTo
     * @param msgBody
     * @throws IOException
     */
    public void msg (String sendTo, String msgBody)throws IOException{
        String cmd = "msg " + sendTo + " " + msgBody + "\n";
        serverOut.write(cmd.getBytes());
    }

    /**
     * checks to make sure login is handled appropriately
     * @param login guest/kyle
     * @param password guest/yes
     * @return
     * @throws IOException
     */
    public boolean login(String login, String password) throws IOException{
        String cmd = "login " + login + " " + password + "\n";
        serverOut.write(cmd.getBytes());

        String response=bufferedIn.readLine();
        System.out.println("Response line:" + response);

        if("ok login".equalsIgnoreCase(response)){
           startMessageReader();
            return true;

        }else{
            return false;
        }

    }

    /**
     * logoffs worker when connection is ended or when logoff is typed
     * @throws IOException
     */
    private void logoff()throws IOException{
        String cmd = "logoff\n";
        serverOut.write(cmd.getBytes());
    }

    /**
     * allows for messages to be sent
     */
    private void startMessageReader(){
        Thread t = new Thread(){
            @Override
            public void run(){
                readMessageLoop();
            }
        };
        t.start();
    }

    /**
     * allows for a certain amount of workers to be msged
     *
     */
    private void readMessageLoop(){
        try{
            String line;
            while((line=bufferedIn.readLine())!=null) {
                String[] tokens = line.split(" ");
                if (tokens != null && tokens.length > 0) {

                    String cmd = tokens[0];
                    if ("online".equalsIgnoreCase(cmd)) {
                        handleOnline(tokens);
                    } else if ( "offline".equalsIgnoreCase(cmd)){
                        handleOffline(tokens);
                    }else if ("msg".equalsIgnoreCase(cmd)){
                        String[] tokensMsg = StringUtils.split(line, null, 3);
                        handleMessage(tokensMsg);
                    }
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
            try {
                socket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * next four methods are the same as serverworker
     * @param tokensMsg
     */
    private void handleMessage(String[] tokensMsg){
        String login = tokensMsg[1];
        String msgBody = tokensMsg[2];

        for(MessageListener listener : messageListeners){
            listener.onMessage(login,msgBody);
        }
    }
    private void handleOffline(String[] tokens) {
        String login = tokens[1];
        for (UserStatusListener listener : userStatusListeners) {
            listener.offline(login);
        }
    }
    private void handleOnline(String[] tokens){
        String login = tokens[1];
        for(UserStatusListener listener : userStatusListeners){
            listener.onLine(login);
        }
    }
    public boolean connect(){

        try {
            this.socket = new Socket(serverName, serverPort);
            System.out.println("Client port is "+ socket.getLocalPort());
            this.serverOut=socket.getOutputStream();
            this.serverIn=socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }
    public void removeUserStatusListener(UserStatusListener listener){
        userStatusListeners.add(listener);
    }

    /**
     * setters
     * @param listener
     */
    public void addUserStatusListener(UserStatusListener listener){
    userStatusListeners.add(listener);
    }
    public void addMessageListener(MessageListener listener){
        messageListeners.add(listener);
    }
    public void removeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
    }
}
