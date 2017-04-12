package ufc.quixada.npi.gpa.specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Participacao;
import ufc.quixada.npi.gpa.model.Pessoa;

public final class AcaoExtensaoEspecification {

	public static Specification<AcaoExtensao> buscar(Pessoa pessoa, Modalidade modalidade, String estado, Integer ano) {
		return new Specification<AcaoExtensao>() {
			public Predicate toPredicate(Root<AcaoExtensao> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.and();
				
				if(pessoa != null) {
					Subquery<AcaoExtensao> participacaoQuery = query.subquery(AcaoExtensao.class);
					Root<Participacao> participacao =  participacaoQuery.from(Participacao.class);
					Join<Participacao, AcaoExtensao> participacoes = participacao.join("acaoExtensao");
					
					participacaoQuery.select(participacoes).where(builder.equal(participacao.get("participante") , pessoa));
					predicate = builder.and(predicate, builder.in(root).value(participacaoQuery));
				}
				
				if(modalidade != null) {
					predicate = builder.and(predicate, builder.equal(root.get("modalidade"), modalidade));
				}
				if(!estado.isEmpty()) {
					Boolean ativo = Boolean.valueOf(estado);
					predicate = builder.and(predicate, builder.equal(root.get("ativo"), ativo));
				}
				if(ano != null) {
					predicate = builder.and(predicate, predicateAno(root, query, builder, ano));
				}
				
				predicate = builder.and(predicate, builder.equal(root.get("status"), Status.APROVADO));
				return predicate;
			}
		};
	}
	
	private static Predicate predicateAno(Root<AcaoExtensao> root, CriteriaQuery<?> query, CriteriaBuilder builder, Integer ano){
		Predicate predicate = builder.and();
		
		String inicioAnoString = "01/01/" + ano;
		String finalAnoString = "31/12/" + ano;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {
			Date inicioAno = (Date) format.parse(inicioAnoString);
			Date finalAno = (Date) format.parse(finalAnoString);
			predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.get("inicio"), finalAno));
			predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.get("termino"), inicioAno));
		} catch (ParseException e) {
		}
		return predicate;
	}
	
	public static Specification<AcaoExtensao> buscarCurso(Integer ano, String curso){
		return new Specification<AcaoExtensao>() {
			public Predicate toPredicate(Root<AcaoExtensao> root, CriteriaQuery<?> query, CriteriaBuilder builder){
				Predicate predicate = builder.and();
				
				if(!curso.isEmpty()) {
					Subquery<AcaoExtensao> bolsaQuery = query.subquery(AcaoExtensao.class);
					Root<Bolsa> bolsa =  bolsaQuery.from(Bolsa.class);
					Join<Bolsa, AcaoExtensao> bolsas = bolsa.join("acaoExtensao");
					
					bolsaQuery.select(bolsas).where(builder.equal(bolsa.get("bolsista").get("curso") , curso));
					
					predicate = builder.and(predicate, builder.in(root).value(bolsaQuery));
				}
				
				if(ano != null) {
					predicate = builder.and(predicate, predicateAno(root, query, builder, ano));
				}
				
				predicate = builder.and(predicate, builder.equal(root.get("status"), Status.APROVADO));
				return predicate;
			}
		};
	}
}
