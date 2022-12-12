package com.dex.fd.model;

import java.io.Serializable;

public class Dependente extends Pessoa implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Funcionario funcionario;
	private Integer idFuncionario;
	private String grau;
	
	public Dependente(){
		super();
	}
	
	public Dependente(Integer id) {
		super(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getGrau() {
		return grau;
	}

	public void setGrau(String grau) {
		this.grau = grau;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	
	
}
