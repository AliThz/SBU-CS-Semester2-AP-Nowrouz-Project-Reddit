import Model.DTO.*;
import Model.Repository.SubRedditRepository;
import ViewModel.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    //region { --- Common --- }

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
        System.out.println();
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
            case "0" -> search(null);
            case "1" -> displayAllPosts(null);
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
        System.out.println();
        displayHeader(user);
        displayTimeline(user);
        displayMainMenu(user);

        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();

        switch (command) {
            case "l" -> logout(user);
            case "p1" -> displayFromTimeline(user, 0);
            case "p2" -> displayFromTimeline(user, 1);
            case "p3" -> displayFromTimeline(user, 2);
            case "p4" -> displayFromTimeline(user, 3);
            case "p5" -> displayFromTimeline(user, 4);
            case "0" -> search(user);
            case "1" -> displayAllPosts(user);
            case "2" -> displayAllSubReddits(user);
            case "3" -> displayMyPosts(user);
            case "4" -> displayMySubReddits(user);
            case "5" -> System.exit(0);
            default -> System.out.println(RED_COLOR + "Enter a correct command !" + RESET_COLOR);
        }

        System.out.println();
        runMainPage(user);
    }
    //endregion

    //endregion

    //region [ - search() - ]
    public static void search(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sSearch\n%s", GREEN_COLOR, RESET_COLOR);
        System.out.printf("%sSearch for users and subreddits here !\nusernames start with u/ and subreddits start with r/ and enter 0 to go back\nSearch :  %s", PURPLE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String search = scanner.next();
        System.out.println();

        if (Objects.equals(search, "0")) {

            if (user != null)
                runMainPage(user);
            else runMainPage();

        } else if (search.toCharArray()[1] == '/') {

            if (search.toCharArray()[0] == 'u') {
                userService = new UserService();
                ArrayList<User> users = userService.get().stream().filter(u -> u.getUsername().contains(search.substring(2))).collect(Collectors.toCollection(ArrayList<User>::new));
                displayUserSearchResult(user, users);
            } else if (search.toCharArray()[0] == 'r') {
                subRedditService = new SubRedditService();
                ArrayList<SubReddit> subReddits = subRedditService.get().stream().filter(u -> u.getTitle().contains(search.substring(2))).collect(Collectors.toCollection(ArrayList<SubReddit>::new));
                displaySubRedditSearchResult(user, subReddits);
            } else {
                System.out.printf("%sNo result found !\nPlease search based on the given pattern !%s", RED_COLOR, RESET_COLOR);
                search(user);
            }
        } else {
            System.out.printf("%sNo result found !\nPlease search based on the given pattern !%s", RED_COLOR, RESET_COLOR);
            search(user);
        }
//        search(user);
    }
    //endregion

    //region [ - displayUserSearchResult(User user, ArrayList<User> users) - ]
    public static void displayUserSearchResult(User user, ArrayList<User> users) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sUser Search Result\n%s", GREEN_COLOR, RESET_COLOR);
        int counter = 0;
        for (User u : users) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displayUserBriefly(u);
        }
        User chosenUser = chooseUser(user, users, Main::search);
        displayUserCompletely(user, chosenUser, u -> displayUserSearchResult(u, users));
    }
    //endregion

    //region [ - displaySubRedditSearchResult(User user, ArrayList<SubReddit> subReddits) - ]
    public static void displaySubRedditSearchResult(User user, ArrayList<SubReddit> subReddits) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sSubReddit Search Result\n%s", GREEN_COLOR, RESET_COLOR);
        int counter = 0;
        for (SubReddit sr : subReddits) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displaySubRedditBriefly(sr);
        }
        SubReddit subReddit = chooseSubReddit(user, subReddits, Main::search);
        displaySubRedditCompletely(user, subReddit, u -> displaySubRedditSearchResult(u, subReddits));
    }
    //endregion

    //region [ - displayMainMenu - ]

    //region [ - displayMainMenu() - ]
    public static void displayMainMenu() {
        System.out.print(BLUE_COLOR);
        System.out.print("0. Search\n1. Posts\n2. SubReddits\n3. Exit\nEnter command :  ");
        System.out.print(RESET_COLOR);
    }
    //endregion

    //region [ - displayMainMenu(User user) - ]
    public static void displayMainMenu(User user) {
        System.out.print(BLUE_COLOR);
        System.out.print("0. Search\n1. All Posts\n2. All SubReddits\n3. My Posts\n4. My SubReddits\n5. Exit\nEnter command :  ");
        System.out.print(RESET_COLOR);
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

    //endregion

    //region { --- User --- }

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
            System.out.println();
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

    //region [ - displayUserBriefly(User user) - ]
    public static void displayUserBriefly(User user) {
        System.out.printf("%sUsername : %s, Here From : %s%s\n", BLUE_COLOR, user.getUsername(), user.getSignUpDate(), BLUE_COLOR);
        ArrayList<SubReddit> subReddits = user.getOwnSubReddits();
        if (subReddits != null) {
            subReddits.sort(Comparator.comparing(SubReddit::getDate));
            Collections.reverse(subReddits);
            for (SubReddit subReddit : subReddits) {
                displaySubRedditBriefly(subReddit);
            }
        }

    }
    //endregion

    //region [ - displayUserCompletely(User user, User chosenUser, Consumer<User> method) - ]
    public static void displayUserCompletely(User user, User chosenUser, Consumer<User> method) {
        displayReddit();
        System.out.printf("    %sUser%s\n", GREEN_COLOR, RESET_COLOR);
        System.out.printf("%sUsername : %s\n%sName : %s %s\n%sAge : %s\n", PURPLE_COLOR, chosenUser.getUsername(), PURPLE_COLOR, chosenUser.getFirstName(), chosenUser.getLastName(), BLUE_COLOR, chosenUser.getAge());

        int counter = 0;
        ArrayList<SubReddit> subReddits = chosenUser.getOwnSubReddits();
        for (SubReddit sr : subReddits) {
            counter++;
            System.out.printf("%s%d.", CYAN_COLOR, counter);
            displaySubRedditBriefly(sr);
        }

        System.out.printf("%sEnter the number of subreddit you wanna choose or 0 to go back :  ", RESET_COLOR);
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();
        System.out.println();
        if (command.equals("0")) {
            method.accept(user);
        } else {
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if (pattern.matcher(command).matches()) {
                int intCommand = Integer.parseInt(command);
                if (intCommand <= subReddits.size()) {
                    subRedditService = new SubRedditService();
                    SubReddit subReddit = subRedditService.getById(subReddits.get(intCommand - 1).getId());
                    displaySubRedditCompletely(user, subReddit, method);
                } else {
                    System.out.printf("%sEnter a correct command ! !\n%s", GREEN_COLOR, RESET_COLOR);
                    System.out.println();
                    displayUserCompletely(user, chosenUser, method);
                }
            } else {
                System.out.printf("%sEnter a correct command !\n%s", RED_COLOR, RESET_COLOR);
                System.out.println();
                displayUserCompletely(user, chosenUser, method);
            }
        }
        displayUserCompletely(user, chosenUser, method);
    }
    //endregion

    //region [ - chooseUser(User user, ArrayList<User> users, Consumer<User> goBack) - ]
    public static User chooseUser(User user, ArrayList<User> users, Consumer<User> goBack) {
        System.out.print("Enter the number of user you wanna choose or 0 to go back :  ");
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();
        System.out.println();
        if (command.equals("0")) {
            goBack.accept(user);
        } else {
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if (pattern.matcher(command).matches()) {
                int intCommand = Integer.parseInt(command);
                if (intCommand <= users.size()) {
                    return users.get(intCommand - 1);
                } else {
                    System.out.printf("%sEnter a correct command !%s\n", RED_COLOR, RESET_COLOR);
                    System.out.println();
                    goBack.accept(user);
                }
            } else {
                System.out.printf("%sEnter a correct command !%s\n", RED_COLOR, RESET_COLOR);
                System.out.println();
                goBack.accept(user);
            }
        }
        return null;
    }
    //endregion

    //endregion

    //region { --- SubReddit --- }

    //region [ - displayAllSubReddits - ]

    //region [ - displayAllSubReddits() - ]
    public static void displayAllSubReddits() {
        System.out.println();
        displayReddit();
        System.out.printf("    %sSubReddits\n%s", GREEN_COLOR, RESET_COLOR);
        subRedditService = new SubRedditService();
        ArrayList<SubReddit> subReddits = subRedditService.get();
        int counter = 0;
        for (SubReddit sr : subReddits) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displaySubRedditBriefly(sr);
        }
        SubReddit subReddit = chooseSubReddit(null, subReddits, u -> runMainPage());
        displaySubRedditCompletely(null, subReddit, u -> displayAllSubReddits());

    }
    //endregion

    //region [ - displayAllSubReddits(User user) - ]
    public static void displayAllSubReddits(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sSubReddits\n%s", GREEN_COLOR, RESET_COLOR);
        subRedditService = new SubRedditService();
        ArrayList<SubReddit> subReddits = subRedditService.get();
        int counter = 0;
        for (SubReddit sr : subReddits) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displaySubRedditBriefly(sr);
        }
        SubReddit subReddit = chooseSubReddit(user, subReddits, u -> runMainPage(u));
        displaySubRedditCompletely(user, subReddit, u -> displayAllSubReddits(u));
    }
    //endregion

    //endregion

    //region [ - displayMySubReddits(User user) - ]
    public static void displayMySubReddits(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sMy SubReddits%s\n", GREEN_COLOR, RESET_COLOR);
        System.out.printf("%s0. Back\n1. My Own SubReddits\n2. Joined SubReddits\nEnter your choice :  %s", BLUE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        System.out.println();
        switch (command) {
            case "0" -> runMainPage(user);
            case "1" -> displayOwnedSubReddits(user);
            case "2" -> displayJoinedSubReddits(user);
            default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
        }
        System.out.println();
        runMainPage(user);
    }

    //endregion

    //region [ - displayOwnedSubReddits(User user) - ]
    public static void displayOwnedSubReddits(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sMy Own SubReddits%s\n", GREEN_COLOR, RESET_COLOR);

        subRedditService = new SubRedditService();

        ArrayList<SubReddit> subReddits = subRedditService.getCreatedSubReddit(user);
        int counter = 0;
        for (SubReddit sr : subReddits) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displaySubRedditBriefly(sr);
        }

        System.out.printf("\n%s0. Back\n1. Open\n2. Create\n3. Edit\n4. Remove\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        System.out.println();
        switch (command) {
            case "0":
                displayMySubReddits(user);
                break;
            case "1":
                SubReddit subReddit = chooseSubReddit(user, subReddits, Main::displayOwnedSubReddits);
                displaySubRedditCompletely(user, subReddit, Main::displayOwnedSubReddits);
                break;
            case "2":
                System.out.print("Dou wanna create a subreddit ? (y/n) ");
                command = scanner.next();
                if (Objects.equals(command, "y"))
                    createSubReddit(user);
                else displayOwnedSubReddits(user);
                break;
            case "3":
                subReddit = chooseSubReddit(user, subReddits, Main::displayOwnedSubReddits);
                editSubReddit(subReddit, user);
                break;
            case "4":
                subReddit = chooseSubReddit(user, subReddits, Main::displayOwnedSubReddits);
                removeSubReddit(subReddit, user);
                break;
            default:
                System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
        }
        System.out.println();
        displayOwnedSubReddits(user);
    }

    //endregion

    //region [ - chooseSubReddit(User user, ArrayList<SubReddit> subReddits, Consumer<User> goBack) - ]
    public static SubReddit chooseSubReddit(User user, ArrayList<SubReddit> subReddits, Consumer<User> goBack) {
        System.out.printf("\n%sEnter the number of SubReddit you wanna choose or 0 to go back :  ", RESET_COLOR);
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();
        System.out.println();
        if (command.equals("0")) {
            goBack.accept(user);
        } else {
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if (pattern.matcher(command).matches()) {
                int intCommand = Integer.parseInt(command);
                if (intCommand <= subReddits.size()) {
                    return subReddits.get(intCommand - 1);
                } else {
                    System.out.printf("%sEnter a correct command !%s\n", RED_COLOR, RESET_COLOR);
                    System.out.println();
                    goBack.accept(user);
                }
            } else {
                System.out.printf("%sEnter a correct command !%s\n", RED_COLOR, RESET_COLOR);
                System.out.println();
                goBack.accept(user);
            }
        }
        return null;
    }

    //endregion

    //region [ - displayJoinedSubReddits(User user) - ]
    public static void displayJoinedSubReddits(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sMy Joined SubReddits%s\n", GREEN_COLOR, RESET_COLOR);

        subRedditService = new SubRedditService();

        ArrayList<SubReddit> subReddits = subRedditService.getJoinedSubReddit(user);
        int counter = 0;
        for (SubReddit sr : subReddits) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displaySubRedditBriefly(sr);
        }

        System.out.printf("\n%s0. Back\n1. Open\n2. Leave\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        System.out.println();
        switch (command) {
            case "0":
                displayMySubReddits(user);
                break;
            case "1":
                SubReddit subReddit = chooseSubReddit(user, subReddits, Main::displayJoinedSubReddits);
                displaySubRedditCompletely(user, subReddit, Main::displayJoinedSubReddits);
                break;
            case "2":
                subReddit = chooseSubReddit(user, subReddits, Main::displayJoinedSubReddits);
                leaveSubReddit(user, subReddit);
                break;
            default:
                System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
        }
        System.out.println();
        displayJoinedSubReddits(user);
    }

    //endregion

    //region [ - displaySubRedditCompletely(User user, SubReddit subReddit, , Consumer<User> method) - ]
    public static void displaySubRedditCompletely(User user, SubReddit subReddit, Consumer<User> method) {
        displayReddit();
        System.out.printf("    %sSubReddit%s\n", GREEN_COLOR, RESET_COLOR);
        System.out.printf("%sTitle : %s\n%sby %s\n%sDescription : %s\n", PURPLE_COLOR, subReddit.getTitle(), PURPLE_COLOR, subReddit.getCreator().getUsername(), BLUE_COLOR, subReddit.getDescription());
//        subReddit.getPosts().forEach(p -> System.out.printf("%s%s\n%s%s\n%s%s", CYAN_COLOR, p.getCreator().getUsername(),p.getTitle(), p.getMessage(), RESET_COLOR));
        int counter = 0;
        ArrayList<Post> posts = subReddit.getPosts();
        for (Post p : posts) {
            counter++;
            System.out.printf("%s%d.", CYAN_COLOR, counter);
            displayPostBriefly(p);
        }

        System.out.printf("%sEnter the number of post you wanna choose or 0 to go back :  ", RESET_COLOR);
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();
        System.out.println();
        if (command.equals("0")) {
            method.accept(user);
        } else {
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if (pattern.matcher(command).matches()) {
                int intCommand = Integer.parseInt(command);
                if (intCommand <= posts.size()) {
                    displayPostCompletely(user, posts.get(intCommand - 1));
                } else {
                    System.out.printf("%sEnter a correct command ! !\n%s", GREEN_COLOR, RESET_COLOR);
                    System.out.println();
                    displaySubRedditCompletely(user, subReddit, method);
                }
            } else {
                System.out.printf("%sEnter a correct command !\n%s", RED_COLOR, RESET_COLOR);
                System.out.println();
                displaySubRedditCompletely(user, subReddit, method);
            }
        }
        displaySubRedditCompletely(user, subReddit, method);
    }
    //endregion

    //region [ - displaySubRedditBriefly(SubReddit subReddit) - ]
    public static void displaySubRedditBriefly(SubReddit subReddit) {
        System.out.printf("%sTitle : %s, Description : %s, Date : %s%s\n", BLUE_COLOR, subReddit.getTitle(), subReddit.getDescription(), subReddit.getDate(), BLUE_COLOR);
        ArrayList<Post> posts = subReddit.getPosts();
        if (posts != null) {
            posts.sort(Comparator.comparing(Post::getDate));
            Collections.reverse(posts);
            for (Post post : posts) {
                displayPostBriefly(post);
            }
        }
    }
    //endregion

    //region [ - createSubReddit(User user) - ]
    public static void createSubReddit(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sCreating SubReddit%s", GREEN_COLOR, RESET_COLOR);

        subRedditService = new SubRedditService();
        SubReddit subReddit = new SubReddit();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Title :  ");
        subReddit.setTitle(scanner.nextLine());

        System.out.print("Description :  ");
        subReddit.setDescription(scanner.nextLine());

        SubReddit createdSubReddit = subRedditService.create(subReddit, user);
        if (createdSubReddit == null) {
            System.out.printf("%sPlease choose another title%s\n", RED_COLOR, RESET_COLOR);
            System.out.printf("\n%s0. Back\n1. Create SubReddit\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
            String command = scanner.next();
            System.out.println();
            switch (command) {
                case "0" -> displayMySubReddits(user);
                case "1" -> createSubReddit(user);
                default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
            }
            System.out.println();
            runMainPage(user);
        } else {
            System.out.printf("%sSubReddit created successfully !%s\n\n", GREEN_COLOR, RESET_COLOR);
            displayOwnedSubReddits(user);
        }
    }
    //endregion

    //region [ - editSubReddit(SubReddit subReddit, User user) - ]
    public static void editSubReddit(SubReddit subReddit, User user) {
        subRedditService = new SubRedditService();

        Scanner scanner = new Scanner(System.in);
        System.out.printf("Title ( old : %s ) :  ", subReddit.getTitle());
        subReddit.setTitle(scanner.nextLine());

        System.out.printf("Description ( old : %s ) :  ", subReddit.getDescription());
        subReddit.setDescription(scanner.nextLine());

        SubReddit createdSubReddit = subRedditService.edit(subReddit, user);
        if (createdSubReddit == null) {
            System.out.printf("%sPlease choose another title%s\n", RED_COLOR, RESET_COLOR);
            System.out.printf("\n%s0. Back\n1. Edit SubReddit\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
            String command = scanner.next();
            System.out.println();
            switch (command) {
                case "0" -> displayOwnedSubReddits(user);
                case "1" -> editSubReddit(subReddit, user);
                default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
            }
            System.out.println();
            runMainPage();
        } else {
            System.out.printf("%sSubReddit created successfully !%s\n\n", GREEN_COLOR, RESET_COLOR);
            displayOwnedSubReddits(user);
        }
    }
    //endregion

    //region [ - removeSubReddit(SubReddit subReddit, User user) - ]
    public static void removeSubReddit(SubReddit subReddit, User user) {
        if (subReddit.getCreator().getId().equals(user.getId())) {
            subRedditService = new SubRedditService();
            subRedditService.remove(subReddit, user);
        } else {
            System.out.printf("%sYou cant remove this subreddit !\n%s", RED_COLOR, RESET_COLOR);
        }
    }
    //endregion

    //region [ - joinSubReddit(User user) - ]
    public static void joinSubReddit(User user) {
        System.out.print("Enter the number of SubReddit you wanna join or 0 to go back :  ");
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();
        if (command.equals("0")) {
            displayAllSubReddits(user);
        } else {
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if (pattern.matcher(command).matches()) {
                int intCommand = Integer.parseInt(command);
                SubReddit subReddit = subRedditService.get().get(intCommand - 1);
                if (subReddit.getCreator().getId().equals(user.getId())) {
                    System.out.printf("%sYou selected your own subreddit !%s\n", RED_COLOR, RESET_COLOR);
                    displayAllSubReddits(user);
                } else if (user.getJoinedSubReddits().contains(subReddit)) {
                    System.out.printf("%sYou have already joined this subreddit !%s\n", RED_COLOR, RESET_COLOR);
                } else {
                    subRedditService = new SubRedditService();
                    subRedditService.join(subReddit, user);
                    System.out.printf("%sYou have joined the subreddit successfully !\n%s", GREEN_COLOR, RESET_COLOR);
                }
            } else {
                System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
                System.out.println();
                displayAllSubReddits(user);
            }
        }
    }
    //endregion

    //region [ - leaveSubReddit(User user) - ]
    public static void leaveSubReddit(User user) {
        System.out.print("Enter the number of SubReddit you wanna leave or 0 to go back :  ");
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();
        if (command.equals("0")) {
            displayAllSubReddits(user);
        } else {
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if (pattern.matcher(command).matches()) {
                int intCommand = Integer.parseInt(command);
                SubReddit subReddit = subRedditService.get().get(intCommand - 1);
                if (subReddit.getCreator().getId().equals(user.getId())) {
                    System.out.printf("%sYou selected your own subreddit !%s\n", RED_COLOR, RESET_COLOR);
                    displayAllSubReddits(user);
                } else if (user.getJoinedSubReddits().stream().filter(sr -> sr.getId().equals(subReddit.getId())).findAny().isEmpty()) {
                    System.out.printf("%sYou haven't already joined this subreddit yet !%s\n", RED_COLOR, RESET_COLOR);
                } else {
                    subRedditService = new SubRedditService();
                    subRedditService.leave(new UserSubReddit(user, subReddit));
                    System.out.printf("%sYou have left the subreddit successfully !\n%s", GREEN_COLOR, RESET_COLOR);
                }
            } else {
                System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
                System.out.println();
                displayAllSubReddits(user);
            }
        }
    }
//endregion

    //region [ - leaveSubReddit(User user, SubReddit subReddit) - ]
    public static void leaveSubReddit(User user, SubReddit subReddit) {

        if (subReddit.getCreator().getId().equals(user.getId())) {
            System.out.printf("%sYou selected your own subreddit !%s\n", RED_COLOR, RESET_COLOR);
            displayAllSubReddits(user);
        } else if (user.getJoinedSubReddits().stream().filter(sr -> sr.getId().equals(subReddit.getId())).findAny().isEmpty()) {
            System.out.printf("%sYou haven't already joined this subreddit yet !%s\n", RED_COLOR, RESET_COLOR);
        } else {
            subRedditService = new SubRedditService();
            subRedditService.leave(new UserSubReddit(user, subReddit));
            System.out.printf("%sYou have left the subreddit successfully !\n%s", GREEN_COLOR, RESET_COLOR);
        }

    }
    //endregion

    //endregion

    //region { --- Post --- }

    //region [ - displayTimeline - ]

    //region [ - displayTimeline() - ]
    public static void displayTimeline() {
        System.out.print(CYAN_COLOR);
        System.out.println("\nExplore the newest posts on reddit ...\n(5 Newest Posts :)");
        postService = new PostService();
        ArrayList<Post> posts = postService.getByDate();

        int i = 0;
        for (Post post : posts) {
            System.out.print("p" + (i + 1) + ". ");
            displayPostBriefly(post);
            i++;
            if (i >= 5)
                break;
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

        int i = 0;
        for (Post post : posts) {
            System.out.print("p" + (i + 1) + ". ");
            displayPostBriefly(post);
            i++;
            if (i >= 5)
                break;
        }

        System.out.println("...");
        System.out.print(RESET_COLOR);
    }
//endregion

//endregion

    //region [ - displayAllPosts(User user) - ]
    public static void displayAllPosts(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sPosts%s\n", GREEN_COLOR, RESET_COLOR);
        postService = new PostService();
        commentService = new CommentService();
//        postService.get().forEach(Main::displayPostBriefly);
        int counter = 0;
        ArrayList<Post> posts = postService.getByDate();
        for (Post p : posts) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displayPostBriefly(p);
        }

        System.out.printf("\n%sEnter the number of post you wanna see or 0 to go back :  %s", WHITE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();
        if (command.equals("0")) {
            if (user != null) runMainPage(user);
            else runMainPage();
        } else {
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if (pattern.matcher(command).matches()) {
                int intCommand = Integer.parseInt(command);
                if (intCommand <= posts.size()) {
                    Post post = posts.get(intCommand - 1);
                    displayPostCompletely(post);

                    String postCommand;

                    if (user != null) {
                        System.out.printf("%s0. Back\n1. Comment\n2. Up Vote\n3. Down Vote\n%s", WHITE_COLOR, RESET_COLOR);
                        postCommand = scanner.nextLine();
                        switch (postCommand) {
                            case "0" -> displayAllPosts(user);
                            case "1" -> leaveComment(user, post);
                            case "2" -> postService.vote(post, user, true);
                            case "3" -> postService.vote(post, user, false);
                        }
                    } else {
                        System.out.printf("%s\n0. Back\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
                        postCommand = scanner.nextLine();
                        System.out.println();
                        displayAllPosts(user);
                    }

                } else {
                    System.out.printf("%sEnter a correct command ! !\n%s", GREEN_COLOR, RESET_COLOR);
                    System.out.println();
                    displayAllPosts(user);
                }
            } else {
                System.out.printf("%sEnter a correct command !\n%s", RED_COLOR, RESET_COLOR);
                System.out.println();
                displayAllPosts(user);
            }
        }
    }
//endregion

    //region [ - displayFromTimeline - ]

    //region [ - displayFromTimeline(int index) - ]
    public static void displayFromTimeline(int index) {
        System.out.println();
        postService = new PostService();
        displayPostCompletely(postService.getTimeline().get(index));
        System.out.printf("\n%s0. Back\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String postCommand = scanner.next();
        System.out.println();
        switch (postCommand) {
            case "0" -> runMainPage();
            default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
        }
        System.out.println();
        runMainPage();
    }
//endregion

    //region [ - displayFromTimeline(User user, int index) - ]
    public static void displayFromTimeline(User user, int index) {
        System.out.println();
        postService = new PostService();
        displayPostCompletely(postService.getTimeline(user).get(index));
        System.out.printf("\n%s0. Back\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String postCommand = scanner.next();
        System.out.println();
        switch (postCommand) {
            case "0" -> runMainPage(user);
            default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
        }
        System.out.println();
        runMainPage();
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

    // region [ - displayPostCompletely(User user, Post post) - ]
    public static void displayPostCompletely(User user, Post post) {
        displayReddit();
        System.out.printf("    %sPost%s\n", GREEN_COLOR, RESET_COLOR);
        System.out.printf("%sSubReddit : %s\n%sTitle : %s\n%s%s\n%sUpVotes : %s , Karma : %s , DownVotes : %s\n", PURPLE_COLOR, post.getSubReddit().getTitle(), BLUE_COLOR, post.getTitle(), CYAN_COLOR, post.getMessage(), YELLOW_COLOR, post.getUpVotes(), post.getKarma(), post.getDownVotes());
        post.getComments().forEach(c -> System.out.printf("%s%s\n%s%s", RESET_COLOR, RESET_COLOR, c.getCreator().getUsername(), c.getMessage()));

        if (user != null)
            System.out.printf("%s0. Back\n1. Comment\n2. Up Vote\n3. Down Vote\n4. Edit\n5. Remove\n\n%sEnter your desirable command :  ", WHITE_COLOR, RESET_COLOR);
        else System.out.printf("%s0. Back\n\n%sEnter your desirable command :  ", WHITE_COLOR, RESET_COLOR);

        postService = new PostService();
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        System.out.println();

        if (user != null)
            switch (command) {
                case "0" -> {
                }
                case "1" -> leaveComment(user, post);
                case "2" -> postService.vote(post, user, true);
                case "3" -> postService.vote(post, user, false);
                case "4" -> editPost(user, post);
                case "5" -> removePost(user, post);
                default -> System.out.println(RED_COLOR + "Enter a correct command !" + RESET_COLOR);
            }
        else
            switch (command) {
                case "0" -> {
                }
                default -> System.out.println(RED_COLOR + "Enter a correct command !" + RESET_COLOR);
            }

    }
    //endregion

    //region [ - editPost(User user, Post post) - ]
    public static void editPost(User user, Post post) {

    }
//endregion

    //region [ - removePost(User user, Post post) - ]
    public static void removePost(User user, Post post) {

    }
//endregion

    //region [ - displayPostBriefly(Post post) - ]
    public static void displayPostBriefly(Post post) {
        System.out.printf("%sTitle : %s, SubReddit : %s, Karma : %d, Date : %s, %s%s\n%s", CYAN_COLOR, post.getTitle(), post.getSubReddit().getTitle(), post.getKarma(), post.getDate(), WHITE_COLOR, post.getMessage().substring(0, post.getMessage().length() / 2) + "...", CYAN_COLOR);
    }
//endregion

    //region [ - choosePost(User user, ArrayList<SubReddit> subReddits, Consumer<User> goBack) - ]
    public static Post choosePost(User user, ArrayList<Post> posts, Consumer<User> goBack) {
        System.out.printf("\n%sEnter the number of post you wanna choose or 0 to go back :  ", RESET_COLOR);
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();
        System.out.println();
        if (command.equals("0")) {
            goBack.accept(user);
        } else {
            Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
            if (pattern.matcher(command).matches()) {
                int intCommand = Integer.parseInt(command);
                if (intCommand <= posts.size()) {
                    return posts.get(intCommand - 1);
                } else {
                    System.out.printf("%sEnter a correct command !%s\n", RED_COLOR, RESET_COLOR);
                    System.out.println();
                    goBack.accept(user);
                }
            } else {
                System.out.printf("%sEnter a correct command !%s\n", RED_COLOR, RESET_COLOR);
                System.out.println();
                goBack.accept(user);
            }
        }
        return null;
    }

    //endregion

    //region [ - createPost(User user - ]
    public static void createPost(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sCreating Post\n%s", GREEN_COLOR, RESET_COLOR);

        postService = new PostService();
        subRedditService = new SubRedditService();
        Post post = new Post();

        ArrayList<SubReddit> subReddits = Stream.concat(subRedditService.getCreatedSubReddit(user).stream(), subRedditService.getJoinedSubReddit(user).stream()).collect(Collectors.toCollection(ArrayList<SubReddit>::new));
        int counter = 0;
        for (SubReddit sr : subReddits) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displaySubRedditBriefly(sr);
        }
        post.setSubReddit(chooseSubReddit(user, subReddits, Main::createPost));

        post.setCreator(user);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Title :  ");
        post.setTitle(scanner.nextLine());

        System.out.print("Message :  ");
        post.setMessage(scanner.nextLine());

        Post createdPost = postService.create(post, user);
        if (createdPost == null) {
            System.out.printf("%sThe post can not be created !%s\n", RED_COLOR, RESET_COLOR);
            System.out.printf("\n%s0. Back\n1. Create Post\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
            String command = scanner.next();
            System.out.println();
            switch (command) {
                case "0" -> displayMyPosts(user);
                case "1" -> createPost(user);
                default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
            }
            System.out.println();
            runMainPage(user);
        } else {
            System.out.printf("%sPost created successfully !%s\n\n", GREEN_COLOR, RESET_COLOR);
            displayOwnedPosts(user);
        }
    }
    //endregion

    //region [ - displayMyPosts(User user) - ]
    public static void displayMyPosts(User user) {
        System.out.println();
        displayReddit();
        System.out.printf("    %sMy Posts%s\n", GREEN_COLOR, RESET_COLOR);
        System.out.printf("%s0. Back\n1. My Own Posts\n\nEnter your choice :  %s", BLUE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        System.out.println();
        switch (command) {
            case "0" -> runMainPage(user);
            case "1" -> displayOwnedPosts(user);
            default -> System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
        }
        System.out.println();
        runMainPage(user);
    }
    //endregion

    //region [ - displayOwnedPosts(User user) - ]
    public static void displayOwnedPosts(User user) {
        displayReddit();
        System.out.printf("    %sMy Own Posts%s\n", GREEN_COLOR, RESET_COLOR);

        postService = new PostService();

        ArrayList<Post> posts = postService.getByCreator(user);
        int counter = 0;
        for (Post p : posts) {
            counter++;
            System.out.printf("%s%d.", BLUE_COLOR, counter);
            displayPostBriefly(p);
        }

        System.out.printf("\n%s0. Back\n1. Open\n2. Create\n3. Edit\n4. Remove\nEnter your choice :  %s", WHITE_COLOR, RESET_COLOR);
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        System.out.println();
        switch (command) {
            case "0":
                displayMyPosts(user);
                break;
            case "1":
                Post post = choosePost(user, posts, Main::displayOwnedPosts);
//                displaySubRedditCompletely(user, subReddit, Main::displayOwnedSubReddits);
                displayPostCompletely(user, post);
                break;
            case "2":
                System.out.print("Dou wanna create a post ? (y/n) ");
                command = scanner.next();
                if (Objects.equals(command, "y"))
                    createPost(user);
                else displayOwnedPosts(user);
                break;
//            case "3":
//                subReddit = chooseSubReddit(user, subReddits, Main::displayOwnedSubReddits);
//                editSubReddit(subReddit, user);
//                break;
//            case "4":
//                subReddit = chooseSubReddit(user, subReddits, Main::displayOwnedSubReddits);
//                removeSubReddit(subReddit, user);
//                break;
            default:
                System.out.printf("%sEnter a correct command !%s", RED_COLOR, RESET_COLOR);
        }
        System.out.println();
        displayOwnedPosts(user);
    }
    //endregion

    //endregion

    //region { --- Comment --- }

    //region [ - leaveComment(User user, Post post) - ]
    public static void leaveComment(User user, Post post) {
        commentService = new CommentService();
        Scanner scanner = new Scanner(System.in);
        String title, message;
        System.out.print("Title :  ");
        title = scanner.nextLine();
//        scanner.nextLine();
        System.out.print("Message :  ");
        message = scanner.nextLine();
        Comment comment = new Comment(post.getSubReddit(), user, title, message, post);
        commentService.create(comment, user);
    }
//endregion

    //endregion

//endregion


}