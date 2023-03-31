package br.ifrs.edu.br.basesmanager.useful;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifrs.edu.br.basesmanager.models.Servidor;
import br.ifrs.edu.br.basesmanager.models.ServidorCargo;
import br.ifrs.edu.br.basesmanager.repositories.ServidorCargoRepository;
import br.ifrs.edu.br.basesmanager.repositories.ServidorRepository;

@RestController
@RequestMapping("/servidores")
public class ServidoresUteis {

	@Autowired
	ServidorCargoRepository servidorCargoRepository;

	@Autowired
	ServidorRepository servidorRepository;

	@GetMapping("/excluir")
	public ResponseEntity<?> excluir() {
		List<Servidor> listaDeServidores = servidorRepository.findAll();
		List<Servidor> listaDeServidoresInativos = new ArrayList<Servidor>();

		for (Servidor servidor : listaDeServidores) {
			List<ServidorCargo> cargosDoServidor = servidorCargoRepository.findByServidor(servidor);
			// remove todos os cargos inativos
			for (ServidorCargo cargo : cargosDoServidor) {
				if (cargo.getStatus().equalsIgnoreCase("INATIVO")) {
					servidorCargoRepository.delete(cargo);
				}
			}
			// remove todos os servidores que não tem cargo vinculado
			cargosDoServidor = servidorCargoRepository.findByServidor(servidor);
			if (cargosDoServidor.size() == 0) {
				servidorRepository.delete(servidor);
			}

		}
		return ResponseEntity.ok(true);
	}

	@GetMapping("/trocacnparaminusculo")
	public ResponseEntity<?> trocacnparaminusculo() {
		List<Servidor> listaDeServidores = servidorRepository.findAll();

		for (Servidor servidor : listaDeServidores) {

			servidor.setCn(servidor.getCn().toLowerCase());
			servidorRepository.save(servidor);

		}
		return ResponseEntity.ok(true);
	}

	// persistir tipoServidor
	@GetMapping("/persistetiposervidor")
	public ResponseEntity<?> persisteTipoServidor() {
		List<ServidorCargo> listaDeServidoresCargos = servidorCargoRepository.findAll();

		for (ServidorCargo servidorCargo : listaDeServidoresCargos) {
			Servidor servidor = servidorCargo.getServidor();
			if (servidorCargo.getCargo().contains("PROFESSOR") || servidorCargo.getCargo().contains("PROF")) {
				if(servidor.getTipoServidor() == null 
						|| servidor.getTipoServidor().equalsIgnoreCase(null))
					servidor.setTipoServidor("docente");
			}else {
				servidor.setTipoServidor("tae");
			}
			servidorRepository.save(servidor);
		}
		return ResponseEntity.ok(true);
	}

}
