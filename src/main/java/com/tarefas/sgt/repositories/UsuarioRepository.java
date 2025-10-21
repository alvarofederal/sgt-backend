package com.tarefas.sgt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.tarefas.sgt.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	public Optional<Usuario> findById(@Param("id") Integer id);
	
	public Optional<Usuario> findByEmail(@Param("email") String email);
    
	public Boolean existsByEmail(String email);

	public Optional<Usuario> findByEmailIgnoreCase(String email);
	
	public Optional<Usuario> findByCpf(String cpf);
}
