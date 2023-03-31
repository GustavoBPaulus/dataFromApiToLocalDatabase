package br.ifrs.edu.br.basesmanager.useful;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.ifrs.edu.br.basesmanager.dtos.SenhasDto;
import br.ifrs.edu.br.basesmanager.repositories.AlunoRepository;
import br.ifrs.edu.br.basesmanager.repositories.ServidorRepository;
@Controller
@RequestMapping("/convertersenha")
public class ConsultaSenhaServidor {
	@Autowired
	ServidorRepository servidorRepository;
	
	@Autowired
	AlunoRepository alunoRepository;
	
	@GetMapping
	public String converteSenhaGet(ModelMap model) {
		model.addAttribute("senhaDto", new SenhasDto());
		return "/retornaSenha";
	}
	
	@PostMapping
	public String converteSenhaPost(SenhasDto senhas, ModelMap model) {
		model.addAttribute("senhaDto", new SenhasDto());
		System.out.println("usuário: "+senhas.getLogin() + " tipo: "+senhas.getTipo());
		if(senhas.getTipo().equals("aluno"))
			System.out.println("senha aluno:" + CriptografiaUtil.desencriptar(alunoRepository.findById(senhas.getLogin()).get().getSenha()));
		else
			System.out.println("senha servidor:" + CriptografiaUtil.desencriptar(servidorRepository.findById(senhas.getLogin()).get().getSenha()));
		return "/retornaSenha";
	}
	
}
