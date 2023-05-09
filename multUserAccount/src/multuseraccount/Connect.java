/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multuseraccount;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author WINDOWS 10
 */
public class Connect {

    // FOR THE DATABASE
    public static Connection connectDB() {
// FIRST, LETS CREATE OUR DATABASE
        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection connect
                    = DriverManager.getConnection("jdbc:mysql://localhost/multiuser", "root", ""); // LINK YOUR DATABASE, ROOT IS OUR DEFAULT USERNAME AND THE PASSWORD IS NULL OR EMPTY

            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
