package ufc.quixada.npi.gpa.util;

public class Constants {

	// LDAP
	public static final String LDAP_URL = "ldap.url";
	public static final String LDAP_BASE = "ldap.base";
	public static final String LDAP_USER = "ldap.user";
	public static final String LDAP_PASSWORD = "ldap.password";
	public static final String LDAP_OU = "ldap.ou";
	public static final String BASE = "ldap.url";

	//Mensagens
	public static final String MENSAGEM_ACAO_EXTENSAO_INEXISTENTE = "A Açao de extensão solicitada não foi encontrada";
	public static final String MENSAGEM_PERMISSAO_NEGADA = "Permissão negada";
	public static final String LOGIN_INVALIDO = "Usuário e/ou senha inválidos";
		
	// Model attributes
	public static final String PARECERISTAS = "pareceristas";
	public static final String RELATORES = "relatores";
	public static final String ACOES_AGUARDANDO_PARECER = "acoesAguardandoParecer";
	public static final String ACOES_AGUARDANDO_PARECERISTA = "acoesAguardandoParecerista";
	public static final String ACOES_AGUARDANDO_RELATO = "acoesAguardandoRelato";
	public static final String ACOES_AGUARDANDO_RELATOR = "acoesAguardandoRelator";
	public static final String ACOES_AGUARDANDO_HOMOLOGACAO = "acoesAguardandoHomologacao";
	public static final String ACOES_HOMOLOGADAS = "acoesHomologadas";
	public static final String ACAO_EXTENSAO = "acaoExtensao";
	public static final String PERMISSAO_COORDENADOR = "coordenador";
	public static final String PERMISSAO_DIRETOR = "diretor";
	public static final String PERMISSAO_PARECERISTA = "parecerista";
	public static final String PERMISSAO_RELATOR = "relator";
	public static final String PERMISSAO_PARTICIPANTE = "participante";
	public static final String PERMISSAO = "permissao";
	public static final String PAPEL_DIRECAO = "direcao";
	public static final String ACOES_DIRECAO_SIZE = "acoesDirecaoSize";
	public static final String ACOES_TRAMITACAO = "acoesTramitacao";
	public static final String ACOES_NOVAS = "acoesNovas";
	public static final String ACOES_PARECER_RELATOR = "acoesParecerRelator";
	public static final String ACOES_PARECER_TECNICO = "acoesParecerTecnico";
	public static final String ACOES_PARTICIPACAO = "acoesParticipacao";
	
	//Paginas
	public static final String PAGINA_INICIAL = "principal/index";
	public static final String PAGINA_CADASTRAR_ACAO_EXTENSAO = "coordenacao/crud/cadastrarExtensao";
	public static final String PAGINA_INICIAL_DIRECAO = "direcao/index";
	public static final String PAGINA_ADICIONAR_PARTICIPACAO = "coordenacao/crud/adicionar-participacao";
	public static final String PAGINA_DETALHES_ACAO_EXTENSAO = "detalhes/acao/detalhes";
	public static final String PAGINA_LISTAR_PARTICIPACOES = "coordenacao/crud/listar-participacoes";
	public static final String PAGINA_CRIAR_PARCERIA_EXTERNA = "coordenacao/crud/criar-parceria-externa";
	public static final String PAGINA_LISTAR_ACOES_COORDENACAO = "coordenacao/listagem/listagem";

	// Redirects
	public static final String REDIRECT_PAGINA_DETALHES_ACAO = "redirect:/detalhes/";
	public static final String REDIRECT_PAGINA_ADICIONAR_PARTICIPACAO= "redirect:/participacoes/";
	public static final String REDIRECT_PAGINA_LISTAR_ACAO_EXTENSAO = "redirect:/coordenacao/listagem";
	public static final String REDIRECT_PAGINA_ACAO_EXTENSAO ="redirect:/detalhes/";
	
	//Exceptions
	public static final String USUARIO_NAO_ENCONTRADO_EXCEPTION = "Usuário não encontrado";
	public static final String ATRIBUIR_PARECERISTA_EXCEPTION = "Não foi possível atribuir! A ação não está aguardando um parecerista!";
	
	//Mensagens
	public static final String ERRO = "erro";
	public static final String ALERTA_PARECER = "alertaParecer";
	public static final String ALERTA_RELATO = "alertaRelato";
	public static final String MESSAGE_PARECERISTA_NAO_ATRIBUIDO = "O parecerista ainda não foi atribuído!";
	public static final String MESSAGE_RELATOR_NAO_ATRIBUIDO = "O relator ainda não foi atribuído!";
	public static final String MESSAGE_CADASTRO_SUCESSO = "Cadastrado com sucesso!";
	public static final String MESSAGE_CADASTRO_ERROR = "Não foi possível cadastrar!";
	public static final String MESSAGE_STATUS_RESPONSE = "status";
	public static final String ACAO_EXTENSAO_ID = "acaoExtensaoId";
	public static final String PARCEIROS = "parceiros";
	public static final String RESPONSE_DATA = "result";
		
	//Errors
	public static final String ERROR_PESSOA_JA_PARTICIPANTE = "acaoExtensao.pessoaParticipante";

}