/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import business.User;
import business.Review;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import data.MovieDB;
import data.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Fred Scott Southeast Community College INFO
 */
public class API extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String json = "";

        String uri = request.getRequestURI();
        String[] part = uri.split("/");
        String last = part[part.length - 1];

        if ("users".equals(last)) {
            try {
                LinkedHashMap<Integer, User> users = UserDB.getAllUsers();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.setPrettyPrinting().create();
                json = gson.toJson(users);
            } catch (SQLException ex) {
                response.setStatus(500);
            }
        } else if ("users".equals(part[part.length - 2])) {
            try {
                int id = Integer.parseInt(last);
                User user = UserDB.getUser(id);
                if (user == null) {
                    response.setStatus(404);
                } else {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.setPrettyPrinting().create();
                    json = gson.toJson(user);
                }

            } catch (NumberFormatException ex) {
                response.setStatus(404);
            } catch (SQLException ex) {
                response.setStatus(500);
            }
        }

        try ( PrintWriter out = response.getWriter()) {
            if ("".equals(json)) {
                response.setStatus(404);
            } else {
                out.write(json);
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String json = "";

        String uri = request.getRequestURI();
        String[] part = uri.split("/");

        String last = part[part.length - 1];

        if ("users".equals(last)) {

            JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
            String email = data.get("email").getAsString();
            String firstName = data.get("firstName").getAsString();
            String lastName = data.get("lastName").getAsString();
            String username = data.get("username").getAsString();

            User user = new User(email, firstName, lastName, username, "");
            try {
                int id = UserDB.insertIntoUser(user);
                response.setStatus(201);

                //request.getServletPath() gets the URL for the servlet in case
                //the project name or mapping changes
                json = "{\"location\": \"" + request.getServletPath() + "/users/" + id + "\"}";
                System.out.println(json);
            } catch (SQLException ex) {
                response.setStatus(500);
            }

        } else if ("users".equals(part[part.length - 2])) {
            //you shouldn't post to /users/X
            //that would be a PUT, not a POST
            response.setStatus(404);

        } else if ("reviews".equals(last)) {
            JsonObject data = new Gson().fromJson(request.getReader(), JsonObject.class);
            int ratingID = data.get("ratingID").getAsInt();
            int movieID = data.get("movieID").getAsInt();
            int userID = data.get("userID").getAsInt();
            int rating = data.get("rating").getAsInt();
            
            Review review = new Review(ratingID, movieID, userID, rating);
            try {
                int id = MovieDB.insertReview(review);
                response.setStatus(201);
                
                json = "{\"location\": \"" + request.getServletPath() + "/reviews/" + id + "\"}";
                System.out.println(json);
            } catch(SQLException ex) {
                response.setStatus(500);
            }
        }
        

        try ( PrintWriter out = response.getWriter()) {
            if ("".equals(json)) {
                response.setStatus(404);
            } else {
                out.write(json);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        String[] parts = uri.split("/");
        String last = parts[parts.length - 1];
        
        try {
            int reviewIDtoDelete = parseInt(last);
            MovieDB.deleteReview(reviewIDtoDelete);
            response.setStatus(200);
        } catch (SQLException ex) {
            response.setStatus(500);

        }catch (NumberFormatException e) {
            response.setStatus(400);
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
