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

public class SubRedditRepository implements IRepository<SubReddit, UUID> {

    //region [ - Field - ]
    private UserRepository userRepository;
    //endregion

    //region [ - Constructor - ]

    //region [ - SubRedditRepository() - ]
    public SubRedditRepository() {
        userRepository = new UserRepository();
    }
    //endregion

    //endregion

    //region [ - Methods - ]

    //region [ - insert(SubReddit subReddit) - ]
    @Override
    public void insert(SubReddit subReddit) {
        try {
            FileWriter fileWriter = new FileWriter("src/file/SubReddit.txt", true);
            fileWriter.write(subReddit.getInformation());
            fileWriter.close();
            System.out.println("SubReddit successfully to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - insert(ArrayList<SubReddit> subReddits) - ]
    @Override
    public void insert(ArrayList<SubReddit> subReddits) {
        try {
            FileWriter fileWriter = new FileWriter("src/file/SubReddit.txt", false);
            PrintWriter printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
            subReddits.forEach(this::insert);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    //endregion

    //region [ - update(SubReddit subReddit) - ]
    @Override
    public void update(SubReddit subReddit) {
        ArrayList<SubReddit> subReddits = select();
        subReddits.stream().filter(sr -> sr.getId().equals(subReddit.getId())).findFirst().ifPresent(subReddits::remove);
        subReddits.add(subReddit);
        insert(subReddits);
    }
    //endregion

    //region [ - delete(SubReddit subReddit) - ]
    @Override
    public void delete(SubReddit subReddit) {
        delete(subReddit.getId());
    }
    //endregion

    //region [ - delete(UUID id) - ]
    public void delete(UUID id) {
        ArrayList<SubReddit> subReddits = select();
        subReddits.remove(subReddits.stream().filter(sr -> sr.getId().equals(id)).findFirst().get());
        insert(subReddits);
    }
    //endregion

    //region [ - ArrayList<SubReddit> select() - ]
    @Override
    public ArrayList<SubReddit> select() {
        ArrayList<SubReddit> subReddits = new ArrayList<>();
        try {
            File file = new File("src/file/SubReddit.txt");
            Scanner myReader = new Scanner(file).useDelimiter(";");

            SubReddit subReddit = new SubReddit();
            ArrayList<User> users = userRepository.select();

            int counter = 0;

            while (myReader.hasNext()) {
                counter++;
                UUID creatorId = subReddit.getCreatorId();

                switch (counter) {
                    case 1 -> subReddit.setId(UUID.fromString(myReader.next()));
                    case 2 ->
                            subReddit.setCreator(users.stream().filter(u -> u.getId().equals(creatorId)).findFirst().get());
                    case 3 -> subReddit.setTitle(myReader.next());
                    case 4 -> subReddit.setDescription(myReader.next());
                    case 5 -> subReddit.setDate(LocalDate.parse(myReader.next()));
                }

                if (counter % 5 == 0) {
                    subReddits.add(subReddit);
                    myReader.nextLine();
                    counter = 0;
                    subReddit = new SubReddit();
                }

                if (counter == 1) {
                    subReddit.setCreatorId(UUID.fromString(myReader.next()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return subReddits;
    }
    //endregion

    //region [ - select(UUID id) - ]
    @Override
    public SubReddit select(UUID id) {
        ArrayList<SubReddit> subReddits = select();
        SubReddit subReddit = select().stream().filter(a -> a.getId().equals(id)).findFirst().get();
        return subReddit;
    }
    //endregion

    //endregion

}
