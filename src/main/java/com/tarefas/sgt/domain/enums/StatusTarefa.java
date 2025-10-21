package com.tarefas.sgt.domain.enums;

public enum StatusTarefa {

	PENDENTE(0, "Pendente"), EM_ANDAMENTO(1, "Em andamento"), CONCLUIDA(2, "Concluída");
	
	private Integer codigo;
	private String descricao;
	
	private StatusTarefa(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static StatusTarefa toEnum(Integer cod) {
		if(cod == null) {
			return null;
		}
		
		for(StatusTarefa x : StatusTarefa.values()) {
			if(cod.equals(x.getCodigo())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Status Tarefa Inválido");
	}
}
