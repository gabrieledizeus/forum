package alura.com.forum.controller;

import alura.com.forum.domain.topicos.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @GetMapping
    public List<DadosDetalhamentoTopico> listar() {
        return repository.findAll().stream()
                .map(DadosDetalhamentoTopico::new)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> listarPorId(@PathVariable Long id) {
        var topico = repository.findById(id).orElseThrow();
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {
        var topico = new Topico();
        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());
        topico.setCurso(dados.curso());
        topico.setAutor(dados.autor());
        topico.setStatus(StatusTopico.ABERTO); //

        repository.save(topico);
        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoTopico> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid DadosCadastroTopico dados) {


        var topico = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tópico não encontrado"));

        if (dados.titulo() != null) topico.setTitulo(dados.titulo());
        if (dados.mensagem() != null) topico.setMensagem(dados.mensagem());
        if (dados.curso() != null) topico.setCurso(dados.curso());
        if (dados.autor() != null) topico.setAutor(dados.autor());



        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
