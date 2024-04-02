import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        userRepository.insert(new User("bc","b", "c", 15, new Account("ali.thz83@gmail.com", "Ab!@#12345")));
        ArrayList<User> users = userRepository.select();
    }
}