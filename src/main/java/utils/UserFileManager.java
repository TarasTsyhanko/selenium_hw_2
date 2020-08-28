package utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class UserFileManager {
    private static final String filePath = "src/main/resources/users/users.json";

    public static User[] readFile() {
        User[] users = new User[0];
        try {
            users = new ObjectMapper().readValue(new File(filePath), User[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
