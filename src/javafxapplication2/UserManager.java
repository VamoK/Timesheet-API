/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxapplication2;


import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;

/**
 *
 * @author Vamokuhle
 */
public class UserManager implements UserInterface {

    private Connection conn;

    public UserManager(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }

    @Override
    public String addUser(String username, String password) {
        
        String response="";
        try {

            // Database query to insert user info
            String query = "INSERT INTO [Users].[dbo].[USER_INFO] (username, password) VALUES (?, ?)";
            try (Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(query)) {
                // Set query parameters
                ps.setString(1, username);
                ps.setString(2, password); // Store encrypted password

                // Execute the update (insert)
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    response = "User added successfully. Please sign in";
                } else {
                    response = "Failed to add a user";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return response;
    }

    @Override
    public User getUser(String username, String password) throws SQLServerException {
        
        String response = "";
        User user = null;
        
        try {
            String query = "SELECT * FROM [Users].[dbo].[USER_INFO] WHERE username = ? AND password = ?";
            
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();
            
            
            if(rs.next()){
                user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword("password");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }
}
