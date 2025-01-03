//Joshua Taye Alemayehu
package com.bookstore.step1to7;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            return;
        }
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb", "root", "0521")) {
            String query = "DELETE FROM Books WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, bookId);
            stmt.executeUpdate();

            PrintWriter out = response.getWriter();
            out.println("<h1>Book successfully deleted!</h1>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
