package com.ravunana.ensino.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.Aula} entity.
 */
public class AulaDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime data;

    @NotNull
    private String sumario;

    @NotNull
    private Integer licao;

    @NotNull
    private Boolean dada;


    private Long utilizadorId;

    private String utilizadorLogin;

    private Set<PlanoAulaDTO> planoAulas = new HashSet<>();

    private Long turmaId;

    private String turmaDescricao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public Integer getLicao() {
        return licao;
    }

    public void setLicao(Integer licao) {
        this.licao = licao;
    }

    public Boolean isDada() {
        return dada;
    }

    public void setDada(Boolean dada) {
        this.dada = dada;
    }

    public Long getUtilizadorId() {
        return utilizadorId;
    }

    public void setUtilizadorId(Long userId) {
        this.utilizadorId = userId;
    }

    public String getUtilizadorLogin() {
        return utilizadorLogin;
    }

    public void setUtilizadorLogin(String userLogin) {
        this.utilizadorLogin = userLogin;
    }

    public Set<PlanoAulaDTO> getPlanoAulas() {
        return planoAulas;
    }

    public void setPlanoAulas(Set<PlanoAulaDTO> planoAulas) {
        this.planoAulas = planoAulas;
    }

    public Long getTurmaId() {
        return turmaId;
    }

    public void setTurmaId(Long turmaId) {
        this.turmaId = turmaId;
    }

    public String getTurmaDescricao() {
        return turmaDescricao;
    }

    public void setTurmaDescricao(String turmaDescricao) {
        this.turmaDescricao = turmaDescricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AulaDTO aulaDTO = (AulaDTO) o;
        if (aulaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aulaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AulaDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", sumario='" + getSumario() + "'" +
            ", licao=" + getLicao() +
            ", dada='" + isDada() + "'" +
            ", utilizadorId=" + getUtilizadorId() +
            ", utilizadorLogin='" + getUtilizadorLogin() + "'" +
            ", turmaId=" + getTurmaId() +
            ", turmaDescricao='" + getTurmaDescricao() + "'" +
            "}";
    }
}
