package ufc.quixada.npi.gpa.validator;

import static ufc.quixada.npi.gpa.util.Constants.BOLSISTA;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_ALUNO_JA_BOLSISTA;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_ALUNO_JA_BOLSISTA_OUTRO_PROJETO;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_QUANTIDADE_BOLSAS_EXEDIDAS;
import static ufc.quixada.npi.gpa.util.Constants.ERROR_INFORMAR_BOLSAS_RECEBIDAS;

import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ufc.quixada.npi.gpa.model.Bolsa;
import ufc.quixada.npi.gpa.model.Bolsa.TipoBolsa;
import ufc.quixada.npi.gpa.repository.BolsaRepository;

@Named
public class BolsaValidator implements Validator{

	@Autowired
	private BolsaRepository bolsaRepository;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Bolsa b = (Bolsa) target;
		validaProjetoAtual(b, errors);
		validaTodosProjetos(b, errors);
		validaQuantidadeBolsas(b, errors);
	}

	private void validaQuantidadeBolsas(Bolsa b, Errors errors) {
		if(b.getTipo().equals(TipoBolsa.REMUNERADO)) {
			Integer quantidadeBolsasRecebidas = b.getAcaoExtensao().getBolsasRecebidas();
			Integer quantidadeBolsasAtual = bolsaRepository.countByAcaoExtensaoAndTipoAndAtivo(b.getAcaoExtensao(), TipoBolsa.REMUNERADO, true);
			
			if(quantidadeBolsasRecebidas == null) {
				errors.rejectValue(BOLSISTA, ERROR_INFORMAR_BOLSAS_RECEBIDAS);
			}else if(quantidadeBolsasAtual >= quantidadeBolsasRecebidas) {
				errors.rejectValue(BOLSISTA, ERROR_QUANTIDADE_BOLSAS_EXEDIDAS);
			}
		}
	}

	private void validaProjetoAtual(Bolsa b, Errors errors) {
		List<Bolsa> bolsas = b.getAcaoExtensao().getBolsistas();
			
		for(Bolsa bolsa : bolsas) {
			if(b.getBolsista().equals(bolsa.getBolsista()) && bolsa.isAtivo()) {
				errors.rejectValue(BOLSISTA, ERROR_ALUNO_JA_BOLSISTA);
				break;
			}
		}
	}

	private void validaTodosProjetos(Bolsa b, Errors errors) {
		if(b.getTipo().equals(TipoBolsa.REMUNERADO)) {
			List<Bolsa> bolsas = bolsaRepository.findByBolsista(b.getBolsista());
			
			for(Bolsa bolsa : bolsas) {
				if(b.getBolsista().equals(bolsa.getBolsista()) && bolsa.isAtivo() 
						&& bolsa.getTipo().equals(TipoBolsa.REMUNERADO)) {
					errors.rejectValue(BOLSISTA, ERROR_ALUNO_JA_BOLSISTA_OUTRO_PROJETO);
					break;
				}
			}
		}
	}

}
