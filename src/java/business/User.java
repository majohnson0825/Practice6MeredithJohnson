/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author fssco
 */
public class User implements Serializable{
    private int userID;
    private String email, firstName, lastName, username, hash;
    private ArrayList<String> roles;

    public User() {
        this.roles = new ArrayList<String>();
    }

    public User(String email, String firstName, String lastName, String username, String hash) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.hash = hash;
        this.userID = 0;
        this.roles = new ArrayList<String>();
    }
    
    

    public User(int userID, String email, String firstName, String lastName, String username, String hash) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.hash = hash;
        this.roles = new ArrayList<String>();

    }

    public User(int userID, String email, String firstName, String lastName, String username, String hash, ArrayList<String> roles) {
        this.userID = userID;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.hash = hash;
        this.roles = roles;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
    
    public void addRole(String ro) {
        this.roles.add(ro);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    
    
}
