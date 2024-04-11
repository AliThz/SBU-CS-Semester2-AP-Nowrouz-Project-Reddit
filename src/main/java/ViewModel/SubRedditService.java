package ViewModel;

import Model.DTO.*;
import Model.Repository.PostRepository;
import Model.Repository.SubRedditRepository;
import Model.Repository.UserSubRedditRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class SubRedditService {

    //region [ - Fields - ]

    //region [ - Styles - ]

    /*CONSOLE TEXT COLOR*/
    private static final String RESET_COLOR = "\033[0m";
    private static final String BLACK_COLOR = "\033[30m";
    private static final String RED_COLOR = "\033[31m";
    private static final String GREEN_COLOR = "\033[32m";
    private static final String YELLOW_COLOR = "\033[33m";
    private static final String BLUE_COLOR = "\033[34m";
    private static final String PURPLE_COLOR = "\033[35m";
    private static final String CYAN_COLOR = "\033[36m";
    private static final String WHITE_COLOR = "\033[37m";

    /*CONSOLE TEXT STYLE*/
    private static final String BOLDON_STYLE = "\033[1m";
    private static final String BOLDOFF_STYLE = "\033[22m";

    //endregion

    private SubRedditRepository subRedditRepository;
    private PostService postService;
    private UserSubRedditRepository userSubRedditRepository;

    //endregion

    //region [ - Constructor - ]
    public SubRedditService() {
        subRedditRepository = new SubRedditRepository();
        postService = new PostService();
        userSubRedditRepository = new UserSubRedditRepository();
    }
    //endregion

    //region [ - Methods - ]

    //region [ - create(SubReddit subReddit, User user)) - ]
    public SubReddit create(SubReddit subReddit, User user) {
        if (!user.getAccount().getHasLoggedIn())
            System.out.println("You should login first !");
        else {
//            if (validateTitle(subReddit.getTitle())) {
                subReddit.setCreator(user);
                subRedditRepository.insert(subReddit);
                userSubRedditRepository.insert(new UserSubReddit(subReddit.getCreator(), subReddit, true));
                return subReddit;
//            }
        }
        return null;
    }
    //endregion

    //region [ - get() - ]
    public ArrayList<SubReddit> get() {
        ArrayList<SubReddit> subReddits = subRedditRepository.select();
        subReddits.forEach(sr -> sr.setPosts(postService.getBySubReddit(sr)));
        return subReddits;
    }
    //endregion

    //region [ - getById(UUID id) - ]
    public SubReddit getById(UUID id) {
        SubReddit subReddit = subRedditRepository.select(id);
        subReddit.setPosts(postService.getBySubReddit(subReddit));
        return subReddit;
    }
    //endregion

    //region [ - edit(SubReddit subReddit, User user) - ]
    public void edit(SubReddit subReddit, User user) {
        if (!user.getAccount().getHasLoggedIn())
            System.out.println("You should login first !");
        else
            subRedditRepository.update(subReddit);
    }
    //endregion

    //region [ - remove(SubReddit subReddit, User user) - ]
    public void remove(SubReddit subReddit, User user) {
        if (!user.getAccount().getHasLoggedIn())
            System.out.println("You should login first");
        else {
            subReddit.setPosts(postService.getBySubReddit(subReddit));
            subReddit.getPosts().forEach(sr -> postService.remove(sr, user));
            subRedditRepository.delete(subReddit);
        }
    }
    //endregion


    //region [ - getJoinedSubReddit(User user) - ]
    public ArrayList<SubReddit> getJoinedSubReddit(User user) {
        ArrayList<SubReddit> subReddits = (ArrayList<SubReddit>) userSubRedditRepository.select().stream().filter(sr -> sr.getUser().getId().equals(user.getId()) && !sr.getSubReddit().getCreator().getId().equals(user.getId())).map(UserSubReddit::getSubReddit).toList();
        subReddits.forEach(sr -> sr.setPosts(postService.getBySubReddit(sr)));
        return subReddits;
    }
    //endregion

    //region [ - getCreatedSubReddit(User user) - ]
    public ArrayList<SubReddit> getCreatedSubReddit(User user) {
        ArrayList<SubReddit> subReddits = (ArrayList<SubReddit>) userSubRedditRepository.select().stream().filter(usr -> usr.getUser().getId().equals(user.getId()) && usr.getSubReddit().getCreator().getId().equals(user.getId())).map(UserSubReddit::getSubReddit).toList();
        subReddits.forEach(sr -> sr.setPosts(postService.getBySubReddit(sr)));
        return subReddits;
    }
    //endregion

    //region [ - join(SubReddit subReddit, User user) - ]
    public void join(SubReddit subReddit, User user) {
        userSubRedditRepository.insert(new UserSubReddit(user, subReddit, false));
    }
    //endregion

    //region [ - leave(SubReddit subReddit, User user) - ]
    public void leave(UserSubReddit userSubReddit) {
        userSubRedditRepository.delete(userSubReddit);
    }
    //endregion

    // region [ - validateTitle(String title) - ]
    private boolean validateTitle(String title) {
        for (SubReddit subReddit : get()) {
            if (Objects.equals(subReddit.getTitle(), title)) {
                System.out.printf("%sThis title is taken !%s\n", RED_COLOR, RESET_COLOR);
                return false;
            }
        }
        return true;
    }
    //endregion

    //endregion

}
