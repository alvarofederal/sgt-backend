package com.tarefas.sgt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tarefas.sgt.domain.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Integer> {

}
