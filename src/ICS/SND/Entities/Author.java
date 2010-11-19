package ICS.SND.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Author {
    private int id;
    private String authorName;
    
    @Column(length = 1024)
    public String getAuthorName() {
        return authorName;
    }
    
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getAuthorId() {
        return id;
    }
 
    public void setAuthorId(int authorId) {
        this.id = authorId;
    }    
}
