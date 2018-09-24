/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classroom;

import java.io.File;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;

/**
 * FXML Controller class
 *
 * @author Nazmus Sakib
 */
public class HomeController implements Initializable {

    @FXML
    private TableView<Post> table;
    @FXML
    private TableColumn<Post, String> name;
    @FXML
    private TableColumn<Post, String> title;
    @FXML
    private TableColumn<Post, String> content;
    @FXML
    private Button chat;
    @FXML
    private Button file;
    
    ObservableList<Post> oblist=FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost/classroom","root","12345");
            ResultSet rs=con.createStatement().executeQuery("select * from posts");
            while(rs.next()){
                oblist.add(new Post(rs.getString("name"),rs.getString("title"),rs.getString("content")));
            }
        }catch(Exception ex){
            System.out.println("Could Not Connect");
        }
        name.setCellValueFactory(new PropertyValueFactory<>("ownerName"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        content.setCellValueFactory(new PropertyValueFactory<>("content"));
        table.setItems(oblist);
    }
    
     public void chat(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Chatroom.fxml"));
        Scene scene=new Scene(root);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        
    }
    
    public void upload(ActionEvent event) throws IOException{
    
        FileChooser fc = new FileChooser();
        File selected=fc.showOpenDialog(null);
        byte []b=new byte[2002];
        Socket sr=new Socket("localhost",80);
        InputStream is=sr.getInputStream();
        if(selected != null){
        FileOutputStream fr=new FileOutputStream(selected.getAbsolutePath());
        is.read(b,0,b.length);
        fr.write(b, 0, b.length);
        }
    }
}