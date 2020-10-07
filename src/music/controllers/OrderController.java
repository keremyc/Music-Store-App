package music.controllers;

import music.business.*;
import music.data.DBUtil;
import music.data.InvoiceDB;
import music.data.ProductDB;
import music.data.UserDB;
import music.util.CookieUtil;
import music.util.MailUtil;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Calendar;

public class OrderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String url = "";
        if (uri.endsWith("/showCart")){
            url = showCart(request);
        }

        getServletContext().getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        String url = "";
        if (uri.endsWith("/addItem")){
            url = addItem(request);
        } else if (uri.endsWith("/updateItem")){
            url = update(request);
        } else if (uri.endsWith("/removeItem")){
            url = remove(request);
        } else if (uri.endsWith("/checkUser")){
            url = checkUser(request);
        } else if (uri.endsWith("displayInvoice")){
            url = displayInvoice(request);
        } else if (uri.endsWith("/processUser")){
            url = processUser(request);
        } else if (uri.endsWith("/displayCreditCard")){
            url = "/cart/credit_card.jsp";
        } else if (uri.endsWith("/displayUser")){
            url = "/cart/user.jsp";
        } else if (uri.endsWith("/completeOrder")){
            url = completeOrder(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String showCart(HttpServletRequest request){
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        if (cart == null || cart.getCount() == 0){
            session.setAttribute("emptyCart", "Your cart is empty");
        }
        return "/cart/cart.jsp";
    }

    private String addItem(HttpServletRequest request){
        HttpSession session = request.getSession();
        Cart cart = (Cart)session.getAttribute("cart");
        if (cart == null){
            cart = new Cart();
        }

        String productCode = request.getParameter("productCode");
        Product product = ProductDB.select(productCode);
        if (product != null){
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            cart.addItem(lineItem);
            session.removeAttribute("emptyCart");
        }
        session.setAttribute("cart", cart);
        return "/cart/cart.jsp";
    }

    private String update(HttpServletRequest request){

        HttpSession session = request.getSession();
        String productCode = request.getParameter("productCode");
        Cart cart = (Cart)session.getAttribute("cart");

        int quantity;
        try {
            quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity < 0)
                quantity = 1;
        }catch (NumberFormatException exp){
            quantity = 1;
        }

        Product product = ProductDB.select(productCode);
        if (product != null && cart != null){
            LineItem lineItem = new LineItem();
            lineItem.setQuantity(quantity);
            lineItem.setProduct(product);
            if (quantity > 0)
                cart.addItem(lineItem);
            else
                cart.removeItem(lineItem);
        }

        return "/cart/cart.jsp";

    }

    private String remove(HttpServletRequest request){
        HttpSession session = request.getSession();
        String productCode = request.getParameter("productCode");
        Cart cart = (Cart)session.getAttribute("cart");
        Product product = ProductDB.select(productCode);

        if (cart != null && product != null){
            LineItem lineItem = new LineItem();
            lineItem.setProduct(product);
            cart.removeItem(lineItem);
        }
        return "/cart/cart.jsp";
    }

    private String checkUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        String url = "/cart/user.jsp";
        if (user != null && !user.getAddress1().equals("")){
            url = "/order/displayInvoice";
        } else {
            Cookie[] cookies = request.getCookies();
            String email = CookieUtil.getCookieValue(cookies, "email");
            if (email.equals("")){
                user = new User();
            } else {
                user = UserDB.select(email);
                if (user != null && !user.getAddress1().equals("")){
                    url = "/order/displayInvoice";
                }
            }
        }
        session.setAttribute("user", user);
        return url;
    }

    private String processUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String company = request.getParameter("company");
        String address1 = request.getParameter("address1");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zip = request.getParameter("zip");
        String country = request.getParameter("country");

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setCompanyName(company);
        user.setAddress1(address1);
        user.setAddress2(address2);
        user.setCity(city);
        user.setState(state);
        user.setZip(zip);
        user.setCountry(country);

        if (UserDB.emailExists(email)){
            UserDB.update(user);
            user = UserDB.select(email);
        } else {
            UserDB.insert(user);
        }
        session.setAttribute("user", user);
        return "/order/displayInvoice";
    }

    private String displayInvoice(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("user");
        Cart cart = (Cart)session.getAttribute("cart");

        java.util.Date today = new java.util.Date();

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(today);
        invoice.setLineItems(cart.getItems());
        invoice.setUser(user);
        session.setAttribute("invoice", invoice);

        return "/cart/invoice.jsp";
    }

    private String completeOrder(HttpServletRequest request, HttpServletResponse response){

        HttpSession session = request.getSession();
        Invoice invoice = (Invoice)session.getAttribute("invoice");

        String creditCardType = request.getParameter("creditCardType");
        String creditCardNumber = request.getParameter("creditCardNumber");
        String creditCardMonth = request.getParameter("creditCardExpirationMonth");
        String creditCardYear = request.getParameter("creditCardExpirationYear");

        User user = invoice.getUser();
        user.setCreditCardType(creditCardType);
        user.setCreditCardNumber(creditCardNumber);
        user.setCreditCardExpirationDate(creditCardMonth + "/" + creditCardYear);

        if (UserDB.emailExists(user.getEmail())){
            UserDB.update(user);
        } else {
            UserDB.insert(user);
        }
        invoice.setUser(user);

        // write the new invoice to the db.
        InvoiceDB.insert(invoice);

        // set emailCookie in user's browser
        Cookie emailCookie = new Cookie("email", user.getEmail());
        emailCookie.setPath("/");
        emailCookie.setMaxAge(60 * 60 * 24 * 365 * 2);
        response.addCookie(emailCookie);

        // remove all items in the user's cart
        session.removeAttribute("cart");
        session.setAttribute("emptyCart", "Your cart is empty");

        //send an email to the user to confirm the order
        String to = user.getEmail();
        String from = "confirmation@freshcornrecords.com";
        String subject = "Order Confirmation";
        String body = "Dear " + user.getFirstName() + ",\n\n" +
                "Thanks for ordering from us. " +
                "You should receive your order in 3-5 business days. " +
                "Please contact us if you have any questions.\n" +
                "Have a great day and thanks again!\n\n" +
                "Joe King\n" +
                "Fresh Corn Records";
        boolean isBodyHTML = false;

        MailUtil.sendEmail(from, to, subject, body, isBodyHTML);

        return "/cart/complete.jsp";

    }


}
