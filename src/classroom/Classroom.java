package classroom;

import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;
import javafx.scene.Node;

public class Classroom extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost/classroom","root","12345");
            PreparedStatement ps=null;
            ps=con.prepareCall("select * from teachers");
            ps.execute();
            ResultSet rs=ps.getResultSet();
        }catch(Exception ex){
            System.out.println("Could Not Connect");
        }
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
     public void student(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene scene=new Scene(root);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        
    }
     public void login(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene scene=new Scene(root);
        Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        
    }



    public static void main(String[] args) throws IOException {

        launch(args);
    }
}
