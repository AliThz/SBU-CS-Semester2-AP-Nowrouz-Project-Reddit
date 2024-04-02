import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class AccountRepository implements Repository<Account> {

    //region [ - Constructor - ]
    public AccountRepository() {
    }
    //endregion

    //region [ - Methods - ]

    //region [ - insert(Account account) - ]
    @Override
    public void insert(Account account) {
        try {
            FileWriter fileWriter = new FileWriter("Account.txt", true);
            fileWriter.write(account.getInformation());
            fileWriter.close();
            System.out.println("Account successfully saves in the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - insert(ArrayList<Account> accounts) - ]
    @Override
    public void insert(ArrayList<Account> accounts) {
        File file = new File("Account.txt");
        if (file.delete()) {
            file = new File("Account.txt");
        }
        for (Account u : accounts) {
            try {
                FileWriter fileWriter = new FileWriter("Account.txt", true);
                fileWriter.write(u.toString());
                fileWriter.close();
                System.out.println("Account successfully to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
    //endregion

    //region [ - update(Account account) - ]
    @Override
    public void update(Account account) {
        ArrayList<Account> accounts = select();
        for (Account u : accounts) {
            if (u.getId() == account.getId()) {
                u = account;
                break;
            }
        }
        insert(accounts);
    }
    //endregion

    //region [ - delete(Account account) - ]
    @Override
    public void delete(Account account) {
        ArrayList<Account> accounts = select();
        accounts.remove(account);
        insert(accounts);
    }
    //endregion

    //region [ - select() - ]
    @Override
    public ArrayList<Account> select() {
        ArrayList<Account> accounts = new ArrayList<>();
        Account account = new Account();
        try {
            File file = new File("Account.txt");
            Scanner myReader = new Scanner(file).useDelimiter(";");

            int counter = 0;

            while (myReader.hasNext()) {
                counter++;

                switch (counter) {
                    case 1 -> account.setId(UUID.fromString(myReader.next()));
                    case 2 -> account.setEmail(myReader.next());
                    case 3 -> account.setPassword(myReader.next());
                    case 4 -> account.setHasLoggedIn(myReader.nextBoolean());
                }

                if (counter == 4) {
                    accounts.add(account);
                    counter = 0;
                    myReader.nextLine();
                    account = new Account();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return accounts;
    }

    //endregion

    //endregion

}
