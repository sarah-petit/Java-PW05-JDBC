package fr.isen.java2.db.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceFactory {

	private DataSourceFactory() {
		// This is a static class that should not be instantiated.
		// Here's a way to remember it when this class will have 2K lines and you come
		// back to it in 2 years
		throw new IllegalStateException("This is a static class that should not be instantiated");
	}

	/**
	 * @return a connection to a Database
	 * @throws SQLException renvoie une exception si on n'arrive pas à se connecter à la bdd
	 * Je me permets d'utiliser throws SQLException ici puisque cette méthode
	 * est utilisé uniquement à l'intérieur de try ... catch (SQLException) ...
	 *
	 */
	public static Connection getConnection() throws SQLException {
		// La méthode getConnection() de la classe DriverManager permet de se connecter
		// à n'importe quelle bdd à partir de son url
		// Pas besoin de lui dire quel type de source de donnée nous utilisons
		return DriverManager.getConnection("jdbc:sqlite:sqlite.db");
	}
}