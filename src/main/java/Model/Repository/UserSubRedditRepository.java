package Model.Repository;

import Model.DTO.Account;
import Model.DTO.SubReddit;
import Model.DTO.User;
import Model.DTO.UserSubReddit;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserSubRedditRepository {

    //region [ - Fields - ]

    private UserRepository userRepository;
    private SubRedditRepository subRedditRepository;

    //endregion

    //region [ - Constructor - ]
    public UserSubRedditRepository() {
        userRepository = new UserRepository();
        subRedditRepository = new SubRedditRepository();
    }
    //endregion

    //region [ - Methods - ]

    //region [ - insert(UserSubReddit userSubReddit) - ]
    public void insert(UserSubReddit userSubReddit) {
        try {
            FileWriter fileWriter = new FileWriter("src/file/UserSubReddit.txt", true);
            fileWriter.write(userSubReddit.getInformation());
            fileWriter.close();
            System.out.println("UserSubReddit successfully added to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - insert(ArrayList<UserSubReddit> userSubReddits) - ]
    public void insert(ArrayList<UserSubReddit> userSubReddits) {
        try {
            FileWriter fileWriter = new FileWriter("src/file/UserSubReddit.txt", false);
            PrintWriter printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
            userSubReddits.forEach(this::insert);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - update(UserSubReddit userSubReddit) - ]
    public void update(UserSubReddit userSubReddit) {
        ArrayList<UserSubReddit> userSubReddits = select();
        userSubReddits.stream().filter(usr -> usr.getUserId() == userSubReddit.getUser().getId() && usr.getSubRedditId() == userSubReddit.getSubReddit().getId()).findFirst().ifPresent(userSubReddits::remove);
        userSubReddits.add(userSubReddit);
        insert(userSubReddits);
    }
    //endregion

    //region [ - delete(UserSubReddit userSubReddit) - ]
    public void delete(UserSubReddit userSubReddit) {
        ArrayList<UserSubReddit> userSubReddits = select();
        userSubReddits.removeIf(usr -> usr.getUser().getId().equals(userSubReddit.getUser().getId()) && usr.getSubReddit().getId().equals(userSubReddit.getSubReddit().getId()));
        insert(userSubReddits);
    }
    //endregion

    //region [ - delete(SubReddit subReddit) - ]
    public void delete(SubReddit subReddit) {
        ArrayList<UserSubReddit> userSubReddits = select();
        userSubReddits.stream().filter(usr -> usr.getSubReddit().getId().equals(subReddit.getId())).collect(Collectors.toCollection(ArrayList<UserSubReddit>::new)).forEach(userSubReddits::remove);
        insert(userSubReddits);
    }
    //endregion

    //region [ - delete(User user) - ]
    public void delete(User user) {
        ArrayList<UserSubReddit> userSubReddits = select();
        userSubReddits.stream().filter(usr -> usr.getUser().getId() == user.getId()).collect(Collectors.toCollection(ArrayList<UserSubReddit>::new)).forEach(userSubReddits::remove);
        insert(userSubReddits);
    }
    //endregion

    //region [ - ArrayList<UserSubReddit> select() - ]
    public ArrayList<UserSubReddit> select() {
        ArrayList<UserSubReddit> userSubReddits = new ArrayList<>();
        try {
            File file = new File("src/file/UserSubReddit.txt");
            Scanner myReader = new Scanner(file).useDelimiter(";");

            UserSubReddit userSubReddit = new UserSubReddit();
            ArrayList<User> users = userRepository.select();
            ArrayList<SubReddit> subReddits = subRedditRepository.select();

            int counter = 0;

            while (myReader.hasNext()) {
                counter++;

                if (counter == 1) {
                    userSubReddit.setUserId(UUID.fromString(myReader.next()));
                }

                if (counter == 2) {
                    userSubReddit.setSubRedditId(UUID.fromString(myReader.next()));
                }

                UUID userId = userSubReddit.getUserId();
                UUID subRedditId = userSubReddit.getSubRedditId();

                switch (counter) {
                    case 1 ->
                            userSubReddit.setUser(users.stream().filter(u -> u.getId().equals(userId)).findFirst().get());
                    case 2 ->
                            userSubReddit.setSubReddit(subReddits.stream().filter(sr -> sr.getId().equals(subRedditId)).findFirst().get());
                    case 3 -> userSubReddit.setIsOwned(myReader.nextBoolean());
                }

                if (counter == 3) {
                    userSubReddits.add(userSubReddit);
                    myReader.nextLine();
                    counter = 0;
                    userSubReddit = new UserSubReddit();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return userSubReddits;
    }
    //endregion

    //endregion

}
