package ufc.quixada.npi.gpa.util;

public class Constants {
	
	//LDAP
	public static final String LDAP_URL = "ldap.url";
	public static final String LDAP_BASE = "ldap.base";
	public static final String LDAP_USER = "ldap.user";
	public static final String LDAP_PASSWORD = "ldap.password";
	public static final String LDAP_OU = "ldap.ou";
	public static final String BASE = "ldap.url";
	
	public static final String LOGIN_INVALIDO = "Usuário e/ou senha inválidos";

	//Model attributes
	public static final String PARECERISTAS = "pareceristas";
	public static final String ACOES_AGUARDANDO_PARECER = "acoesAguardandoParecer";
	public static final String ACOES_AGUARDANDO_PARECERISTA = "acoesAguardandoParecerista";
	public static final String ACOES_AGUARDANDO_RELATO = "acoesAguardandoRelato";
	public static final String ACOES_AGUARDANDO_RELATOR = "acoesAguardandoRelator";
	public static final String ACOES_AGUARDANDO_HOMOLOGACAO = "acoesAguardandoHomologacao";
	public static final String ACOES_HOMOLOGADAS = "acoesHomologadas";
	
	//Load fragments
	public static final String LOAD_PARECERISTAS = "index :: pareceristas";
	
	//Paginas
	public static final String PAGINA_INICIAL = "principal/index";
	public static final String REDIRECT_PAGINA_ADICIONAR_PARTICIPACAO= "redirect:/participacoes/";
	public static final String PAGINA_ADICIONAR_PARTICIPACAO = "coordenacao/crud/adicionar-participacao";
	
	//Exceptions
	public static final String USUARIO_NAO_ENCONTRADO_EXCEPTION = "Usuário não encontrado";
	
	//errors
	public static final String ERROR_PESSOA_JA_PARTICIPANTE = "acaoExtensao.pessoaParticipante";
}
