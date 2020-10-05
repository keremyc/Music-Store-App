package music.controllers;

import music.business.User;
import music.data.UserDB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String url = "";
        if (uri.endsWith("/deleteCookies")){
            url = deleteCookies(request, response);
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String url = "";
        if (uri.endsWith("/subscribeToEmail")){
            url = subscribeToEmail(request, response);
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    private String deleteCookies(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie c: cookies){
            c.setPath("/");
            c.setMaxAge(0);
            response.addCookie(c);
        }
        return "/delete_cookies.jsp";
    }

    private String subscribeToEmail(HttpServletRequest request, HttpServletResponse response){
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        request.setAttribute("user", user);

        String url;
        String message;
        if (UserDB.emailExists(email)){
            message = "This email address already exists. <br>"
                    + "Please enter another email address.";
            request.setAttribute("message", message);
            url = "/email/index.jsp";
        }else {
            UserDB.insert(user);
            message = "";
            request.setAttribute("message", message);
            url = "/email/thanks.jsp";
        }
        return url;
    }
}
