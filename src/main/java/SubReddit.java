import javax.xml.stream.events.Comment;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class SubReddit {

    //region [ - Fields - ]

    private UUID id;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    private String description;
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    private ArrayList<Post> posts;
    public ArrayList<Post> getPosts() {
        return posts;
    }
    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    private LocalDate date;
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    //endregion

    //region [ - Constructor - ]
    public SubReddit() {
    }
    //endregion

    //region [ - Method - ]

    //region [ - toString() - ]
    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;\n",id, title, description, date);
    }
    //endregion

    //endregion

}
