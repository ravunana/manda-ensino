package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.ContratoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.ContratoDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.Contrato}.
 */
@RestController
@RequestMapping("/api")
public class ContratoResource {

    private final Logger log = LoggerFactory.getLogger(ContratoResource.class);

    private static final String ENTITY_NAME = "contrato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratoService contratoService;

    public ContratoResource(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    /**
     * {@code POST  /contratoes} : Create a new contrato.
     *
     * @param contratoDTO the contratoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contratoDTO, or with status {@code 400 (Bad Request)} if the contrato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contratoes")
    public ResponseEntity<ContratoDTO> createContrato(@Valid @RequestBody ContratoDTO contratoDTO) throws URISyntaxException {
        log.debug("REST request to save Contrato : {}", contratoDTO);
        if (contratoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contrato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContratoDTO result = contratoService.save(contratoDTO);
        return ResponseEntity.created(new URI("/api/contratoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contratoes} : Updates an existing contrato.
     *
     * @param contratoDTO the contratoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratoDTO,
     * or with status {@code 400 (Bad Request)} if the contratoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contratoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contratoes")
    public ResponseEntity<ContratoDTO> updateContrato(@Valid @RequestBody ContratoDTO contratoDTO) throws URISyntaxException {
        log.debug("REST request to update Contrato : {}", contratoDTO);
        if (contratoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContratoDTO result = contratoService.save(contratoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contratoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contratoes} : get all the contratoes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contratoes in body.
     */
    @GetMapping("/contratoes")
    public ResponseEntity<List<ContratoDTO>> getAllContratoes(Pageable pageable) {
        log.debug("REST request to get a page of Contratoes");
        Page<ContratoDTO> page = contratoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contratoes/:id} : get the "id" contrato.
     *
     * @param id the id of the contratoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contratoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contratoes/{id}")
    public ResponseEntity<ContratoDTO> getContrato(@PathVariable Long id) {
        log.debug("REST request to get Contrato : {}", id);
        Optional<ContratoDTO> contratoDTO = contratoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contratoDTO);
    }

    /**
     * {@code DELETE  /contratoes/:id} : delete the "id" contrato.
     *
     * @param id the id of the contratoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contratoes/{id}")
    public ResponseEntity<Void> deleteContrato(@PathVariable Long id) {
        log.debug("REST request to delete Contrato : {}", id);
        contratoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/contratoes?query=:query} : search for the contrato corresponding
     * to the query.
     *
     * @param query the query of the contrato search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/contratoes")
    public ResponseEntity<List<ContratoDTO>> searchContratoes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Contratoes for query {}", query);
        Page<ContratoDTO> page = contratoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
