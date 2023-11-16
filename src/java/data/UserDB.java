/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import business.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fssco
 */
public class UserDB {

    private static final Logger LOG = Logger.getLogger(UserDB.class.getName());

    public static int insertIntoUser(User user) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO user (UserID, Email, FirstName, LastName, Username, Hash) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, user.getUserID());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getUsername());
            ps.setString(6, user.getHash());
            ps.executeUpdate();
            try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert user sql", e);
            throw e;
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert user null pointer??", e);
                throw e;
            }
        }
    }


    public static int getIDforUsername(String username)throws SQLException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT UserID FROM user "
                + "WHERE Username = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            int id = -1;
            if (rs.next()) {
                id = rs.getInt("UserID");
            }

            return id;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** get id for username sql", e);
            throw e;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** get id for username null pointer??", e);
                throw e;
            }
        }
    }

    public static int insertRoles(User user) throws SQLException{
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO userrole (Username, Rolename) "
                + "VALUES (?, ?)";
        try {
            int count = 0;
            
            ps = connection.prepareStatement(query);
            
            for (String role : user.getRoles()) {

                
                ps.setString(1, user.getUsername());
                ps.setString(2, role);

                count += ps.executeUpdate();
            }
            return count;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert role sql", e);
            throw e;
        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert role null pointer??", e);
                throw e;
            }
        }
    }

    public static LinkedHashMap<Integer, User> getAllUsers() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedHashMap<Integer, User> users = new LinkedHashMap<Integer, User>();
        
        //LEFT JOIN to get all users without roles in addition to rows for each user roll
        String query = "SELECT * from user as u LEFT JOIN userrole as r on u.Username = r.Username";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            User user = null;
            //While we have rows
            while (rs.next()) {
                //if the hashmap already has that user id in it and there's a role in the row
                if (users.containsKey(rs.getInt("UserID")) && rs.getObject("Rolename") != null) {
                    //we can just add the role from the row because everything else should already be in there
                    users.get(rs.getInt("UserID")).addRole(rs.getString("Rolename"));
                } else {
                    //else we need to create the user object with ALL of the info for the row
                    user = new User();
                    user.setUserID(rs.getInt("UserID"));
                    user.setEmail(rs.getString("Email"));
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setUsername(rs.getString("Username"));
                    //there's the possibility of no role
                    if (rs.getObject("Rolename") != null) {
                        user.addRole(rs.getString("Rolename"));
                    }

                    users.put(user.getUserID(), user);
                }

            }
            return users;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** get all users sql", e);
            throw e;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** get all users null pointer??", e);
                throw e;
            }
        }
    }

      public static User getUser(int userID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //LEFT JOIN to get all users without roles in addition to rows for each user roll
        String query = "SELECT * from user as u LEFT JOIN userrole as r on u.Username = r.Username WHERE UserID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            User user = null;
            //While we have rows
            while (rs.next()) {
                //if the hashmap already has that user id in it and there's a role in the row
                if (user != null && rs.getObject("Rolename") != null) {
                    //we can just add the role from the row because everything else should already be in there
                    user.addRole(rs.getString("Rolename"));
                } else {
                    //else we need to create the user object with ALL of the info for the row
                    user = new User();
                    user.setUserID(rs.getInt("UserID"));
                    user.setEmail(rs.getString("Email"));
                    user.setFirstName(rs.getString("FirstName"));
                    user.setLastName(rs.getString("LastName"));
                    user.setUsername(rs.getString("Username"));
                    //there's the possibility of no role
                    if (rs.getObject("Rolename") != null) {
                        user.addRole(rs.getString("Rolename"));
                    }

                }

            }
            return user;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** get all users sql", e);
            throw e;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** get all users null pointer??", e);
                throw e;
            }
        }
    }
}
