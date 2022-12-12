package com.dex.fd.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dex.fd.dao.FuncionarioDAO;
import com.dex.fd.dao.interfaces.GenericDAO;
import com.dex.fd.dao.jdbc.GenericDAOImpl;
import com.dex.fd.model.Funcionario;
import com.dex.fd.service.exceprion.DBException;
import com.dex.fd.service.interfaces.ServiceImpl;

public class FuncionarioService extends ServiceImpl<Funcionario> implements Serializable{

	private static final long serialVersionUID = 1L;

	@Override
	protected GenericDAO<Funcionario> getDao() {
		return new FuncionarioDAO();
	}
	
	public List<Funcionario> getByParteNome(String parteNome) throws DBException {
		List<Funcionario> list = new ArrayList<>();

		GenericDAOImpl.configuraConexao();
		list = new FuncionarioDAO().getByParteNome(parteNome);
		GenericDAOImpl.closeConnection();

		return list;
	}

}
