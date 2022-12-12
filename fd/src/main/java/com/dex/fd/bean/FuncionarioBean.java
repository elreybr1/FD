package com.dex.fd.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.SelectEvent;

import com.dex.fd.model.Dependente;
import com.dex.fd.model.Funcionario;
import com.dex.fd.service.FuncionarioService;
import com.dex.fd.service.exceprion.DBException;

public class FuncionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	 private Funcionario funcionario;
	 private FuncionarioService service;
	 private Funcionario funcionarioSelecionada;
	 private List<Dependente> dependentes;
	 private Integer idFuncionarioSelecionada;
	 private List<Funcionario> funcionarios;
	
	public FuncionarioBean() {
	}
	
	public void onItemSelect(SelectEvent<Integer> event) {
		this.idFuncionarioSelecionada = event.getObject();
		pesqFuncionarioSelecionada();
	}
	
	public void pesqFuncionarioSelecionada() {
		this.funcionarios = new ArrayList<>();
		try {
			if (this.idFuncionarioSelecionada != null) {
				this.funcionarioSelecionada = this.service.getById(this.idFuncionarioSelecionada);
				this.funcionarios.add(this.funcionarioSelecionada);
			}
		} catch (SQLException e) {
			addMessage(FacesMessage.SEVERITY_FATAL, "ERRO", e.getMessage());
		}
	}
	
	public List<Funcionario> compleText(String query){
		List<Funcionario> list = new ArrayList<>();
		try {
			list = this.service.getByParteNome(query.toLowerCase());
		} catch (DBException ex) {
			addMessage(FacesMessage.SEVERITY_FATAL, "ERRO",ex.getMessage());
		}
		
		return list;
	}
	
	public String salva() {
		Funcionario pessoaToPersist = this.funcionarioSelecionada;
		try {
			this.funcionarioSelecionada = service.save(pessoaToPersist);
			this.funcionarios = service.getAll("NOME_COMPLETO");
		} catch (DBException ex) {
			addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", ex.getMessage());
		}
		addMessage(FacesMessage.SEVERITY_INFO, "SUCESSO", "Pessoa salva na base.");
		this.limpa();

		return "#";
	}

	
	public void remove() {
		try {
			service.removeById(funcionarioSelecionada.getId());
			this.funcionarios.remove(funcionarioSelecionada);
			addMessage(FacesMessage.SEVERITY_INFO, "SUCESSO", "Remoção realizada.");
		} catch (DBException e) {
			addMessage(FacesMessage.SEVERITY_FATAL, "ERRO", e.getMessage());
		}
	}

	public void limpa() {
		this.funcionarios = new ArrayList<>();
	}
	
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		
		FacesContext.getCurrentInstance().addMessage( null, new FacesMessage(severity, summary, detail));
		
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public FuncionarioService getService() {
		return service;
	}
	public void setService(FuncionarioService service) {
		this.service = service;
	}
	public Funcionario getFuncionarioSelecionada() {
		return funcionarioSelecionada;
	}
	public void setFuncionarioSelecionada(Funcionario funcionarioSelecionada) {
		this.funcionarioSelecionada = funcionarioSelecionada;
	}
	public List<Dependente> getDependentes() {
		return dependentes;
	}
	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}



	public Integer getIdFuncionarioSelecionada() {
		return idFuncionarioSelecionada;
	}



	public void setIdFuncionarioSelecionada(Integer idFuncionarioSelecionada) {
		this.idFuncionarioSelecionada = idFuncionarioSelecionada;
		try {
			this.funcionarioSelecionada = this.service.getById(idFuncionarioSelecionada);
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}



	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}



	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}



}
