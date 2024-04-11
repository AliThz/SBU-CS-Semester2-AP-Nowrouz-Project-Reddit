package ViewModel;

import Model.DTO.Comment;
import Model.DTO.Post;
import Model.DTO.User;
import Model.Repository.CommentRepository;
import Model.Repository.SubRedditRepository;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class CommentService {

    //region [ - Field - ]

    private CommentRepository commentRepository;

    //endregion

    //region [ - Constructor - ]

    //region [ - CommentService() - ]
    public CommentService() {
        commentRepository = new CommentRepository();
    }
    //endregion

    //endregion

    //region [ - Methods - ]

    //region [ - create(Comment comment, User user) - ]
    public  void create(Comment comment, User user) {
        if (!user.getAccount().getHasLoggedIn())
            System.out.println("You should login first !");
        else
            commentRepository.insert(comment);
    }
    //endregion

    //region [ - get() - ]
    public ArrayList<Comment> get() {
        ArrayList<Comment> comments = commentRepository.select();
        return comments;
    }
    //endregion

    //region [ - getById(UUID id) - ]
    public Comment getById(UUID id) {
        Comment comment = commentRepository.select(id);
        return comment;
    }
    //endregion

    //region [ - edit(Comment comment, User user) - ]
    public void edit(Comment comment, User user) {
        if (!user.getAccount().getHasLoggedIn())
            System.out.println("You should login first !");
        else
            commentRepository.update(comment);
    }
    //endregion

    //region [ - remove(Comment comment, User user) - ]
    public void remove(Comment comment, User user) {
        if (!user.getAccount().getHasLoggedIn())
            System.out.println("You should login first");
        else
            commentRepository.delete(comment);
    }
    //endregion


    //region [ - getByPost(Post post) - ]
    public ArrayList<Comment> getByPost(Post post) {
//        ArrayList<Comment> comments = commentRepository.select().stream().filter(c -> c.getRepliedPost().getId().equals(post.getId())).collect(Collectors.toCollection(ArrayList<Comment>::new));
        ArrayList<Comment> comments = commentRepository.select();
        return comments;
    }
    //endregion

    //region [ - getByUser(User user) - ]
    public ArrayList<Comment> getByUser(User user) {
        ArrayList<Comment> comments = commentRepository.select().stream().filter(c -> c.getRepliedPost().getCreator().getId().equals(user.getId())).collect(Collectors.toCollection(ArrayList<Comment>::new));
        return comments;
    }
    //endregion

    //endregion

}
