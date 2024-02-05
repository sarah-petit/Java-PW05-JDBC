package fr.isen.java2.db.daos;

import fr.isen.java2.db.entities.Genre;
import fr.isen.java2.db.entities.Movie;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static fr.isen.java2.db.daos.DataSourceFactory.getConnection;

public class MovieDao {

	/**
	 *
	 * @return le liste de tous les films contenus dans le bdd
	 */
	public List<Movie> listMovies() {
		List<Movie> listMovies = new ArrayList<>();
		try(Connection connection = getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie JOIN genre ON movie.genre_id = genre.idgenre")) {
				try(ResultSet results = statement.executeQuery()) {
					while(results.next()) {
						int id = results.getInt("idmovie");
						String title = results.getString("title");
						LocalDate releaseDate = results.getDate("release_date").toLocalDate();
						Genre genre = new Genre(results.getInt("idgenre"), results.getString("name"));
						int duration = results.getInt("duration");
						String director = results.getString("director");
						String summary = results.getString("summary");
						Movie movie = new Movie(id, title, releaseDate, genre, duration, director, summary);
						listMovies.add(movie);
					}
					return listMovies;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param genreName le nom du genre dont on souhaite le liste des films
	 * @return le liste des films correspondant au genre demandé
	 */
	public List<Movie> listMoviesByGenre(String genreName) {
		List<Movie> listMovies = new ArrayList<>();
		try(Connection connection = getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM movie JOIN genre ON movie.genre_id = genre.idgenre WHERE genre.name = ?")) {
				statement.setString(1, genreName);
				try(ResultSet results = statement.executeQuery()) {
					while(results.next()) {
						int id = results.getInt("idmovie");
						String title = results.getString("title");
						LocalDate releaseDate = results.getDate("release_date").toLocalDate();
						Genre genre = new Genre(results.getInt("idgenre"), results.getString("name"));
						int duration = results.getInt("duration");
						String director = results.getString("director");
						String summary = results.getString("summary");
						Movie movie = new Movie(id, title, releaseDate, genre, duration, director, summary);
						listMovies.add(movie);
					}
					return listMovies;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param movie le film que l'on souhait ajouter dans la bdd
	 * @return le film qui a été ajouté en bdd avec son id dans la bdd
	 */
	public Movie addMovie(Movie movie) {
		try (Connection connection = getConnection()) {
			String sqlQuery = "INSERT INTO movie(title,release_date,genre_id,duration,director,summary) VALUES(?,?,?,?,?,?)";
			try (PreparedStatement statement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, movie.getTitle());
				statement.setDate(2, java.sql.Date.valueOf(movie.getReleaseDate()));
				statement.setInt(3,movie.getGenre().getId());
				statement.setInt(4, movie.getDuration());
				statement.setString(5, movie.getDirector());
				statement.setString(6, movie.getSummary());
				statement.executeUpdate();
				ResultSet id = statement.getGeneratedKeys();
				if(id.next()) {
					movie.setId(id.getInt(1));
					return movie;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
