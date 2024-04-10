import Model.DTO.Account;
import Model.DTO.Post;
import Model.DTO.SubReddit;
import Model.DTO.User;
import Model.Repository.SubRedditRepository;
import ViewModel.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.*;
import java.util.regex.Pattern;

public class Main {

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

    private static AccountService accountService;
    private static UserService userService;
    private static PostService postService;
    private static CommentService commentService;
    private static SubRedditService subRedditService;

    //endregion

    //region [ - Constructor - ]
    public Main() {
        accountService = new AccountService();
        userService = new UserService();
        postService = new PostService();
        commentService = new CommentService();
        subRedditService = new SubRedditService();
    }
    //endregion

    //region [ - Methods - ]

    //region [ - main(String[] args) - ]
    public static void main(String[] args) {
//        Model.Repository.UserRepository userRepository = new Model.Repository.UserRepository();
//        userRepository.insert(new Model.DTO.User("bc","b", "c", 15, new Model.DTO.Account("ali.thz83@gmail.com", "Ab!@#12345")));
//        ArrayList<Model.DTO.User> users = userRepository.select();
//        System.out.println();

//        Gson gson = new Gson();
//        String jsonObject = gson.toJson(new Model.DTO.User("bc","b", "c", 15, new Model.DTO.Account("ali.thz83@gmail.com", "Ab!@#12345")));
//        Model.DTO.User user = gson.fromJson(jsonObject, Model.DTO.User.class);
//        System.out.println(user.getFirstName());


        userService = new UserService();
//        UserService userService1 = new UserService();
//        User user = new User("User6", "User", "6", 23, new Account("User6@gmail.com", "Abc!@#123"));
//        userService.create(user);
//
//
        subRedditService = new SubRedditService();
//        SubReddit subReddit = new SubReddit(user, "SubReddit 6", "Sixth SubReddit");
//        subRedditService.create(subReddit, user);


        postService = new PostService();
//        SubReddit sr1 = subRedditService.getById(UUID.fromString("755819e8-d53d-427b-9956-98263d9ad715"));
//        User u1 = userService.getById(UUID.fromString("c7e85981-e62f-40b4-9ca8-fcb22fc99283"));
//        Post post = new Post(sr1,u1,"Post 2", "This is Post 2");
//        postService.create(post, u1);


        runMainPage();
    }
    //endregion

    //region [ - runMainPage - ]

    //region [ - runMainPage() - ]
    public static void runMainPage() {
        displayHeader();
        displayTimeline();
        displayMainMenu();

        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();

        switch (command) {
            case "l" -> login();
            case "s" -> signUp();
            case "p1" -> displayFromTimeline(0);
            case "p2" -> displayFromTimeline(1);
            case "p3" -> displayFromTimeline(2);
            case "p4" -> displayFromTimeline(3);
            case "p5" -> displayFromTimeline(4);
            case "1" -> displayAllPosts(new User());
            case "2" -> displayAllSubReddits();
            case "3" -> System.exit(0);
            default -> System.out.println(RED_COLOR + "Enter a correct command !" + RESET_COLOR);
        }

        System.out.println();
        runMainPage();
    }
    //endregion

    //region [ - runMainPage(User user) - ]
    public static void runMainPage(User user) {
        displayHeader(user);
        displayTimeline(user);
        displayMainMenu(user);

        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();

        switch (command) {
            case "p1" -> displayTimeline();
            case "p2" -> displayTimeline();
            case "p3" -> displayTimeline();
            case "p4" -> displayTimeline();
            case "p5" -> displayTimeline();
            case "1" -> displayAllPosts(user);
            case "2" -> displayAllSubReddits(user);
            case "3" -> System.exit(0);
            case "l" -> logout(user);
            default -> System.out.println(RED_COLOR + "Enter a correct command !" + RESET_COLOR);
        }

        System.out.println();
        runMainPage(user);
    }
    //endregion

    //endregion

    //region [ - displayHeader - ]

    //region [ - displayHeader() - ]
    public static void displayHeader() {
        displayReddit();
        System.out.printf("          %sl. Log in | s. Sign up%s", GREEN_COLOR, RESET_COLOR);
    }
    //endregion

    //region [ - displayHeader(User user) - ]
    public static void displayHeader(User user) {
        displayReddit();
        System.out.printf("          %sHi %s ! | l. Log out%s", GREEN_COLOR, user.getUsername(), RESET_COLOR);
    }
    //endregion

    //endregion

    //region [ - displayReddit() - ]
    public static void displayReddit() {
        System.out.printf("%s%sReddit%s%s", BOLDON_STYLE, RED_COLOR, RESET_COLOR, BOLDOFF_STYLE);
    }
    //endregion

    //region [ - displayTimeline - ]

    //region [ - displayTimeline() - ]
    public static void displayTimeline() {
        System.out.print(CYAN_COLOR);
        System.out.println("\nExplore the newest posts on reddit ...");
        postService = new PostService();
        ArrayList<Post> posts = postService.getTimeline();
        if (posts.size() >= 5) for (int i = 0; i < 5; i++) {
            System.out.print("p" + (i + 1) + ". ");
            displayPostBriefly(posts.get(i));
        }
        else {
            int i = 0;
            for (Post post : posts) {
                System.out.print((i + 1) + ". ");
                displayPostBriefly(post);
                i++;
            }
        }
        System.out.println("...");
        System.out.print(RESET_COLOR);
    }
    //endregion

    //region [ - displayTimeline(User user) - ]
    public static void displayTimeline(User user) {
        System.out.print(CYAN_COLOR);
        System.out.println("\nExplore the newest posts on reddit ...");
        postService = new PostService();
        ArrayList<Post> posts = postService.getTimeline(user);
//        ArrayList<Post> posts = postService.getTimeline();
        if (posts != null) {
            if (posts.size() >= 5) {
                for (int i = 0; i < 5; i++) {
                    System.out.print("p" + (i + 1) + ". ");
                    displayPostBriefly(posts.get(i));
                }
            } else {
                int i = 0;
                for (Post post : posts) {
                    System.out.print((i + 1) + ". ");
                    displayPostBriefly(post);
                    i++;
                }
            }
        } else {
            System.out.println("Nothing to show now !");
        }
        System.out.println("...");
        System.out.print(RESET_COLOR);
    }
    //endregion

    //endregion

    //region [ - displayAllSubReddits - ]

    //region [ - displayAllSubReddits() - ]
    public static void displayAllSubReddits() {
        System.out.println();
        displayReddit();
        System.out.printf("    %sSubReddits%s\n", GREEN_COLOR, RESET_COLOR);
        subRedditService = new SubRedditService();
//        subRedditService.get().forEach(Main::displaySubRedditBriefly);
        int counter = 0;
        for (SubReddit sr : subRedditService.get()) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displaySubRedditBriefly(sr);
        }
        System.out.printf("\n%s0. Back\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        switch (command) {
            case "0" -> runMainPage();
            default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
        }
        System.out.println();
        displayAllSubReddits();
    }
    //endregion

    //region [ - displayAllSubReddits(User user) - ]
    public static void displayAllSubReddits(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sSubReddits%s\n", GREEN_COLOR, RESET_COLOR);
        subRedditService = new SubRedditService();
//        subRedditService.get().forEach(Main::displaySubRedditBriefly);
        int counter = 0;
        for (SubReddit sr : subRedditService.get()) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displaySubRedditBriefly(sr);
        }
        System.out.printf("\n%s0. Back\n1. Join\n2. Create\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        switch (command) {
            case "0" -> runMainPage(user);
            case "1" -> joinSubReddit(user);
            case "2" -> createSubReddit(user);
            default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
        }
        System.out.println();
        displayAllSubReddits(user);
    }
    //endregion

    //endregion

    //region [ - displayAllPosts(User user) - ]
    public static void displayAllPosts(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sPosts%s\n", GREEN_COLOR, RESET_COLOR);
        postService = new PostService();
//        postService.get().forEach(Main::displayPostBriefly);
        int counter = 0;
        ArrayList<Post> posts = postService.getByDate();
        for (Post p : posts) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displayPostBriefly(p);
        }

        System.out.printf("\n%s0. Back\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        int command;
        if (scanner.hasNextInt()) {
            command = scanner.nextInt();

            if (command == 0) {
                runMainPage(user);
                return;
            }

            if (command <= posts.size()) {
                postService = new PostService();
                Post post = posts.get(command - 1);
                displayPostCompletely(post);
                System.out.printf("\n%s0. Back\n1. UpVote\n2.DownVote\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
                String postCommand = scanner.next();
                switch (postCommand) {
                    case "0" -> displayAllPosts(user);
                    case "1" -> postService.vote(post, user, true);
                    case "2" -> postService.vote(post, user, false);
                    default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
                }
                System.out.println();
                displayPostCompletely(post);
            } else {
                System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
                System.out.println();
                displayAllSubReddits();
            }
        } else {
            System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
            System.out.println();
            displayAllPosts(user);
        }

    }
    //endregion

    //region [ - displayFromTimeline - ]

    //region [ - displayFromTimeline(int index) - ]
    public static void displayFromTimeline(int index) {
        System.out.println();
        postService = new PostService();
        displayPostCompletely(postService.getByDate().get(index));
        System.out.printf("\n%s0. Back\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String postCommand = scanner.next();
        switch (postCommand) {
            case "0" -> runMainPage();
            default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
        }
        System.out.println();
        runMainPage();
    }
    //endregion

    //region [ - displayFromTimeline(User user) - ]
    public static void displayFromTimeline(User user) {

    }
    //endregion

    //endregion

    //region [ - displayPostCompletely(Post post) - ]
    public static void displayPostCompletely(Post post) {
        displayReddit();
        System.out.printf("    %sPost%s\n", GREEN_COLOR, RESET_COLOR);
        System.out.printf("%sSubReddit : %s\n%sTitle : %s\n%s%s\n%sUpVotes : %s , Karma : %s , DownVotes : %s\n", PURPLE_COLOR, post.getSubReddit().getTitle(), BLUE_COLOR, post.getTitle(), CYAN_COLOR, post.getMessage(), YELLOW_COLOR, post.getUpVotes(), post.getKarma(), post.getDownVotes());
        post.getComments().forEach(c -> System.out.printf("%s%s\n%s%s", RESET_COLOR, RESET_COLOR, c.getCreator().getUsername(), c.getMessage()));
    }
    //endregion

    //region [ - displayPostBriefly(Post post) - ]
    public static void displayPostBriefly(Post post) {
        System.out.printf("%sTitle : %s, SubReddit : %s, Karma : %d, Date : %s, %s%s\n%s", CYAN_COLOR, post.getTitle(), post.getSubReddit().getTitle(), post.getKarma(), post.getDate(), WHITE_COLOR, post.getMessage().substring(0, post.getMessage().length() / 2) + "...", CYAN_COLOR);
    }
    //endregion

    //region [ - displaySubRedditBriefly(SubReddit subReddit) - ]
    public static void displaySubRedditBriefly(SubReddit subReddit) {
        System.out.printf("%s%s, Karma : %s, Date : %s%s\n", BLUE_COLOR, subReddit.getTitle(), subReddit.getDescription(), subReddit.getDate(), BLUE_COLOR);
        ArrayList<Post> posts = subReddit.getPosts();
        posts.sort(Comparator.comparing(Post::getDate));
        Collections.reverse(posts);
        for (Post post : posts) {
            displayPostBriefly(post);
        }
    }
    //endregion

    //region [ - displayMainMenu - ]

    //region [ - displayMainMenu() - ]
    public static void displayMainMenu() {
        System.out.print(BLUE_COLOR);
        System.out.print("1. Posts\n2. SubReddits\n3. Exit\nEnter command :  ");
        System.out.print(RESET_COLOR);
    }
    //endregion

    //region [ - displayMainMenu(User user) - ]
    public static void displayMainMenu(User user) {
        System.out.print(BLUE_COLOR);
        System.out.print("1. Posts\n2. SubReddits\n3. Exit\nEnter command :  ");
        System.out.print(RESET_COLOR);
    }
    //endregion

    //endregion

    //region [ - login() - ]
    public static void login() {
        System.out.println();
        displayReddit();
        System.out.printf("    %s%sLog in%s%s\n", GREEN_COLOR, BOLDON_STYLE, BOLDOFF_STYLE, RESET_COLOR);

        userService = new UserService();
        Scanner scanner = new Scanner(System.in);
        User user = new User(new Account());

        System.out.print("Email :  ");
        user.getAccount().setEmail(scanner.next());

        scanner.nextLine();

        System.out.print("Password :  ");
        user.getAccount().setPassword(scanner.next());

        User loggedInUser = userService.logIn(user);
        if (loggedInUser == null) {
            System.out.printf("%sNo account found! Please check your email or password%s\n", RED_COLOR, RESET_COLOR);
            System.out.printf("\n%s0. Back\n1. Login\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
            String command = scanner.next();
            switch (command) {
                case "0" -> runMainPage();
                case "1" -> login();
                default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
            }
            System.out.println();
            runMainPage();
        } else {
            System.out.printf("%sYou logged in successfully!%s\n\n", GREEN_COLOR, RESET_COLOR);
            runMainPage(loggedInUser);
        }
    }
    //endregion

    //region [ - logout(User user) - ]
    public static void logout(User user) {
        userService = new UserService();
        userService.logOut(user);
        runMainPage();
    }
    //endregion

    //region [ - signUp() - ]
    public static void signUp() {
        System.out.println();
        displayReddit();
        System.out.printf("    %s%sSign Up%s%s\n", GREEN_COLOR, BOLDON_STYLE, BOLDOFF_STYLE, RESET_COLOR);

        userService = new UserService();
        Scanner scanner = new Scanner(System.in);
        User user = new User(new Account());

        System.out.print("Username :  ");
        user.setUsername(scanner.next());

        scanner.nextLine();

        System.out.print("Email :  ");
        user.getAccount().setEmail(scanner.next());

        scanner.nextLine();

        System.out.print("Password :  ");
        user.getAccount().setPassword(scanner.next());

        User signedUpInUser = userService.create(user);
        if (signedUpInUser == null) {
            System.out.printf("%sPlease check your entered information%s\n", RED_COLOR, RESET_COLOR);
            System.out.printf("\n%s0. Back\n1. Signup\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
            String command = scanner.next();
            switch (command) {
                case "0" -> runMainPage();
                case "1" -> signUp();
                default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
            }
            System.out.println();
            runMainPage();
        } else {
            System.out.printf("%sYou signed up successfully!%s\n\n", GREEN_COLOR, RESET_COLOR);
            runMainPage(signedUpInUser);
        }
    }
    //endregion

    //region [ - createSubReddit(User user) - ]
    public static void createSubReddit(User user) {
        subRedditService = new SubRedditService();
        SubReddit subReddit = new SubReddit();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Title :  ");
        subReddit.setTitle(scanner.next());

        scanner.nextLine();

        System.out.print("Description :  ");
        subReddit.setDescription(scanner.nextLine());

        SubReddit createdSubReddit = subRedditService.create(subReddit, user);
        if (createdSubReddit == null) {
            System.out.printf("%sPlease choose another title%s\n", RED_COLOR, RESET_COLOR);
            System.out.printf("\n%s0. Back\n1. Create SubReddit\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
            String command = scanner.next();
            switch (command) {
                case "0" -> displayAllSubReddits(user);
                case "1" -> createSubReddit(user);
                default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
            }
            System.out.println();
            runMainPage();
        } else {
            System.out.printf("%sSubReddit created successfully !%s\n\n", GREEN_COLOR, RESET_COLOR);
            displayAllSubReddits(user);
        }
    }
    //endregion

    public static void joinSubReddit(User user) {
        System.out.println("Enter the number of SubReddit you wanna join or 0 to go back :  ");
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();
        switch (command) {
            case "0":
                displayAllSubReddits(user);
                break;
            default:
                Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
                if (pattern.matcher(command).matches()) {
                    int intCommand = Integer.parseInt(command);
                    SubReddit subReddit = subRedditService.get().get(intCommand - 1);
                    if (subReddit.getCreator().getId().equals(user.getId())) {
                        System.out.printf("%sYou selected your own subreddit !%s", RED_COLOR, RESET_COLOR);
                        displayAllSubReddits(user);
                    }
                    subRedditService = new SubRedditService();
                    subRedditService.join(subReddit, user);
                } else {
                    System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
                    System.out.println();
                    displayAllSubReddits(user);
                }
        }
    }

    //endregion
}