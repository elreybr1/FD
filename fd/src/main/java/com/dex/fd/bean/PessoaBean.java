package com.dex.fd.bean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import com.dex.fd.bean.backing.PessoaBacking;
import com.dex.fd.model.Dependente;
import com.dex.fd.model.Funcionario;
import com.dex.fd.model.Pessoa;
import com.dex.fd.service.PessoaService;
import com.dex.fd.service.exceprion.DBException;

@Named
public class PessoaBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject private PessoaBacking backing;
	@Inject private Pessoa pessoa;
	@Inject private Pessoa pessoaSelecionada;
	@Inject private PessoaService pessoaService;
	@Inject private Funcionario funcionarioSelecionada;
	@Inject private Dependente dependenteSelecionada;
	private Integer idPessoaSelecionada;
	private List<Pessoa> pessoas;
	
	public PessoaBean() {}
	
	public void init() {
		this.pessoas = new ArrayList<>();
		this.backing.init();
	}
	
	public void onItemSelect(SelectEvent<Integer> event) {
		this.idPessoaSelecionada = event.getObject();
		pesqPessoaSelecionada();
		
	}
	
	public void pesqPessoaSelecionada() {
		this.pessoas = new ArrayList<>();
		try {
			if(this.idPessoaSelecionada != null) {
				this.pessoaSelecionada = this.pessoaService.getById(idPessoaSelecionada);
			   this.pessoas.add(this.pessoaSelecionada);
			}
		}catch (SQLException e) {
			addMessage(FacesMessage.SEVERITY_FATAL,"ERRO", e.getMessage());
		}
	}
	
	public List<Pessoa> completeText(String query){
		List<Pessoa> list = new ArrayList<>();
		try {
			list = this.pessoaService.getByParteNome(query.toLowerCase());
		}catch(DBException ex) {
			addMessage(FacesMessage.SEVERITY_FATAL, "ERRO",ex.getMessage());
		}
		
		return list;
	}
	
	public void getAll() {
		try {
			this.pessoas = this.pessoaService.getAll("NOME");
		}catch(DBException ex) {
			addMessage(FacesMessage.SEVERITY_FATAL, "ERRO",ex.getMessage());
		}
	}
	
	public String salva() {
		Pessoa pessoaToPersist = this.pessoaSelecionada;
		try {
			this.pessoaSelecionada = pessoaService.save(pessoaToPersist);
			this.pessoas = pessoaService.getAll("NOME");
		}catch(DBException ex) {
			addMessage(FacesMessage.SEVERITY_FATAL, "ERRO",ex.getMessage());
		}
		addMessage(FacesMessage.SEVERITY_INFO, "SUCESSO", "Pessoa salva na base.");
		this.limpa();
		
		return "#";
	}
	
	public boolean getTemPessoaSelecionada() {
		return this.pessoaSelecionada != null;
	}
	
	public void cadastraNovo() {
		this.pessoaSelecionada = new Pessoa();
	}
	
	public void remove() {
		try {
			pessoaService.removeById(pessoaSelecionada.getId());
			this.pessoas.remove(pessoaSelecionada);
			addMessage(FacesMessage.SEVERITY_FATAL, "SUCESSO", "Remoção realizada");
		}catch (DBException e) {
			addMessage(FacesMessage.SEVERITY_FATAL, "ERRO",e.getMessage());
		}
	}
	
	public void limpa() {
		this.pessoas = new ArrayList<>();
	}
	
	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}
	
	
	public PessoaBacking getBacking() {
		return backing;
	}

	public void setBacking(PessoaBacking backing) {
		this.backing = backing;
	}

	public Pessoa getPessoaSelecionada() {
		return pessoaSelecionada;
	}

	public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
		this.pessoaSelecionada = pessoaSelecionada;
	}

	public Funcionario getFuncionarioSelecionada() {
		return funcionarioSelecionada;
	}

	public void setFuncionarioSelecionada(Funcionario funcionarioSelecionada) {
		this.funcionarioSelecionada = funcionarioSelecionada;
	}

	public Dependente getDependenteSelecionada() {
		return dependenteSelecionada;
	}

	public void setDependenteSelecionada(Dependente dependenteSelecionada) {
		this.dependenteSelecionada = dependenteSelecionada;
	}

	public Integer getIdPessoaSelecionada() {
		return idPessoaSelecionada;
	}

	public void setIdPessoaSelecionada(Integer idPessoaSelecionada) {
		this.idPessoaSelecionada = idPessoaSelecionada;
		try {
			this.pessoaSelecionada = this.pessoaService.getById(idPessoaSelecionada);
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public PessoaService getPessoaService() {
		return pessoaService;
	}

	public void setPessoaService(PessoaService pessoaService) {
		this.pessoaService = pessoaService;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
}