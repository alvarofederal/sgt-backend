package com.tarefas.sgt.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarefas.sgt.domain.Tarefa;
import com.tarefas.sgt.domain.dtos.TarefaDTO;
import com.tarefas.sgt.domain.enums.StatusTarefa;
import com.tarefas.sgt.repositories.TarefaRepository;
import com.tarefas.sgt.services.exceptions.DataIntegrityViolationException;
import com.tarefas.sgt.services.exceptions.ObjectnotFoundException;

@Service
public class TarefaService {

	@Autowired
	private TarefaRepository repository;
	

	public Tarefa findById(Integer id) {
		Optional<Tarefa> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! ID: " + id));
	}

	public List<Tarefa> findAll() {
		return repository.findAll();
	}

	public Tarefa create(TarefaDTO obj) {
		return repository.save(newTarefa(obj));
	}

	public Tarefa update(Integer id, @Valid TarefaDTO objDTO) {
		objDTO.setId(id);
		Tarefa oldObj = findById(id);
		oldObj = newTarefa(objDTO);
		return repository.save(oldObj);
	}

	private Tarefa newTarefa(TarefaDTO obj) {
		Tarefa tarefa = new Tarefa();
		if(obj.getId() != null) {
			tarefa.setId(obj.getId());
		}
		
		if(obj.getId() == null) {
			tarefa.setDataCriacao(LocalDate.now());
		}
		
		tarefa.setTitulo(obj.getTitulo());
		tarefa.setDescricao(obj.getDescricao());
		tarefa.setDataVencimento(obj.getDataVencimento());
		tarefa.setStatusTarefa(StatusTarefa.toEnum(obj.getStatusTarefa()));
		return tarefa;
	}
	
	public void delete(Integer id) {
		Tarefa obj = findById(id);
		if (obj.getId() > 0) {
			throw new DataIntegrityViolationException("Cliente possui ordens de serviço e não pode ser deletado!");
		}
		repository.deleteById(id);
	}
	
	// Update direto: seta enum fixo (código 2 = CONCLUIDO)
    public Tarefa atualizarStatusConcluido(Integer id) {
        Tarefa tarefa = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        tarefa.setStatusTarefa(StatusTarefa.CONCLUIDA);  // Seta direto o enum
        return repository.save(tarefa);
    }
}
