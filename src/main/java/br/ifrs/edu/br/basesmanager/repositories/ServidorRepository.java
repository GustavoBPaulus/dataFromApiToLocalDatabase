package br.ifrs.edu.br.basesmanager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifrs.edu.br.basesmanager.models.Servidor;

@Repository
public interface ServidorRepository extends JpaRepository<Servidor, String>{

}
