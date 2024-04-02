import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {

    //region [ - Fields - ]
    private UUID id;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
            this.email = email;
    }

    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
            this.password = password;
    }

    private Boolean hasLoggedIn;
    public Boolean getHasLoggedIn() {
        return hasLoggedIn;
    }
    public void setHasLoggedIn(Boolean hasLoggedIn) {
        this.hasLoggedIn = hasLoggedIn;
    }

    private String information;
    public String getInformation() {
        return String.format("%s;%s;%s;%s;\n",id, email, password, hasLoggedIn);
    }

    //endregion

    //region [ - Constructor - ]

    //region [ - Account() - ]
    public Account() {
        id = UUID.randomUUID();
    }
    //endregion

    //region [ - Account(String email, String password) - ]
    public Account(String email, String password) {
        id = UUID.randomUUID();

        if (validateEmail(email)) {
            this.email = email;
        } else {
            System.out.println("!! Invalid Email !!");
            Scanner scanner = new Scanner(System.in);
            email = scanner.next();
            new Account(email, password);
        }

        if (validatePassword(password)) {
            this.password = password;
        } else {
            System.out.println("!! Invalid Password !!");
            Scanner scanner = new Scanner(System.in);
            password = scanner.next();
            new Account(email, password);
        }

        hasLoggedIn = true;
    }
    //endregion

    //region [ - Account(String email, String password) - ]
    public Account(UUID id, String email, String password) {
        this.id = id;

        if (validateEmail(email)) {
            this.email = email;
        } else {
            System.out.println("!! Invalid Email !!");
            Scanner scanner = new Scanner(System.in);
            email = scanner.next();
            new Account(email, password);
        }

        if (validatePassword(password)) {
            this.password = password;
        } else {
            System.out.println("!! Invalid Password !!");
            Scanner scanner = new Scanner(System.in);
            password = scanner.next();
            new Account(email, password);
        }

        hasLoggedIn = true;
    }
    //endregion

    //endregion

    //region [ - Methods - ]

    //region [ - logIn() - ]
//    public Account logIn(Account account) {
//        for (Account a : selectAccounts()) {
//            if (Objects.equals(account.email, a.email) && Objects.equals(account.password, a.password)) {
//                a.hasLoggedIn = true;
//                return a;
//            }
//        }
//        return null;
//    }
    //endregion

    //region [ - logOut() - ]
    public void logOut() {
        hasLoggedIn = false;
    }
    //endregion

    //region [ - validatePassword(String enteredPassword) - ]
    public boolean validatePassword(String enteredPassword) {
//        String passwordRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,17}$";
        String passwordRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,17}$";

        Pattern pattern = Pattern.compile(passwordRegEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(enteredPassword);

        return matcher.matches();

    }

    //endregion

    // region [ - validateEmail(String enteredEmail) - ]
    public boolean validateEmail(String enteredEmail) {
        Pattern validEmailRegEx = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailRegEx.matcher(enteredEmail);
        return matcher.matches();
    }
    //endregion

    //region [ - changeEmail(String newEmail) - ]
    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }
    //endregion

    //region [ - changePassword(String newPassword) - ]
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
    //endregion

    //endregion

}
