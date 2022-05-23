package br.com.paloma.forumaluraapi.model.dto;

import br.com.paloma.forumaluraapi.model.Curso;
import br.com.paloma.forumaluraapi.model.Topico;
import br.com.paloma.forumaluraapi.repository.CursoRepository;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class TopicoForm {

    @NotNull
    @NotBlank
    @Length(min = 5)
    private String titulo;
    @NotNull @NotBlank
    private String mensagem;
    @NotNull @NotBlank
    private String nomeCurso;

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }


    public Topico converter(CursoRepository cursoRepository) {
        Curso curso = cursoRepository.findByNome(nomeCurso);
        return new Topico(titulo, mensagem, curso);
    }
}
