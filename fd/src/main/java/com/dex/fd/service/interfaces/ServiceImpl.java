package com.dex.fd.service.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.dex.fd.dao.interfaces.GenericDAO;
import com.dex.fd.dao.jdbc.GenericDAOImpl;
import com.dex.fd.service.exceprion.DBException;

public abstract class ServiceImpl<T> implements GenericService<T> {
	protected abstract GenericDAO<T> getDao();

	@Override
	public T save(T entidade) throws DBException {
		T newEntidade = null;
		GenericDAOImpl.configuraConexao();
		newEntidade = getDao().save(entidade);
		GenericDAOImpl.closeConnection();
		return newEntidade;
	}

	@Override
	public T getById(Integer id) throws DBException {
		T newEntidade = null;
		GenericDAOImpl.configuraConexao();
		newEntidade = getDao().getById(id);
		GenericDAOImpl.closeConnection();
		return newEntidade;
	}

	@Override
	public List<T> getAll(String orderBy) throws DBException {
		List<T> todosOsRegistros = new ArrayList<>();
		GenericDAOImpl.configuraConexao();
		todosOsRegistros = getDao().getAll(orderBy);
		GenericDAOImpl.closeConnection();
		return todosOsRegistros;
	}

	@Override
	public Boolean update(T entidade) throws DBException {
		Boolean retorno = Boolean.FALSE;
		GenericDAOImpl.configuraConexao();
		retorno = getDao().update(entidade);
		GenericDAOImpl.closeConnection();
		return retorno;
	}

	@Override
	public Boolean removeById(Integer id) throws DBException {
		Boolean retorno = Boolean.FALSE;
		GenericDAOImpl.configuraConexao();
		retorno = getDao().removeById(id);
		GenericDAOImpl.closeConnection();
		return retorno;
	}

}
