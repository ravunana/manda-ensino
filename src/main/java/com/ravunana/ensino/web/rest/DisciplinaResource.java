package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.DisciplinaService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.DisciplinaDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.Disciplina}.
 */
@RestController
@RequestMapping("/api")
public class DisciplinaResource {

    private final Logger log = LoggerFactory.getLogger(DisciplinaResource.class);

    private static final String ENTITY_NAME = "disciplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplinaService disciplinaService;

    public DisciplinaResource(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    /**
     * {@code POST  /disciplinas} : Create a new disciplina.
     *
     * @param disciplinaDTO the disciplinaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplinaDTO, or with status {@code 400 (Bad Request)} if the disciplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disciplinas")
    public ResponseEntity<DisciplinaDTO> createDisciplina(@Valid @RequestBody DisciplinaDTO disciplinaDTO) throws URISyntaxException {
        log.debug("REST request to save Disciplina : {}", disciplinaDTO);
        if (disciplinaDTO.getId() != null) {
            throw new BadRequestAlertException("A new disciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisciplinaDTO result = disciplinaService.save(disciplinaDTO);
        return ResponseEntity.created(new URI("/api/disciplinas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disciplinas} : Updates an existing disciplina.
     *
     * @param disciplinaDTO the disciplinaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplinaDTO,
     * or with status {@code 400 (Bad Request)} if the disciplinaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplinaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disciplinas")
    public ResponseEntity<DisciplinaDTO> updateDisciplina(@Valid @RequestBody DisciplinaDTO disciplinaDTO) throws URISyntaxException {
        log.debug("REST request to update Disciplina : {}", disciplinaDTO);
        if (disciplinaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisciplinaDTO result = disciplinaService.save(disciplinaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplinaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /disciplinas} : get all the disciplinas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplinas in body.
     */
    @GetMapping("/disciplinas")
    public ResponseEntity<List<DisciplinaDTO>> getAllDisciplinas(Pageable pageable) {
        log.debug("REST request to get a page of Disciplinas");
        Page<DisciplinaDTO> page = disciplinaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disciplinas/:id} : get the "id" disciplina.
     *
     * @param id the id of the disciplinaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplinaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disciplinas/{id}")
    public ResponseEntity<DisciplinaDTO> getDisciplina(@PathVariable Long id) {
        log.debug("REST request to get Disciplina : {}", id);
        Optional<DisciplinaDTO> disciplinaDTO = disciplinaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disciplinaDTO);
    }

    /**
     * {@code DELETE  /disciplinas/:id} : delete the "id" disciplina.
     *
     * @param id the id of the disciplinaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disciplinas/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable Long id) {
        log.debug("REST request to delete Disciplina : {}", id);
        disciplinaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/disciplinas?query=:query} : search for the disciplina corresponding
     * to the query.
     *
     * @param query the query of the disciplina search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/disciplinas")
    public ResponseEntity<List<DisciplinaDTO>> searchDisciplinas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Disciplinas for query {}", query);
        Page<DisciplinaDTO> page = disciplinaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
