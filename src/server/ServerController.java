/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * FXML Controller class
 *
 * @author Nazmus Sakib
 */
public class ServerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextArea outputPane;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    ArrayList clientOutputStreams;
        ArrayList<String> onlineUsers;

	public class ClientHandler implements Runnable	{
		BufferedReader reader;
		Socket sock;
                PrintWriter client;


		public ClientHandler(Socket clientSocket, PrintWriter user) {
		// new inputStreamReader and then add it to a BufferedReader
                        client = user;
			try {
				sock = clientSocket;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
				reader = new BufferedReader(isReader);
			} // end try
			catch (Exception ex) {
				outputPane.appendText("Error beginning StreamReader. \n");
			} // end catch

		} // end ClientHandler()

		public void run() {
                        String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" ;
			String[] data;

			try {
				while ((message = reader.readLine()) != null) {

					outputPane.appendText("Received: " + message + "\n");
					data = message.split(":");
                                        for (String token:data) {

                                        outputPane.appendText(token + "\n");

                                        }

                                        if (data[2].equals(connect)) {

                                                tellEveryone((data[0] + ":" + data[1] + ":" + chat));
                                                userAdd(data[0]);

					} else if (data[2].equals(disconnect)) {

                                            tellEveryone((data[0] + ":has disconnected." + ":" + chat));
                                            userRemove(data[0]);

					} else if (data[2].equals(chat)) {

                                            tellEveryone(message);

					} else {
                                            outputPane.appendText("No Conditions were met. \n");
                                        }


			     } // end while
			} // end try
			catch (Exception ex) {
				outputPane.appendText("Lost a connection. \n");
                                ex.printStackTrace();
                                clientOutputStreams.remove(client);
			} // end catch
		} // end run()
	}
        @FXML
        private void startButtonActionPerformed(ActionEvent event) {                                            
        // TODO add your handling code here:
        Thread starter = new Thread(new ServerStart());
        starter.start();

        outputPane.appendText("Server started. \n");
    }                                           
        @FXML
        private void stopButtonActionPerformed(ActionEvent event) {                                           
        // TODO add your handling code here:

        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
        outputPane.appendText("Server stopping... \n");

    }                          
     public class ServerStart implements Runnable {
        public void run() {
                    clientOutputStreams = new ArrayList();
                    onlineUsers = new ArrayList();  

                    try {
                    	ServerSocket serverSock = new ServerSocket(5000);

                    	while (true) {
				// set up the server writer function and then begin at the same
			  	// the listener using the Runnable and Thread
				Socket clientSock = serverSock.accept();
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);

				// use a Runnable to start a 'second main method that will run
				// the listener
				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
				outputPane.appendText("Got a connection. \n");
			} // end while
		} // end try
		catch (Exception ex)
		{
			outputPane.appendText("Error making a connection. \n");
		} // end catch

	} // end go()
    }

    	public void userAdd (String data) {
            String message, add = ": :Connect", done = "Server: :Done", name = data;
            outputPane.appendText("Before " + name + " added. \n");
            onlineUsers.add(name);
            outputPane.appendText("After " + name + " added. \n");
            String[] tempList = new String[(onlineUsers.size())];
            onlineUsers.toArray(tempList);

                for (String token:tempList) {

                    message = (token + add);
                    tellEveryone(message);
                }
                tellEveryone(done);
	}

	public void userRemove (String data) {
                String message, add = ": :Connect", done = "Server: :Done", name = data;
                onlineUsers.remove(name);
                String[] tempList = new String[(onlineUsers.size())];
		onlineUsers.toArray(tempList);

                for (String token:tempList) {

                    message = (token + add);
                    tellEveryone(message);
                }
                tellEveryone(done);
	}

        public void tellEveryone(String message) {
	// sends message to everyone connected to server
		Iterator it = clientOutputStreams.iterator();

		while (it.hasNext()) {
			try {
				PrintWriter writer = (PrintWriter) it.next();
				writer.println(message);
				outputPane.appendText("Sending: " + message + "\n");
                                writer.flush();
                               // outputPane.setCaretPosition(outputPane.getDocument().getLength());

			} // end try
			catch (Exception ex) {
				outputPane.appendText("Error telling everyone. \n");
			} // end catch
		} // end while
	} // end tellEveryone()
}
