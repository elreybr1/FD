package com.dex.fd.service;

import java.util.ArrayList;
import java.util.List;

import com.dex.fd.dao.PessoaDAO;
import com.dex.fd.dao.interfaces.GenericDAO;
import com.dex.fd.dao.jdbc.GenericDAOImpl;
import com.dex.fd.model.Pessoa;
import com.dex.fd.service.exceprion.DBException;
import com.dex.fd.service.interfaces.ServiceImpl;

public class PessoaService extends ServiceImpl<Pessoa> {

	@Override
	protected GenericDAO<Pessoa> getDao() {
		return new PessoaDAO();
	}
	
	
	public List<Pessoa> getByParteNome(String parteNome) throws DBException {
		List<Pessoa> list = new ArrayList<>();

		GenericDAOImpl.configuraConexao();
		list = new PessoaDAO().getByParteNome(parteNome);
		GenericDAOImpl.closeConnection();

		return list;
	}

}
