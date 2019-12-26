package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.AreaFormacao;
import com.ravunana.ensino.repository.AreaFormacaoRepository;
import com.ravunana.ensino.repository.search.AreaFormacaoSearchRepository;
import com.ravunana.ensino.service.dto.AreaFormacaoDTO;
import com.ravunana.ensino.service.mapper.AreaFormacaoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AreaFormacao}.
 */
@Service
@Transactional
public class AreaFormacaoService {

    private final Logger log = LoggerFactory.getLogger(AreaFormacaoService.class);

    private final AreaFormacaoRepository areaFormacaoRepository;

    private final AreaFormacaoMapper areaFormacaoMapper;

    private final AreaFormacaoSearchRepository areaFormacaoSearchRepository;

    public AreaFormacaoService(AreaFormacaoRepository areaFormacaoRepository, AreaFormacaoMapper areaFormacaoMapper, AreaFormacaoSearchRepository areaFormacaoSearchRepository) {
        this.areaFormacaoRepository = areaFormacaoRepository;
        this.areaFormacaoMapper = areaFormacaoMapper;
        this.areaFormacaoSearchRepository = areaFormacaoSearchRepository;
    }

    /**
     * Save a areaFormacao.
     *
     * @param areaFormacaoDTO the entity to save.
     * @return the persisted entity.
     */
    public AreaFormacaoDTO save(AreaFormacaoDTO areaFormacaoDTO) {
        log.debug("Request to save AreaFormacao : {}", areaFormacaoDTO);
        AreaFormacao areaFormacao = areaFormacaoMapper.toEntity(areaFormacaoDTO);
        areaFormacao = areaFormacaoRepository.save(areaFormacao);
        AreaFormacaoDTO result = areaFormacaoMapper.toDto(areaFormacao);
        areaFormacaoSearchRepository.save(areaFormacao);
        return result;
    }

    /**
     * Get all the areaFormacaos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AreaFormacaoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AreaFormacaos");
        return areaFormacaoRepository.findAll(pageable)
            .map(areaFormacaoMapper::toDto);
    }


    /**
     * Get one areaFormacao by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AreaFormacaoDTO> findOne(Long id) {
        log.debug("Request to get AreaFormacao : {}", id);
        return areaFormacaoRepository.findById(id)
            .map(areaFormacaoMapper::toDto);
    }

    /**
     * Delete the areaFormacao by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AreaFormacao : {}", id);
        areaFormacaoRepository.deleteById(id);
        areaFormacaoSearchRepository.deleteById(id);
    }

    /**
     * Search for the areaFormacao corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AreaFormacaoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AreaFormacaos for query {}", query);
        return areaFormacaoSearchRepository.search(queryStringQuery(query), pageable)
            .map(areaFormacaoMapper::toDto);
    }
}
