/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classroom;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.net.*;
import java.io.*;
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.Action;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Nazmus Sakib
 */
public class ChatroomController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    String username="Sakib", serverIP = "127.0.0.1";
    int Port = 5000;
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
    ArrayList<String> userList = new ArrayList();
    Boolean isConnected = false;
    
    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;
    @FXML
    private Button sendButton;
    @FXML
    private TextArea usersList;
    @FXML
    private TextArea chatTextArea;
    @FXML
    private TextArea inputTextArea;
    @FXML 
    private Button back;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
     public class IncomingReader implements Runnable{

        public void run() {
            String[] data;
            String stream, done = "Done", connect = "Connect", disconnect = "Disconnect", chat = "Chat";

            try {
                while ((stream = reader.readLine()) != null) {

                    data = stream.split(":");

                     if (data[2].equals(chat)) {

                        chatTextArea.appendText(data[0] + ": " + data[1] + "\n");
                        

                    } else if (data[2].equals(connect)){

                        chatTextArea.clear();
                        userAdd(data[0]);

                    } else if (data[2].equals(disconnect)) {


                        userRemove(data[0]);

                    } else if (data[2].equals(done)) {


                        usersList.setText("");
                        writeUsers();
                        userList.clear();

                    }
                 
                }
           }catch(Exception ex) {
           }
        }
    }
 public void ListenThread() {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }

    public void userAdd(String data) {
         userList.add(data);

     }

    public void userRemove(String data) {
         chatTextArea.appendText(data + " has disconnected.\n");

     }

    public void writeUsers() {
         String[] tempList = new String[(userList.size())];
         userList.toArray(tempList);
         for (String token:tempList) {

             usersList.appendText(token + "\n");

         }

     }

    public void sendDisconnect() {

       String bye = (username + ": :Disconnect");
        try{
            writer.println(bye); // Sends server the disconnect signal.
            writer.flush(); // flushes the buffer
        } catch (Exception e) {
            chatTextArea.appendText("Could not send Disconnect message.\n");
        }

      }

    public void Disconnect() {

        try {
               chatTextArea.appendText("Disconnected.\n");
               sock.close();
        } catch(Exception ex) {
               chatTextArea.appendText("Failed to disconnect. \n");
        }
        isConnected = false;
        
        usersList.setText("");

      }
    @FXML
    private void connectButtonActionPerformed(ActionEvent event) {                                              
        // TODO add your handling code here:
            if (isConnected == false) {

            try {
                sock = new Socket(serverIP, Port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(streamreader);
                writer = new PrintWriter(sock.getOutputStream());
                writer.println(username + ":has connected.:Connect"); // Displays to everyone that user connected.
                writer.flush(); // flushes the buffer
                isConnected = true; // Used to see if the client is connected.
            } catch (Exception ex) {
                chatTextArea.appendText("Cannot Connect! Try Again. \n");
               
            }
            ListenThread();
        } else if (isConnected == true) {
            chatTextArea.appendText("You are already connected. \n");
        }
    }                                             
    @FXML
    private void disconnectButtonActionPerformed(ActionEvent event) {                                                 
        // TODO add your handling code here:
        sendDisconnect();
        Disconnect();
    }                                                
    @FXML
    private void sendButtonActionPerformed(ActionEvent event) {                                           
        // TODO add your handling code here:
        String nothing = "";
        if ((inputTextArea.getText()).equals(nothing)) {
            inputTextArea.setText("");
            inputTextArea.requestFocus();
        } else {
            try {
               writer.println(username + ":" + inputTextArea.getText() + ":" + "Chat");
               writer.flush(); // flushes the buffer
            } catch (Exception ex) {
                chatTextArea.appendText("Message was not sent. \n");
            }
            inputTextArea.setText("");            
            inputTextArea.requestFocus();
        }

        inputTextArea.setText("");
        inputTextArea.requestFocus();
    } 
    @FXML
      public void back(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene=new Scene(root);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        
    }
}
