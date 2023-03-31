package br.ifrs.edu.br.basesmanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ifrs.edu.br.basesmanager.models.Servidor;
import br.ifrs.edu.br.basesmanager.models.ServidorCargo;

@Repository
public interface ServidorCargoRepository extends JpaRepository<ServidorCargo, String> {

 List<ServidorCargo> findByStatus(String status);
 
 List<ServidorCargo> findByServidor(Servidor servidor);
 
}
