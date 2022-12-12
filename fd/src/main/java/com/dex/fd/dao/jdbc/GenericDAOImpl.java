package com.dex.fd.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dex.fd.dao.interfaces.GenericDAO;
import com.dex.fd.service.exceprion.DBException;

public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
	private static Connection conn;

	public abstract T popular(ResultSet rs) throws SQLException;

	public abstract String getTableName();

	public abstract PreparedStatement preparePersistStatement(T entidade, Boolean isUpdate) throws SQLException;

	public static void configuraConexao() throws DBException {
		try {
			if (conn == null || conn.isClosed()) {
				try {
					conn = Conexao.getConexao();
				} catch (ClassNotFoundException e) {
					throw new DBException();
				}
			}
		} catch (SQLException ex) {
			throw new DBException(ex);
		}
	}

	protected Statement getStatement() throws DBException {
		try {
			return conn.createStatement();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	protected PreparedStatement getPreparedStatement(String sql) throws DBException {
		try {
			return conn.prepareStatement(sql);
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}

	protected T getLastRecord() throws DBException {
		T t = null;
		String query = "SELECT * FROM " + getTableName() + 
				" WHERE ID = (SELECT MAX(ID) FROM " + getTableName() + ")";
		try {
			ResultSet rs = getStatement().executeQuery(query);
			if (rs.next()) {
				t = popular(rs);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return t;
	}

	@Override
	public Boolean removeById(Integer id) throws DBException {
		String sql = "DELETE FROM " + getTableName() + " WHERE ID = ?";
		try {
			PreparedStatement ps = getPreparedStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException ex) {
			throw new DBException(ex);
		}
		return true;
	}

	@Override
	public List<T> getAll(String orderBy) throws DBException {
		List<T> lista = new ArrayList<>();
		String sql = "SELECT * FROM " + getTableName();
		if (orderBy != null) {
			sql += " ORDER BY " + orderBy;
		}
		try {
			ResultSet rs = getStatement().executeQuery(sql);
			while (rs.next()) {
				lista.add(popular(rs));
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return lista;
	}

	@Override
	public T save(T entidade) throws DBException {
		T newEntidade = null;
		try {
			preparePersistStatement(entidade, false).executeUpdate();
			newEntidade = getLastRecord();
		} catch (SQLException ex) {
			throw new DBException(ex);
		}
		return newEntidade;
	}

	@Override
	public Boolean update(T entidade) throws DBException {
		Boolean retorno = Boolean.FALSE;
		try {
			preparePersistStatement(entidade, true).executeUpdate();
			// Caso a atualização ocorra sem interrupções, pode-se considerar a operação bem
			// sucedida
			retorno = Boolean.TRUE;
		} catch (SQLException ex) {
			throw new DBException(ex);
		}
		return retorno;
	}

	@Override
	public T getById(Integer id) throws DBException {
		T t = null;
		String sql = "SELECT * FROM " + getTableName() + " WHERE ID = ?";
		try {
			PreparedStatement ps = getPreparedStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				t = popular(rs);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return t;
	}

	@Override
	public String getInsertClause(Integer qtty) {
		String clause = "INSERT INTO " + getTableName() + " VALUES (NULL";
		for (int i = 0; i < qtty; i++) {
			clause += ",?";
		}
		clause += ")";
		return clause;
	}

	@Override
	public String getUpdateClause(String[] fields) {
		String clause = "UPDATE " + getTableName() + " SET ";
		for (String f : fields) {
			clause += f + " = ?, ";
		}
		clause = clause.substring(0, clause.lastIndexOf(","));
		clause += " WHERE ID = ?";
		return clause;
	}

	public static void closeConnection() throws DBException {
		try {
			conn.close();
		} catch (SQLException e) {
			throw new DBException(e);
		}
	}
}
