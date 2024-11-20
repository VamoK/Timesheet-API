/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxapplication2;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

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
    public void addUser(String username, String password) {
        try {
            // Generate a salt
            byte[] salt = generateSalt();
            
            // Hash the password with PBKDF2 and the salt
            String hashedPassword = hashPassword(password, salt);

            // Convert the salt to Base64 for storage in DB
            String saltBase64 = Base64.getEncoder().encodeToString(salt);

            // Database query to insert user info
            String query = "INSERT INTO [Users].[dbo].[USER_INFO] (username, password, salt) VALUES (?, ?, ?)";
            try (Connection conn = getConn(); PreparedStatement ps = conn.prepareStatement(query)) {
                // Set query parameters
                ps.setString(1, username);
                ps.setString(2, hashedPassword); // Store hashed password
                ps.setString(3, saltBase64); // Store salt as Base64 string

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

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // Typically 16 bytes is good enough
        random.nextBytes(salt);
        return salt;
    }

    private String hashPassword(String password, byte[] salt) {
        try {
            // PBKDF2 with SHA-256 and 10000 iterations
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10000, 256); // 256-bit hash
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hashedPassword = skf.generateSecret(spec).getEncoded();
            
            // Convert hashed password to Base64 for storage
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }
}
