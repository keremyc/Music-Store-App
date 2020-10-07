package music.controllers;

import music.business.Invoice;
import music.data.InvoiceDB;
import music.data.ReportDB;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class AdminController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        String url = "/admin";
        if (uri.endsWith("/processInvoice")){
            url = processInvoice(request);
        } else if (uri.endsWith("/displayInvoices")){
            url = displayInvoices(request);
        } else if (uri.endsWith("/displayReport")){
            displayReport(request, response);
            return;
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        String url = "/admin";
        if (uri.endsWith("/displayInvoices")){
            url = displayInvoices(request);
        } else if (uri.endsWith("/displayInvoice")){
            url = displayInvoice(request, response);
        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    private String displayInvoices(HttpServletRequest request){

        List<Invoice> unprocessedInvoices = InvoiceDB.selectUnprocessedInvoices();

        if (unprocessedInvoices != null){
            if (unprocessedInvoices.size() <= 0){
                unprocessedInvoices = null;
            }
        }

        HttpSession session = request.getSession();
        session.setAttribute("unprocessedInvoices", unprocessedInvoices);
        return "/admin/invoices.jsp";
    }

    private String displayInvoice(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        String invoiceNumberStr = request.getParameter("invoiceNumber");
        long invoiceNumber = Long.parseLong(invoiceNumberStr);
        List<Invoice> unprocessedInvoices = (List<Invoice>)session.getAttribute("unprocessedInvoices");

        Invoice invoice = null;
        for (Invoice in: unprocessedInvoices){
            invoice = in;
            if (invoice.getInvoiceNumber() == invoiceNumber){
                break;
            }
        }

        session.setAttribute("invoice", invoice);
        return "/admin/invoice.jsp";
    }

    private String processInvoice(HttpServletRequest request){
        HttpSession session = request.getSession();
        Invoice invoice = (Invoice) session.getAttribute("invoice");
        InvoiceDB.update(invoice); // make it processed

        return "/adminPage/displayInvoices?";
    }

    private void displayReport(HttpServletRequest request, HttpServletResponse response){
        String reportName = request.getParameter("reportName");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        Workbook workbook;
        if (reportName.equalsIgnoreCase("userEmail")){
            workbook = ReportDB.getUserEmail();
        } else if (reportName.equalsIgnoreCase("downloadDetail")){
            workbook = ReportDB.gerDownloadDetail(startDate, endDate);
        } else {
            workbook = new HSSFWorkbook();
        }

        response.setHeader("content-disposition",
                "attachment; fileName=" + reportName + ".xls");

        try(OutputStream out = response.getOutputStream()){
            workbook.write(out);
        } catch (IOException exp) {
            System.err.println(exp);
        }
    }

}
