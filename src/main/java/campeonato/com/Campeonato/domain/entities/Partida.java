package campeonato.com.Campeonato.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Partida {

    @Id
    private Long id;

    private String estadio;

    private String uf;

    private Long clubes1Id;

    private Long clubes2Id;

    private LocalDateTime dataHorario;

    private Boolean status;

    private Integer golsClube1;

    private Integer golsClube2;

    public Partida() {
    }

    public Partida(Long id, String estadio, String uf, Long clubes1Id, Long clubes2Id, LocalDateTime dataHorario, Boolean status, Integer golsClube1, Integer golsClube2) {
        this.id = id;
        this.estadio = estadio;
        this.uf = uf;
        this.clubes1Id = clubes1Id;
        this.clubes2Id = clubes2Id;
        this.dataHorario = dataHorario;
        this.status = status;
        this.golsClube1 = golsClube1;
        this.golsClube2 = golsClube2;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstadio() {
        return estadio;
    }

    public void setEstadio(String estadio) {
        this.estadio = estadio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Long getClubes1Id() {
        return clubes1Id;
    }

    public void setClubes1Id(Long clubes1Id) {
        this.clubes1Id = clubes1Id;
    }

    public Long getClubes2Id() {
        return clubes2Id;
    }

    public void setClubes2Id(Long clubes2Id) {
        this.clubes2Id = clubes2Id;
    }

    public LocalDateTime getDataHorario() {
        return dataHorario;
    }

    public void setDataHorario(LocalDateTime dataHorario) {
        this.dataHorario = dataHorario;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getGolsClube1() {
        return golsClube1;
    }

    public void setGolsClube1(Integer golsClube1) {
        this.golsClube1 = golsClube1;
    }

    public Integer getGolsClube2() {
        return golsClube2;
    }

    public void setGolsClube2(Integer golsClube2) {
        this.golsClube2 = golsClube2;
    }

}