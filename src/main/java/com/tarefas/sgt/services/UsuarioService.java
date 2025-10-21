package com.tarefas.sgt.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tarefas.sgt.domain.SignForm;
import com.tarefas.sgt.domain.Usuario;
import com.tarefas.sgt.domain.dtos.UsuarioDTO;
import com.tarefas.sgt.repositories.UsuarioRepository;
import com.tarefas.sgt.services.exceptions.DataIntegrityViolationException;
import com.tarefas.sgt.services.exceptions.ObjectnotFoundException;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
//	@Autowired
//	private PessoaRepository pessoaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Usuario findById(Integer id) {
		Optional<Usuario> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id: " + id));
	}

	public List<Usuario> findAll() {
		return repository.findAll();
	}

	public Usuario create(UsuarioDTO objDTO) {
		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaPorCpfEEmail(objDTO);
		Usuario newObj = new Usuario(objDTO.getId(),objDTO.getNome(), objDTO.getCpf(), objDTO.getEmail(), objDTO.getSenha(), objDTO.getDataCriacao());
		return repository.save(newObj);
	}
 
	public Usuario update(Integer id, @Valid UsuarioDTO objDTO) {
		objDTO.setId(id);
		Usuario oldObj = findById(id);
		
		if(!objDTO.getSenha().equals(oldObj.getSenha())) 
			objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		
		validaPorCpfEEmail(objDTO);
		oldObj = new Usuario(objDTO.getId(),objDTO.getNome(), objDTO.getCpf(), objDTO.getEmail(), objDTO.getSenha(), objDTO.getDataCriacao());
		return repository.save(oldObj);
	}

	private void validaPorCpfEEmail(UsuarioDTO objDTO) {
		Optional<Usuario> obj = repository.findByCpf(objDTO.getCpf());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}

		obj = repository.findByEmail(objDTO.getEmail());
		if (obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
	}
	
	public Usuario register(SignForm signForm) {
		Usuario user = new Usuario();
		user.setNome(signForm.getNome());
		user.setEmail(signForm.getEmail());
		user.setSenha(encoder.encode(signForm.getPassword()));
		user.setCpf(signForm.getCpf());

		user.addPerfil(com.tarefas.sgt.domain.enums.Perfil.ADMIN);
		Usuario users = repository.save(user);
		return users;
	}
	
	public boolean existsByEmail(String email) {
		if (repository.existsByEmail(email)) {
			return true;
		}
		return false;
	}

}
