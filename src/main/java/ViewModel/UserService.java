package ViewModel;

import Model.DTO.*;
import Model.Repository.AccountRepository;
import Model.Repository.UserRepository;
import Model.Repository.UserSubRedditRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

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

    private UserRepository userRepository;

    private AccountRepository accountRepository;

    private UserSubRedditRepository userSubRedditRepository;

    private User user;

    public User getUser() {
        return user;
    }

    //endregion

    //region [ - Constructor - ]

    //region [ - UserService() - ]
    public UserService() {
        userRepository = new UserRepository();
        accountRepository = new AccountRepository();
        userSubRedditRepository = new UserSubRedditRepository();
    }
    //endregion

    //region [ - UserService(User user) - ]
    public UserService(User user) {
        userRepository = new UserRepository();
        accountRepository = new AccountRepository();
        this.user = user;
    }
    //endregion

    //endregion

    //region [ - Methods - ]

    //region [ - validate(User user) - ]
    public boolean validate(User user) {
        return validateUsername(user.getUsername()) && validateEmail(user.getAccount().getEmail()) && validatePassword(user.getAccount().getPassword());
    }
    //endregion

    //region [ - get() - ]
    public ArrayList<User> get() {
        ArrayList<User> users = userRepository.select();
        for (User u : users) {
            ArrayList<SubReddit> joinedSubReddits = new ArrayList<>();
            ArrayList<SubReddit> ownSubReddits = new ArrayList<>();
            for (UserSubReddit usr : userSubRedditRepository.select()) {
                if (usr.getUser().getId().equals(u.getId())) {
                    if (usr.getIsOwned()) {
                        ownSubReddits.add(usr.getSubReddit());
                    } else {
                        joinedSubReddits.add(usr.getSubReddit());
                    }
                }
            }
            u.setJoinedSubReddits(joinedSubReddits);
            u.setOwnSubReddits(ownSubReddits);
        }
        return users;
    }
    //endregion

    //region [ - getById(UUID id) - ]
    public User getById(UUID id) {
        return userRepository.select(id);
    }
    //endregion

    //region [ - create(User user) - ]
    public User create(User user) {
        if (validate(user)) {
            userRepository.insert(user);
            user.getAccount().setHasLoggedIn(true);
            this.user = user;
            return user;
        }
        return null;
    }
    //endregion

    //region [ - delete(User user) - ]
    public void remove(User user) {
        userRepository.delete(user);
    }
    //endregion

    //region [ - edit(User editedUser) - ]
    public void edit(User editedUser) {
        if (!user.getAccount().getHasLoggedIn()) {
            System.out.println("You haven't logged in");
            return;
        }
        if (editedUser.getUsername() != null) user.setUsername(editedUser.getUsername());
        if (editedUser.getFirstName() != null) user.setFirstName(editedUser.getFirstName());
        if (editedUser.getLastName() != null) user.setLastName(editedUser.getLastName());
        if (editedUser.getAge() != 0) user.setAge(editedUser.getAge());
        if (editedUser.getAccount().getEmail() != null) {
            if (editedUser.getAccount().getPassword() != null) {
                user.setAccount(new Account(user.getAccount().getId(), editedUser.getAccount().getEmail(), editedUser.getAccount().getPassword()));
            } else {
                user.setAccount(new Account(user.getAccount().getId(), editedUser.getAccount().getEmail(), user.getAccount().getPassword()));
            }
        } else {
            if (editedUser.getAccount().getPassword() != null) {
                user.setAccount(new Account(user.getAccount().getId(), user.getAccount().getEmail(), editedUser.getAccount().getPassword()));
            } else {
                user.setAccount(new Account(user.getAccount().getId(), user.getAccount().getEmail(), user.getAccount().getPassword()));
            }
        }

        userRepository.update(user);
    }
    //endregion

    //region [ - logIn(User user) - ]
    public User logIn(User user) {
        for (User u : get()) {
            Account a = u.getAccount();
            if (Objects.equals(user.getAccount().getEmail(), a.getEmail()) && Objects.equals(user.getAccount().getPassword(), a.getPassword())) {
                a.setHasLoggedIn(true);
                this.user = u;
                return u;
            }
        }
        return null;
    }
    //endregion

    //region [ - logOut(User user) - ]
    public void logOut(User user) {
        user.getAccount().setHasLoggedIn(false);
    }
    //endregion

    //region [ - validatePassword(String enteredPassword) - ]
    private boolean validatePassword(String enteredPassword) {
        String passwordRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,17}$";

        Pattern pattern = Pattern.compile(passwordRegEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(enteredPassword);

        if (!matcher.matches()) System.out.printf("%sInvalid password !%s\n", RED_COLOR, RESET_COLOR);

        return matcher.matches();
    }

    //endregion

    // region [ - validateEmail(String enteredEmail) - ]
    private boolean validateEmail(String enteredEmail) {
        Pattern validEmailRegEx = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailRegEx.matcher(enteredEmail);

        if (!matcher.matches()) System.out.printf("%sInvalid email !%s\n", RED_COLOR, RESET_COLOR);

        return matcher.matches();
    }
    //endregion

    // region [ - validateUsername(String username) - ]
    private boolean validateUsername(String username) {
        for (User user : get()) {
            if (Objects.equals(user.getUsername(), username)) {
                System.out.printf("%sThis username is taken !%s\n", RED_COLOR, RESET_COLOR);
                return false;
            }
        }
        return true;
    }
    //endregion

    //endregion

}
