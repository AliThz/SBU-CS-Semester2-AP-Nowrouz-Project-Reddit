import java.util.UUID;

public class Comment extends Post {

    //region [ - Field - ]

    private Post repliedPost;
    public Post getRepliedPost() {
        return repliedPost;
    }
    public void setRepliedPost(Post repliedPost) {
        this.repliedPost = repliedPost;
    }

    //endregion

    //region [ - Constructor - ]
    public Comment() {
        repliedPost = new Post();
    }
    //endregion

    //region [ - Method - ]

    //region [ - toString() - ]
    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%d;%d;%d;",
                repliedPost.getId(), getId(), getCreator().getId(),
                getTitle(), getMessage(), getUpVotes(), getDownVotes(), getKarma());
    }
    //endregion

    //endregion

}
