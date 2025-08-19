package campeonato.com.Campeonato.DoMain.Model;

import java.time.LocalDateTime;

public class Partida {

    private Long id;

    private String estadio;

    private String uf;

    private Long clube1Id;

    private Long clube2Id;

    private LocalDateTime dataHorario;

    private Boolean status;

    private Integer golsClube1;

    private Integer golsClube2;

    public Partida() {
    }

    public Partida(Long id, String estadio, String uf, Long clube1Id, Long clube2Id, LocalDateTime dataHorario, Boolean status, Integer golsClube1, Integer golsClube2) {
        this.id = id;
        this.estadio = estadio;
        this.uf = uf;
        this.clube1Id = clube1Id;
        this.clube2Id = clube2Id;
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

    public Long getClube1Id() {
        return clube1Id;
    }

    public void setClube1Id(Long clube1Id) {
        this.clube1Id = clube1Id;
    }

    public Long getClube2Id() {
        return clube2Id;
    }

    public void setClube2Id(Long clube2Id) {
        this.clube2Id = clube2Id;
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