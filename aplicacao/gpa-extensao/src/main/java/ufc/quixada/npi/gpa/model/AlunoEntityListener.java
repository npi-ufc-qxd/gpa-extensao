package ufc.quixada.npi.gpa.model;

import javax.persistence.PostLoad;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import br.ufc.quixada.npi.ldap.model.Usuario;
import br.ufc.quixada.npi.ldap.service.UsuarioService;

@Component
public class AlunoEntityListener implements ApplicationContextAware{

	private static ApplicationContext context;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostLoad
	public void loadAluno(Aluno aluno) {
		context.getAutowireCapableBeanFactory().autowireBean(this);
		
		Usuario usuario = usuarioService.getByCpf(aluno.getPessoa().getCpf());
		aluno.setMatricula(usuario.getMatricula());
	}
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		context = ctx;
	}

}
