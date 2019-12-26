package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.DossificacaoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.DossificacaoDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.Dossificacao}.
 */
@RestController
@RequestMapping("/api")
public class DossificacaoResource {

    private final Logger log = LoggerFactory.getLogger(DossificacaoResource.class);

    private static final String ENTITY_NAME = "dossificacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DossificacaoService dossificacaoService;

    public DossificacaoResource(DossificacaoService dossificacaoService) {
        this.dossificacaoService = dossificacaoService;
    }

    /**
     * {@code POST  /dossificacaos} : Create a new dossificacao.
     *
     * @param dossificacaoDTO the dossificacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dossificacaoDTO, or with status {@code 400 (Bad Request)} if the dossificacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dossificacaos")
    public ResponseEntity<DossificacaoDTO> createDossificacao(@Valid @RequestBody DossificacaoDTO dossificacaoDTO) throws URISyntaxException {
        log.debug("REST request to save Dossificacao : {}", dossificacaoDTO);
        if (dossificacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new dossificacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DossificacaoDTO result = dossificacaoService.save(dossificacaoDTO);
        return ResponseEntity.created(new URI("/api/dossificacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dossificacaos} : Updates an existing dossificacao.
     *
     * @param dossificacaoDTO the dossificacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dossificacaoDTO,
     * or with status {@code 400 (Bad Request)} if the dossificacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dossificacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dossificacaos")
    public ResponseEntity<DossificacaoDTO> updateDossificacao(@Valid @RequestBody DossificacaoDTO dossificacaoDTO) throws URISyntaxException {
        log.debug("REST request to update Dossificacao : {}", dossificacaoDTO);
        if (dossificacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DossificacaoDTO result = dossificacaoService.save(dossificacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dossificacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dossificacaos} : get all the dossificacaos.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dossificacaos in body.
     */
    @GetMapping("/dossificacaos")
    public ResponseEntity<List<DossificacaoDTO>> getAllDossificacaos(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Dossificacaos");
        Page<DossificacaoDTO> page;
        if (eagerload) {
            page = dossificacaoService.findAllWithEagerRelationships(pageable);
        } else {
            page = dossificacaoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dossificacaos/:id} : get the "id" dossificacao.
     *
     * @param id the id of the dossificacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dossificacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dossificacaos/{id}")
    public ResponseEntity<DossificacaoDTO> getDossificacao(@PathVariable Long id) {
        log.debug("REST request to get Dossificacao : {}", id);
        Optional<DossificacaoDTO> dossificacaoDTO = dossificacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dossificacaoDTO);
    }

    /**
     * {@code DELETE  /dossificacaos/:id} : delete the "id" dossificacao.
     *
     * @param id the id of the dossificacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dossificacaos/{id}")
    public ResponseEntity<Void> deleteDossificacao(@PathVariable Long id) {
        log.debug("REST request to delete Dossificacao : {}", id);
        dossificacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/dossificacaos?query=:query} : search for the dossificacao corresponding
     * to the query.
     *
     * @param query the query of the dossificacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/dossificacaos")
    public ResponseEntity<List<DossificacaoDTO>> searchDossificacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Dossificacaos for query {}", query);
        Page<DossificacaoDTO> page = dossificacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
