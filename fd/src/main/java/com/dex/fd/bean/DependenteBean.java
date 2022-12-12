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
import com.dex.fd.service.DependenteService;
import com.dex.fd.service.exceprion.DBException;

public class DependenteBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject private Dependente dependenteSelecionado;
	@Inject private DependenteService service;
	@Inject private Dependente depentende;
	private Integer idDependenteSelecionada;
	private List<Dependente> dependentes;
	private List<Funcionario> funcionarios;
	
	public DependenteBean() {
	}
	
	public void onItemSelect(SelectEvent<Integer> event) {
		this.idDependenteSelecionada = event.getObject();
		pesqFuncionarioSelecionada();
	}
	
	public void pesqFuncionarioSelecionada() {
		this.funcionarios = new ArrayList<>();
		try {
			if (this.idDependenteSelecionada != null) {
				this.dependenteSelecionado = this.service.getById(this.idDependenteSelecionada);
				this.dependentes.add(this.dependenteSelecionado);
			}
		} catch (SQLException e) {
			addMessage(FacesMessage.SEVERITY_FATAL, "ERRO", e.getMessage());
		}
	}
	
	public List<Dependente> compleText(String query){
		List<Dependente> list = new ArrayList<>();
		try {
			list = this.service.getByParteNome(query.toLowerCase());
		} catch (DBException ex) {
			addMessage(FacesMessage.SEVERITY_FATAL, "ERRO",ex.getMessage());
		}
		
		return list;
	}
	
	public String salva() {
		Dependente pessoaToPersist = this.dependenteSelecionado;
		try {
			this.dependenteSelecionado = service.save(pessoaToPersist);
			this.dependentes = service.getAll("NOME_COMPLETO");
		} catch (DBException ex) {
			addMessage(FacesMessage.SEVERITY_ERROR, "ERRO", ex.getMessage());
		}
		addMessage(FacesMessage.SEVERITY_INFO, "SUCESSO", "Pessoa salva na base.");
		this.limpa();

		return "#";
	}

	
	public void remove() {
		try {
			service.removeById(dependenteSelecionado.getId());
			this.dependentes.remove(dependenteSelecionado);
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
	
	

	public Dependente getDependenteSelecionado() {
		return dependenteSelecionado;
	}

	public void setDependenteSelecionado(Dependente dependenteSelecionado) {
		this.dependenteSelecionado = dependenteSelecionado;
	}

	public DependenteService getService() {
		return service;
	}
	public void setService(DependenteService service) {
		this.service = service;
	}
	public Dependente getDepentende() {
		return depentende;
	}
	public void setDepentende(Dependente depentende) {
		this.depentende = depentende;
	}
	public Integer getIdDependenteSelecionada() {
		return idDependenteSelecionada;
	}
	public void setIdDependenteSelecionada(Integer idDependenteSelecionada) {
		this.idDependenteSelecionada = idDependenteSelecionada;
	}
	public List<Dependente> getDependentes() {
		return dependentes;
	}
	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	

	
}
