import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class User extends Person {

    //region [ - Fields - ]

    private ArrayList<Post> posts;

    private ArrayList<Post> likedPosts;

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

    //region [ - Method - ]



    //endregion

}