package ufc.quixada.npi.gpa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "equipe_de_trabalho")
public class Participacao {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	private Pessoa participante;
	
	@Enumerated(EnumType.STRING)
	private Funcao funcao;
	
	private String descricaoFuncao;
	
	@Enumerated(EnumType.STRING)
	private Instituicao instituicao;
	
	private String nomeInstituicao;
	
	private Integer cargaHoraria;
	
	private Date dataInicio;	
	
	private Date dataTermino;
	
	private String nomeParticipante;
	
	private String cpfParticipante;
	
	private boolean coordenador;
	
	@ManyToOne
	@JoinColumn(name = "acao_id")
	private AcaoExtensao acaoExtensao;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Pessoa getParticipante() {
		return participante;
	}

	public void setParticipante(Pessoa participante) {
		this.participante = participante;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public String getDescricaoFuncao() {
		return descricaoFuncao;
	}

	public void setDescricaoFuncao(String descricaoFuncao) {
		this.descricaoFuncao = descricaoFuncao;
	}

	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	public String getNomeInstituicao() {
		return nomeInstituicao;
	}

	public void setNomeInstituicao(String nomeInstituicao) {
		this.nomeInstituicao = nomeInstituicao;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(Date dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getNomeParticipante() {
		return nomeParticipante;
	}

	public void setNomeParticipante(String nomeParticipante) {
		this.nomeParticipante = nomeParticipante;
	}

	public String getCpfParticipante() {
		return cpfParticipante;
	}

	public void setCpfParticipante(String cpfParticipante) {
		this.cpfParticipante = cpfParticipante;
	}

	public boolean isCoordenador() {
		return coordenador;
	}

	public void setCoordenador(boolean coordenador) {
		this.coordenador = coordenador;
	}

	@JsonIgnore
	public AcaoExtensao getAcaoExtensao() {
		return acaoExtensao;
	}

	public void setAcaoExtensao(AcaoExtensao acaoExtensao) {
		this.acaoExtensao = acaoExtensao;
	}

	public enum Funcao {
		STA ("Servidor Técnico Administrativo"), DOCENTE("Docente"), OUTRA("Outra");
		
		private String descricao;
		
		private Funcao(String descricao){
			this.descricao=descricao;
		}
		public String getDescricao(){
			return this.descricao;
		}
	}
	public enum Instituicao{
		UFC("Universidade Federal do Ceará"), OUTRA_IES("Outra Instituição de Ensino Superior"), OUTRA("Outra Instituição");
		
		private String descricao;
		
		private Instituicao(String descricao){
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
		Participacao other = (Participacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
