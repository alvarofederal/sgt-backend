package com.tarefas.sgt.services;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tarefas.sgt.domain.Tarefa;
import com.tarefas.sgt.domain.Usuario;
import com.tarefas.sgt.domain.enums.Perfil;
import com.tarefas.sgt.domain.enums.StatusTarefa;
import com.tarefas.sgt.repositories.TarefaRepository;
import com.tarefas.sgt.repositories.UsuarioRepository;

@Service
public class DBService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public void instanciaDB() {

		Usuario u1 = new Usuario(null, "Valdir Amaral", "550.482.150-95", "valdir@mail.com", encoder.encode("123"), LocalDate.now());
		u1.addPerfil(Perfil.ADMIN);
		Usuario u2 = new Usuario(null, "Albert Einstein", "111.661.890-74", "einstein@mail.com", encoder.encode("123"), LocalDate.now());
		u2.addPerfil(Perfil.ADMIN);
		Usuario u3 = new Usuario(null, "Marie Curie", "322.429.140-06", "curie@mail.com", encoder.encode("123"), LocalDate.now());
		Usuario u4 = new Usuario(null, "Charles Darwin", "792.043.830-62", "darwin@mail.com", encoder.encode("123"), LocalDate.now());
		Usuario u5 = new Usuario(null, "Stephen Hawking", "177.409.680-30", "hawking@mail.com", encoder.encode("123"), LocalDate.now());
		Usuario u6 = new Usuario(null, "Max Planck", "081.399.300-83", "planck@mail.com", encoder.encode("123"), LocalDate.now());
		
		Tarefa t1 = new Tarefa(null, "Lavar as louça", "Usar água e detergente", LocalDate.now(), LocalDate.of(2025, 11, 15), StatusTarefa.PENDENTE); 
		Tarefa t2 = new Tarefa(null, "Lavar a garagem", "Usar água e sabão em pedra", LocalDate.now(), LocalDate.of(2025, 11, 15), StatusTarefa.EM_ANDAMENTO); 
		Tarefa t3 = new Tarefa(null, "Lavar o carro", "Usar água e sabão neutro", LocalDate.now(), LocalDate.of(2025, 11, 15), StatusTarefa.PENDENTE); 
		Tarefa t4 = new Tarefa(null, "Lavar o banheiro", "Usar esfregão, água e sabão", LocalDate.now(), LocalDate.of(2025, 11, 15), StatusTarefa.PENDENTE); 
		Tarefa t5 = new Tarefa(null, "Trocar a resistência do chuveiro", "Desligar a luz geral, e usar ferramentas adequadas", LocalDate.now(), LocalDate.of(2025, 11, 15), StatusTarefa.PENDENTE); 
		
		usuarioRepository.saveAll(Arrays.asList(u1, u2, u3, u4, u5, u6));
		tarefaRepository.saveAll(Arrays.asList(t1, t2, t3, t4, t5));
	}
}
