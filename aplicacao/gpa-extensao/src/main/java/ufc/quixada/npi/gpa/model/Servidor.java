package ufc.quixada.npi.gpa.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
//@EntityListeners(ServidorEntityListener.class)
public class Servidor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(optional = false)
	private Pessoa pessoa;

	private String siape;

	@Enumerated(EnumType.STRING)
	private Funcao funcao;

	@Enumerated(EnumType.STRING)
	private Dedicacao dedicacao;

	public Servidor() {
	}
	
	public Servidor(Pessoa pessoa, Funcao funcao, Dedicacao dedicacao, String siape) {
		super();
		this.pessoa = pessoa;
		this.funcao = funcao;
		this.dedicacao = dedicacao;
		this.siape = siape;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getSiape() {
		return siape;
	}

	public void setSiape(String siape) {
		this.siape = siape;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public Dedicacao getDedicacao() {
		return dedicacao;
	}

	public void setDedicacao(Dedicacao dedicacao) {
		this.dedicacao = dedicacao;
	}

	public enum Funcao {
		DOCENTE("Docente"), STA("Técnico Administrativo");

		private String descricao;

		private Funcao(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}

	enum Dedicacao {
		EXCLUSIVA("Exclusiva"), H40("40h"), H20("20h");

		private String descricao;

		private Dedicacao(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return descricao;
		}
	}
}
