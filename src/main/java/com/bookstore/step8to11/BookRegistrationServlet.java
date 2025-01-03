//Joshua Taye Alemayehu

package com.bookstore.step8to11;

import jakarta.servlet.http.HttpServlet;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequestMapping("/addBook")
public class BookRegistrationServlet extends HttpServlet {
    private static final String query = "INSERT INTO Books (title, author, price) VALUES (?, ?, ?)";
    @Setter
    private DBConnectionManager db;

    @GetMapping
    @ResponseBody
    public String showForm() {
        return "<html><body>"
                + "<h2>Book Registration Form</h2>"
                + "<form method='post' action='/online_bookstore_management_system/books/addBook'>"
                + "Title: <input type='text' name='title'><br>"
                + "Author: <input type='text' name='author'><br>"
                + "Price: <input type='number' name='price'><br>"
                + "<input type='submit' value='Add Book'>"
                + "</form>"
                + "</body></html>";
    }

    @PostMapping
    @ResponseBody
    public String addBook(@RequestParam String title, @RequestParam String author, @RequestParam String price) {
        db.openConnection();

        try {
            PreparedStatement ps = db.getConnection().prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setFloat(3, Float.parseFloat(price));
            int count = ps.executeUpdate();
            db.closeConnection();
            if (count == 1) {
                return "Book Registration Successful";
            } else {
                return "Book Registration Failed";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Book Registration Failed";
        } finally {
            db.closeConnection();
        }
    }
}
