package Model.DTO;
import java.util.UUID;

public class UserSubReddit {

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

    private boolean isOwned;
    public boolean getIsOwned() {
        return isOwned;
    }
    public void setIsOwned(boolean owned) {
        isOwned = owned;
    }

    private String information;
    public String getInformation() {
        return String.format("%s;%s;%s;\n",user.getId(), subReddit.getId(), getIsOwned());
    }

    //endregion

    //region [ - Constructor - ]

    //region [ - UserSubReddit() - ]
    public UserSubReddit() {
    }
    //endregion

    //region [ - UserSubReddit(User user, SubReddit subReddit, boolean isOwned) - ]
    public UserSubReddit(User user, SubReddit subReddit, boolean isOwned) {
        this.user = user;
        this.subReddit = subReddit;
        this.isOwned = isOwned;
    }
    //endregion

    //endregion

}
