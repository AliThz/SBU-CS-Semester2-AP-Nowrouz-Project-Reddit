//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Scanner;
//import java.util.UUID;
//
//public class SubRedditRepository implements Repository<SubReddit> {
//
//    //region [ - Field - ]
////    private UserRepository userRepository;
//    //endregion
//
//    //region [ - Constructor - ]
//    public SubRedditRepository() {
////        userRepository = new UserRepository();
//    }
//    //endregion
//
//    //region [ - Methods - ]
//
//    //region [ - insert(SubReddit subReddit) - ]
//    @Override
//    public void insert(SubReddit subReddit) {
//        try {
//            File file = new File("SubReddit.txt");
//            if (file.createNewFile()) {
//                FileWriter fileWriter = new FileWriter("SubReddit.txt");
//                fileWriter.write(subReddit.toString());
//                fileWriter.close();
//                System.out.println("SubReddit successfully to the file.");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }
//    //endregion
//
//    //region [ - insert(ArrayList<SubReddit> subReddits) - ]
//    @Override
//    public void insert(ArrayList<SubReddit> subReddits) {
//        File file = new File("SubReddit.txt");
//        if(file.delete()) {
//            file = new File("SubReddit.txt");
//        }
//        for (SubReddit p : subReddits) {
//            try {
//                if (file.createNewFile()) {
//                    FileWriter fileWriter = new FileWriter("SubReddit.txt");
//                    fileWriter.write(p.toString());
//                    fileWriter.close();
//                    System.out.println("SubReddit successfully to the file.");
//                }
//            } catch (IOException e) {
//                System.out.println("An error occurred.");
//                e.printStackTrace();
//            }
//        }
//    }
//    //endregion
//
//    //region [ - update(SubReddit subReddit) - ]
//    @Override
//    public void update(SubReddit subReddit) {
//        ArrayList<SubReddit> subReddits = select();
//        for (SubReddit p : subReddits) {
//            if (p.getId() == subReddit.getId()) {
//                p = subReddit;
//                break;
//            }
//        }
//        insert(subReddits);
//    }
//    //endregion
//
//    //region [ - delete(SubReddit subReddit) - ]
//    @Override
//    public void delete(SubReddit subReddit) {
//        ArrayList<SubReddit> subReddits = select();
//        subReddits.remove(subReddit);
//        insert(subReddits);
//    }
//    //endregion
//
//    //region [ - ArrayList<SubReddit> select() - ]
//    @Override
//    public ArrayList<SubReddit> select() {
//        ArrayList<SubReddit> subReddits = new ArrayList<>();
//        SubReddit subReddit = new SubReddit();
//        try {
//            File file = new File("SubReddit.txt");
//            Scanner myReader = new Scanner(file).useDelimiter(";");
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
//            ArrayList<User> users = userRepository.select();
//            int counter = 0;
//            while (myReader.hasNext()) {
//                counter++;
//
//                switch (counter) {
//                    case 1 -> subReddit.setId(UUID.fromString(myReader.next()));
//                    case 2 -> subReddit.setCreator(users.stream().filter(a -> a.getId() == UUID.fromString(myReader.next())).toList().getFirst());
//                    case 3 -> subReddit.setTitle(myReader.next());
//                    case 4 -> subReddit.setMessage(myReader.next());
//                    case 5 -> subReddit.setUpVotes(myReader.nextInt());
//                    case 6 -> subReddit.setDownVotes(myReader.nextInt());
//                    case 7 -> subReddit.setDate(LocalDate.parse(myReader.next(), formatter));
//                }
//
//                if (counter % 7 == 0) {
//                    subReddits.add(subReddit);
//                    counter = 0;
//                    subReddit = new SubReddit();
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//
//        return subReddits;
//    }
//    //endregion
//
//    //endregion
//
//
//
//}
