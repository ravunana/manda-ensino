package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Classe;
import com.ravunana.ensino.repository.ClasseRepository;
import com.ravunana.ensino.repository.search.ClasseSearchRepository;
import com.ravunana.ensino.service.dto.ClasseDTO;
import com.ravunana.ensino.service.mapper.ClasseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Classe}.
 */
@Service
@Transactional
public class ClasseService {

    private final Logger log = LoggerFactory.getLogger(ClasseService.class);

    private final ClasseRepository classeRepository;

    private final ClasseMapper classeMapper;

    private final ClasseSearchRepository classeSearchRepository;

    public ClasseService(ClasseRepository classeRepository, ClasseMapper classeMapper, ClasseSearchRepository classeSearchRepository) {
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
        this.classeSearchRepository = classeSearchRepository;
    }

    /**
     * Save a classe.
     *
     * @param classeDTO the entity to save.
     * @return the persisted entity.
     */
    public ClasseDTO save(ClasseDTO classeDTO) {
        log.debug("Request to save Classe : {}", classeDTO);
        Classe classe = classeMapper.toEntity(classeDTO);
        classe = classeRepository.save(classe);
        ClasseDTO result = classeMapper.toDto(classe);
        classeSearchRepository.save(classe);
        return result;
    }

    /**
     * Get all the classes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClasseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Classes");
        return classeRepository.findAll(pageable)
            .map(classeMapper::toDto);
    }


    /**
     * Get one classe by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClasseDTO> findOne(Long id) {
        log.debug("Request to get Classe : {}", id);
        return classeRepository.findById(id)
            .map(classeMapper::toDto);
    }

    /**
     * Delete the classe by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Classe : {}", id);
        classeRepository.deleteById(id);
        classeSearchRepository.deleteById(id);
    }

    /**
     * Search for the classe corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClasseDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Classes for query {}", query);
        return classeSearchRepository.search(queryStringQuery(query), pageable)
            .map(classeMapper::toDto);
    }
}
