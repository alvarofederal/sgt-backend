package com.tarefas.sgt.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tarefas.sgt.domain.Tarefa;
import com.tarefas.sgt.domain.dtos.TarefaDTO;
import com.tarefas.sgt.services.TarefaService;

@RestController
@RequestMapping(value = "/tarefas")
public class TarefaResource {

	@Autowired
	private TarefaService service;

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<TarefaDTO> findById(@PathVariable Integer id) {
		Tarefa obj = service.findById(id);
		return ResponseEntity.ok().body(new TarefaDTO(obj));
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<TarefaDTO>> findAll() {
		List<Tarefa> list = service.findAll();
		List<TarefaDTO> listDTO = list.stream().map(obj -> new TarefaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping(produces = "application/json")
	public ResponseEntity<TarefaDTO> create(@Valid @RequestBody TarefaDTO obj) {
		Tarefa newObj = service.create(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<TarefaDTO> update(@PathVariable Integer id, @Valid @RequestBody TarefaDTO objDTO) {
		Tarefa newObj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new TarefaDTO(newObj));
	}
	
	@PatchMapping(value = "/{id}/concluir", produces = "application/json")
    public ResponseEntity<Tarefa> concluir(@PathVariable Integer id) {
        return ResponseEntity.ok(service.atualizarStatusConcluido(id));
    }
}
