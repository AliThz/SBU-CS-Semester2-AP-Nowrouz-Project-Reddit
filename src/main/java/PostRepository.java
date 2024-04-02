import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class PostRepository implements Repository<Post> {

    //region [ - Field - ]
    private UserRepository userRepository;
    //endregion

    //region [ - Constructor - ]
    public PostRepository() {
        userRepository = new UserRepository();
    }
    //endregion

    //region [ - Methods - ]

    //region [ - insert(Post post) - ]
    @Override
    public void insert(Post post) {
        try {
            File file = new File("Post.txt");
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter("Post.txt");
                fileWriter.write(post.toString());
                fileWriter.close();
                System.out.println("Post successfully to the file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - insert(ArrayList<Post> posts) - ]
    @Override
    public void insert(ArrayList<Post> posts) {
        File file = new File("Post.txt");
        if(file.delete()) {
            file = new File("Post.txt");
        }
        for (Post p : posts) {
            try {
                if (file.createNewFile()) {
                    FileWriter fileWriter = new FileWriter("Post.txt");
                    fileWriter.write(p.toString());
                    fileWriter.close();
                    System.out.println("Post successfully to the file.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
    //endregion

    //region [ - update(Post post) - ]
    @Override
    public void update(Post post) {
        ArrayList<Post> posts = select();
        for (Post p : posts) {
            if (p.getId() == post.getId()) {
                p = post;
                break;
            }
        }
        insert(posts);
    }
    //endregion

    //region [ - delete(Post post) - ]
    @Override
    public void delete(Post post) {
        ArrayList<Post> posts = select();
        posts.remove(post);
        insert(posts);
    }
    //endregion

    //region [ - ArrayList<Post> select() - ]
    @Override
    public ArrayList<Post> select() {
        ArrayList<Post> posts = new ArrayList<>();
        Post post = new Post();
        try {
            File file = new File("Post.txt");
            Scanner myReader = new Scanner(file).useDelimiter(";");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            ArrayList<User> users = userRepository.select();
            int counter = 0;
            while (myReader.hasNext()) {
                counter++;

                switch (counter) {
                    case 1 -> post.setId(UUID.fromString(myReader.next()));
                    case 2 -> post.setCreator(users.stream().filter(a -> a.getId() == UUID.fromString(myReader.next())).toList().getFirst());
                    case 3 -> post.setTitle(myReader.next());
                    case 4 -> post.setMessage(myReader.next());
                    case 5 -> post.setUpVotes(myReader.nextInt());
                    case 6 -> post.setDownVotes(myReader.nextInt());
                    case 7 -> post.setDate(LocalDate.parse(myReader.next(), formatter));
                }

                if (counter % 7 == 0) {
                    posts.add(post);
                    counter = 0;
                    post = new Post();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return posts;
    }
    //endregion

    //endregion

}
