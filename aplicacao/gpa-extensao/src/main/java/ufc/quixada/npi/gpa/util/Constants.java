package ufc.quixada.npi.gpa.util;

public class Constants {

	// LDAP
	public static final String LDAP_URL = "ldap.url";
	public static final String LDAP_BASE = "ldap.base";
	public static final String LDAP_USER = "ldap.user";
	public static final String LDAP_PASSWORD = "ldap.password";
	public static final String LDAP_OU = "ldap.ou";
	public static final String BASE = "ldap.url";

	public static final String LOGIN_INVALIDO = "Usuário e/ou senha inválidos";

	// Model attributes
	public static final String PARECERISTAS = "pareceristas";
	public static final String ACOES_AGUARDANDO_PARECER = "acoesAguardandoParecer";
	public static final String ACOES_AGUARDANDO_PARECERISTA = "acoesAguardandoParecerista";
	public static final String ACOES_AGUARDANDO_RELATO = "acoesAguardandoRelato";
	public static final String ACOES_AGUARDANDO_RELATOR = "acoesAguardandoRelator";
	public static final String ACOES_AGUARDANDO_HOMOLOGACAO = "acoesAguardandoHomologacao";
	public static final String ACOES_HOMOLOGADAS = "acoesHomologadas";
	public static final String ERRO = "erro";

	// Load fragments
	public static final String PAGE_LOAD_PARECERISTAS = "detalhe/acao/detalhes :: pareceristas";

	// Paginas
	public static final String PAGINA_INICIAL = "principal/index";
	public static final String PAGINA_INICIAL_DIRECAO = "direcao/index";

	// Redirects
	public static final String REDIRECT_PAGINA_DETALHES_ACAO = "redirect:/detalhe/acao/";

	// Exception Messages
	public static final String ERRO_ATRIBUIR_PARECERISTA = "Não foi possível atribuir! A ação não está aguardando um parecerista!";
}
