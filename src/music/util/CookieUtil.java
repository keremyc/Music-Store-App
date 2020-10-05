package music.util;

import javax.servlet.http.Cookie;

public class CookieUtil {

    public static String getCookieValue(Cookie[] cookies, String cookieName){
        String cookieValue = "";
        for (Cookie cookie: cookies){
            String name = cookie.getName();
            if (name.equalsIgnoreCase(cookieName)){
                cookieValue = cookie.getValue();
            }
        }
        return cookieValue;
    }
}
