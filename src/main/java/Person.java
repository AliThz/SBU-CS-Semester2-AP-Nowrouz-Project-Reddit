import java.util.ArrayList;
import java.util.UUID;

public class Person {

    //region [ - Fields - ]

    private final UUID Id;
    public UUID getHogwartsId() {
        return Id;
    }

    protected String firstName;
    protected String lastName;
    protected int age;
    protected Account account;
    protected Boolean hasLoggedIn;

    //endregion

    //region [ - Constructors - ]

    //region [ - Person(String firstName, String lastName, int age, Account account) - ]
    public Person(String firstName, String lastName, int age, Account account) {
        Id = UUID.randomUUID();
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.account = account;
        this.hasLoggedIn = true;
    }
    //endregion

    //region [ - Person(Account account) - ]
    public Person(Account account) {
        Id = UUID.randomUUID();
        this.account = account;
        this.hasLoggedIn = true;
    }
    //endregion

    //endregion

    //region [ - Method - ]

    //region [ - logOut() - ]
    public void logOut() {
        hasLoggedIn = false;
    }
    //endregion

    //endregion

}
