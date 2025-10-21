package com.tarefas.sgt.domain;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignForm {
	
    @NotBlank
    @Size(min = 3, max = 30)
    private String nome;
 
    @NotBlank
    @Size(max = 30)
    @Email
    private String email;
    
	protected String cpf;
     
	protected Set<Integer> perfis = new HashSet<>();
    
    @NotBlank
    @Size(min = 2, max = 40)
    private String password;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Integer> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Integer> perfis) {
		this.perfis = perfis;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
