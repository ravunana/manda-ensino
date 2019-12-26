package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.FichaMedica;
import com.ravunana.ensino.repository.FichaMedicaRepository;
import com.ravunana.ensino.repository.search.FichaMedicaSearchRepository;
import com.ravunana.ensino.service.dto.FichaMedicaDTO;
import com.ravunana.ensino.service.mapper.FichaMedicaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FichaMedica}.
 */
@Service
@Transactional
public class FichaMedicaService {

    private final Logger log = LoggerFactory.getLogger(FichaMedicaService.class);

    private final FichaMedicaRepository fichaMedicaRepository;

    private final FichaMedicaMapper fichaMedicaMapper;

    private final FichaMedicaSearchRepository fichaMedicaSearchRepository;

    public FichaMedicaService(FichaMedicaRepository fichaMedicaRepository, FichaMedicaMapper fichaMedicaMapper, FichaMedicaSearchRepository fichaMedicaSearchRepository) {
        this.fichaMedicaRepository = fichaMedicaRepository;
        this.fichaMedicaMapper = fichaMedicaMapper;
        this.fichaMedicaSearchRepository = fichaMedicaSearchRepository;
    }

    /**
     * Save a fichaMedica.
     *
     * @param fichaMedicaDTO the entity to save.
     * @return the persisted entity.
     */
    public FichaMedicaDTO save(FichaMedicaDTO fichaMedicaDTO) {
        log.debug("Request to save FichaMedica : {}", fichaMedicaDTO);
        FichaMedica fichaMedica = fichaMedicaMapper.toEntity(fichaMedicaDTO);
        fichaMedica = fichaMedicaRepository.save(fichaMedica);
        FichaMedicaDTO result = fichaMedicaMapper.toDto(fichaMedica);
        fichaMedicaSearchRepository.save(fichaMedica);
        return result;
    }

    /**
     * Get all the fichaMedicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FichaMedicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FichaMedicas");
        return fichaMedicaRepository.findAll(pageable)
            .map(fichaMedicaMapper::toDto);
    }


    /**
     * Get one fichaMedica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FichaMedicaDTO> findOne(Long id) {
        log.debug("Request to get FichaMedica : {}", id);
        return fichaMedicaRepository.findById(id)
            .map(fichaMedicaMapper::toDto);
    }

    /**
     * Delete the fichaMedica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FichaMedica : {}", id);
        fichaMedicaRepository.deleteById(id);
        fichaMedicaSearchRepository.deleteById(id);
    }

    /**
     * Search for the fichaMedica corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FichaMedicaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FichaMedicas for query {}", query);
        return fichaMedicaSearchRepository.search(queryStringQuery(query), pageable)
            .map(fichaMedicaMapper::toDto);
    }
}
