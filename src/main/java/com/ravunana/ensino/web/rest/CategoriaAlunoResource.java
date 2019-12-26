package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.CategoriaAlunoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.CategoriaAlunoDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.ravunana.ensino.domain.CategoriaAluno}.
 */
@RestController
@RequestMapping("/api")
public class CategoriaAlunoResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaAlunoResource.class);

    private static final String ENTITY_NAME = "categoriaAluno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategoriaAlunoService categoriaAlunoService;

    public CategoriaAlunoResource(CategoriaAlunoService categoriaAlunoService) {
        this.categoriaAlunoService = categoriaAlunoService;
    }

    /**
     * {@code POST  /categoria-alunos} : Create a new categoriaAluno.
     *
     * @param categoriaAlunoDTO the categoriaAlunoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categoriaAlunoDTO, or with status {@code 400 (Bad Request)} if the categoriaAluno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categoria-alunos")
    public ResponseEntity<CategoriaAlunoDTO> createCategoriaAluno(@Valid @RequestBody CategoriaAlunoDTO categoriaAlunoDTO) throws URISyntaxException {
        log.debug("REST request to save CategoriaAluno : {}", categoriaAlunoDTO);
        if (categoriaAlunoDTO.getId() != null) {
            throw new BadRequestAlertException("A new categoriaAluno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategoriaAlunoDTO result = categoriaAlunoService.save(categoriaAlunoDTO);
        return ResponseEntity.created(new URI("/api/categoria-alunos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categoria-alunos} : Updates an existing categoriaAluno.
     *
     * @param categoriaAlunoDTO the categoriaAlunoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categoriaAlunoDTO,
     * or with status {@code 400 (Bad Request)} if the categoriaAlunoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categoriaAlunoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categoria-alunos")
    public ResponseEntity<CategoriaAlunoDTO> updateCategoriaAluno(@Valid @RequestBody CategoriaAlunoDTO categoriaAlunoDTO) throws URISyntaxException {
        log.debug("REST request to update CategoriaAluno : {}", categoriaAlunoDTO);
        if (categoriaAlunoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CategoriaAlunoDTO result = categoriaAlunoService.save(categoriaAlunoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categoriaAlunoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /categoria-alunos} : get all the categoriaAlunos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categoriaAlunos in body.
     */
    @GetMapping("/categoria-alunos")
    public ResponseEntity<List<CategoriaAlunoDTO>> getAllCategoriaAlunos(Pageable pageable) {
        log.debug("REST request to get a page of CategoriaAlunos");
        Page<CategoriaAlunoDTO> page = categoriaAlunoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categoria-alunos/:id} : get the "id" categoriaAluno.
     *
     * @param id the id of the categoriaAlunoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categoriaAlunoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categoria-alunos/{id}")
    public ResponseEntity<CategoriaAlunoDTO> getCategoriaAluno(@PathVariable Long id) {
        log.debug("REST request to get CategoriaAluno : {}", id);
        Optional<CategoriaAlunoDTO> categoriaAlunoDTO = categoriaAlunoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categoriaAlunoDTO);
    }

    /**
     * {@code DELETE  /categoria-alunos/:id} : delete the "id" categoriaAluno.
     *
     * @param id the id of the categoriaAlunoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categoria-alunos/{id}")
    public ResponseEntity<Void> deleteCategoriaAluno(@PathVariable Long id) {
        log.debug("REST request to delete CategoriaAluno : {}", id);
        categoriaAlunoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/categoria-alunos?query=:query} : search for the categoriaAluno corresponding
     * to the query.
     *
     * @param query the query of the categoriaAluno search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/categoria-alunos")
    public ResponseEntity<List<CategoriaAlunoDTO>> searchCategoriaAlunos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CategoriaAlunos for query {}", query);
        Page<CategoriaAlunoDTO> page = categoriaAlunoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
