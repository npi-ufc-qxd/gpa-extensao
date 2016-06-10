package ufc.quixada.npi.gpa.validator;

import static ufc.quixada.npi.gpa.util.Constants.ERROR_PESSOA_JA_PARTICIPANTE;

import java.util.List;

import javax.inject.Named;

import org.springframework.validation.Errors;

import ufc.quixada.npi.gpa.model.Participacao;

@Named
public class ParticipacaoValidator implements org.springframework.validation.Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Participacao p = (Participacao) target;
		participacaoUsuario(p, errors);
	}

	private void participacaoUsuario(Participacao p, Errors errors) {
		List<Participacao> participacoes = p.getAcaoExtensao().getEquipeDeTrabalho();

		if (p.getParticipante() != null) {
			for (Participacao participacao : participacoes) {
				if (p.getParticipante().equals(participacao.getParticipante())) {
					errors.reject(ERROR_PESSOA_JA_PARTICIPANTE, ERROR_PESSOA_JA_PARTICIPANTE);
					continue;
				}
			}
		} else if (p.getCpfParticipante() != null) {
			for (Participacao participacao : participacoes) {
				if (p.getCpfParticipante().equals(participacao.getCpfParticipante())) {
					errors.reject(ERROR_PESSOA_JA_PARTICIPANTE, ERROR_PESSOA_JA_PARTICIPANTE);
					continue;
				}
			}
		}
	}
}