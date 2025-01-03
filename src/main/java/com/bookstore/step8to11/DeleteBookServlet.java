//Joshua Taye Alemayehu

package com.bookstore.step8to11;

import jakarta.servlet.http.HttpServlet;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RequestMapping("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
    private static final String query = "delete from Books where id = ?";

    @Setter
    private DBConnectionManager db;

    @PostMapping
    @ResponseBody
    public String deleteBook(@RequestParam("id") int id) {
        db.openConnection();
        Connection connection = db.getConnection();
        StringBuilder response = new StringBuilder();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            int count = ps.executeUpdate();
            if (count == 1) {
                response.append("<h2>Record is deleted successfully.</h2>");
            } else {
                response.append("<h2>Record not deleted.</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            response.append("<h1>").append(se.getMessage()).append("</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            response.append("<h1>").append(e.getMessage()).append("</h1>");
        }

        db.closeConnection();
        return response.toString();
    }
}