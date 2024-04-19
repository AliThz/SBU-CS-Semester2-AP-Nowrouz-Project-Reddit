package Model.DTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Post {

    //region [ - Fields - ]

    private UUID id;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    private UUID subRedditId;
    public UUID getSubRedditId() {
        return subRedditId;
    }
    public void setSubRedditId(UUID subRedditId) {
        this.subRedditId = subRedditId;
    }

    private SubReddit subReddit;
    public SubReddit getSubReddit() {
        return subReddit;
    }
    public void setSubReddit(SubReddit subReddit) {
        this.subReddit = subReddit;
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

    private String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    private int upVotes;
    public int getUpVotes() {
        return upVotes;
    }
    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    private int downVotes;
    public int getDownVotes() {
        return downVotes;
    }
    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    private int karma;
    public int getKarma() {
        karma = upVotes - downVotes;
        return karma;
    }

    private ArrayList<Comment> comments;
    public ArrayList<Comment> getComments() {
        return comments;
    }
    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    private LocalDate date;
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    private String information;
    public String getInformation() {
        return String.format("%s;%s;%s;%s;%s;%d;%d;%s;\n",id, subReddit.getId(), creator.getId(), title, message, upVotes, downVotes, date);
    }

    //endregion

    //region [ - Constructor - ]

    //region [ - Post() - ]
    public Post() {
        id = UUID.randomUUID();
        creator = new User();
        comments = new ArrayList<>();
        date = LocalDate.now();
    }
    //endregion

    //region [ - Post(SubReddit subReddit, User creator, String title, String message) - ]
    public Post(SubReddit subReddit, User creator, String title, String message) {
        id = UUID.randomUUID();
        this.subReddit = subReddit;
        this.creator = creator;
        this.title = title;
        this.message = message;
        date = LocalDate.now();
    }
    //endregion

    //endregion

}
