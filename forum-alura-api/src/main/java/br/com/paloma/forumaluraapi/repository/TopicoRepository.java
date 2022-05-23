package br.com.paloma.forumaluraapi.repository;

import br.com.paloma.forumaluraapi.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    //usando a jpa pra fazer a pesquisa pelo nome do atributo do relacionamento
    Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);

    //para diferenciar o atributo da classe do atributo do relacionamento em caso de ambiguidade
    //List<Topico> findByCurso_Nome(String nomeCurso)

    //Para dar um nome personalizado a jpa precisa que passemos a query em jpql
    //@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
    //List<Topico> carregarPorCurso(@Param("nomeCurso") String nomeCurso);
}
