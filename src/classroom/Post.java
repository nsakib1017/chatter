package classroom;

import javafx.beans.property.SimpleStringProperty;


/**
 *
 * @author jwright
 */
public class Post {
    private SimpleStringProperty ownerName,title,content;
    public Post(String ownerName, String title,String content) {
        this.ownerName = new SimpleStringProperty(ownerName);
        this.title = new SimpleStringProperty(title);
        this.content = new SimpleStringProperty(content);
       
    }
    public String getOwnerName() {
        return ownerName.get();
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = new SimpleStringProperty(ownerName);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }
    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content = new SimpleStringProperty(content);
    }
    
//    public String toString()
//    {
//        return String.format("%s %s", title, content);
//    }
}