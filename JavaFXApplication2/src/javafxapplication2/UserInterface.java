/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package javafxapplication2;

import com.microsoft.sqlserver.jdbc.SQLServerException;

/**
 *
 * @author Vamokuhle
 */
public interface UserInterface {
    public void addUser(String username , String password) throws SQLServerException;
}
