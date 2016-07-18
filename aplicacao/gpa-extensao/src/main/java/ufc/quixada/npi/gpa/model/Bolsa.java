package ufc.quixada.npi.gpa.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Bolsa {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Aluno bolsista;
	private Date inicio;
	private Date termino;
	private Integer cargaHoraria;
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	private boolean ativo;
	@OneToMany
	private List<FrequenciaBolsista> frenquencias;
	
	public Bolsa(){
		
	}
	
	public Bolsa(Aluno bolsista, Date inicio, Date termino, Integer cargaHoraria, Tipo tipo) {
		super();
		this.bolsista = bolsista;
		this.inicio = inicio;
		this.termino = termino;
		this.cargaHoraria = cargaHoraria;
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Aluno getBolsista() {
		return bolsista;
	}


	public void setBolsista(Aluno bolsista) {
		this.bolsista = bolsista;
	}


	public Date getInicio() {
		return inicio;
	}


	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}


	public Date getTermino() {
		return termino;
	}


	public void setTermino(Date termino) {
		this.termino = termino;
	}


	public Integer getCargaHoraria() {
		return cargaHoraria;
	}


	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}


	public Tipo getTipo() {
		return tipo;
	}


	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}


	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public List<FrequenciaBolsista> getFrequencias(){
		return frenquencias;
	}
	public void addFrequencia(FrequenciaBolsista frequencia){
		if(this.frenquencias==null){
			this.frenquencias = new ArrayList<>();
		}
		this.frenquencias.add(frequencia);
	}
	enum Tipo{
		REMUNERADO("Remunerado"),VOLUNTARIO("Voluntário");
		private String descricao;
		
		private Tipo(String descricao){
			this.descricao=descricao;
		}

		public String getDescricao() {
			return descricao;
		}
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
		Bolsa other = (Bolsa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
