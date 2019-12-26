package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.MatriculaService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.MatriculaDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.Matricula}.
 */
@RestController
@RequestMapping("/api")
public class MatriculaResource {

    private final Logger log = LoggerFactory.getLogger(MatriculaResource.class);

    private static final String ENTITY_NAME = "matricula";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MatriculaService matriculaService;

    public MatriculaResource(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    /**
     * {@code POST  /matriculas} : Create a new matricula.
     *
     * @param matriculaDTO the matriculaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new matriculaDTO, or with status {@code 400 (Bad Request)} if the matricula has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/matriculas")
    public ResponseEntity<MatriculaDTO> createMatricula(@Valid @RequestBody MatriculaDTO matriculaDTO) throws URISyntaxException {
        log.debug("REST request to save Matricula : {}", matriculaDTO);
        if (matriculaDTO.getId() != null) {
            throw new BadRequestAlertException("A new matricula cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatriculaDTO result = matriculaService.save(matriculaDTO);
        return ResponseEntity.created(new URI("/api/matriculas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /matriculas} : Updates an existing matricula.
     *
     * @param matriculaDTO the matriculaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated matriculaDTO,
     * or with status {@code 400 (Bad Request)} if the matriculaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the matriculaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/matriculas")
    public ResponseEntity<MatriculaDTO> updateMatricula(@Valid @RequestBody MatriculaDTO matriculaDTO) throws URISyntaxException {
        log.debug("REST request to update Matricula : {}", matriculaDTO);
        if (matriculaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MatriculaDTO result = matriculaService.save(matriculaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, matriculaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /matriculas} : get all the matriculas.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of matriculas in body.
     */
    @GetMapping("/matriculas")
    public ResponseEntity<List<MatriculaDTO>> getAllMatriculas(Pageable pageable) {
        log.debug("REST request to get a page of Matriculas");
        Page<MatriculaDTO> page = matriculaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /matriculas/:id} : get the "id" matricula.
     *
     * @param id the id of the matriculaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the matriculaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/matriculas/{id}")
    public ResponseEntity<MatriculaDTO> getMatricula(@PathVariable Long id) {
        log.debug("REST request to get Matricula : {}", id);
        Optional<MatriculaDTO> matriculaDTO = matriculaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(matriculaDTO);
    }

    /**
     * {@code DELETE  /matriculas/:id} : delete the "id" matricula.
     *
     * @param id the id of the matriculaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/matriculas/{id}")
    public ResponseEntity<Void> deleteMatricula(@PathVariable Long id) {
        log.debug("REST request to delete Matricula : {}", id);
        matriculaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/matriculas?query=:query} : search for the matricula corresponding
     * to the query.
     *
     * @param query the query of the matricula search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/matriculas")
    public ResponseEntity<List<MatriculaDTO>> searchMatriculas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Matriculas for query {}", query);
        Page<MatriculaDTO> page = matriculaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
