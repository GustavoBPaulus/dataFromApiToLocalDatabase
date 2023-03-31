package br.ifrs.edu.br.basesmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifrs.edu.br.basesmanager.services.AlunosFromApiService;
import br.ifrs.edu.br.basesmanager.services.ServidoresFromApiService;

@RestController
@RequestMapping("/sincronizareitoria")
public class sincronizaReitoriaController {
	@Autowired
	ServidoresFromApiService servidoresApi;
	
	@Autowired
	AlunosFromApiService alunosApi;
	
	@GetMapping
	public ResponseEntity<?> sincronizaApi() {
		//boolean servidoresSincronizados = servidoresApi.sincronizaServidores();
		boolean alunosSincronizados = alunosApi.sincronizaAlunos();
		//System.out.println("sincronizado: "+ (servidoresSincronizados && alunosSincronizados) );
		return ResponseEntity.ok(alunosSincronizados);
	}
	
}
