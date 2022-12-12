package com.dex.fd.bean.backing;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;	
import com.dex.fd.model.Dependente;
import com.dex.fd.model.Funcionario;
import com.dex.fd.model.Pessoa;
import com.dex.fd.service.FuncionarioService;
import com.dex.fd.service.PessoaService;

public class PessoaBacking implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject private PessoaService pessoaService;
	@Inject private FuncionarioService funcionarioService;
	@Inject private Dependente dependenteService;
	private List<Pessoa> pessoas;
	private List<Funcionario> funcionarios;
	private List<Dependente> dependentes;
	private Integer idFuncionario;
	private Integer idDependente;

	public void init() {
		try {
			this.pessoas = this.pessoaService.getAll("NOME");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		

	
	public PessoaService getPessoaService() {
		return pessoaService;
	}
	public void setPessoaService(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}
	public FuncionarioService getFuncionarioService() {
		return funcionarioService;
	}
	public void setFuncionarioService(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}
	public Dependente getDependenteService() {
		return dependenteService;
	}
	public void setDependenteService(Dependente dependenteService) {
		this.dependenteService = dependenteService;
	}
	public List<Pessoa> getPessoa() {
		return pessoas;
	}
	public void setPessoa(List<Pessoa> pessoa) {
		this.pessoas = pessoa;
	}
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	public List<Dependente> getDependetes() {
		return dependentes;
	}
	public void setDependetes(List<Dependente> dependetes) {
		this.dependentes = dependetes;
	}
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Integer getIdDependente() {
		return idDependente;
	}

	public void setIdDependente(Integer idDependente) {
		this.idDependente = idDependente;
	}

}
