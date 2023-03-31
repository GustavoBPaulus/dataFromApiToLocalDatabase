package br.ifrs.edu.br.basesmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifrs.edu.br.basesmanager.models.Aluno;
import br.ifrs.edu.br.basesmanager.models.AlunoCurso;
@Repository
public interface AlunoCursoRepository extends JpaRepository<AlunoCurso, String>{
    List<AlunoCurso> findByAluno(Aluno aluno);
}
