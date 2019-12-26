package com.ravunana.ensino.web.rest;

import com.ravunana.ensino.service.ContactoInstituicaoEnsinoService;
import com.ravunana.ensino.web.rest.errors.BadRequestAlertException;
import com.ravunana.ensino.service.dto.ContactoInstituicaoEnsinoDTO;

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
 * REST controller for managing {@link com.ravunana.ensino.domain.ContactoInstituicaoEnsino}.
 */
@RestController
@RequestMapping("/api")
public class ContactoInstituicaoEnsinoResource {

    private final Logger log = LoggerFactory.getLogger(ContactoInstituicaoEnsinoResource.class);

    private static final String ENTITY_NAME = "contactoInstituicaoEnsino";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactoInstituicaoEnsinoService contactoInstituicaoEnsinoService;

    public ContactoInstituicaoEnsinoResource(ContactoInstituicaoEnsinoService contactoInstituicaoEnsinoService) {
        this.contactoInstituicaoEnsinoService = contactoInstituicaoEnsinoService;
    }

    /**
     * {@code POST  /contacto-instituicao-ensinos} : Create a new contactoInstituicaoEnsino.
     *
     * @param contactoInstituicaoEnsinoDTO the contactoInstituicaoEnsinoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactoInstituicaoEnsinoDTO, or with status {@code 400 (Bad Request)} if the contactoInstituicaoEnsino has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contacto-instituicao-ensinos")
    public ResponseEntity<ContactoInstituicaoEnsinoDTO> createContactoInstituicaoEnsino(@Valid @RequestBody ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO) throws URISyntaxException {
        log.debug("REST request to save ContactoInstituicaoEnsino : {}", contactoInstituicaoEnsinoDTO);
        if (contactoInstituicaoEnsinoDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactoInstituicaoEnsino cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactoInstituicaoEnsinoDTO result = contactoInstituicaoEnsinoService.save(contactoInstituicaoEnsinoDTO);
        return ResponseEntity.created(new URI("/api/contacto-instituicao-ensinos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contacto-instituicao-ensinos} : Updates an existing contactoInstituicaoEnsino.
     *
     * @param contactoInstituicaoEnsinoDTO the contactoInstituicaoEnsinoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactoInstituicaoEnsinoDTO,
     * or with status {@code 400 (Bad Request)} if the contactoInstituicaoEnsinoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactoInstituicaoEnsinoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contacto-instituicao-ensinos")
    public ResponseEntity<ContactoInstituicaoEnsinoDTO> updateContactoInstituicaoEnsino(@Valid @RequestBody ContactoInstituicaoEnsinoDTO contactoInstituicaoEnsinoDTO) throws URISyntaxException {
        log.debug("REST request to update ContactoInstituicaoEnsino : {}", contactoInstituicaoEnsinoDTO);
        if (contactoInstituicaoEnsinoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactoInstituicaoEnsinoDTO result = contactoInstituicaoEnsinoService.save(contactoInstituicaoEnsinoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactoInstituicaoEnsinoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contacto-instituicao-ensinos} : get all the contactoInstituicaoEnsinos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactoInstituicaoEnsinos in body.
     */
    @GetMapping("/contacto-instituicao-ensinos")
    public ResponseEntity<List<ContactoInstituicaoEnsinoDTO>> getAllContactoInstituicaoEnsinos(Pageable pageable) {
        log.debug("REST request to get a page of ContactoInstituicaoEnsinos");
        Page<ContactoInstituicaoEnsinoDTO> page = contactoInstituicaoEnsinoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contacto-instituicao-ensinos/:id} : get the "id" contactoInstituicaoEnsino.
     *
     * @param id the id of the contactoInstituicaoEnsinoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactoInstituicaoEnsinoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contacto-instituicao-ensinos/{id}")
    public ResponseEntity<ContactoInstituicaoEnsinoDTO> getContactoInstituicaoEnsino(@PathVariable Long id) {
        log.debug("REST request to get ContactoInstituicaoEnsino : {}", id);
        Optional<ContactoInstituicaoEnsinoDTO> contactoInstituicaoEnsinoDTO = contactoInstituicaoEnsinoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactoInstituicaoEnsinoDTO);
    }

    /**
     * {@code DELETE  /contacto-instituicao-ensinos/:id} : delete the "id" contactoInstituicaoEnsino.
     *
     * @param id the id of the contactoInstituicaoEnsinoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contacto-instituicao-ensinos/{id}")
    public ResponseEntity<Void> deleteContactoInstituicaoEnsino(@PathVariable Long id) {
        log.debug("REST request to delete ContactoInstituicaoEnsino : {}", id);
        contactoInstituicaoEnsinoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/contacto-instituicao-ensinos?query=:query} : search for the contactoInstituicaoEnsino corresponding
     * to the query.
     *
     * @param query the query of the contactoInstituicaoEnsino search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/contacto-instituicao-ensinos")
    public ResponseEntity<List<ContactoInstituicaoEnsinoDTO>> searchContactoInstituicaoEnsinos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ContactoInstituicaoEnsinos for query {}", query);
        Page<ContactoInstituicaoEnsinoDTO> page = contactoInstituicaoEnsinoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
