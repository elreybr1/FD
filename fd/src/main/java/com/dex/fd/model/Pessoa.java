package com.dex.fd.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Pessoa implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nomeCompleto;
	private Integer idade;
	private LocalDate dataNascimento;
	private String cpf;
	
	public Pessoa() {
		
	}
	
	public Pessoa(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public Integer getIdade() {
		return idade;
	}
	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	public LocalDate getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
