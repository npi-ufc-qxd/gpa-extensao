package ufc.quixada.npi.gpa.specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import ufc.quixada.npi.gpa.model.AcaoExtensao;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Modalidade;
import ufc.quixada.npi.gpa.model.AcaoExtensao.Status;
import ufc.quixada.npi.gpa.model.Pessoa;

public final class AcaoExtensaoEspecification {

	public static Specification<AcaoExtensao> buscar(Pessoa coordenador, Modalidade modalidade, Integer ano) {
		return new Specification<AcaoExtensao>() {
			public Predicate toPredicate(Root<AcaoExtensao> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				Predicate predicate = builder.and();
				
				if(coordenador != null) {
					predicate = builder.and(predicate, builder.equal(root.<Integer>get("coordenador"), coordenador));
				}
				if(modalidade != null) {
					predicate = builder.and(predicate, builder.equal(root.<Modalidade>get("modalidade"), modalidade));
				}
				if(ano != null) {
					String inicioAnoString = "01/01/" + ano;
					String finalAnoString = "31/12/" + ano;
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
					
					try {
						Date inicioAno = (Date)format.parse(inicioAnoString);
						Date finalAno = (Date)format.parse(finalAnoString);
						predicate = builder.and(predicate, builder.lessThanOrEqualTo(root.<Date>get("inicio"), finalAno));
						predicate = builder.and(predicate, builder.greaterThanOrEqualTo(root.<Date>get("termino"), inicioAno));
					} catch (ParseException e) {
					}
				}
				
				predicate = builder.and(predicate, builder.equal(root.<Status>get("status"), Status.APROVADO));
				return predicate;
			}
		};
	}
}
