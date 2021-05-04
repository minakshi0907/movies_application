package com.example.moviesapplication.ui.activity

import com.example.moviesapplication.model.movies.Movie

object FakeMovieData {

    /*
    ** Fake Data for unit testing
     */
    val movies = arrayOf(
        Movie(
            1,
            "Outside the Wire",
            "https://image.tmdb.org/t/p/w780/pt2SbE1UHMtrX69jAqHMIfPYdXQ.jpg",
            "The Avengers and their allies must be willing to sacrifice all in an attempt to " +
                    "defeat the powerful Thanos before his blitz of devastation and ruin puts an end to " +
                    "the universe.", 1.276, "2012-08-01", 5.4, 11

        ),
        Movie(
            2,
            "Wonder Woman 1984",
            "https://image.tmdb.org/t/p/w780/",
            "The movie deals with the championship-winning German soccer team of 1954. Its story is linked with two others: " +
                    "The family of a young boy is split due to the events in World War II, and the father returns from Russia after eleven years. " +
                    "The second story is about a reporter and his wife reporting from the tournament.", 5.0, "2003-10-15",
            6.7, 15

        ),
        Movie(
            3,
            "The Godfather",
            "https://nyc3.digitaloceanspaces.com/open-api-spaces/open-api-static/blog/1/The_Godfather-fragment_factory.png",
            "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
            6.0, "2019-09-12", 5.8, 13

        ),
        Movie(
            4,
            "Soul",
            "https://nyc3.digitaloceanspaces.com/open-api-spaces/open-api-static/blog/1/The_Dark_Knight-fragment_factory_1.png",
            "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest " +
                    "psychological and physical tests of his ability to fight injustice.", 8.0, "2017-08-25",
            7.0, 45

        ),
        Movie(
            5,
            "The Lord of the Rings: The Return of the King",
            "https://nyc3.digitaloceanspaces.com/open-api-spaces/open-api-static/blog/1/The_Lord_of_the_Rings_The_Return_of_the_King-fragment_factory_2.png",
            "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
            9.0, "2016-06-22", 8.0, 56

        )
    )
}