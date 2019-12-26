package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.LocalizacaoInstituicaoEnsinoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.LocalizacaoInstituicaoEnsinoDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.LocalizacaoInstituicaoEnsino}.
 */
@RestController
@RequestMapping("/api")
public class LocalizacaoInstituicaoEnsinoResource {

    private final Logger log = LoggerFactory.getLogger(LocalizacaoInstituicaoEnsinoResource.class);

    private static final String ENTITY_NAME = "localizacaoInstituicaoEnsino";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LocalizacaoInstituicaoEnsinoService localizacaoInstituicaoEnsinoService;

    public LocalizacaoInstituicaoEnsinoResource(LocalizacaoInstituicaoEnsinoService localizacaoInstituicaoEnsinoService) {
        this.localizacaoInstituicaoEnsinoService = localizacaoInstituicaoEnsinoService;
    }

    /**
     * {@code POST  /localizacao-instituicao-ensinos} : Create a new localizacaoInstituicaoEnsino.
     *
     * @param localizacaoInstituicaoEnsinoDTO the localizacaoInstituicaoEnsinoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new localizacaoInstituicaoEnsinoDTO, or with status {@code 400 (Bad Request)} if the localizacaoInstituicaoEnsino has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/localizacao-instituicao-ensinos")
    public ResponseEntity<LocalizacaoInstituicaoEnsinoDTO> createLocalizacaoInstituicaoEnsino(@Valid @RequestBody LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO) throws URISyntaxException {
        log.debug("REST request to save LocalizacaoInstituicaoEnsino : {}", localizacaoInstituicaoEnsinoDTO);
        if (localizacaoInstituicaoEnsinoDTO.getId() != null) {
            throw new BadRequestAlertException("A new localizacaoInstituicaoEnsino cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalizacaoInstituicaoEnsinoDTO result = localizacaoInstituicaoEnsinoService.save(localizacaoInstituicaoEnsinoDTO);
        return ResponseEntity.created(new URI("/api/localizacao-instituicao-ensinos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /localizacao-instituicao-ensinos} : Updates an existing localizacaoInstituicaoEnsino.
     *
     * @param localizacaoInstituicaoEnsinoDTO the localizacaoInstituicaoEnsinoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated localizacaoInstituicaoEnsinoDTO,
     * or with status {@code 400 (Bad Request)} if the localizacaoInstituicaoEnsinoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the localizacaoInstituicaoEnsinoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/localizacao-instituicao-ensinos")
    public ResponseEntity<LocalizacaoInstituicaoEnsinoDTO> updateLocalizacaoInstituicaoEnsino(@Valid @RequestBody LocalizacaoInstituicaoEnsinoDTO localizacaoInstituicaoEnsinoDTO) throws URISyntaxException {
        log.debug("REST request to update LocalizacaoInstituicaoEnsino : {}", localizacaoInstituicaoEnsinoDTO);
        if (localizacaoInstituicaoEnsinoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LocalizacaoInstituicaoEnsinoDTO result = localizacaoInstituicaoEnsinoService.save(localizacaoInstituicaoEnsinoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, localizacaoInstituicaoEnsinoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /localizacao-instituicao-ensinos} : get all the localizacaoInstituicaoEnsinos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of localizacaoInstituicaoEnsinos in body.
     */
    @GetMapping("/localizacao-instituicao-ensinos")
    public ResponseEntity<List<LocalizacaoInstituicaoEnsinoDTO>> getAllLocalizacaoInstituicaoEnsinos(Pageable pageable) {
        log.debug("REST request to get a page of LocalizacaoInstituicaoEnsinos");
        Page<LocalizacaoInstituicaoEnsinoDTO> page = localizacaoInstituicaoEnsinoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /localizacao-instituicao-ensinos/:id} : get the "id" localizacaoInstituicaoEnsino.
     *
     * @param id the id of the localizacaoInstituicaoEnsinoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the localizacaoInstituicaoEnsinoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/localizacao-instituicao-ensinos/{id}")
    public ResponseEntity<LocalizacaoInstituicaoEnsinoDTO> getLocalizacaoInstituicaoEnsino(@PathVariable Long id) {
        log.debug("REST request to get LocalizacaoInstituicaoEnsino : {}", id);
        Optional<LocalizacaoInstituicaoEnsinoDTO> localizacaoInstituicaoEnsinoDTO = localizacaoInstituicaoEnsinoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(localizacaoInstituicaoEnsinoDTO);
    }

    /**
     * {@code DELETE  /localizacao-instituicao-ensinos/:id} : delete the "id" localizacaoInstituicaoEnsino.
     *
     * @param id the id of the localizacaoInstituicaoEnsinoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/localizacao-instituicao-ensinos/{id}")
    public ResponseEntity<Void> deleteLocalizacaoInstituicaoEnsino(@PathVariable Long id) {
        log.debug("REST request to delete LocalizacaoInstituicaoEnsino : {}", id);
        localizacaoInstituicaoEnsinoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/localizacao-instituicao-ensinos?query=:query} : search for the localizacaoInstituicaoEnsino corresponding
     * to the query.
     *
     * @param query the query of the localizacaoInstituicaoEnsino search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/localizacao-instituicao-ensinos")
    public ResponseEntity<List<LocalizacaoInstituicaoEnsinoDTO>> searchLocalizacaoInstituicaoEnsinos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LocalizacaoInstituicaoEnsinos for query {}", query);
        Page<LocalizacaoInstituicaoEnsinoDTO> page = localizacaoInstituicaoEnsinoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
