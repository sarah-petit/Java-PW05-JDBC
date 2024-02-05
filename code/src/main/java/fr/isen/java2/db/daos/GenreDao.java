package fr.isen.java2.db.daos;

import fr.isen.java2.db.entities.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static fr.isen.java2.db.daos.DataSourceFactory.getConnection;

public class GenreDao {

	/**
	 *
	 * @return la liste de tous les genres contenus dans la bdd
	 */
	public List<Genre> listGenres() {
		List<Genre> listGenres = new ArrayList<>();
		try(Connection connection = getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre")) {
				try(ResultSet results = statement.executeQuery()) {
					while(results.next()) {
						Genre genre = new Genre(results.getInt("idgenre"), results.getString("name"));
						listGenres.add(genre);
					}
					return listGenres;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param name le nom du genre que l'on cherche
	 * @return le genre correspondant dans la bdd
	 */
	public Genre getGenre(String name) {
		try(Connection connection = getConnection()) {
			try(PreparedStatement statement = connection.prepareStatement("SELECT * FROM genre WHERE name=?")) {
				statement.setString(1, name);
				try(ResultSet result = statement.executeQuery()) {
					if(result.next()) {
                        return new Genre(result.getInt("idgenre"), result.getString("name"));
					}
				}
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *
	 * @param name le nom du genre que l'on souhaite ajouter dans la bdd
	 */
	public void addGenre(String name) {
		try (Connection connection = getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement("INSERT INTO genre(name) VALUES(?)")) {
				statement.setString(1, name);
				statement.executeUpdate();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
