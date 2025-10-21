package com.tarefas.sgt.domain.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tarefas.sgt.domain.Tarefa;

public class TarefaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotNull(message = "O campo TITULO é requerido")
	private String titulo;
	
	@NotNull(message = "O campo DESCRIÇÃO é requerido")
	private String descricao;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCriacao = LocalDate.now();
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVencimento;

	@NotNull(message = "O campo STATUS é requerido")
	private Integer statusTarefa;

	public TarefaDTO() {
		super();
	}

	public TarefaDTO(Tarefa obj) {
		this.id = obj.getId();
		this.titulo = obj.getTitulo();
		this.descricao = obj.getDescricao();
		this.dataCriacao = obj.getDataCriacao();
		this.dataVencimento = obj.getDataVencimento();
		this.statusTarefa = obj.getStatusTarefa().getCodigo();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatusTarefa() {
		return statusTarefa;
	}

	public void setStatusTarefa(Integer statusTarefa) {
		this.statusTarefa = statusTarefa;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

}
