package Model.Repository;

import Model.DTO.Account;
import Model.DTO.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class UserRepository implements IRepository<User, UUID> {

    //region [ - Field - ]

    private AccountRepository accountRepository;

    //endregion

    //region [ - Constructor - ]

    //region [ - UserRepository() - ]
    public UserRepository() {
        accountRepository = new AccountRepository();
    }
    //endregion

    //endregion

    //region [ - Methods - ]

    //region [ - insert(User user) - ]
    @Override
    public void insert(User user) {
        try {
            FileWriter fileWriter = new FileWriter("src/file/User.txt", true);
            fileWriter.write(user.getInformation());
            accountRepository.insert(user.getAccount());
            fileWriter.close();
            System.out.println("User successfully to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - insert(ArrayList<User> users) - ]
    @Override
    public void insert(ArrayList<User> users) {
        File file = new File("src/file/User.txt");
        if (file.delete()) {
            file = new File("src/file/User.txt");
        }
        for (User u : users) {
            try {
                FileWriter fileWriter = new FileWriter("src/file/User.txt", true);
                fileWriter.write(u.getInformation());
                fileWriter.close();
                System.out.println("User successfully to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
    //endregion

    //region [ - update(User user) - ]
    @Override
    public void update(User user) {
        ArrayList<User> users = select();
        ArrayList<Account> accounts = accountRepository.select();
        users.stream().filter(u -> u.getId() == user.getId()).findFirst().ifPresent(this::delete);
        accounts.stream().filter(a -> a.getId() == user.getAccount().getId()).findFirst().ifPresent(accountRepository::delete);
        accountRepository.insert(user.getAccount());
        insert(user);
    }
    //endregion

    //region [ - delete(User user) - ]
    @Override
    public void delete(User user) {
        ArrayList<User> users = select();
        ArrayList<Account> accounts = accountRepository.select();
        accounts.stream().filter(a -> a.getId() == user.getAccount().getId()).findFirst().ifPresent(accountRepository::delete);
        users.remove(user);
        insert(users);
    }
    //endregion

    //region [ - delete(UUID id) - ]
    public void delete(UUID id) {
        ArrayList<User> users = select();
        users.remove(users.stream().filter(a -> a.getId().equals(id)).findFirst().get());
        insert(users);
    }
    //endregion

    //region [- select() -]
    @Override
    public ArrayList<User> select() {
        ArrayList<User> users = new ArrayList<>();
        try {
            File file = new File("src/file/User.txt");
            Scanner myReader = new Scanner(file).useDelimiter(";");

            User user = new User();
            ArrayList<Account> accounts = accountRepository.select();

            int counter = 0;

            while (myReader.hasNext()) {
                counter++;
                UUID accountId = user.getAccountId();

                switch (counter) {
                    case 1 -> user.setId(UUID.fromString(myReader.next()));
                    case 2 -> user.setUsername(myReader.next());
                    case 3 -> user.setFirstName(myReader.next());
                    case 4 -> user.setLastName(myReader.next());
                    case 5 -> user.setAge(myReader.nextInt());
                    case 6 -> user.setAccount(accounts.stream().filter(a -> a.getId().equals(accountId)).findFirst().get());
                    case 7 -> user.setSignUpDate(LocalDate.parse(myReader.next()));
                }

                if (counter == 7) {
                    users.add(user);
                    myReader.nextLine();
                    counter = 0;
                    user = new User();
                }

                if (counter == 5) {
                    user.setAccountId(UUID.fromString(myReader.next()));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return users;
    }

    //endregion

    //region [ - select(UUID id) - ]
    @Override
    public User select(UUID id) {
        ArrayList<Account> accounts = accountRepository.select();
        User user = select().stream().filter(a -> a.getId().equals(id)).findFirst().get();
//        user.setAccount(accounts.stream().filter(a -> a.getId() == user.getAccount().getId()).findFirst().get());
        return user;
    }
    //endregion

    //endregion

}
