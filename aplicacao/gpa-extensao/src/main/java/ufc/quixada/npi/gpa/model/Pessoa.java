package ufc.quixada.npi.gpa.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

@Entity
public class Pessoa {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	private String nome;
	private String email;
	@CPF
	private String cpf;
	@ManyToMany
	@JoinTable(name="papel_pessoa", joinColumns=@JoinColumn(name="pessoa_id"), inverseJoinColumns=@JoinColumn(name="papel_id"))
	private List<Papel> papeis;
	public Pessoa(String nome, String email, String cpf, List<Papel> papeis) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpf = cpf;
		this.papeis = papeis;
	}
	public Pessoa() {
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public List<Papel> getPapeis() {
		return papeis;
	}
	public void setPapeis(List<Papel> papeis) {
		this.papeis = papeis;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
