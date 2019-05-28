/**
 *  Kyle Parke MUC
 */
package com.muc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.OutputStream;
import java.util.Date;

/**
 * start the server with a specific port
 */
public class ServerMain {
    public static void main(String[] args) {

        int port = 8818;
        Server server = new Server(port);
        server.start();
    }


}

