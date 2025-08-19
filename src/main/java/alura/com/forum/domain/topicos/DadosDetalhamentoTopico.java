package alura.com.forum.domain.topicos;

import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(Long id, String titulo, String mensagem, String curso, LocalDateTime dataCriacao, StatusTopico status, String autor) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensagem(), topico.getCurso(), topico.getDataCriacao(), topico.getStatus(), topico.getAutor());
    }
}
