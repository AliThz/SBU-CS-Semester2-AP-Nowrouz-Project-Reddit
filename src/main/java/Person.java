import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Person {

    //region [ - Fields - ]

    private UUID id;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    private String username;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    private String firstName;
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String lastName;
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    private UUID accountId;
    public UUID getAccountId() {
        return accountId;
    }
    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    private Account account;
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    private LocalDate signUpDate;
    public void setSignUpDate(LocalDate signUpDate) {
        this.signUpDate = signUpDate;
    }
    public LocalDate getSignUpDate() {
        return signUpDate;
    }

    private String information;
    public String getInformation() {
        return String.format("%s;%s;%s;%s;%d;%s;%s;\n", id, username, firstName, lastName, age, account.getId(), signUpDate);
    }

    //endregion

    //region [ - Constructors - ]

    //region [ - Person(String username, String firstName, String lastName, int age, Account account) - ]
    public Person(String username, String firstName, String lastName, int age, Account account) {
        id = UUID.randomUUID();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.account = account;
        signUpDate = LocalDate.now();
    }
    //endregion

    //region [ - Person(Account account) - ]
    public Person(Account account) {
        id = UUID.randomUUID();
        this.account = account;
        signUpDate = LocalDate.now();
    }
    //endregion

    //region [ - Person() - ]
    public Person() {
    }
    //endregion

    //region [ - Method - ]

    //region [ - editPersonalInformation(Person person) - ]
    public void editPersonalInformation(Person person) {
        if (!account.getHasLoggedIn()) {
            System.out.println("You haven't logged in");
            return;
        }
        if (person.firstName != null) firstName = person.firstName;
        if (person.lastName != null) lastName = person.lastName;
        if (person.age != 0) age = person.age;
        if (person.account.getEmail() != null) account.setEmail(person.account.getEmail());
        if (person.account.getPassword() != null) account.setPassword(person.account.getPassword());
    }
    //endregion

    //endregion

}
