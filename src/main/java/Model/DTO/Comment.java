package Model.DTO;

import java.util.UUID;

public class Comment extends Post {

    //region [ - Field - ]

    private UUID repliedPostId;
    public UUID getRepliedPostId() {
        return repliedPostId;
    }
    public void setRepliedPostId(UUID repliedPostId) {
        this.repliedPostId = repliedPostId;
    }

    private Post repliedPost;
    public Post getRepliedPost() {
        return repliedPost;
    }
    public void setRepliedPost(Post repliedPost) {
        this.repliedPost = repliedPost;
    }

    @Override
    public String getInformation() {
        return String.format("%s;%s;%s;%s;%s;%d;%d;%s;%s;\n",getId(), getSubReddit().getId(), getCreator().getId(), getTitle(), getMessage(), getUpVotes(), getDownVotes(), getDate(), repliedPost.getId());
    }

    //endregion

    //region [ - Constructor - ]

    //region [ - Comment() - ]
    public Comment() {
        repliedPost = new Post();
    }
    //endregion

    //region [ - Comment(SubReddit subReddit, User creator, String title, String message, Post repliedPost) - ]
    public Comment(SubReddit subReddit, User creator, String title, String message, Post repliedPost) {
        super(subReddit, creator, title, message);
        this.repliedPost = repliedPost;
    }
    //endregion

    //endregion

}
