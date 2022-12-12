package com.dex.fd.dao.interfaces;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.dex.fd.service.exceprion.DBException;

public interface GenericDAO<T> {
	T save(T entidade) throws DBException;
	T getById(Integer id) throws DBException;
	/**
	 * Este método aceita o parâmetro orderBy, significando que 
	 * é possível definir uma ordenação na query que fará o SELECT.
	 * Caso o parâmetro seja nulo nenhuma ordenação é feita, exceto a padrão do SGBD.
	 */
	List<T> getAll(String orderBy) throws DBException;
	Boolean update(T entidade) throws DBException;
	Boolean removeById(Integer id) throws DBException;
	T popular(ResultSet rs) throws SQLException;
	String getInsertClause(Integer qtty);
	String getUpdateClause(String[] fields);
	PreparedStatement preparePersistStatement(
			T entidade, Boolean isUpdate) throws SQLException;
}