package com.ravunana.ensino.service.mapper;

import com.ravunana.ensino.domain.*;
import com.ravunana.ensino.service.dto.DetalhePagamentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DetalhePagamento} and its DTO {@link DetalhePagamentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, EmolumentoMapper.class, DepositoMapper.class})
public interface DetalhePagamentoMapper extends EntityMapper<DetalhePagamentoDTO, DetalhePagamento> {

    @Mapping(source = "utilizador.id", target = "utilizadorId")
    @Mapping(source = "utilizador.login", target = "utilizadorLogin")
    @Mapping(source = "emolumento.id", target = "emolumentoId")
    @Mapping(source = "emolumento.nome", target = "emolumentoNome")
    @Mapping(source = "deposito.id", target = "depositoId")
    @Mapping(source = "deposito.numeroTalao", target = "depositoNumeroTalao")
    DetalhePagamentoDTO toDto(DetalhePagamento detalhePagamento);

    @Mapping(source = "utilizadorId", target = "utilizador")
    @Mapping(source = "emolumentoId", target = "emolumento")
    @Mapping(source = "depositoId", target = "deposito")
    DetalhePagamento toEntity(DetalhePagamentoDTO detalhePagamentoDTO);

    default DetalhePagamento fromId(Long id) {
        if (id == null) {
            return null;
        }
        DetalhePagamento detalhePagamento = new DetalhePagamento();
        detalhePagamento.setId(id);
        return detalhePagamento;
    }
}
