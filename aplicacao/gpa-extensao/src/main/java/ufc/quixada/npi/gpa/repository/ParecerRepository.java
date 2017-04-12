package ufc.quixada.npi.gpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ufc.quixada.npi.gpa.model.Parecer;
import ufc.quixada.npi.gpa.model.Pessoa;

@Repository
public interface ParecerRepository extends CrudRepository<Parecer, Integer> {

	@Query("SELECT se.pessoa FROM Servidor se, Pessoa pe WHERE se.pessoa = pe AND pe NOT IN (SELECT pa.participante FROM AcaoExtensao ae, equipe_de_trabalho pa WHERE pa.acaoExtensao.id = :idAcao AND pa MEMBER OF ae.equipeDeTrabalho)")
	List<Pessoa> getPossiveisPareceristas(@Param("idAcao")Integer idAcao);

}
