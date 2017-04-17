package ufc.quixada.npi.gpa.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Parecer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	private Posicionamento posicionamento;

	@Column(columnDefinition = "TEXT")
	private String parecer;

	@Column(columnDefinition = "TEXT")
	private String observacoes;
	
	private Date dataAtribuicao;

	private Date dataRealizacao;

	@ManyToOne
	@JoinColumn(name = "parecerista_id")
	private Pessoa responsavel;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date prazo;

	@OneToMany(mappedBy = "parecer", cascade = CascadeType.MERGE)
	@OrderBy("dataDeSolicitacao DESC")
	private List<Pendencia> pendencias;

	public Parecer() {
	}

	public Parecer(Posicionamento posicionamento, String parecer, Date dataAtribuicao, Date dataRealizacao,
			Pessoa responsavel, Date prazo) {
		super();
		this.posicionamento = posicionamento;
		this.parecer = parecer;
		this.dataAtribuicao = dataAtribuicao;
		this.dataRealizacao = dataRealizacao;
		this.responsavel = responsavel;
		this.prazo = prazo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Posicionamento getPosicionamento() {
		return posicionamento;
	}

	public void setPosicionamento(Posicionamento posicionamento) {
		this.posicionamento = posicionamento;
	}

	public String getParecer() {
		return parecer;
	}

	public void setParecer(String parecer) {
		this.parecer = parecer;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Date getDataAtribuicao() {
		return dataAtribuicao;
	}

	public void setDataAtribuicao(Date dataAtribuicao) {
		this.dataAtribuicao = dataAtribuicao;
	}

	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	public Pessoa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Pessoa responsavel) {
		this.responsavel = responsavel;
	}

	public Date getPrazo() {
		return prazo;
	}

	public void setPrazo(Date prazo) {
		this.prazo = prazo;
	}

	public List<Pendencia> getPendencias() {
		return pendencias;
	}

	public void setPendencias(List<Pendencia> pendencias) {
		this.pendencias = pendencias;
	}

	public void addPendencia(Pendencia pendencia) {
		if (this.pendencias == null) {
			this.pendencias = new ArrayList<Pendencia>();
		}

		pendencia.setParecer(this);
		this.pendencias.add(pendencia);
	}

	public void setPendenciasResolvidas() {
		if (this.pendencias != null) {
			for (Pendencia pendencia : this.pendencias) {
				pendencia.setResolvida(true);
			}
		}
	}
	
	public boolean temParecerista(){
		if(getResponsavel()!= null){
			return true;
		}
		return false;
	}

	public enum Posicionamento {
		FAVORAVEL("Favorável"), NAO_FAVORAVEL("Não Favorável");
		private String descricao;

		private Posicionamento(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return this.descricao;
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
		Parecer other = (Parecer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
