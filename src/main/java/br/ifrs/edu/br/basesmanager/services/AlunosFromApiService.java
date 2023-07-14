package br.ifrs.edu.br.basesmanager.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ifrs.edu.br.basesmanager.models.Aluno;
import br.ifrs.edu.br.basesmanager.models.AlunoCurso;
import br.ifrs.edu.br.basesmanager.repositories.AlunoCursoRepository;
import br.ifrs.edu.br.basesmanager.repositories.AlunoRepository;
import br.ifrs.edu.br.basesmanager.useful.CriptografiaUtil;
import clientApiReitoria.model.AlunoCursoModelo;
import clientApiReitoria.model.AlunoModel;
import clientApiReitoria.service.RetornaListaDeAlunosApiReitoria;

@Service
public class AlunosFromApiService {

	@Autowired
	AlunoRepository alunoRepository;

	@Autowired
	AlunoCursoRepository alunoCursoRepository;

	public boolean sincronizaAlunos() {
		RetornaListaDeAlunosApiReitoria retornaAlunos = new RetornaListaDeAlunosApiReitoria();
		List<AlunoModel> alunosModelo = retornaAlunos.retornaListaDeAlunos();
		List<Aluno> alunos = convertAlunoModeloToAluno(alunosModelo);
		alunos.forEach(s -> {
			alunoRepository.save(s);
			System.out.println(s.getLogin());
		});

		// seta lista de cursos por alunos já cadastrados e adiciona senha se
		// estiver nula
		alunosModelo.forEach(am -> {
			Aluno aluno = alunoRepository.findById(am.getLogin()).get();
			aluno.setListaCursosAluno(converteListaAlunoCursoModeloToAlunoCursoEcadastraAlunoCursoEcadastraServidorCargo(am.getCursosAluno(), aluno));
			if (aluno.getSenha() == null || aluno.getSenha().equals(""))
				aluno.setSenha(CriptografiaUtil.encriptar(aluno.getLogin() + "@ibiruba.ifrs"));
			alunoRepository.save(aluno);
		});

		return !alunos.isEmpty();
	}

	private List<Aluno> convertAlunoModeloToAluno(List<AlunoModel> alunosModelo) {
		List<Aluno> alunos = new ArrayList<Aluno>();
		alunosModelo.forEach(am -> {
			Aluno aluno = new Aluno();
			aluno.setData_nascimento(am.getData_nascimento());
			aluno.setEmail(am.getEmail());
			aluno.setLogin(am.getLogin());
			aluno.setNome_completo(am.getNome_completo());
			aluno.setSexo(am.getSexo());
			
			

			alunos.add(aluno);
		});

		return alunos;
	}

	private List<AlunoCurso> converteListaAlunoCursoModeloToAlunoCursoEcadastraAlunoCursoEcadastraServidorCargo(List<AlunoCursoModelo> alunosCursoModelo,
			Aluno a) {
		List<AlunoCurso> alunosCurso = new ArrayList<AlunoCurso>();
		alunosCursoModelo.forEach(acm -> {
			AlunoCurso alunoCurso = new AlunoCurso();
			alunoCurso.setCod_curso(acm.getCod_curso());
			alunoCurso.setCurriculo(acm.getCurriculo());
			alunoCurso.setAluno(a);
			alunoCurso.setMatricula(acm.getMatricula());
			alunoCurso.setNome_curso(acm.getNome_curso());
			alunoCurso.setStatus_discente(acm.getStatus_discente());
			alunoCurso.setTurma_entrada(acm.getTurma_entrada());
			alunosCurso.add(alunoCurso);
			
			alunoCursoRepository.save(alunoCurso);
		});

		return alunosCurso;
	}

}
