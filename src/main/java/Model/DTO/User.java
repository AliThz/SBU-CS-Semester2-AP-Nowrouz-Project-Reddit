package Model.DTO;

import java.util.ArrayList;

public class User extends Person {

    //region [ - Fields - ]

    private ArrayList<Post> ownPosts;
    public ArrayList<Post> getOwnPosts() {
        return ownPosts;
    }
    public void setOwnPosts(ArrayList<Post> posts) {
        this.ownPosts = posts;
    }

    private ArrayList<Post> likedPosts;
    public ArrayList<Post> getLikedPosts() {
        return likedPosts;
    }
    public void setLikedPosts(ArrayList<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    private ArrayList<SubReddit> ownSubReddits;
    public ArrayList<SubReddit> getOwnSubReddits() {
        return ownSubReddits;
    }
    public void setOwnSubReddits(ArrayList<SubReddit> ownSubReddits) {
        this.ownSubReddits = ownSubReddits;
    }

    private ArrayList<SubReddit> joinedSubReddits;
    public ArrayList<SubReddit> getJoinedSubReddits() {
        return joinedSubReddits;
    }
    public void setJoinedSubReddits(ArrayList<SubReddit> joinedSubReddits) {
        this.joinedSubReddits = joinedSubReddits;
    }
    //endregion

    //region [ - Constructors - ]

    //region [ - User(String username, String firstName, String lastName, int age, Account account) - ]
    public User(String username, String firstName, String lastName, int age, Account account) {
        super(username,firstName, lastName, age, account);
    }
    //endregion

    //region [ - User(Account account) - ]
    public User(Account account) {
        super(account);
    }
    //endregion

    //region [ - User() - ]
    public User() {
    }
    //endregion

    //endregion

}