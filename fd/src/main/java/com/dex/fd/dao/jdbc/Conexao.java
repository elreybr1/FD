package com.dex.fd.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexao {
	private static final String URL = "jdbc:mysql://localhost:3306/empregado";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	private static Connection CONN = null;

	private Conexao() {
	}

	protected static Connection getConexao() throws ClassNotFoundException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			if (CONN == null || CONN.isClosed()) {
				CONN = DriverManager.getConnection(URL, USER, PASSWORD);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return CONN;
	}
}
