package br.com.paloma.forumaluraapi.controllers;

import br.com.paloma.forumaluraapi.model.Topico;
import br.com.paloma.forumaluraapi.model.dto.AtualizacaoTopicoForm;
import br.com.paloma.forumaluraapi.model.dto.DetalhesTopicoDTO;
import br.com.paloma.forumaluraapi.model.dto.TopicoDTO;
import br.com.paloma.forumaluraapi.model.dto.TopicoForm;
import br.com.paloma.forumaluraapi.repository.CursoRepository;
import br.com.paloma.forumaluraapi.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

//A notação @Controller assume que esta sendo direcionada para uma view e com a notacao @ResponseBody retornamos o json da api
// o REST controler faz a mesma coisa
@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    CursoRepository cursoRepository;

    //onde usar cache: nos dados que raramente mudam, o get não seria um bom exemplo como foi feito aqui no projeto

     @GetMapping
     @Cacheable(value = "listaDeTopicos")//passar uma string para diferenciar os caches
     //request param é um parametro passado para a requisição, o falso torna o nome não obrigatorio
     public Page<TopicoDTO> listarTopicos(@RequestParam(required = false) String nomeCurso,
                                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10)
                                                           Pageable paginacao){
         //habilitar @EnableSpringDataWebSupport no main da aplicação para usar o pageable na requisição do get
         //pageableDefault organiza a paginação se nenhum parametro for passado pelo usuario
         //http://localhost:8080/topicos?page=0&size=5&sort=id,desc&sort=titulo,asc
         //o sort é acumulativo

//         Sort sort = Sort.by("titulo").descending();
//         Pageable paginacao = PageRequest.of(pagina, qtd, sort);

//         @RequestParam int pagina, @RequestParam int qtd, @RequestParam(required = false) String ordenacao){
//         paginacao = PageRequest.of(pagina, qtd, Sort.Direction.DESC, ordenacao);

        if (nomeCurso == null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDTO.converterEmDTO(topicos);
        } else {
            Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
            return TopicoDTO.converterEmDTO(topicos);
        }
    }

    @PostMapping
    //vai limpar o cache de acordo com o id de cache passado e limpar todos os registros
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<TopicoDTO> create(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriComponentsBuilder){
         Topico topico = form.converter(cursoRepository);
         topicoRepository.save(topico);

         URI uri = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
         return ResponseEntity.created(uri).body(new TopicoDTO(topico));
    }

    @GetMapping("/{id}")
    @Transactional //para commitar a atualização
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<DetalhesTopicoDTO> detalhar(@PathVariable Long id){
         Optional<Topico> topico = topicoRepository.findById(id);
         if (topico.isPresent()){
             return ResponseEntity.ok(new DetalhesTopicoDTO(topico.get()));
         }
         return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional //para commitar a atualização
    public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
         Optional<Topico> optional = topicoRepository.findById(id);
         if (optional.isPresent()){
             Topico topico = form.atualizar(id, optional.get());
             return ResponseEntity.ok(new TopicoDTO(topico));
         }
         return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional //para commitar a atualização
    public ResponseEntity remover(@PathVariable Long id){
         Optional<Topico> optional = topicoRepository.findById(id);
         if (optional.isPresent()){
             topicoRepository.deleteById(id);
             return ResponseEntity.ok().build();
         }
         return ResponseEntity.notFound().build();
    }

}
