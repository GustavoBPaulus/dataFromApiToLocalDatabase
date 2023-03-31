package br.ifrs.edu.br.basesmanager.models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Aluno {
	
	@Id
	private String login;
	
	@Column
	private String nome_completo;
	
	@Column
	private String senha;
	
	@Column
	private String email;
	
	@Column
	private String data_nascimento;
	
	@Column
	private String sexo;
	
	@Column
	private String tipoAluno;
	
	@OneToMany(mappedBy = "aluno")
	private List<AlunoCurso> listaCursosAluno;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		//validar se a senha estiver nula e setar uma senha inicial	
		this.login = login;
	}
	public String getNome_completo() {
		return nome_completo;
	}
	public void setNome_completo(String nome_completo) {
		this.nome_completo = nome_completo;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getData_nascimento() {
		return data_nascimento;
	}
	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public List<AlunoCurso> getListaCursosAluno() {
		return listaCursosAluno;
	}
	public void setListaCursosAluno(List<AlunoCurso> listaCursosAluno) {
		this.listaCursosAluno = listaCursosAluno;
	}
	public String getTipoAluno() {
		return tipoAluno;
	}
	public void setTipoAluno(String tipoAluno) {
		this.tipoAluno = tipoAluno;
	}
	
}