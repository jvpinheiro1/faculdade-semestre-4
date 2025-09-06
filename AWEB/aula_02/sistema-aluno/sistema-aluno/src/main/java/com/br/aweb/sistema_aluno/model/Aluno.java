package com.br.aweb.sistema_aluno.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatorio!")
    private String name;
    private String materia;

    @NotNull(message = "A idade é obrigatoria!")
    private Integer idade;

    public Aluno() {}

    public Aluno(Long id, @NotBlank(message = "O nome é obrigatorio!") String name, String materia,
            @NotNull(message = "A idade é obrigatoria!") Integer idade) {
        this.id = id;
        this.name = name;
        this.materia = materia;
        this.idade = idade;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMateria() {
        return materia;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }
    
    

}
