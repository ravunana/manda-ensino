package com.ravunana.ensino.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.ravunana.ensino.domain.DocumentoMatricula} entity.
 */
public class DocumentoMatriculaDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean fotografia;

    @NotNull
    private Boolean certificado;

    @NotNull
    private Boolean bilhete;

    private Boolean resenciamentoMilitar;

    private Boolean cartaoVacina;

    private Boolean atestadoMedico;

    private Boolean fichaTrnasferencia;

    private Boolean historicoEscolar;

    private Boolean cedula;

    private String descricao;

    private Integer anoLectivo;

    private ZonedDateTime data;


    private Long matriculaId;

    private String matriculaNumero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isFotografia() {
        return fotografia;
    }

    public void setFotografia(Boolean fotografia) {
        this.fotografia = fotografia;
    }

    public Boolean isCertificado() {
        return certificado;
    }

    public void setCertificado(Boolean certificado) {
        this.certificado = certificado;
    }

    public Boolean isBilhete() {
        return bilhete;
    }

    public void setBilhete(Boolean bilhete) {
        this.bilhete = bilhete;
    }

    public Boolean isResenciamentoMilitar() {
        return resenciamentoMilitar;
    }

    public void setResenciamentoMilitar(Boolean resenciamentoMilitar) {
        this.resenciamentoMilitar = resenciamentoMilitar;
    }

    public Boolean isCartaoVacina() {
        return cartaoVacina;
    }

    public void setCartaoVacina(Boolean cartaoVacina) {
        this.cartaoVacina = cartaoVacina;
    }

    public Boolean isAtestadoMedico() {
        return atestadoMedico;
    }

    public void setAtestadoMedico(Boolean atestadoMedico) {
        this.atestadoMedico = atestadoMedico;
    }

    public Boolean isFichaTrnasferencia() {
        return fichaTrnasferencia;
    }

    public void setFichaTrnasferencia(Boolean fichaTrnasferencia) {
        this.fichaTrnasferencia = fichaTrnasferencia;
    }

    public Boolean isHistoricoEscolar() {
        return historicoEscolar;
    }

    public void setHistoricoEscolar(Boolean historicoEscolar) {
        this.historicoEscolar = historicoEscolar;
    }

    public Boolean isCedula() {
        return cedula;
    }

    public void setCedula(Boolean cedula) {
        this.cedula = cedula;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAnoLectivo() {
        return anoLectivo;
    }

    public void setAnoLectivo(Integer anoLectivo) {
        this.anoLectivo = anoLectivo;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Long getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Long matriculaId) {
        this.matriculaId = matriculaId;
    }

    public String getMatriculaNumero() {
        return matriculaNumero;
    }

    public void setMatriculaNumero(String matriculaNumero) {
        this.matriculaNumero = matriculaNumero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DocumentoMatriculaDTO documentoMatriculaDTO = (DocumentoMatriculaDTO) o;
        if (documentoMatriculaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), documentoMatriculaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocumentoMatriculaDTO{" +
            "id=" + getId() +
            ", fotografia='" + isFotografia() + "'" +
            ", certificado='" + isCertificado() + "'" +
            ", bilhete='" + isBilhete() + "'" +
            ", resenciamentoMilitar='" + isResenciamentoMilitar() + "'" +
            ", cartaoVacina='" + isCartaoVacina() + "'" +
            ", atestadoMedico='" + isAtestadoMedico() + "'" +
            ", fichaTrnasferencia='" + isFichaTrnasferencia() + "'" +
            ", historicoEscolar='" + isHistoricoEscolar() + "'" +
            ", cedula='" + isCedula() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", anoLectivo=" + getAnoLectivo() +
            ", data='" + getData() + "'" +
            ", matriculaId=" + getMatriculaId() +
            ", matriculaNumero='" + getMatriculaNumero() + "'" +
            "}";
    }
}
