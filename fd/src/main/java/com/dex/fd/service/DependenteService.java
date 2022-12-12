package com.dex.fd.service;

import java.util.ArrayList;
import java.util.List;

import com.dex.fd.dao.DependenteDAO;
import com.dex.fd.dao.interfaces.GenericDAO;
import com.dex.fd.dao.jdbc.GenericDAOImpl;
import com.dex.fd.model.Dependente;
import com.dex.fd.service.exceprion.DBException;
import com.dex.fd.service.interfaces.ServiceImpl;

public class DependenteService extends ServiceImpl<Dependente> {

	@Override
	protected GenericDAO<Dependente> getDao() {
		return new DependenteDAO();
	}
	
	public List<Dependente> getByParteNome(String parteNome) throws DBException {
		List<Dependente> list = new ArrayList<>();

		GenericDAOImpl.configuraConexao();
		list = new DependenteDAO().getByParteNome(parteNome);
		GenericDAOImpl.closeConnection();

		return list;
	}

}

