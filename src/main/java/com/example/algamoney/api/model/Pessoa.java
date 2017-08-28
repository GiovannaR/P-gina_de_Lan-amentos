package com.example.algamoney.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "pessoa")
public class Pessoa {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long codigo;
		
		@NotNull
		private String nome;
		
		@Embedded
		private Endereco endereco;
		
		@NotNull(message = "O Ativo \\u00e9 obrigat\\u00f3rio;")
		private boolean ativo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
