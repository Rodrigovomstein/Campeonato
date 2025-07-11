package campeonato.com.Campeonato.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

public class Estadio {

    @Data
    @Entity
    @Table(name= "estadio")
    public class Estadio {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String nome;
        @Column(name="estado")
        private String uf;

        @Column(name="data_criacao")
        private LocalDate dataCriacao;
        @Column(name="ativo")
        private Boolean status;
    }
}
