package fileserver;

import java.io.*;
import java.net.*;
import javafx.stage.FileChooser;

public class fileserver {
public static void main(String[] args) throws Exception {
ServerSocket s=new ServerSocket(80);
Socket sr=s.accept();
FileChooser fc = new FileChooser();
File selected=fc.showOpenDialog(null);
FileInputStream fr=new FileInputStream(selected.getAbsolutePath());
byte b[]=new byte[20002];
fr.read(b, 0, b.length);
OutputStream os=sr.getOutputStream();
os.write(b, 0, b.length);
}
}