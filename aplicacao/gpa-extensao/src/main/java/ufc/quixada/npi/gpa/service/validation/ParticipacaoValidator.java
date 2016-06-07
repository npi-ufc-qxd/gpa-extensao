package ufc.quixada.npi.gpa.service.validation;

import java.util.List;

import javax.inject.Named;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ufc.quixada.npi.gpa.model.Participacao;

@Named
public class ParticipacaoValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Participacao p = (Participacao) target;
		participacaoUsuario(p, errors);
	}

	private void participacaoUsuario(Participacao p, Errors errors) {
		List<Participacao> participacoes = p.getAcaoExtensao().getEquipeDeTrabalho();
		
		if(p.getParticipante() != null) {
			for(Participacao participacao: participacoes) {
				if(p.getParticipante().equals(participacao.getParticipante())) {
					errors.reject("acaoExtensao.pessoaParticipante", "acaoExtensao.pessoaParticipante");
					continue;
				}
			}
		} else if(p.getCpfParticipante() != null) {
			for(Participacao participacao: participacoes) {
				if(p.getCpfParticipante().equals(participacao.getCpfParticipante())) {
					errors.reject("acaoExtensao.pessoaParticipante", "acaoExtensao.pessoaParticipante");
					continue;
				}
			}
		}
	}


}
