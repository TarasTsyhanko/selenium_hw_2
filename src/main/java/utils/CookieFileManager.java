package utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.openqa.selenium.Cookie;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CookieFileManager {
    private static final String filePath = "src/main/resources/cookies/userOne.json";

    public static List<Cookie> readFile(String filePath) {
        MyCookie[] cookies = new MyCookie[0];
        try {
            cookies = new ObjectMapper().readValue(new File(filePath), MyCookie[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Cookie> cookieList = new ArrayList<>();
        Date expiryTime = new Date(System.currentTimeMillis() + 1000000000);
        Arrays.stream(cookies).forEach(c->{
            Cookie cookie = new Cookie(c.getName(), c.getValue(), c.getDomain(),c.getPath(),expiryTime,c.isSecure(),c.isHttpOnly());
            cookieList.add(cookie);
        });
        return cookieList;
    }

    public static boolean writeFile(Set<Cookie> cookies) {
        try {
            new ObjectMapper().writeValue(new File(filePath), cookies);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
