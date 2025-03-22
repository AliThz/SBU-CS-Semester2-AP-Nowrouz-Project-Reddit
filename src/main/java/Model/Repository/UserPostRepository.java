package Model.Repository;

import Model.DTO.*;
import org.apache.commons.lang3.BooleanUtils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserPostRepository {

    //region [ - Fields - ]

    private UserRepository userRepository;
    private PostRepository postRepository;

    //endregion

    //region [ - Constructor - ]

    public UserPostRepository() {
     userRepository = new UserRepository();
     postRepository = new PostRepository();
    }

    //endregion

    //region [ - Methods - ]

    //region [ - insert() - ]
    public void insert(UserPost userpost) {
        try {
            FileWriter fileWriter = new FileWriter("src/file/UserPost.txt", true);
            fileWriter.write(userpost.getInformation());
            fileWriter.close();
//            System.out.println("User successfully to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - insert(ArrayList<UserPost> userposts) - ]
    public void insert(ArrayList<UserPost> userposts) {
        try {
            FileWriter fileWriter = new FileWriter("src/file/UserPost.txt", false);
            PrintWriter printWriter = new PrintWriter(fileWriter, false);
            printWriter.flush();
            printWriter.close();
            fileWriter.close();
            userposts.forEach(this::insert);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - update(UserPost userpost) - ]
    public void update(UserPost userpost) {
        ArrayList<UserPost> userPosts = select();
        userPosts.stream().filter(up -> up.getUser().getId().equals(userpost.getUser().getId()) && up.getPost().getId().equals(userpost.getPost().getId())).findFirst().ifPresent(userPosts::remove);
        userPosts.add(userpost);
        insert(userPosts);
    }
    //endregion

    //region [ - delete(UserPost userpost) - ]
    public void delete(UserPost userpost) {
        ArrayList<UserPost> userPosts = select();
        userPosts.stream().filter(up -> up.getPost().getId().equals(userpost.getPost().getId())).collect(Collectors.toCollection(ArrayList<UserPost>::new)).forEach(userPosts::remove);
        insert(userPosts);
    }
    //endregion

    //region [ - ArrayList<UserPost> select() - ]
    public ArrayList<UserPost> select() {
        ArrayList<UserPost> userPosts = new ArrayList<>();
        try {
            File file = new File("src/file/UserPost.txt");
            Scanner myReader = new Scanner(file).useDelimiter(";");

            UserPost userPost = new UserPost();
            ArrayList<User> users = userRepository.select();
            ArrayList<Post> posts = postRepository.select();

            int counter = 0;

            while (myReader.hasNext()) {
                counter++;

                if (counter == 1) {
                    userPost.setUserId(UUID.fromString(myReader.next()));
                }

                if (counter == 2) {
                    userPost.setPostId(UUID.fromString(myReader.next()));
                }

                UUID userId = userPost.getUserId();
                UUID subRedditId = userPost.getPostId();

                switch (counter) {
                    case 1 -> userPost.setUser(users.stream().filter(u -> u.getId().equals(userId)).findFirst().get());
                    case 2 -> userPost.setPost(posts.stream().filter(p -> p.getId().equals(subRedditId)).findFirst().get());
                    case 3 -> userPost.setVote(BooleanUtils.toBooleanObject((myReader.next())));
                }

                if (counter == 3) {
                    userPosts.add(userPost);
                    myReader.nextLine();
                    counter = 0;
                    userPost = new UserPost();
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return userPosts;
    }
    //endregion

    //endregion

}
