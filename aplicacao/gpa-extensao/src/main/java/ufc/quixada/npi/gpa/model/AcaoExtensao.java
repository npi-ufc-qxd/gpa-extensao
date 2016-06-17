package ufc.quixada.npi.gpa.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



@Entity
public class AcaoExtensao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String codigo;
	
	private String identificador;
	
	private String titulo;
	
	private String resumo;
	
	@Enumerated(EnumType.STRING)
	private Modalidade modalidade;
	
	private Integer horasPraticas;
	
	private Integer horasTeoricas;
		
	private String ementa;
		
	private String programacao;
	
	private Date inicio;
	
	private Date termino;
	
	private boolean prorrogavel;
	
	@ManyToOne
	private Pessoa coordenador;
	
	@ManyToOne
	private AcaoExtensao vinculo;
	
	private Integer bolsasSolicitadas;
	
	private Date dataDeHomologacao;
	
	private Integer bolsasRecebidas;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@OneToOne
	private Documento anexo;
	
	@OneToMany(cascade=CascadeType.REMOVE, mappedBy = "acaoExtensao")
	private List<Participacao> equipeDeTrabalho;
	
	@OneToMany(mappedBy = "acaoExtensao", cascade={CascadeType.MERGE, CascadeType.REMOVE})
	private List<ParceriaExterna> parceriasExternas;
	
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.REMOVE})
	private Parecer parecerTecnico;
	
	@OneToOne(cascade={CascadeType.MERGE, CascadeType.REMOVE})
	private Parecer parecerRelator;

	public AcaoExtensao() {
	}

	public AcaoExtensao(String codigo, String identificador, String titulo, String resumo, Modalidade modalidade,
			Date inicio, Date termino, boolean prorrogavel, Pessoa coordenador, Integer bolsasSolicitadas,
			Status status, Documento anexo) {
		this.codigo = codigo;
		this.identificador = identificador;
		this.titulo = titulo;
		this.resumo = resumo;
		this.modalidade = modalidade;
		this.inicio = inicio;
		this.termino = termino;
		this.prorrogavel = prorrogavel;
		this.coordenador = coordenador;
		this.bolsasSolicitadas = bolsasSolicitadas;
		this.status = status;
		this.anexo = anexo;
	}

	public AcaoExtensao getVinculo() {
		return vinculo;
	}

	public void setVinculo(AcaoExtensao vinculo) {
		this.vinculo = vinculo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getTitulo() {
		return titulo;
		}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	public Modalidade getModalidade() {
		return modalidade;
	}

	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}

	public Integer getHorasPraticas() {
		return horasPraticas;
	}

	public void setHorasPraticas(Integer horasPraticas) {
		this.horasPraticas = horasPraticas;
	}

	public Integer getHorasTeoricas() {
		return horasTeoricas;
	}

	public void setHorasTeoricas(Integer horasTeoricas) {
		this.horasTeoricas = horasTeoricas;
	}

	public String getEmenta() {
		return ementa;
	}

	public void setEmenta(String ementa) {
		this.ementa = ementa;
	}

	public String getProgramacao() {
		return programacao;
	}

	public void setProgramacao(String programacao) {
		this.programacao = programacao;
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

	public boolean isProrrogavel() {
		return prorrogavel;
	}

	public void setProrrogavel(boolean prorrogavel) {
		this.prorrogavel = prorrogavel;
	}

	public Pessoa getCoordenador() {
		return coordenador;
	}

	public void setCoordenador(Pessoa coordenador) {
		this.coordenador = coordenador;
	}

	public Integer getBolsasSolicitadas() {
		return bolsasSolicitadas;
	}

	public void setBolsasSolicitadas(Integer bolsasSolicitadas) {
		this.bolsasSolicitadas = bolsasSolicitadas;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Documento getAnexo() {
		return anexo;
	}

	public void setAnexo(Documento anexo) {
		this.anexo = anexo;
	}

	public List<Participacao> getEquipeDeTrabalho() {
		return equipeDeTrabalho;
	}

	public void setEquipeDeTrabalho(List<Participacao> equipeDeTrabalho) {
		this.equipeDeTrabalho = equipeDeTrabalho;
	}

	public List<ParceriaExterna> getParceriasExternas() {
		return parceriasExternas;
	}

	public void addParceriaExterna(ParceriaExterna parceriasExterna) {
		if(this.parceriasExternas == null){
			this.parceriasExternas = new ArrayList<>();
		}
		this.parceriasExternas.add(parceriasExterna);
	}

	public Parecer getParecerTecnico() {
		return parecerTecnico;
	}

	public void setParecerTecnico(Parecer parecerTecnico) {
		this.parecerTecnico = parecerTecnico;
	}

	public Parecer getParecerRelator() {
		return parecerRelator;
	}

	public void setParecerRelator(Parecer parecerRelator) {
		this.parecerRelator = parecerRelator;
	}

	public Date getDataDeHomologacao() {
		return dataDeHomologacao;
	}

	public void setDataDeHomologacao(Date dataDeHomologacao) {
		this.dataDeHomologacao = dataDeHomologacao;
	}

	public Integer getBolsasRecebidas() {
		return bolsasRecebidas;
	}

	public void setBolsasRecebidas(Integer bolsasRecebidas) {
		this.bolsasRecebidas = bolsasRecebidas;
	}

	public enum Modalidade {
		PROGRAMA("Programa"), PROJETO("Projeto"), CURSO("Curso"), EVENTO("Evento"), PRESTACAO_DE_SERVICO(
				"Prestação de Serviço");
		private String descricao;

		private Modalidade(String descricao) {
			this.descricao = descricao;
		}

		public String getDescricao() {
			return this.descricao;
		}
	}

	public enum Status {
		NOVO("NOVO"), AGUARDANDO_PARECERISTA("AGUARDANDO PARECERISTA"), AGUARDANDO_PARECER_TECNICO(
				"AGUARDANDO PARECER TÉCNICO"), AGUARDANDO_PARECER_RELATOR(
						"AGUARDANDO PARECER"), AGUARDANDO_RELATOR("AGUARDANDO RELATOR"), RESOLVENDO_PENDENCIAS_PARECER(
								"RESOLVENDO PENDÊNCIAS DO PARECER"), RESOLVENDO_PENDENCIAS_RELATO(
										"RESOLVENDO RESTRIÇÕES DO RELATO"), AGUARDANDO_HOMOLOGACAO(
												"AGUARDANDO HOMOLOGAÇÃO"), APROVADO("APROVADO"), REPROVADO("REPROVADO");
		private String descricao;

		private Status(String descricao) {
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
		AcaoExtensao other = (AcaoExtensao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
