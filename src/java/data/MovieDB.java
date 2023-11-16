package data;

import business.Movie;
import business.Review;
import java.sql.*;
import java.time.Year;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieDB {

    private static final Logger LOG = Logger.getLogger(MovieDB.class.getName());
    
        public static int insertReview(Review review) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "INSERT INTO ratings (ratingID, movieID, userID, rating) VALUES (?, ?, ?, ?) ";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, review.getRatingID());
            ps.setInt(2, review.getMovieID());
            ps.setInt(3, review.getUserID());
            ps.setInt(4, review.getRating());

            return ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** insert review sql", e);
            throw e;

        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** insert review null pointer??", e);
                throw e;
            }

        }
    }
    public static int deleteReview(int ratingID, int userID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM ratings "
                + "WHERE ratingID = ? AND userID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, ratingID);
            ps.setInt(2, userID);

            return ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** delete review sql", e);
            throw e;

        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** delete review null pointer??", e);
                throw e;
            }

        }
    }
    
    
    public static int deleteReview(int ratingID) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "DELETE FROM ratings "
                + "WHERE ratingID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, ratingID);

            return ps.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** delete review sql", e);
            throw e;

        } finally {
            try {
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** delete review null pointer??", e);
                throw e;
            }

        }
    }

    public static LinkedHashMap<Integer, Review> getReviewsForID(int id) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedHashMap<Integer, Review> reviews = new LinkedHashMap<Integer, Review>();

        String query = "SELECT * FROM ratings "
                + "WHERE userID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            Review review = null;
            while (rs.next()) {
                review = new Review();
                review.setRatingID(rs.getInt("ratingID"));
                review.setMovieID(rs.getInt("movieID"));
                review.setUserID(rs.getInt("userID"));
                review.setRating(rs.getInt("rating"));
                reviews.put(review.getRatingID(), review);

            }
            return reviews;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** get review for id sql", e);
            throw e;
        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** get review for id null pointer??", e);
                throw e;
            }
        }
    }

    public static LinkedHashMap<Integer, Movie> selectAllMovies() throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        LinkedHashMap<Integer, Movie> movies = new LinkedHashMap<Integer, Movie>();
        String query = "SELECT * FROM movies ";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            Movie movie = null;
            while (rs.next()) {
                movie = new Movie();
                movie.setId(rs.getInt("movieID"));
                movie.setTitle(rs.getString("title"));
                movie.setDirector(rs.getString("director"));
                movie.setYear(rs.getInt("year"));
                movies.put(movie.getId(), movie);

            }
            return movies;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select all movies sql", e);
            throw e;

        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select all movies null pointer??", e);
                throw e;
            }

        }
    }
    
     public static Movie selectMovieByID(int id) throws SQLException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Movie movie = new Movie();
        
        String query = "SELECT * FROM movies where movieID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                movie.setId(rs.getInt("movieID"));
                movie.setTitle(rs.getString("title"));
                movie.setDirector(rs.getString("director"));
                movie.setYear((rs.getInt("year")));

            } else {
                throw new SQLException("Movie not found.");
            }
            
            return movie;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "*** select one movie sql", e);
            throw e;

        } finally {
            try {
                rs.close();
                ps.close();
                pool.freeConnection(connection);
            } catch (Exception e) {
                LOG.log(Level.SEVERE, "*** select one movie null pointer??", e);
                throw e;
            }

        }
     }

}
