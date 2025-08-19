package alura.com.forum.domain.topicos;

import java.time.LocalDateTime;

public record DadosCadastroTopico(String titulo, String mensagem, String curso, String autor, StatusTopico statusTopico) {}
