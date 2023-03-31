package br.ifrs.edu.br.basesmanager.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Servidor {	
	@Id
	private String login;
	@Column
	private String senha;
	@Column
	private String data_nascimento;
	@Column
	private String sexo;
	@Column
	private String nome_completo;
	@Column
	private String email;
	@Column
	private String cn;
	@Column
	private String perfil;
	@Column
	private String tipoServidor;
	
	
	@OneToMany(mappedBy = "servidor", fetch = FetchType.LAZY)
	private List<ServidorCargo> listaCargos = new ArrayList<ServidorCargo>();

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		//validar se a senha estiver nula e setar uma senha inicial
		
		this.senha = senha;
	}

	public String getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(String data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<ServidorCargo> getListaCargos() {
		return listaCargos;
	}

	public void setListaCargos(List<ServidorCargo> listaCargos) {
		this.listaCargos = listaCargos;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getNome_completo() {
		return nome_completo;
	}

	public void setNome_completo(String nome_completo) {
		this.nome_completo = nome_completo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getTipoServidor() {
		return tipoServidor;
	}

	public void setTipoServidor(String tipoServidor) {
		this.tipoServidor = tipoServidor;
	}

}