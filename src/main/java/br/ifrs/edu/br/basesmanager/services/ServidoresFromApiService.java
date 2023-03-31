package br.ifrs.edu.br.basesmanager.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.ifrs.edu.br.basesmanager.models.Servidor;
import br.ifrs.edu.br.basesmanager.models.ServidorCargo;
import br.ifrs.edu.br.basesmanager.repositories.ServidorCargoRepository;
import br.ifrs.edu.br.basesmanager.repositories.ServidorRepository;
import br.ifrs.edu.br.basesmanager.useful.CriptografiaUtil;
import clientApiReitoria.model.ServidorCargoModelo;
import clientApiReitoria.model.ServidorModelo;
import clientApiReitoria.service.RetornaListaDeServidoresApiReitoria;

@Service
public class ServidoresFromApiService {
	@Autowired
	ServidorRepository servidorRepository;
	
	@Autowired
	ServidorCargoRepository servidorCargoRepository;
	
	public boolean sincronizaServidores() {
		RetornaListaDeServidoresApiReitoria retornaServidores = new RetornaListaDeServidoresApiReitoria();
		List<ServidorModelo> servidoresModelo = retornaServidores.retornaListaDeServidores();
		System.out.println("quantidade de servidores retornados: "+servidoresModelo.size());
		List<Servidor> servidores = convertServidorModeloToServidor(servidoresModelo);
		//seta servidores
		servidores.forEach(s ->{
			servidorRepository.save(s);
			System.out.println(s.getLogin());
		});
		
		//seta lista de cargos por servidores já cadastrados e adiciona senha se estiver nula
		servidoresModelo.forEach(sm ->{
			Servidor servidor = servidorRepository.findById(sm.getLogin()).get();
			if(servidor.getSenha() == null || servidor.getSenha().equals(""))
				servidor.setSenha(CriptografiaUtil.encriptar(servidor.getLogin() + "@ibiruba.ifrs"));
			
			servidor.setListaCargos(converteServidorCargoModeloToServidorCargo(sm.getListaCargos(), servidor));
			
			
			servidorRepository.save(servidor);
		});
		
		return !servidores.isEmpty();
	}

	private List<Servidor> convertServidorModeloToServidor(List<ServidorModelo> servidorModelo) {
		List<Servidor> servidores = new ArrayList<Servidor>();

		servidorModelo.forEach(am -> {
			Servidor servidor = new Servidor();
			servidor.setData_nascimento(am.getData_nascimento());
			servidor.setEmail(am.getEmail());

			servidor.setLogin(am.getLogin());
			servidor.setNome_completo(am.getNome_completo());
			servidor.setSexo(am.getSexo());
			//seta no CN o que vem no email, visto que o email é o primeiroNome.UltimoNome
			servidor.setCn(am.getEmail().split("@")[0]);
			
			servidores.add(servidor);

		});

		return servidores;
	}
	
	

	private List<ServidorCargo> converteServidorCargoModeloToServidorCargo(
			List<ServidorCargoModelo> servidoresCargoModelo, Servidor s) {
		List<ServidorCargo> servidoresCargo = new ArrayList<ServidorCargo>();
		servidoresCargoModelo.forEach(scm -> {
			ServidorCargo servidorCargo = new ServidorCargo();
			servidorCargo.setCargo(scm.getCargo());
			servidorCargo.setStatus(scm.getStatus());
			servidorCargo.setServidor(s);
			
			servidorCargo.setMatricula(scm.getMatricula());
			
			servidoresCargo.add(servidorCargo);
			
			servidorCargoRepository.save(servidorCargo);
		});

		return servidoresCargo;
	}

}
