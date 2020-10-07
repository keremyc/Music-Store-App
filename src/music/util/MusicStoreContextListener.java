package music.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MusicStoreContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();

        // initialize the customer service email address that's used throughout the web site
        String custServEmail = sc.getInitParameter("custServEmail");
        sc.setAttribute("custServEmail", custServEmail);

        //initialize the current year that's used in the copyright notice
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentYear = currentDate.get(Calendar.YEAR);
        sc.setAttribute("currentYear", currentYear);

        // initialize the array of years that's used for the credit card expiration years
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = currentYear; i < currentYear + 10; i++){
            years.add(i);
        }
        sc.setAttribute("creditCardExpirationYears", years);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //nothing
    }
}
