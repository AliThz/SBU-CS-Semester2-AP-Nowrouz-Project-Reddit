import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Account {

    //region [ - Fields - ]

    private String email;
    public String getUsername() {
        return email;
    }


    private String password;
    public String getPassword() {
        return password;
    }

    //endregion

    //region [ - Constructor - ]

    //region [ - Account(String email, String password) - ]
    public Account(String email, String password) {
        if (validateEmail(email)) {
            this.email = email;
        }
        else {
            System.out.println("!! Invalid Email !!");
            Scanner scanner = new Scanner(System.in);
            email = scanner.next();
            new Account(email, password);
        }

        if (validatePassword(password)) {
            this.password = password;
        }
        else {
            System.out.println("!! Invalid Password !!");
            Scanner scanner = new Scanner(System.in);
            password = scanner.next();
            new Account(email, password);
        }
    }
    //endregion

    //endregion

    //region [ - Methods - ]

    //region [ - validatePassword(String enteredPassword) - ]
    public boolean validatePassword(String enteredPassword) {
//        String passwordRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,17}$";
        String passwordRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,17}$";

        Pattern pattern = Pattern.compile(passwordRegEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(enteredPassword);

        return matcher.matches();

    }

    //endregion

    // region [ - validatePassword(String enteredPassword) - ]
    public boolean validateEmail(String enteredEmail) {
        Pattern validEmailRegEx = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validEmailRegEx.matcher(enteredEmail);
        return matcher.matches();
    }
    //endregion

    //region [ - changeUsername(String newUsername) - ]
    public void changeUsername(String newEmail) {
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
