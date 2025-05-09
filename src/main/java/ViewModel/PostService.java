package ViewModel;

import Model.DTO.Post;
import Model.DTO.User;
import Model.DTO.SubReddit;
import Model.DTO.UserPost;
import Model.Repository.PostRepository;
import Model.Repository.UserPostRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class PostService {

    //region [ - Field - ]

    private PostRepository postRepository;
    private CommentService commentService;
    private UserPostRepository userPostRepository;

    //endregion

    //region [ - Constructor - ]

    //region [ - PostService() - ]
    public PostService() {
        postRepository = new PostRepository();
        commentService = new CommentService();
        userPostRepository = new UserPostRepository();
    }
    //endregion

    //endregion

    //region [ - Methods - ]

    //region [ - create(Post post, User user) - ]
    public Post create(Post post, User user) {
        if (!user.getAccount().getHasLoggedIn())
            System.out.println("You should login first !");
        else {
            postRepository.insert(post);
            userPostRepository.insert(new UserPost(user, post, null));
            return post;
        }
        return null;
    }
    //endregion

    //region [ - get() - ]
    public ArrayList<Post> get() {
        ArrayList<Post> posts = postRepository.select();
        posts.forEach(p -> p.setComments(commentService.getByPost(p)));
        return posts;
    }
    //endregion

    //region [ - getById(UUID id) - ]
    public Post getById(UUID id) {
        Post post = postRepository.select(id);
        post.setComments(commentService.getByPost(post));
        return post;
    }
    //endregion

    //region [ - edit(Post post, User user) - ]
    public Post edit(Post post, User user) {
        if (!user.getAccount().getHasLoggedIn())
            System.out.println("You should login first !");
        else {
            postRepository.update(post);
            return post;
        }
        return null;
    }
    //endregion

    //region [ - remove(Post post, User user) - ]
    public void remove(Post post, User user) {
        if (!user.getAccount().getHasLoggedIn())
            System.out.println("You should login first");
        else {
            userPostRepository.select().stream().filter(up -> up.getPost().getId().equals(post.getId())).collect(Collectors.toCollection(ArrayList<UserPost>::new)).forEach(userPostRepository::delete);
            post.getComments().forEach(c -> commentService.remove(c, user));
            postRepository.delete(post);
        }
    }
    //endregion

    //region [ - getBySubReddit(SubReddit subReddit) - ]
    public ArrayList<Post> getBySubReddit(SubReddit subReddit) {
        ArrayList<Post> posts = postRepository.select().stream().filter(p -> p.getSubReddit().getId().equals(subReddit.getId())).collect(Collectors.toCollection(ArrayList<Post>::new));
        if (!posts.isEmpty())
            posts.forEach(p -> p.setComments(commentService.getByPost(p)));
        return posts;
    }
    //endregion

    //region [ - getByCreator(User user) - ]
    public ArrayList<Post> getByCreator(User user) {
        ArrayList<Post> posts = postRepository.select().stream().filter(p -> p.getCreator().getId().equals(user.getId())).collect(Collectors.toCollection(ArrayList<Post>::new));
        posts.forEach(p -> p.setComments(commentService.getByPost(p)));
        return posts;
    }
    //endregion

    //region [ - getByUserCommented(User user) - ]
    public ArrayList<Post> getByUserCommented(User user) {
//        ArrayList<Post> posts = postRepository.select().stream().filter(p -> commentService.getByPost(p).stream().anyMatch(c -> c.getCreator().getId().equals(user.getId()))).collect(Collectors.toCollection(ArrayList<Post>::new));
//        posts.forEach(p -> p.setComments(commentService.getByPost(p)));
        ArrayList<Post> allPosts = get();
        ArrayList<Post> posts = new ArrayList<>();

        for (var post : allPosts) {
            for (var comment : post.getComments()) {
                if (comment.getCreator().getId().equals(user.getId())) {
                    posts.add(post);
                    break;
                }
            }
        }

        return posts;
    }
    //endregion

    //region [ - getByDate() - ]
    public ArrayList<Post> getByDate() {
        ArrayList<Post> posts = postRepository.select();
        posts.sort(Comparator.comparing(Post::getDate));
        Collections.reverse(posts);
        return posts;
    }
    //endregion

    //region [ - getBySubReddits(ArrayList<SubReddit>, User user) - ]
    public ArrayList<Post> getBySubReddits(User user) {
        if (!user.getAccount().getHasLoggedIn())
            System.out.println("You should login first !");
        else {
            ArrayList<SubReddit> subReddits = user.getJoinedSubReddits();
            ArrayList<Post> posts = new ArrayList<>();
            if (subReddits != null)
                subReddits.forEach(sr -> posts.addAll(getBySubReddit(sr)));
            return posts;
        }
        return null;
    }
    //endregion

    //region [ - ArrayList<Post> getTimeline - ]

    //region [ - ArrayList<Post> getTimeline() - ]
    public ArrayList<Post> getTimeline() {
        ArrayList<Post> posts = getByDate();
        return posts;
    }
    //endregion

    //region [ - ArrayList<Post> getTimeline(User user) - ]
    public ArrayList<Post> getTimeline(User user) {
        ArrayList<Post> posts = getBySubReddits(user);
        posts.forEach(p -> p.setComments(commentService.getByPost(p)));
        if (posts != null) {
            posts.sort(Comparator.comparing(Post::getDate));
            Collections.reverse(posts);
        }
        return posts;
    }
    //endregion

    //endregion

    //region [ - vote(Post post, User user, boolean vote) - ]
    public void vote(Post post, User user, boolean vote) {
        UserPost userPost = userPostRepository.select().stream().filter(up -> up.getPost().getId().equals(post.getId()) && up.getUser().getId().equals(user.getId())).findFirst().orElse(null);
        if (userPost == null) {
            userPost = new UserPost(user, post, null);
            userPostRepository.insert(userPost);
        }

        if (userPost.getVote() == null) {
            if (vote) {
                post.setUpVotes(post.getUpVotes() + 1);
                userPost.setVote(true);
            } else {
                post.setDownVotes(post.getDownVotes() + 1);
                userPost.setVote(false);
            }
            edit(post, user);
            userPostRepository.update(new UserPost(user, post, vote));
        } else {
            if (userPost.getVote() == vote) {
                if (vote) {
                    post.setUpVotes(post.getUpVotes() - 1);
                } else {
                    post.setDownVotes(post.getDownVotes() - 1);
                }
                userPost.setVote(null);
                edit(post, user);
                userPost.setUser(user);
                userPost.setPost(post);
                userPostRepository.update(userPost);
            } else {
                if (vote) {
                    post.setUpVotes(post.getUpVotes() + 1);
                    post.setUpVotes(post.getDownVotes() - 1);
                } else {
                    post.setUpVotes(post.getUpVotes() - 1);
                    post.setDownVotes(post.getDownVotes() + 1);
                }
                userPost.setVote(vote);
                edit(post, user);
                userPostRepository.update(userPost);
            }
        }
    }
    //endregion

    //endregion

}
