package br.ifrs.edu.br.basesmanager.useful;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifrs.edu.br.basesmanager.models.Aluno;
import br.ifrs.edu.br.basesmanager.models.AlunoCurso;
import br.ifrs.edu.br.basesmanager.models.Servidor;
import br.ifrs.edu.br.basesmanager.models.ServidorCargo;
import br.ifrs.edu.br.basesmanager.repositories.AlunoCursoRepository;
import br.ifrs.edu.br.basesmanager.repositories.AlunoRepository;
import br.ifrs.edu.br.basesmanager.repositories.ServidorCargoRepository;
import br.ifrs.edu.br.basesmanager.repositories.ServidorRepository;

@RestController
@RequestMapping("/alunos")
public class AlunosUteis {
	
	@Autowired
	AlunoCursoRepository alunoCursoRepository;
	
	@Autowired
	AlunoRepository alunoRepository;
	
	@GetMapping("/excluir")
	public ResponseEntity<?>  excluir() {
		List<Aluno> listaDeAlunos =  alunoRepository.findAll();
		List<AlunoCurso> listaDeServidoresInativos = new ArrayList<AlunoCurso>();
		
		for(Aluno aluno : listaDeAlunos) {
			List<AlunoCurso> cursosDoAluno =  aluno.getListaCursosAluno();
			//remove todos os cursos inativos
			for(AlunoCurso alunoCurso : cursosDoAluno) {
				if(!alunoCurso.getStatus_discente().equalsIgnoreCase("ATIVO") &&
						!alunoCurso.getStatus_discente().equalsIgnoreCase("FORMANDO")) {
					alunoCursoRepository.delete(alunoCurso);
				}
			}
			//remove todos os alunos que não tem curso vinculado
			cursosDoAluno =  alunoCursoRepository.findByAluno(aluno);
			if(cursosDoAluno.size() == 0) {
				alunoRepository.delete(aluno);
			}

			
		}
		return ResponseEntity.ok(true);
	}
	
	@GetMapping("/trocaemailparaminusculo")
	public ResponseEntity<?>  trocacnparaminusculo() {
		List<Aluno> listaDeAlunos =  alunoRepository.findAll();
		
		
		for(Aluno aluno : listaDeAlunos) {
			
			aluno.setEmail(aluno.getEmail().toLowerCase());
			alunoRepository.save(aluno);

			
		}
		return ResponseEntity.ok(true);
	}
	
	@GetMapping("/persistetipoaluno")
	public ResponseEntity<?>  persisteTipoAluno() {
		List<AlunoCurso> listaDeAlunosCursos =  alunoCursoRepository.findAll();
		
		for(AlunoCurso alunoCurso : listaDeAlunosCursos) {
			Aluno aluno = alunoCurso.getAluno();
			if(alunoCurso.getNome_curso().equalsIgnoreCase("MATEMÁTICA - LICENCIATURA") ||
					alunoCurso.getNome_curso().equalsIgnoreCase("BACHAREL EM CIÊNCIA DA COMPUTAÇÃO")
					) {
				//falta engenharia mecânica e agronomia migrar para o sigaa
				aluno.setTipoAluno("superior");
	
				}
				else if(alunoCurso.getNome_curso().equalsIgnoreCase("AGROPECUÁRIA - INTEGRADO AO ENSINO MÉDIO") ||
						alunoCurso.getNome_curso().equalsIgnoreCase("INFORMÁTICA - INTEGRADO AO ENSINO MÉDIO")	||
						alunoCurso.getNome_curso().equalsIgnoreCase("MECÂNICA - INTEGRADO AO ENSINO MÉDIO")
						) {
				
					if(aluno.getTipoAluno() == null || !aluno.getTipoAluno().equalsIgnoreCase("superior"))
						aluno.setTipoAluno("integrado");
			}
				else if(alunoCurso.getNome_curso().equalsIgnoreCase("MECÂNICA") ||
						alunoCurso.getNome_curso().equalsIgnoreCase("ELETROTÉCNICA")	
						) {
					if(aluno.getTipoAluno() == null || !aluno.getTipoAluno().equalsIgnoreCase("superior"))
						aluno.setTipoAluno("subsequente");
			}
			alunoRepository.save(aluno);
		}
		
		return ResponseEntity.ok(true);
	}
}
