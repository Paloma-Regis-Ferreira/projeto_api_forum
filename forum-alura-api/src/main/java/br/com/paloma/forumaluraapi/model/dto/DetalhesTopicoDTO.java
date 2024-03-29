package br.com.paloma.forumaluraapi.model.dto;

import br.com.paloma.forumaluraapi.model.StatusTopico;
import br.com.paloma.forumaluraapi.model.Topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DetalhesTopicoDTO {

    private Long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataDeCriacao;
    private String nomeAutor;
    private StatusTopico status;

    private List<RespostaDTO> respostas;

    public DetalhesTopicoDTO(Topico topico) {
        this.id = topico.getId();
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataDeCriacao = topico.getDataCriacao();
        this.nomeAutor = topico.getAutor().getNome();
        this.status = topico.getStatus();

        this.respostas = new ArrayList<>();
        this.respostas.addAll(topico.getRespostas().stream().map(RespostaDTO::new).collect(Collectors.toList()));
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataDeCriacao() {
        return dataDeCriacao;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public List<RespostaDTO> getRespostas() {
        return respostas;
    }
}
