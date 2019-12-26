package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.DocumentacaoPessoa;
import com.ravunana.ensino.repository.DocumentacaoPessoaRepository;
import com.ravunana.ensino.repository.search.DocumentacaoPessoaSearchRepository;
import com.ravunana.ensino.service.dto.DocumentacaoPessoaDTO;
import com.ravunana.ensino.service.mapper.DocumentacaoPessoaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DocumentacaoPessoa}.
 */
@Service
@Transactional
public class DocumentacaoPessoaService {

    private final Logger log = LoggerFactory.getLogger(DocumentacaoPessoaService.class);

    private final DocumentacaoPessoaRepository documentacaoPessoaRepository;

    private final DocumentacaoPessoaMapper documentacaoPessoaMapper;

    private final DocumentacaoPessoaSearchRepository documentacaoPessoaSearchRepository;

    public DocumentacaoPessoaService(DocumentacaoPessoaRepository documentacaoPessoaRepository, DocumentacaoPessoaMapper documentacaoPessoaMapper, DocumentacaoPessoaSearchRepository documentacaoPessoaSearchRepository) {
        this.documentacaoPessoaRepository = documentacaoPessoaRepository;
        this.documentacaoPessoaMapper = documentacaoPessoaMapper;
        this.documentacaoPessoaSearchRepository = documentacaoPessoaSearchRepository;
    }

    /**
     * Save a documentacaoPessoa.
     *
     * @param documentacaoPessoaDTO the entity to save.
     * @return the persisted entity.
     */
    public DocumentacaoPessoaDTO save(DocumentacaoPessoaDTO documentacaoPessoaDTO) {
        log.debug("Request to save DocumentacaoPessoa : {}", documentacaoPessoaDTO);
        DocumentacaoPessoa documentacaoPessoa = documentacaoPessoaMapper.toEntity(documentacaoPessoaDTO);
        documentacaoPessoa = documentacaoPessoaRepository.save(documentacaoPessoa);
        DocumentacaoPessoaDTO result = documentacaoPessoaMapper.toDto(documentacaoPessoa);
        documentacaoPessoaSearchRepository.save(documentacaoPessoa);
        return result;
    }

    /**
     * Get all the documentacaoPessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentacaoPessoaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentacaoPessoas");
        return documentacaoPessoaRepository.findAll(pageable)
            .map(documentacaoPessoaMapper::toDto);
    }


    /**
     * Get one documentacaoPessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DocumentacaoPessoaDTO> findOne(Long id) {
        log.debug("Request to get DocumentacaoPessoa : {}", id);
        return documentacaoPessoaRepository.findById(id)
            .map(documentacaoPessoaMapper::toDto);
    }

    /**
     * Delete the documentacaoPessoa by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DocumentacaoPessoa : {}", id);
        documentacaoPessoaRepository.deleteById(id);
        documentacaoPessoaSearchRepository.deleteById(id);
    }

    /**
     * Search for the documentacaoPessoa corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentacaoPessoaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DocumentacaoPessoas for query {}", query);
        return documentacaoPessoaSearchRepository.search(queryStringQuery(query), pageable)
            .map(documentacaoPessoaMapper::toDto);
    }
}
