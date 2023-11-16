/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import business.Movie;
import business.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import data.MovieDB;
import data.UserDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.realm.SecretKeyCredentialHandler;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 *
 * @author fssco
 */
public class Public extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        Logger LOG = Logger.getLogger(Public.class.getName());

        String url = "/index.jsp";
        
        LinkedHashMap<Integer, Movie> movies = null;
        LinkedHashMap<Integer, User> users = null;
        int[] numbers = new int[]{1,2,3,4,5,6,7,8,9,10};
        
        try {
            movies = MovieDB.selectAllMovies();
            users = UserDB.getAllUsers();

        } catch (Exception ex) {
            Logger.getLogger(Public.class.getName()).log(Level.SEVERE, "DB calls in Public controller failed", ex);
        }

        request.setAttribute("movies", movies);
        request.setAttribute("users", users);
        request.setAttribute("rating", numbers);

       

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
