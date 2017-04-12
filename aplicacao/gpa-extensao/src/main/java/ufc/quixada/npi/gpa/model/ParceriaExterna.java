package ufc.quixada.npi.gpa.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ParceriaExterna {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private boolean geraDemanda;
	
	private boolean definicaoAcoes;
	
	private boolean equipamento;
	
	private boolean financiamento;
	
	private boolean outrasFormas;
	
	private String descricaoOutrasFormas;
	
	@OneToOne
	private AcaoExtensao acaoExtensao;
	
	@ManyToOne
	private Parceiro parceiro;
	
	public ParceriaExterna(boolean geraDemanda, boolean definicaoAcoes, boolean equipamento, boolean financiamento,
			boolean outrasFormas, String descricaoOutrasFormas, Parceiro parceiro) {
		this.geraDemanda = geraDemanda;
		this.definicaoAcoes = definicaoAcoes;
		this.equipamento = equipamento;
		this.financiamento = financiamento;
		this.outrasFormas = outrasFormas;
		this.descricaoOutrasFormas = descricaoOutrasFormas;
		this.parceiro = parceiro;
	}

	public ParceriaExterna() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isGeraDemanda() {
		return geraDemanda;
	}

	public void setGeraDemanda(boolean geraDemanda) {
		this.geraDemanda = geraDemanda;
	}

	public boolean isDefinicaoAcoes() {
		return definicaoAcoes;
	}

	public void setDefinicaoAcoes(boolean definicaoAcoes) {
		this.definicaoAcoes = definicaoAcoes;
	}

	public boolean isEquipamento() {
		return equipamento;
	}

	public void setEquipamento(boolean equipamento) {
		this.equipamento = equipamento;
	}

	public boolean isFinanciamento() {
		return financiamento;
	}

	public void setFinanciamento(boolean financiamento) {
		this.financiamento = financiamento;
	}

	public boolean isOutrasFormas() {
		return outrasFormas;
	}

	public void setOutrasFormas(boolean outrasFormas) {
		this.outrasFormas = outrasFormas;
	}

	public String getDescricaoOutrasFormas() {
		return descricaoOutrasFormas;
	}

	public void setDescricaoOutrasFormas(String descricaoOutrasFormas) {
		this.descricaoOutrasFormas = descricaoOutrasFormas;
	}

	public Parceiro getParceiro() {
		return parceiro;
	}

	public void setParceiro(Parceiro parceiro) {
		this.parceiro = parceiro;
	}

	public AcaoExtensao getAcaoExtensao() {
		return acaoExtensao;
	}

	public void setAcaoExtensao(AcaoExtensao acaoExtensao) {
		this.acaoExtensao = acaoExtensao;
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
		ParceriaExterna other = (ParceriaExterna) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
