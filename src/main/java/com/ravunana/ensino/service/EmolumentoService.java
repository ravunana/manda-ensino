package com.ravunana.ensino.service;

import com.ravunana.ensino.domain.Emolumento;
import com.ravunana.ensino.repository.EmolumentoRepository;
import com.ravunana.ensino.repository.search.EmolumentoSearchRepository;
import com.ravunana.ensino.service.dto.CursoDTO;
import com.ravunana.ensino.service.dto.EmolumentoDTO;
import com.ravunana.ensino.service.mapper.EmolumentoMapper;
import com.ravunana.ensino.service.dto.ClasseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Emolumento}.
 */
@Service
@Transactional
public class EmolumentoService {

    private final Logger log = LoggerFactory.getLogger(EmolumentoService.class);

    private final EmolumentoRepository emolumentoRepository;

    private final EmolumentoMapper emolumentoMapper;

    private final EmolumentoSearchRepository emolumentoSearchRepository;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private ClasseService classeService;

    public EmolumentoService(EmolumentoRepository emolumentoRepository, EmolumentoMapper emolumentoMapper, EmolumentoSearchRepository emolumentoSearchRepository) {
        this.emolumentoRepository = emolumentoRepository;
        this.emolumentoMapper = emolumentoMapper;
        this.emolumentoSearchRepository = emolumentoSearchRepository;
    }

    /**
     * Save a emolumento.
     *
     * @param emolumentoDTO the entity to save.
     * @return the persisted entity.
     */
    public EmolumentoDTO save(EmolumentoDTO emolumentoDTO) {
        log.debug("Request to save Emolumento : {}", emolumentoDTO);
        EmolumentoDTO result = null;

        // if ( emolumentoDTO.getId() != null ) {
        //     Emolumento emolumentoSalvo = emolumentoRepository.findById( emolumentoDTO.getId() ).get();

        //     emolumentoSalvo.setNome( getNomeEmolumento(emolumentoDTO) );
        //     result = emolumentoMapper.toDto(emolumentoSalvo);
        //     emolumentoSearchRepository.save(emolumentoSalvo);

        // } else {
            Emolumento emolumento = emolumentoMapper.toEntity(emolumentoDTO);
            emolumento.setNome(getNomeEmolumento(emolumentoDTO));
            emolumento = emolumentoRepository.save(emolumento);
            result = emolumentoMapper.toDto(emolumento);
            emolumentoSearchRepository.save(emolumento);
        // }
        return result;
    }

    private String getNomeEmolumento( EmolumentoDTO emolumentoDTO ) {

        if ( emolumentoDTO.getCursoId() != null || emolumentoDTO.getClasseId() != null ) {
            CursoDTO curso = cursoService.findOne( emolumentoDTO.getCursoId() ).get();
            ClasseDTO classe = classeService.findOne( emolumentoDTO.getClasseId() ).get();
            return emolumentoDTO.getNome() + " " + classe.getDescricao() + "ª" + " " + curso.getSigla() + " " + emolumentoDTO.getQuantidade().intValue() + "x" + emolumentoDTO.getValor();
        } else {
            return emolumentoDTO.getNome() + " " + emolumentoDTO.getQuantidade().intValue() + "x" + emolumentoDTO.getValor();
        }
    }

    /**
     * Get all the emolumentos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmolumentoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Emolumentos");
        return emolumentoRepository.findAll(pageable)
            .map(emolumentoMapper::toDto);
    }


    /**
     * Get one emolumento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EmolumentoDTO> findOne(Long id) {
        log.debug("Request to get Emolumento : {}", id);
        return emolumentoRepository.findById(id)
            .map(emolumentoMapper::toDto);
    }

    /**
     * Delete the emolumento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Emolumento : {}", id);
        emolumentoRepository.deleteById(id);
        emolumentoSearchRepository.deleteById(id);
    }

    /**
     * Search for the emolumento corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EmolumentoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Emolumentos for query {}", query);
        return emolumentoSearchRepository.search(queryStringQuery(query), pageable)
            .map(emolumentoMapper::toDto);
    }
}
