package com.br.aweb.sistema_aluno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.aweb.sistema_aluno.model.Aluno;

public interface AlunoRepository extends JpaRepository <Aluno, Long>{
    List<Aluno> findByNameContainingIgnoreCase(String name);
    
} 