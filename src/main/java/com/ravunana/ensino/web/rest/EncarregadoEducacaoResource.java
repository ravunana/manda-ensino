package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.EncarregadoEducacaoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.EncarregadoEducacaoDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.EncarregadoEducacao}.
 */
@RestController
@RequestMapping("/api")
public class EncarregadoEducacaoResource {

    private final Logger log = LoggerFactory.getLogger(EncarregadoEducacaoResource.class);

    private static final String ENTITY_NAME = "encarregadoEducacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EncarregadoEducacaoService encarregadoEducacaoService;

    public EncarregadoEducacaoResource(EncarregadoEducacaoService encarregadoEducacaoService) {
        this.encarregadoEducacaoService = encarregadoEducacaoService;
    }

    /**
     * {@code POST  /encarregado-educacaos} : Create a new encarregadoEducacao.
     *
     * @param encarregadoEducacaoDTO the encarregadoEducacaoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encarregadoEducacaoDTO, or with status {@code 400 (Bad Request)} if the encarregadoEducacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/encarregado-educacaos")
    public ResponseEntity<EncarregadoEducacaoDTO> createEncarregadoEducacao(@Valid @RequestBody EncarregadoEducacaoDTO encarregadoEducacaoDTO) throws URISyntaxException {
        log.debug("REST request to save EncarregadoEducacao : {}", encarregadoEducacaoDTO);
        if (encarregadoEducacaoDTO.getId() != null) {
            throw new BadRequestAlertException("A new encarregadoEducacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EncarregadoEducacaoDTO result = encarregadoEducacaoService.save(encarregadoEducacaoDTO);
        return ResponseEntity.created(new URI("/api/encarregado-educacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /encarregado-educacaos} : Updates an existing encarregadoEducacao.
     *
     * @param encarregadoEducacaoDTO the encarregadoEducacaoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encarregadoEducacaoDTO,
     * or with status {@code 400 (Bad Request)} if the encarregadoEducacaoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encarregadoEducacaoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/encarregado-educacaos")
    public ResponseEntity<EncarregadoEducacaoDTO> updateEncarregadoEducacao(@Valid @RequestBody EncarregadoEducacaoDTO encarregadoEducacaoDTO) throws URISyntaxException {
        log.debug("REST request to update EncarregadoEducacao : {}", encarregadoEducacaoDTO);
        if (encarregadoEducacaoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EncarregadoEducacaoDTO result = encarregadoEducacaoService.save(encarregadoEducacaoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encarregadoEducacaoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /encarregado-educacaos} : get all the encarregadoEducacaos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of encarregadoEducacaos in body.
     */
    @GetMapping("/encarregado-educacaos")
    public ResponseEntity<List<EncarregadoEducacaoDTO>> getAllEncarregadoEducacaos(Pageable pageable) {
        log.debug("REST request to get a page of EncarregadoEducacaos");
        Page<EncarregadoEducacaoDTO> page = encarregadoEducacaoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /encarregado-educacaos/:id} : get the "id" encarregadoEducacao.
     *
     * @param id the id of the encarregadoEducacaoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encarregadoEducacaoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/encarregado-educacaos/{id}")
    public ResponseEntity<EncarregadoEducacaoDTO> getEncarregadoEducacao(@PathVariable Long id) {
        log.debug("REST request to get EncarregadoEducacao : {}", id);
        Optional<EncarregadoEducacaoDTO> encarregadoEducacaoDTO = encarregadoEducacaoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(encarregadoEducacaoDTO);
    }

    /**
     * {@code DELETE  /encarregado-educacaos/:id} : delete the "id" encarregadoEducacao.
     *
     * @param id the id of the encarregadoEducacaoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/encarregado-educacaos/{id}")
    public ResponseEntity<Void> deleteEncarregadoEducacao(@PathVariable Long id) {
        log.debug("REST request to delete EncarregadoEducacao : {}", id);
        encarregadoEducacaoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/encarregado-educacaos?query=:query} : search for the encarregadoEducacao corresponding
     * to the query.
     *
     * @param query the query of the encarregadoEducacao search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/encarregado-educacaos")
    public ResponseEntity<List<EncarregadoEducacaoDTO>> searchEncarregadoEducacaos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of EncarregadoEducacaos for query {}", query);
        Page<EncarregadoEducacaoDTO> page = encarregadoEducacaoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
