package com.tarefas.sgt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tarefas.sgt.domain.Tarefa;
import com.tarefas.sgt.domain.Usuario;
import com.tarefas.sgt.domain.dtos.TarefaDTO;
import com.tarefas.sgt.domain.dtos.UsuarioDTO;
import com.tarefas.sgt.domain.enums.StatusTarefa;
import com.tarefas.sgt.repositories.TarefaRepository;
import com.tarefas.sgt.repositories.UsuarioRepository;
import com.tarefas.sgt.services.TarefaService;
import com.tarefas.sgt.services.UsuarioService;

@SpringBootTest
class SGTApplicationTests {
	
    @InjectMocks
    private TarefaService tarefaService;

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    private UsuarioService usuarioService;
    
    @Mock
    private UsuarioRepository usuarioRepository;
    
	@Autowired
	private BCryptPasswordEncoder encoder;
	
    private Tarefa tarefaExemplo;
    private TarefaDTO tarefaDTO;
    
    private Usuario usuarioExemplo;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        tarefaExemplo = new Tarefa(1, "Tarefa 1", "Descrição teste", LocalDate.now(), LocalDate.now().plusDays(1), StatusTarefa.PENDENTE);
        tarefaDTO = new TarefaDTO(tarefaExemplo);

        usuarioExemplo = new Usuario(null, "Usuário de Testes", "550.482.150-95", "usuarioteste@mail.com", encoder.encode("123"), LocalDate.now());
        usuarioDTO     = new UsuarioDTO(usuarioExemplo);
    }

    @Test
    void whenCallFindAllThenReturnListOfTasks() {

        when(tarefaRepository.findAll()).thenReturn(List.of(new Tarefa(), new Tarefa()));

        final var response = tarefaService.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());

        verify(tarefaRepository, times(1)).findAll();
    }
    
    @Test
    void whenCallFindAllThenReturnListOfUser() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario(), new Usuario()));

        final var response = usuarioService.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());

        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void whenCallFindByIdThenReturnTask() {
        Tarefa tarefa = tarefaExemplo;
        tarefa.setId(1);
        when(tarefaRepository.findById(1)).thenReturn(Optional.of(tarefa));

        final var response = tarefaService.findById(1);

        assertNotNull(response);
        assertEquals(1, response.getId());

        verify(tarefaRepository, times(1)).findById(1);
    }

    @Test
    void whenCallFindByIdThenReturnUser() {
        Usuario usuario = usuarioExemplo;
        usuario.setId(1);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        final var response = usuarioService.findById(1);

        assertNotNull(response);
        assertEquals(1, response.getId());

        verify(usuarioRepository, times(1)).findById(1);
    }

    @Test
    void whenCallCreateThenReturnNewTask() {
        Tarefa novaTarefa = tarefaExemplo;
        novaTarefa.setId(1);
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(novaTarefa);

        final var response = tarefaService.create(tarefaDTO);

        assertNotNull(response);
        assertEquals(1, response.getId());

        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

    @Test
    void whenCallUpdateThenReturnUpdatedTask() {
        Tarefa tarefaAtualizada = tarefaExemplo;
        tarefaAtualizada.setId(1);
        tarefaAtualizada.setTitulo("Tarefa Atualizada");
        when(tarefaRepository.findById(1)).thenReturn(Optional.of(new Tarefa()));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefaAtualizada);

        final var response = tarefaService.update(1, tarefaDTO);

        assertNotNull(response);
        assertEquals("Tarefa Atualizada", response.getTitulo());

        verify(tarefaRepository, times(1)).findById(1);
        verify(tarefaRepository, times(1)).save(any(Tarefa.class));
    }

}