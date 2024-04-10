package Model.DTO;

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

    private UUID creatorId;
    public UUID getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    private User creator;
    public User getCreator() {
        return creator;
    }
    public void setCreator(User creator) {
        this.creator = creator;
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

    private ArrayList<User> joinedUsers;
    public ArrayList<User> getJoinedUsers() {
        return joinedUsers;
    }
    public void setJoinedUsers(ArrayList<User> joinedUsers) {
        this.joinedUsers = joinedUsers;
    }

    private String information;
    public String getInformation() {
        return String.format("%s;%s;%s;%s;%s;\n",id, creator.getId(), title, description, date);
    }

    //endregion

    //region [ - Constructor - ]

    //region [ - SubReddit() - ]
    public SubReddit() {
        id = UUID.randomUUID();
        date = LocalDate.now();
    }
    //endregion

    //region [ - SubReddit(String title, String description) - ]
    public SubReddit(User creator, String title, String description) {
        id = UUID.randomUUID();
        this.creator = creator;
        this.title = title;
        this.description = description;
        date = LocalDate.now();
    }
    //endregion

    //endregion

}
