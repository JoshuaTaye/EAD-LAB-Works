//Joshua Taye  Alemayehu

package com.bookstore.step1to7;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/registerBook")
public class BookRegistrationServlet extends HttpServlet {
    private DBConnectionManager dbConnectionManager;

    public void init() throws ServletException {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        dbConnectionManager = (DBConnectionManager) context.getBean("dbConnectionManager");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        float price = Float.parseFloat(req.getParameter("price"));

        try (Connection connection = dbConnectionManager.openConnection()){
            String query = "INSERT INTO Books (title, author, price) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setFloat(3, price);
            statement.executeUpdate();

            PrintWriter out = resp.getWriter();
            out.println("<h1>Book Registration Successful</h1>");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
