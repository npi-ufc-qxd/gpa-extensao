package ufc.quixada.npi.gpa.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
//@EntityListeners(AlunoEntityListener.class)
public class Aluno{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(optional = false, cascade = CascadeType.PERSIST)
	private Pessoa pessoa;
	
	private String matricula;
	
	private String curso;
	
	public Aluno() {
	}
	
	public Aluno(Pessoa pessoa, String matricula, String curso) {
		super();
		this.pessoa = pessoa;
		this.matricula = matricula;
		this.curso = curso;
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

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}
	
	public enum Curso{
		ES("Engenharia de Software"), SI("Sistemas de Informação"), CC("Ciência da Computação"), 
		EC("Engenharia de Computação"), DD("Design Digitial"), REDES("Redes de Computadores");
		
		private String descricao;

		private Curso(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return this.descricao;
		}
	}
}
