package com.tarefas.sgt.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tarefas.sgt.domain.SignForm;
import com.tarefas.sgt.domain.Usuario;
import com.tarefas.sgt.domain.dtos.UsuarioDTO;
import com.tarefas.sgt.services.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioResource {

	@Autowired
	private UsuarioService service;

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<UsuarioDTO> findById(@PathVariable Integer id) {
		Usuario obj = service.findById(id);
		return ResponseEntity.ok().body(new UsuarioDTO(obj));
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<List<UsuarioDTO>> findAll() {
		List<Usuario> list = service.findAll();
		List<UsuarioDTO> listDTO = list.stream().map(obj -> new UsuarioDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
	}

	@PostMapping(produces = "application/json")
	public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTO objDTO) {
		Usuario newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<UsuarioDTO> update(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO objDTO) {
		Usuario obj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new UsuarioDTO(obj));
	}
	
	@PostMapping(value = "/register", produces = "application/json")
	public ResponseEntity<String> registerUser(@Valid @RequestBody SignForm form, final HttpServletRequest request) {
		if (service.existsByEmail(form.getEmail())) {
			return new ResponseEntity<String>("Usuário já cadastrado!", HttpStatus.CONFLICT);
		}
        Usuario newUsers = service.register(form);
        
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUsers.getId()).toUri();
		return ResponseEntity.created(uri).build();
    }
	
}
 

















