package music.controllers;

import music.business.Download;
import music.business.Product;
import music.business.User;
import music.data.DownloadDB;
import music.data.ProductDB;
import music.data.UserDB;
import music.util.CookieUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class CatalogController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo().substring(1);
        String url;
        if (pathInfo.endsWith("/listen")){
            url = listen(request, response);
        } else {
            url = showProduct(request);
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String url = "/catalog";
        if (uri.endsWith("/register")){
            url = registerUser(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);

    }

    private String showProduct(HttpServletRequest request){
        String pathInfo = request.getPathInfo();
        String productCode = pathInfo.substring(1);
        Product product = ProductDB.select(productCode);
        HttpSession session = request.getSession();
        session.setAttribute("product", product);
        return "/catalog" + pathInfo;
    }

    private String listen(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        if (user == null){
            Cookie[] cookies = request.getCookies();
            String email = CookieUtil.getCookieValue(cookies, "email");
            if (email == null || email.equals("")){
                return "/catalog/register.jsp";
            } else {
                user = UserDB.select(email);
                if (user == null){
                    return "/catalog/register.jsp";
                }
                session.setAttribute("user", user);
            }
        }

        Product product = (Product)session.getAttribute("product");

        Download download = new Download();
        download.setUser(user);
        download.setProductCode(product.getCode());
        DownloadDB.insert(download);

        return "/catalog/" + product.getCode() + "/sound.jsp";
    }

    private String registerUser(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        User user;
        if (UserDB.emailExists(email)){
            user = UserDB.select(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            UserDB.update(user);
        } else {
            user = new User();
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            UserDB.insert(user);
        }

        session.setAttribute("user", user);

        Cookie emailCookie = new Cookie("email", email);
        emailCookie.setMaxAge(60 * 60 * 24 * 365 * 2); // 2 years
        emailCookie.setPath("/");
        response.addCookie(emailCookie);

        Product product = (Product)session.getAttribute("product");

        String url = "/catalog/" + product.getCode() + "/sound.jsp";
        return url;
    }
}
