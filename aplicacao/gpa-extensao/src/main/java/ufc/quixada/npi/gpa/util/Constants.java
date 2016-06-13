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
	
	//Mensagens
	public static final String MENSAGEM_ACAO_EXTENSAO_INEXISTENTE = "A Açao de extensão solicitada não foi encontrada";
	public static final String MENSAGEM_PERMISSAO_NEGADA = "Permissão negada";
	public static final String EXCEPTION_ATRIBUIR_PARECERISTA = "Não foi possível atribuir! A ação não está aguardando um parecerista!";

	//Model attributes
	public static final String PARECERISTAS = "pareceristas";
	public static final String ACOES_AGUARDANDO_PARECER = "acoesAguardandoParecer";
	public static final String ACOES_AGUARDANDO_PARECERISTA = "acoesAguardandoParecerista";
	public static final String ACOES_AGUARDANDO_RELATO = "acoesAguardandoRelato";
	public static final String ACOES_AGUARDANDO_RELATOR = "acoesAguardandoRelator";
	public static final String ACOES_AGUARDANDO_HOMOLOGACAO = "acoesAguardandoHomologacao";
	public static final String ACOES_HOMOLOGADAS = "acoesHomologadas";
	public static final String ACAO_EXTENSAO = "acaoextensao";
	public static final String PERMISSAO_COORDENADOR = "coordenador";
	public static final String PERMISSAO_DIRETOR = "diretor";
	public static final String PERMISSAO_PARECERISTA = "parecerista";
	public static final String PERMISSAO_RELATOR = "relator";
	public static final String PERMISSAO_PARTICIPANTE = "participante";
	public static final String PERMISSAO = "permissao";
	public static final String PAPEL_DIRECAO = "direcao";
	
	//Load fragments
	public static final String PAGE_LOAD_PARECERISTAS = "index :: pareceristas";
	
	//Paginas
	public static final String PAGINA_INICIAL = "principal/index";
	public static final String PAGINA_INICIAL_DIRECAO = "direcao/index";
	public static final String REDIRECT_PAGINA_ADICIONAR_PARTICIPACAO = "redirect:/participacoes/";
	public static final String PAGINA_ADICIONAR_PARTICIPACAO = "coordenacao/crud/adicionar-participacao";
	public static final String PAGINA_CRIAR_PARCERIA_EXTERNA = "coordenacao/crud/criar-parceria-externa";
	public static final String PAGINA_LISTAR_ACAO_EXTENSAO = "acaoextensao/listar";
	public static final String PAGINA_DETALHES_ACAO_EXTENSAO = "detalhes/acao/detalhes";
	public static final String REDIRECT_PAGINA_LISTAR_ACAO_EXTENSAO = "redirect:/acaoextensao";
	public static final String PAGINA_LISTAR_PARTICIPACOES = "coordenacao/crud/listar-participacoes";
	public static final String REDIRECT_PAGINA_DETALHES_ACAO = "redirect:/detalhe/acao/";
	
	//Exceptions
	public static final String USUARIO_NAO_ENCONTRADO_EXCEPTION = "Usuário não encontrado";
	
	//Mensagens
	public static final String ERROR_PESSOA_JA_PARTICIPANTE = "acaoExtensao.pessoaParticipante";
	public static final String ERRO = "erro";
	public static final String MESSAGE_CADASTRO_SUCESSO = "Cadastrado com sucesso!";
	public static final String MESSAGE_CADASTRO_ERROR = "Não foi possível cadastrar!";
	public static final String MESSAGE_STATUS_RESPONSE = "status";
	public static final String ACAO_EXTENSAO_ID = "acaoExtensaoId";
	public static final String PARCEIROS = "parceiros";
	
	public static final String RESPONSE_DATA = "result";
}