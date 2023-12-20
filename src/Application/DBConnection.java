/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Anonymous
 */
public class DBConnection {
    public static Connection getConnection(){
     
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/blacklotus", "root", "");            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        
        return conn;
    }        
}
