package ufc.quixada.npi.gpa.util;

public class Constants {

	// LDAP
	public static final String LDAP_URL = "ldap.url";
	public static final String LDAP_BASE = "ldap.base";
	public static final String LDAP_USER = "ldap.user";
	public static final String LDAP_PASSWORD = "ldap.password";
	public static final String LDAP_OU = "ldap.ou";

	// Values
	public static final Integer CARGA_HORARIA_12 = 12;
	public static final String CADASTRAR_FREQUENCIA = "cadastrar";
	public static final String REMOVER_FREQUENCIA = "remover";

	// Mensagens
	public static final String MENSAGEM_ACAO_EXTENSAO_INEXISTENTE = "A Açao de extensão solicitada não foi encontrada";
	public static final String MENSAGEM_PERMISSAO_NEGADA = "Permissão negada";
	public static final String LOGIN_INVALIDO = "Usuário e/ou senha inválidos";

	// Model attributes
	public static final String POSSIVEIS_COORDENADORES = "possiveisCoordenadores";
	public static final String PESSOA_LOGADA = "pessoaLogada";
	public static final String PARECERISTAS = "pareceristas";
	public static final String PENDENCIA = "pendencia";
	public static final String MODALIDADES = "modalidades";
	public static final String ACOES = "acoes";
	public static final String PARTICIPACOES = "participacoes";
	public static final String ACOES_AGUARDANDO_PARECER = "acoesAguardandoParecer";
	public static final String ACOES_AGUARDANDO_PARECERISTA = "acoesAguardandoParecerista";
	public static final String ACOES_AGUARDANDO_RELATO = "acoesAguardandoRelato";
	public static final String ACOES_AGUARDANDO_RELATOR = "acoesAguardandoRelator";
	public static final String ACOES_AGUARDANDO_HOMOLOGACAO = "acoesAguardandoHomologacao";
	public static final String ACOES_HOMOLOGADAS = "acoesHomologadas";
	public static final String ACAO_EXTENSAO = "acaoExtensao";
	public static final String PARCERIAS_EXTERNAS = "parceriasExternas";
	public static final String PERMISSAO_COORDENADOR = "coordenador";
	public static final String PERMISSAO_DIRETOR = "diretor";
	public static final String PERMISSAO_PARECERISTA = "parecerista";
	public static final String PERMISSAO_RELATOR = "relator";
	public static final String PARTICIPANTE = "participante";
	public static final String BOLSISTA = "bolsista";
	public static final String PERMISSAO = "permissao";
	public static final String ACOES_DIRECAO_SIZE = "acoesDirecaoSize";
	public static final String ACOES_COORDENADOR_SIZE = "acoesCoordenadorSize";
	public static final String ACOES_TRAMITACAO = "acoesTramitacao";
	public static final String ACOES_NOVAS = "acoesNovas";
	public static final String ACOES_PARECER_RELATOR = "acoesParecerRelator";
	public static final String ACOES_PARECER_TECNICO = "acoesParecerTecnico";
	public static final String ACTION = "action";
	public static final String SUBMETER = "submeter";
	public static final String BUSCAR = "buscar";
	public static final String ACOES_PARTICIPACAO = "acoesParticipacao";
	public static final String PENDENCIAS = "pendencias";
	public static final String ANO_ATUAL = "anoAtual";
	public static final String ANOS = "anos";
	public static final String ALUNO = "aluno";
	public static final String SERVIDOR = "servidor";
	public static final String SERVIDORES = "servidores";
	public static final String BOLSAS = "bolsas";
	public static final String DEDICACAO = "dedicacao";
	public static final String DEDICACOES = "dedicacoes";
	public static final String PARCEIRO = "parceiro";
	public static final String PARCERIA_EXTERNA = "parceriaExterna";
	public static final String NOVA_PARTICIPACAO = "novaParticipacao";
	public static final String NOVA_BOLSA = "novaBolsa";
	public static final String ACOES_VINCULO = "acoesParaVinculo";
	public static final String FUNCOES = "funcoes";
	public static final String TIPOS = "tipos";
	public static final String INSTITUICOES = "instituicoes";
	public static final String PENDENTE = "pendente";
	public static final String ERROR_UPPERCASE = "ERROR";
	public static final String OK_UPPERCASE = "OK";
	public static final String COORDENADORES = "coordenadores";
	public static final String FREQUENCIAS = "frequencias";
	public static final String CPF_COORDENADOR = "cpfCoordenador";
	public static final String ESTADO = "estado";
	public static final String CURSOS = "cursos";
	public static final String USUARIO = "usuario";
	public static final String ANO = "ano";
	public static final String STATUS = "status";

	// Paginas
	public static final String PAGINA_INICIAL = "principal/index";
	public static final String PAGINA_INICIAL_DIRECAO = "direcao/index";
	public static final String PAGINA_ADICIONAR_PARTICIPACAO = "coordenacao/crud/adicionar-participacao";
	public static final String PAGINA_DETALHES_ACAO_EXTENSAO = "detalhes/acao/detalhes";
	public static final String PAGINA_LISTAR_PARTICIPACOES = "coordenacao/crud/listar-participacoes";
	public static final String PAGINA_LISTAR_ACOES_DIRECAO = "direcao/listagem";
	public static final String PAGINA_HOMOLOGACAO_ACAO_EXTENSAO = "direcao/homologacao";
	public static final String PAGINA_CRIAR_PARCERIA_EXTERNA = "coordenacao/crud/criar-parceria-externa";
	public static final String PAGINA_SUBMETER_ACAO_EXTENSAO = "coordenacao/submissao/submeter";
	public static final String PAGINA_CADASTRO_RETROATIVO_ACAO = "administracao/cadastro-acao/cadastro";
	public static final String PAGINA_BUSCAR_ACAO_EXTENSAO = "buscar/acao-extensao";
	public static final String PAGINA_ACAO_EXTENSAO = "/acao-extensao";
	public static final String PAGINA_LISTAGEM_BOLSISTAS = "administracao/bolsa/bolsistas";
	public static final String PAGINA_DETALHES_BOLSISTA = "/detalhes/bolsista/detalhes-bolsista";
	public static final String PAGINA_CADASTRO_ALUNO = "/administracao/cadastro-aluno/cadastro-aluno";
	public static final String PAGINA_DETALHES_SERVIDOR = "/detalhes/servidor/detalhes-servidor";
	public static final String PAGINA_CADASTRO_SERVIDOR = "/administracao/cadastro-servidor/cadastro-servidor";
	public static final String PAGINA_VINCULAR_PAPEIS = "/administracao/papeis/vincular-papeis";
	
	//fragments
	public static final String FRAGMENTS_TABLE_PARTICIPACOES = "detalhes/acao/table-participacoes :: participacoesList";
	public static final String FRAGMENTS_TABLE_BOLSAS = "detalhes/acao/table-bolsas :: bolsasList";
	public static final String FRAGMENTS_TABLE_PARCERIAS_EXTERNAS = "detalhes/acao/table-parceirias :: table-parceria-externa";
	public static final String FRAGMENTS_TABLE_LISTAGEM_BOLSISTAS = "administracao/bolsa/bolsistas :: table-listagem-bolsistas";
	public static final String FRAGMENTS_INFO_ALUNO = "administracao/cadastro-aluno/cadastro-aluno :: info-cadastro-aluno";
	public static final String FRAGMENTS_INFO_SERVIDOR = "administracao/cadastro-servidor/cadastro-servidor :: info-cadastro-servidor";

	// Redirects
	public static final String REDIRECT_PAGINA_DETALHES_ACAO = "redirect:/detalhes/";
	public static final String REDIRECT_PAGINA_ADICIONAR_PARTICIPACAO = "redirect:/participacoes/";
	public static final String REDIRECT_PAGINA_INICIAL_COORDENACAO = "redirect:/listagem";
	public static final String REDIRECT_PAGINA_INICIAL_DIRECAO = "redirect:/direcao/";
	public static final String REDIRECT_PAGINA_INICIAL_ADMINISTRACAO = "redirect:/buscar/acao-extensao";
	public static final String REDIRECT_PAGINA_INICIAL = "redirect:/";
	public static final String REDIRECT_PAGINA_BUSCAR_ACAO_EXTENSAO = "redirect:/buscar/acao-extensao";
	public static final String REDIRECT_PAGINA_CADASTRO_ALUNOS = "redirect:/admin/alunos";
	public static final String REDIRECT_PAGINA_CADASTRO_SERVIDORES = "redirect:/admin/servidores";
	public static final String REDIRECT_PAGINA_DETALHES_SERVIDOR = "redirect:/buscar/servidor/";
	public static final String REDIRECT_PAGINA_DETALHES_BOLSISTA = "redirect:/bolsa/detalhes/";

	// Exception Messages
	public static final String EXCEPTION_ATRIBUIR_PARECERISTA = "Não foi possível atribuir! A ação não está aguardando um parecerista!";
	public static final String EXCEPTION_RELATORIO = "Não foi possível emitir o relatório! Tente novamente.";
	public static final String EXCEPTION_SALVAR_ARQUIVO = "Falha ao salvar o arquivo! Tente novamente.";
	public static final String EXCEPTION_BUSCAR_ARQUIVO = "Falha ao buscar arquivo";
	public static final String USUARIO_NAO_ENCONTRADO_EXCEPTION = "Usuário não encontrado";
	public static final String EXCEPTION_PARECERISTA_DA_EQUIPE = "Não foi possível atribuir! O parecerista faz parte da equipe!";
	public static final String EXCEPTION_FALHA_ATRIBUIR_FREQUENCIA = "Falha ao atribuir frequência";
	public static final String EXCEPTION_ALUNO_JA_CADASTRADO = "O aluno já está cadastrado";
	public static final String EXCEPTION_SERVIDOR_JA_CADASTRADO = "O servidor já está cadastrado";

	// Mensagens
	public static final String ERRO = "erro";
	public static final String SUCESSO = "sucesso";
	public static final String ALERTA_PARECER = "alertaParecer";
	public static final String ALERTA_RELATO = "alertaRelato";
	public static final String MESSAGE = "message";
	public static final String MESSAGE_PARECERISTA_NAO_ATRIBUIDO = "O parecerista ainda não foi atribuído!";
	public static final String MESSAGE_RELATOR_NAO_ATRIBUIDO = "O relator ainda não foi atribuído!";
	public static final String MESSAGE_ACAO_ENCERRADA = "Acão encerrada com sucesso!";
	public static final String MESSAGE_CADASTRO_SUCESSO = "Cadastrado com sucesso!";
	public static final String MESSAGE_EDITADO_SUCESSO = "Editado com sucesso!";
	public static final String MESSAGE_CADASTRO_ERROR = "Não foi possível cadastrar!";
	public static final String MESSAGE_EDITAR_ERROR = "Não foi possível editar!";
	public static final String MESSAGE_SUBMETER_ERROR = "Não foi possível submeter!";
	public static final String MESSAGE_STATUS_RESPONSE = "status";
	public static final String MESSAGE_SUBMISSAO = "Submetido com sucesso!";
	public static final String MESSAGE_ANEXO_RESPONSE = "anexo-message";
	public static final String MESSAGE_ANEXO = "Necessário fazer upload do documento!";
	public static final String ACAO_EXTENSAO_ID = "acaoExtensaoId";
	public static final String PARCEIROS = "parceiros";
	public static final String RESPONSE_DATA = "result";
	public static final String VALOR_INVALIDO = "Valor inválido!";
	public static final String A_DEFINIR = "a definir";
	public static final String MESSAGE_SALVAR_ARQUIVO_ERROR = "Erro ao salvar o arquivo!";
	public static final String MESSAGE_DATA_ANTERIOR = "A data de inicio deve anteceder a data de termino";

	// Errors
	public static final String ERROR_PESSOA_JA_PARTICIPANTE = "Essa pessoa já participa desse projeto";
	public static final String ERROR_ALUNO_JA_BOLSISTA = "Esse aluno já é bolsista desse projeto";
	public static final String ERROR_ALUNO_JA_BOLSISTA_OUTRO_PROJETO = "Esse aluno já é bolsista remunerado de outro projeto";
	public static final String ERROR_QUANTIDADE_BOLSAS_EXEDIDAS = "Esse projeto já alcançou o máximo de bolsas recebidas";
	public static final String ERROR_INFORMAR_BOLSAS_RECEBIDAS = "É necessário informar a quantidade de bolsas recebidas";

	// Tipos de arquivos
	public static final String PDF = "application/pdf";

	// e-mail properties
	public static final String EMAIL_REMETENTE = "naoresponda@gpaextensao.ufc.br";
	public static final String ASSUNTO_EMAIL = "GPA Extensão - #TITULO_ACAO#";
	public static final String EMAIL_DIRECAO_SUBMISSAO = "A ação #TITULO_ACAO# do servidor #NOME_PESSOA# foi submetida e aguarda a atribuição do parecerista técnico.";
	public static final String EMAIL_COORDENACAO_ATRIBUICAO_PARECERISTA = "#NOME_PESSOA# foi atribuído(a) para emitir um parecer sobre sua ação: #TITULO_ACAO#.";
	public static final String EMAIL_PARECERISTA_ATRIBUICAO_PARECERISTA = "A ação #TITULO_ACAO# foi atribuída para sua apreciação, com prazo até #PRAZO#.";
	public static final String EMAIL_COORDENACAO_EMISSAO_PARECER = "O parecer técnico foi emitido por #NOME_PESSOA# para a sua ação: #TITULO_ACAO#.";
	public static final String EMAIL_DIRECAO_EMISSAO_PARECER = "O parecer técnico para a ação #TITULO_ACAO# foi emitido por #NOME_PESSOA#. A ação aguarda a atribuição do relator.";
	public static final String EMAIL_COORDENACAO_SOLICITACAO_RESOLUCAO_PENDENCIAS = "Foi solicitado que pendências existentes na ação #TITULO_ACAO# sejam resolvidas.";
	public static final String EMAIL_PARECERISTA_RESOLUCAO_PENDENCIAS = "A ação #TITULO_ACAO# do servidor #NOME_PESSOA# passou pela resolução de pendências, e aguarda pelo parecer.";
	public static final String EMAIL_COORDENACAO_ATRIBUICAO_RELATOR = "#NOME_PESSOA# foi encarregado(a) de emitir uma avaliação sobre a sua ação: #TITULO_ACAO#.";
	public static final String EMAIL_RELATOR_ATRIBUICAO_RELATOR = "Você foi atribuído(a) como relator(a) da ação #TITULO_ACAO#, com prazo até #PRAZO#. Sua avaliação sobre a mesma é aguardada.";
	public static final String EMAIL_COORDENACAO_EMISSAO_RELATO = "A avaliação para a sua ação #TITULO_ACAO# foi emitida por #NOME_PESSOA#.";
	public static final String EMAIL_DIRECAO_EMISSAO_RELATO = "A avaliação para a ação #TITULO_ACAO# foi emitida por #NOME_PESSOA#. A ação aguarda a homologação.";
	public static final String EMAIL_RELATOR_RESOLUCAO_PENDENCIAS = "A ação #TITULO_ACAO# do servidor #NOME_PESSOA# passou pela resolução de pendências, e aguarda pela sua avaliação.";
	public static final String EMAIL_COORDENACAO_HOMOLOGACAO = "A sua ação #TITULO_ACAO# foi homologada com o status de #STATUS#.";

	// e-mail variable properties
	public static final String EMAIL_TITULO_ACAO = "#TITULO_ACAO#";
	public static final String EMAIL_NOME_PESSOA = "#NOME_PESSOA#";
	public static final String EMAIL_PRAZO = "#PRAZO#";
	public static final String EMAIL_STATUS = "#STATUS#";

	/**
	 * Caminho da pasta de uploads no sistema de arquivos local.
	 * 
	 * Pode ser necessário personalizar para fins de teste, mas ao fazer o
	 * commit deve-se deixar o caminho original.
	 */

	// Homologação
	public static final String PASTA_DOCUMENTOS_GPA = "/mnt/gpa-extensao-uploads";
}