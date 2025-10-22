package com.tarefas.sgt.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tarefas.sgt.domain.enums.StatusTarefa;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class Tarefa implements Serializable {

	private static final long serialVersionUID = 7207798038215196224L;

    @ApiModelProperty(value = "Código da Tarefa")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @ApiModelProperty(value = "Título da Tarefa")
	private String titulo;

    @ApiModelProperty(value = "Descrição da Tarefa")
	private String descricao;
	
    @ApiModelProperty(value = "Data de Criação da Tarefa")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCriacao = LocalDate.now();

    @ApiModelProperty(value = "Data de Vencimento da Tarefa")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataVencimento;

    @ApiModelProperty(value = "Status da Tarefa")
	private StatusTarefa statusTarefa;

	public Tarefa() {
		super();
	}

	public Tarefa(Integer id, String titulo, String descricao, LocalDate dataCriacao, LocalDate dataVencimento, StatusTarefa statusTarefa) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.dataCriacao = dataCriacao;
		this.dataVencimento = dataVencimento;
		this.statusTarefa = statusTarefa;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public StatusTarefa getStatusTarefa() {
		return statusTarefa;
	}

	public void setStatusTarefa(StatusTarefa statusTarefa) {
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

	@Override
	public int hashCode() {
		return Objects.hash(dataCriacao, dataVencimento, descricao, id, statusTarefa, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarefa other = (Tarefa) obj;
		return Objects.equals(dataCriacao, other.dataCriacao) && Objects.equals(dataVencimento, other.dataVencimento)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id) && statusTarefa == other.statusTarefa
				&& Objects.equals(titulo, other.titulo);
	}

}
