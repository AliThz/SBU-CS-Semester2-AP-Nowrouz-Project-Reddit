package ViewModel;

import Model.DTO.Account;
import Model.DTO.User;
import Model.Repository.AccountRepository;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountService {

    //region [ - Fields - ]

    private AccountRepository accountRepository;

    private Account account;

    //endregion

    //region [ - Constructor - ]

    //region [ - AccountService() - ]
    public AccountService() {
        accountRepository = new AccountRepository();
    }
    //endregion

    //region [ - AccountService() - ]
    public AccountService(Account account) {
        accountRepository = new AccountRepository();
        this.account = account;
    }
    //endregion

    //endregion

    //region [ - Method - ]

    //region [ - validate(Account account) - ]
    public boolean validate(Account account) {
        return  (validateEmail(account.getEmail()) && validatePassword(account.getPassword()));
    }
    //endregion


    //region [ - logIn() - ]
//    public Model.DTO.Account logIn(Model.DTO.Account account) {
//        for (Model.DTO.Account a : selectAccounts()) {
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
        account.setHasLoggedIn(false);
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

    //endregion
}
