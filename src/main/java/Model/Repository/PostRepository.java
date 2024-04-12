package Model.Repository;

import Model.DTO.*;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class PostRepository implements IRepository<Post, UUID> {

    //region [ - Field - ]
    private UserRepository userRepository;
    private SubRedditRepository subRedditRepository;
    //endregion

    //region [ - Constructor - ]

    //region [ - PostRepository() - ]
    public PostRepository() {
        userRepository = new UserRepository();
        subRedditRepository = new SubRedditRepository();
    }
    //endregion1

    //endregion

    //region [ - Methods - ]

    //region [ - insert(Post post) - ]
    @Override
    public void insert(Post post) {
        try {
            FileWriter fileWriter = new FileWriter("src/file/Post.txt", true);
            fileWriter.write(post.getInformation());
            fileWriter.close();
//            System.out.println("Post successfully to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - insert(ArrayList<Post> posts) - ]
    @Override
    public void insert(ArrayList<Post> posts) {
        try {
            FileWriter fileWriter = new FileWriter("src/file/Post.txt", false);
            PrintWriter printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
            posts.forEach(this::insert);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - update(Post post) - ]
    @Override
    public void update(Post post) {
        ArrayList<Post> posts = select();
        posts.stream().filter(p -> p.getId() == post.getId()).findFirst().ifPresent(this::delete);
        insert(post);
    }
    //endregion

    //region [ - delete(Post post) - ]
    @Override
    public void delete(Post post) {
        ArrayList<Post> posts = select();
        posts.stream().filter(p -> p.getId().equals(post.getId())).collect(Collectors.toCollection(ArrayList<Post>::new)).forEach(posts::remove);
        insert(posts);
    }
    //endregion

    //region [ - delete(UUID id) - ]
    public void delete(UUID id) {
        ArrayList<Post> posts = select();
        posts.remove(posts.stream().filter(p -> p.getId().equals(id)).findFirst().get());
        insert(posts);
    }
    //endregion

    //region [ - ArrayList<Post> select() - ]
    @Override
    public ArrayList<Post> select() {
        ArrayList<Post> posts = new ArrayList<>();
        try {
            File file = new File("src/file/Post.txt");
            Scanner myReader = new Scanner(file).useDelimiter(";");

            Post post = new Post();
            ArrayList<User> users = userRepository.select();
            ArrayList<SubReddit> subReddits = subRedditRepository.select();

            int counter = 0;

            while (myReader.hasNext()) {
                counter++;
                UUID creatorId = post.getCreatorId();
                UUID subRedditId = post.getSubRedditId();

                switch (counter) {
                    case 1 -> post.setId(UUID.fromString(myReader.next()));
                    case 2 -> post.setSubReddit(subReddits.stream().filter(sr -> sr.getId().equals(subRedditId)).findFirst().get());
                    case 3 -> post.setCreator(users.stream().filter(u -> u.getId().equals(creatorId)).findFirst().get());
                    case 4 -> post.setTitle(myReader.next());
                    case 5 -> post.setMessage(myReader.next());
                    case 6 -> post.setUpVotes(myReader.nextInt());
                    case 7 -> post.setDownVotes(myReader.nextInt());
                    case 8 -> post.setDate(LocalDate.parse(myReader.next()));
                }

                if (counter == 8) {
                    posts.add(post);
                    myReader.nextLine();
                    counter = 0;
                    post = new Post();
                }

                if (counter == 1) {
                    post.setSubRedditId(UUID.fromString(myReader.next()));
                }

                if (counter == 2) {
                    post.setCreatorId(UUID.fromString(myReader.next()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        return posts;
    }
    //endregion

    //region [ - select(UUID id) - ]
    @Override
    public Post select(UUID id) {
        Post post = select().stream().filter(a -> a.getId().equals(id)).findFirst().get();
        return post;
    }
    //endregion

    //endregion

}
