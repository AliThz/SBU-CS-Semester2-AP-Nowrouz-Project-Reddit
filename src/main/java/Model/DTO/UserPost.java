package Model.DTO;

import java.util.UUID;

public class UserPost {

    //region [ - Fields - ]

    private UUID userId;
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    private User user;
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    private UUID postId;
    public UUID getPostId() {
        return postId;
    }
    public void setPostId(UUID postId) {
        this.postId = postId;
    }

    private Post post;
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }

    private Boolean vote;
    public Boolean getVote() {
        return vote;
    }
    public void setVote(Boolean vote) {
        this.vote = vote;
    }

    private String information;
    public String getInformation() {
        return String.format("%s;%s;%s;\n",user.getId(), post.getId(), vote);
    }

    //endregion

    //region [ - Constructor - ]


    public UserPost() {
    }

    public UserPost(User user, Post post, Boolean vote) {
        this.user = user;
        this.post = post;
        this.vote = vote;
    }

    //endregion

}
