package Model.Repository;

import Model.DTO.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class CommentRepository  implements IRepository<Comment, UUID> {

    //region [ - Field - ]
    private UserRepository userRepository;
    private PostRepository postRepository;
    private SubRedditRepository subRedditRepository;
    //endregion

    //region [ - Constructor - ]

    //region [ - CommentRepository() - ]
    public CommentRepository() {
        userRepository = new UserRepository();
        postRepository = new PostRepository();
        subRedditRepository = new SubRedditRepository();
    }
    //endregion

    //endregion

    //region [ - Methods - ]

    //region [ - insert(Comment comment) - ]
    @Override
    public void insert(Comment comment) {
        try {
            FileWriter fileWriter = new FileWriter("src/file/Comment.txt");
            fileWriter.write(comment.getInformation());
            fileWriter.close();
            System.out.println("Comment successfully to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //endregion

    //region [ - insert(ArrayList<Comment> comments) - ]
    @Override
    public void insert(ArrayList<Comment> comments) {
        File file = new File("src/file/Comment.txt");
        if (file.delete()) {
            file = new File("src/file/Comment.txt");
        }
        for (Comment p : comments) {
            try {
                FileWriter fileWriter = new FileWriter("src/file/Comment.txt");
                fileWriter.write(p.getInformation());
                fileWriter.close();
                System.out.println("Comment successfully to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
    //endregion

    //region [ - update(Comment comment) - ]
    @Override
    public void update(Comment comment) {
        ArrayList<Comment> comments = select();
        comments.stream().filter(p -> p.getId() == comment.getId()).findFirst().ifPresent(this::delete);
        insert(comment);
    }
    //endregion

    //region [ - delete(Comment comment) - ]
    @Override
    public void delete(Comment comment) {
        ArrayList<Comment> comments = select();
        comments.remove(comment);
        insert(comments);
    }
    //endregion

    //region [ - delete(UUID id) - ]
    public void delete(UUID id) {
        ArrayList<Comment> comments = select();
        comments.remove(comments.stream().filter(c -> c.getId().equals(id)).findFirst().get());
        insert(comments);
    }
    //endregion

    //region [ - ArrayList<Comment> select() - ]
    @Override
    public ArrayList<Comment> select() {
        ArrayList<Comment> comments = new ArrayList<>();
        try {
            File file = new File("src/file/Comment.txt");
            Scanner myReader = new Scanner(file).useDelimiter(";");

            Comment comment = new Comment();
            ArrayList<User> users = userRepository.select();
            ArrayList<Post> posts = postRepository.select();
            ArrayList<SubReddit> subReddits = subRedditRepository.select();

            int counter = 0;

            while (myReader.hasNext()) {
                counter++;
                UUID creatorId = comment.getCreatorId();
                UUID repliedPostId = comment.getRepliedPostId();
                UUID subRedditId = comment.getSubRedditId();

                switch (counter) {
                    case 1 -> comment.setId(UUID.fromString(myReader.next()));
                    case 2 -> comment.setCreator(users.stream().filter(u -> u.getId().equals(creatorId)).findFirst().get());
                    case 3 -> comment.setSubReddit(subReddits.stream().filter(sr -> sr.getId().equals(creatorId)).findFirst().get());
                    case 4 -> comment.setTitle(myReader.next());
                    case 5 -> comment.setMessage(myReader.next());
                    case 6 -> comment.setUpVotes(myReader.nextInt());
                    case 7 -> comment.setDownVotes(myReader.nextInt());
                    case 8 -> comment.setDate(LocalDate.parse(myReader.next()));
                    case 9 -> comment.setRepliedPost(posts.stream().filter(p -> p.getId().equals(repliedPostId)).findFirst().get());
                }

                if (counter == 9 ) {
                    comments.add(comment);
                    counter = 0;
                    comment = new Comment();
                }

                if (counter == 1) {
                    comment.setSubRedditId(UUID.fromString(myReader.next()));
                }

                if (counter == 2) {
                    comment.setCreatorId(UUID.fromString(myReader.next()));
                }

                if (counter == 8) {
                    comment.setRepliedPostId(UUID.fromString(myReader.next()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return comments;
    }
    //endregion

    //region [ - select(UUID id) - ]
    @Override
    public Comment select(UUID id) {
        Comment comment = select().stream().filter(a -> a.getId().equals(id)).findFirst().get();
        return comment;
    }
    //endregion

    //endregion
}
