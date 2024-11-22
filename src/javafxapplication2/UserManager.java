/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxapplication2;


import java.sql.*;
import javax.crypto.SecretKey;

/**
 *
 * @author Vamokuhle
 */
public class UserManager implements UserInterface {

    private Connection conn;
    private SecretKey secretKey;

    public UserManager(Connection conn, SecretKey secretKey) {
        this.conn = conn;
        this.secretKey = secretKey;
    }

    public Connection getConn() {
        return conn;
    }

    @Override
    public void addUser(String username, String password) {
        try {
            // Encrypt the password
            String encryptedPassword = EncryptionUtil.encryptPassword(password, secretKey);

            // Database query to insert user info
            String query = "INSERT INTO [Users].[dbo].[USER_INFO] (username, password) VALUES (?, ?)";
            try (Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(query)) {
                // Set query parameters
                ps.setString(1, username);
                ps.setString(2, encryptedPassword); // Store encrypted password

                // Execute the update (insert)
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User added successfully");
                } else {
                    System.out.println("Insert failed");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPassword(String username) {
        try {
            // Database query to get the stored encrypted password
            String query = "SELECT password FROM [Users].[dbo].[USER_INFO] WHERE username = ?";
            try (Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String encryptedPassword = rs.getString("password");

                        // Decrypt the password
                        return EncryptionUtil.decryptPassword(encryptedPassword, secretKey);
                    } else {
                        System.out.println("User not found");
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean validateUser(String username, String inputPassword) {
        try {
            // Database query to get the stored encrypted password
            String query = "SELECT password FROM [Users].[dbo].[USER_INFO] WHERE username = ?";
            try (Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String storedEncryptedPassword = rs.getString("password");

                        // Decrypt the stored password
                        String decryptedStoredPassword = EncryptionUtil.decryptPassword(storedEncryptedPassword, secretKey);

                        // Compare the decrypted stored password with the input password
                        return decryptedStoredPassword.equals(inputPassword);
                    } else {
                        System.out.println("User not found");
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
