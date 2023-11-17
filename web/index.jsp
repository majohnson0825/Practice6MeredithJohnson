<!DOCTYPE html>
<html>

    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <head>
        <meta charset="utf-8">
        <title>Murach's Java Servlets and JSP</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <table>

        <tr>
            <th>title</th>
            <th>director</th>
            <th>year</th>
        </tr>
        <c:forEach var="movie" items="${movies}">
            <tr>
                <td>${movie.value.title}</td>
                <td>${movie.value.director}</td>
                <td>${movie.value.year}</td>
            </tr>
        </c:forEach>

    </table>

    <br>
    <br>

    <div>

        <h1 id="jsDisplay"> </h1>
    </div>


    <ol>

        <li>

            <div>
                <p>This button posts a review with reviewID 0, movieID 1, userID 1 and rating 1.</p>
                <input type="button" id="postHardCoded" value="POST Hardcoded Review">
            </div>
        </li>

        <li>
            <div id="postSpecific">
                <p>Post a review selecting a user and movie:</p>
                <select name="movieID" id="movieID">
                    
            <c:forEach var="movie" items="${movies}" >
              <option value="${movie.value.id}"><c:out value="${movie.value.title}, ${movie.value.director}"/></option>
            </c:forEach>
                </select>
                <select name="userID" id="userID">
             <c:forEach var="user" items="${users}" >
              <option value="${user.value.userID}"><c:out value="${user.value.userID}, ${user.value.username}"/></option>
            </c:forEach>   
               </select>
                
                <select name="rating" id="rating">
            <c:forEach var="rating" items="${rating}">
                <option value="${rating}"><c:out value="${rating}"/></option>
                    </c:forEach>     
                </select>   

                <input type="button" id="postReview" value="POST Review">

            </div>
        </li>

        <li>
            <div>
                <p>Select a review to delete:</p>
                <select name="reviewID" id="reviewID">
                    <c:forEach var="review" items="${reviews}">
                        <option value="${review.value.ratingID}"><c:out value="${movies[Integer.valueOf(review.value.movieID)].title} rated ${review.value.rating}"/></option> 
                        <!--   -->
                    </c:forEach>
                   
                </select>

                <input type="button" id="deleteReview" value="Delete Review">

            </div>
        </li>




    </ol>


    <script>
        document.addEventListener("DOMContentLoaded", () => {

            document.querySelector("#deleteReview").addEventListener("click", () => {
                let reviewID = document.querySelector("#reviewID").value;
                console.log(reviewID);


                fetch('API/reviews/' + reviewID, {
                    method: 'DELETE',
                    headers: {
                        'Content-type': 'application/json; charset=UTF-8',
                    },
                })
                        .then((response) => response.json())
                        .then((json) => console.log(json));
            });

            document.querySelector("#postReview").addEventListener("click", () => {
                let movieID = document.querySelector("#movieID").value;
                let userID = document.querySelector("#userID").value;
                let rating = document.querySelector("#rating").value;


                fetch('API/reviews', {
                    method: 'POST',
                    body: JSON.stringify({
                        ratingID: 0,
                        movieID: movieID,
                        userID: userID,
                        rating: rating,
                    }),
                    headers: {
                        'Content-type': 'application/json; charset=UTF-8',
                    },
                })
                        .then((response) => response.json())
                        .then((json) => console.log(json));
            });

            document.querySelector("#postHardCoded").addEventListener("click", () => {
                fetch('API/reviews', {
                    method: 'POST',
                    body: JSON.stringify({
                        ratingID: 0,
                        movieID: 1,
                        userID: 1,
                        rating: 1,
                    }),
                    headers: {
                        'Content-type': 'application/json; charset=UTF-8',
                    },
                })
                        .then((response) => response.json())
                        .then((json) => console.log(json));
            });


        });
    </script>

</html>
