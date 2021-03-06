package ufc.quixada.npi.gpa.util;

public class Constants {

	// LDAP
	public static final String LDAP_URL = "ldap.url";
	public static final String LDAP_BASE = "ldap.base";
	public static final String LDAP_USER = "ldap.user";
	public static final String LDAP_PASSWORD = "ldap.password";
	public static final String LDAP_OU = "ldap.ou";

	// Permissões
	public static final String PERMISSAO_SERVIDOR = "hasAuthority('SERVIDOR')";
	public static final String PERMISSAO_ADMIN = "hasAuthority('ADMINISTRACAO')";
	public static final String PERMISSAO_COORDENADORIA = "hasAuthority('COORDENADORIA')";
	public static final String PERMISSAO_ADMIN_COORDENADORIA = "hasAnyAuthority('ADMINISTRACAO,COORDENADORIA')";

	// Values
	public static final Integer CARGA_HORARIA_12 = 12;
	public static final String CADASTRAR_FREQUENCIA = "cadastrar";
	public static final String REMOVER_FREQUENCIA = "remover";

	// Mensagens
	public static final String MENSAGEM_ACAO_EXTENSAO_INEXISTENTE = "A Açao de extensão solicitada não foi encontrada";
	public static final String MENSAGEM_PERMISSAO_NEGADA = "Permissão negada";
	public static final String LOGIN_INVALIDO = "Usuário e/ou senha inválidos";


	public static final String MENSAGEM_TRANSFERENCIA_MESMO_COORDENADOR = "Não é possivel transferir coordenação para o mesmo coordenador";

	public static final String MENSAGEM_DATA_IGUAL_MAIOR = "A data de inicio não pode ser igual ou posterior que a data de termino da ação";
	public static final String MENSAGEM_DATA_MENOR = "A data de inicio não pode ser menor que a data de inicio da ação";	
	public static final String MENSAGEM_DATA_HOMOLOGACAO_MAIOR = "A data de homologação não pode maior que a data de termino da ação";
	public static final String MENSAGEM_DATA_HOMOLOGACAO_MENOR = "A data de homologação não pode ser menor que a data de inicio da ação";
	
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
	public static final String PAGINA_LISTAGEM_BOLSISTAS = "administracao/bolsa/bolsistas";
	public static final String PAGINA_DETALHES_BOLSISTA = "/detalhes/bolsista/detalhes-bolsista";
	public static final String PAGINA_CADASTRO_ALUNO = "/administracao/cadastro-aluno/cadastro-aluno";
	public static final String PAGINA_DETALHES_SERVIDOR = "/detalhes/servidor/detalhes-servidor";
	public static final String PAGINA_CADASTRO_SERVIDOR = "/administracao/cadastro-servidor/cadastro-servidor";
	public static final String PAGINA_VINCULAR_PAPEIS = "/administracao/papeis/vincular-papeis";
	public static final String PAGINA_ERRO_404 = "/error/404";
	public static final String PAGINA_ERRO_403 = "/error/403";
	public static final String PAGINA_ERRO_500 = "/error/500";

	// fragments
	public static final String FRAGMENTS_TABLE_PARTICIPACOES = "detalhes/acao/table-participacoes :: participacoesList";
	public static final String FRAGMENTS_TABLE_BOLSAS = "detalhes/acao/table-bolsas :: bolsasList";
	public static final String FRAGMENTS_TABLE_PARCERIAS_EXTERNAS = "detalhes/acao/table-parceirias :: table-parceria-externa";
	public static final String FRAGMENTS_TABLE_LISTAGEM_BOLSISTAS = "administracao/bolsa/bolsistas :: table-listagem-bolsistas";
	public static final String FRAGMENTS_INFO_ALUNO = "administracao/cadastro-aluno/cadastro-aluno :: info-cadastro-aluno";
	public static final String FRAGMENTS_INFO_SERVIDOR = "administracao/cadastro-servidor/cadastro-servidor :: info-cadastro-servidor";

	// Redirects

	public static final String REDIRECT_PAGINA_DETALHES_ACAO = "redirect:/acoes/";
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
	public static final String REDIRECT_PAGINA_MINHAS_ACOES = "redirect:/acoes/minhas/minhas-acoes";
	public static final String REDIRECT_PAGINA_CADASTAR_ACAO = "redirect:/acoes/cadastrar";
	public static final String REDIRECT_PAGINA_EDITAR_ACAO = "redirect:/acoes/editar/";
	public static final String REDIRECT_PAGINA_RESOLVER_PENDENCIAS = "redirect:/acoes/resolver-pendencia/";
	
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

	public static final String EXCEPTION_GET_ANEXO = "Não foi possível encontrar o anexo";
	public static final String EXCEPTION_DELETAR_ARQUIVO = "Não foi possível deletar o arquivo";	
	public static final String EXCEPTION_DATA_INVALIDA = "A data inserida é inválida";
	public static final String EXCEPTION_ACAO_JA_ENCERRADA = "A ação selecionada já está encerrada";
	public static final String EXCEPTION_ACAO_NAO_ENCONTRADA = "A ação não foi encontrada";
	public static final String EXCEPTION_ACAO_SEM_BOLSAS_RECEBIDAS = "Essa ação ainda não recebeu bolsas!";
	public static final String EXCEPTION_ACAO_MAXIMO_BOLSISTAS = "Essa ação já possui o número máximo de bolsistas";
	
	public static final String EXCEPTION_ADICAO_PARCERIA_NAO_PERMITIDA = "Para o status atual da ação, não é permitida a adição de parceria externa";
	public static final String EXCEPTION_EXCLUSAO_PARCERIA_NAO_PERMITIDA = "Para o status atual da ação, não é permitida a exclusão de parceria externa";
	
	public static final String EXCEPTION_STATUS_ACAO_NAO_PERMITE_BOLSISTAS = "Para o status atual da ação, não é permitida a inclusão de bolsistas";
	public static final String EXCEPTION_ACAO_ENCERRADA = "A ação está encerrada";
	public static final String EXCEPTION_STATUS_ACAO_NAO_PERMITE_EXCLUSAO_BOLSISTAS = "Para o status atual da ação, não é permitida a exclusão de bolsistas";
	public static final String EXCEPTION_STATUS_ACAO_NAO_PERMITE_EXCLUSAO_PARCEIRO = "Para o status atual da ação, não é permitida a exclusão de participantes";
	public static final String EXCEPTION_STATUS_ACAO_NAO_PERMITE_ALTERACAO_TEMPO_PARTICIPACAO = "Para o status atual da ação, não é permitida a alteração no tempo de participação";
	public static final String EXCEPTION_STATUS_ACAO_NAO_PERMITE_ENCERRAMENTO = "Para o status atual da ação, não é permitido encerrá-la!";
	public static final String EXCEPTION_COORDENADOR_ACAO_NAO_PODE_SER_EXCLUIDO = "O coordenador da ação não pode ser excluído da participação.";

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
	public static final String CAMPO_OBRIGATORIO_VAZIO = "Um campo obrigatório não pode ser vazio!";
	public static final String A_DEFINIR = "a definir";
	public static final String MESSAGE_SALVAR_ARQUIVO_ERROR = "Erro ao salvar o arquivo!";
	public static final String MESSAGE_DATA_ANTERIOR = "A data de inicio deve anteceder a data de termino";

	public static final String STATUS_MESSAGE_SUCCESS = "success";
	public static final String TITULO_MESSAGE_ACAO_CADASTRADA = "Ação Cadastrada!";
	public static final String TITULO_MESSAGE_ACAO_EDITADA = "Ação Editada!";
	public static final String TITULO_MESSAGE_ACAO_EXCLUIDA = "Ação Excluída!";
	public static final String TITULO_MESSAGE_ACAO_SUBMETIDA = "Ação Submetida!";
	public static final String TITULO_MESSAGE_ACAO_HOMOLOGADA = "Ação Homologada!";
	public static final String TITULO_MESSAGE_ACAO_ENCERRADA = "Ação Encerrada!";
	public static final String TITULO_MESSAGE_CADASTRAR_CODIGO = "Código Cadastrado!";
	public static final String TITULO_MESSAGE_RESOLVER_PENDENCIAS = "Pendências Resolvidas!";
	public static final String TITULO_MESSAGE_TRANSFERIR_COORDENACAO = "Coordenação Transferida!";
	public static final String TITULO_MESSAGE_BOLSA_RECEBIDA = "Bolsas Inseridas!";
	public static final String TITULO_MESSAGE_PARTICIPACAO_ADICIONADA = "Participação Adicionada!";
	public static final String TITULO_MESSAGE_PARTICIPACAO_ALTERADA = "Participação Alterada!";
	public static final String TITULO_MESSAGE_BOLSISTA_ADICIONADO = "Bolsista Adicionado!";
	public static final String TITULO_MESSAGE_BOLSISTA_ALTERADO ="Bolsista Alterado!";
	public static final String TITULO_MESSAGE_PARCERIA_ADICIONADO = "A Parceria foi adicionada!";
	public static final String TITULO_MESSAGE_PARECERISTA_ADICIONADO = "O Parecerista foi adicionado!";
	public static final String TITULO_MESSAGE_PARECERISTA_ALTERADO ="O Parecerista foi alterado!";
	public static final String TITULO_MESSAGE_RELATOR_ADICIONADO ="O Relator foi adicionado!";
	public static final String TITULO_MESSAGE_RELATOR_ALTERADO ="O Relator foi alterado!";
	public static final String TITULO_MESSAGE_PARECER_EMITIDO = "O Parecer foi emitido!";
	public static final String TITULO_MESSAGE_EMITIR_SOLICITACAO_PENDENCIAS = "A Solicitação foi encaminhada!";
	
	
	public static final String CONTEUDO_MESSAGE_ACAO_CADASTRADA = "A ação foi cadastrada com sucesso.";
	public static final String CONTEUDO_MESSAGE_ACAO_EDITADA = "A ação foi editada com sucesso.";
	public static final String CONTEUDO_MESSAGE_ACAO_EXCLUIDA = "A ação foi excluída com sucesso.";
	public static final String CONTEUDO_MESSAGE_ACAO_SUBMETIDA = "A ação foi submetida com sucesso.";
	public static final String CONTEUDO_MESSAGE_ACAO_HOMOLOGADA = "A ação foi homologada com sucesso.";
	public static final String CONTEUDO_MESSAGE_ACAO_ENCERRADA = "A ação foi encerrada com sucesso.";
	public static final String CONTEUDO_MESSAGE_CADASTRAR_CODIGO = "O código da ação foi inserido com sucesso.";
	public static final String CONTEUDO_MESSAGE_RESOLVER_PENDENCIAS = "As pendências foram resolvidas com sucesso.";
	public static final String CONTEUDO_MESSAGE_TRANSFERIR_COORDENACAO = "A coordenação da ação foi transferida com sucesso.";
	public static final String CONTEUDO_MESSAGE_BOLSA_RECEBIDA = "As bolsas foram inseridas com sucesso.";
	public static final String CONTEUDO_MESSAGE_PARTICIPACAO_ADICIONADA = "A participação foi adicionada com sucesso.";
	public static final String CONTEUDO_MESSAGE_PARTICIPACAO_ALTERADA = "A participação foi alterado com sucesso.";
	public static final String CONTEUDO_MESSAGE_BOLSISTA_ADICIONADO = "O bolsista foi adicionado com sucesso.";
	public static final String CONTEUDO_MESSAGE_BOLSISTA_ALTERADO = "O bolsista foi alterado com sucesso.";
	public static final String CONTEUDO_MESSAGE_PARCERIA_ADICIONADO = "A parceria foi adicionada com sucesso.";
	public static final String CONTEUDO_MESSAGE_PARECERISTA_ADICIONADO = "O parecerista foi adicionado com sucesso.";
	public static final String CONTEUDO_MESSAGE_PARECERISTA_ALTERADO = "O parecerista foi alterado com sucesso.";
	public static final String CONTEUDO_MESSAGE_RELATOR_ADICIONADO = "O relator foi adicionado com sucesso.";
	public static final String CONTEUDO_MESSAGE_RELATOR_ALTERADO = "O relator foi alterado com sucesso.";
	public static final String CONTEUDO_MESSAGE_PARECER_EMITIDO = "O parecer foi emitido com sucesso!";
	public static final String CONTEUDO_MESSAGE_EMITIR_SOLICITACAO_PENDENCIAS = "A solicitação para resolver as pendências foi encaminhada com sucesso.";
	
	// Errors
	public static final String ERROR_PARTICIPANTE_NAO_INFORMADO = "O participante não foi informado!";
	public static final String ERROR_QTD_HORAS_NAO_PERMITIDA = "A quantidade de horas informadas não é permitida!";
	public static final String ERROR_PESSOA_JA_PARTICIPANTE = "Essa pessoa já participa desse projeto";
	public static final String ERROR_ALUNO_JA_BOLSISTA = "Esse aluno já é bolsista desse projeto";
	public static final String ERROR_ALUNO_JA_BOLSISTA_OUTRO_PROJETO = "Esse aluno já é bolsista remunerado de outro projeto";
	public static final String ERROR_QUANTIDADE_BOLSAS_EXEDIDAS = "Esse projeto já alcançou o máximo de bolsas recebidas";
	public static final String ERROR_INFORMAR_BOLSAS_RECEBIDAS = "É necessário informar a quantidade de bolsas recebidas";
	public static final String ERROR_DATA_INVALIDA = "A data informada é inválida!";
	public static final String ERROR_ADICIONAR_PARTICIPANTE_NAO_PERMITIDO = "Para o status atual da ação, não é permitido adicionar participantes.";
	public static final String ERROR_PARCEIRO_JA_PARTICIPANTE = "O parceiro escolhido já participa dessa ação!";
	public static final String ERROR_PARTICIPACAO_ACAO_ENCERRADA = "A ação está encerrada";	
	
	public static final String STATUS_MESSAGE_ERROR = "error";
	public static final String TITULO_MESSAGE_ACAO_CADASTRADA_ERROR = "Erro ao cadastrar Ação!";
	public static final String TITULO_MESSAGE_ACAO_SUBMETIDA_ERROR = "Erro ao submeter Ação!";
	public static final String TITULO_MESSAGE_ACAO_EDITADA_ERROR = "Erro ao editar Ação!";
	public static final String TITULO_MESSAGE_ACAO_HOMOLOGADA_ERROR = "Erro ao homologar Ação!";
	public static final String TITULO_MESSAGE_CADASTRAR_CODIGO_ERROR = "Erro ao cadastrar o Código!";
	public static final String TITULO_MESSAGE_BOLSA_RECEBIDA_ERROR = "Erro ao adicionar as Bolsas!";
	public static final String TITULO_MESSAGE_TRANSFERIR_COORDENACAO_ERROR = "Erro ao transferir a Coordenação!";
	public static final String TITULO_MESSAGE_PARTICIPACAO_ADICIONADA_ERROR = "Erro ao adicionar a Participação!";
	public static final String TITULO_MESSAGE_PARTICIPACAO_ALTERADA_ERROR = "Erro ao alterar a Participação!";
	public static final String TITULO_MESSAGE_BOLSISTA_ADICIONADO_ERROR = "Erro ao adicionar o Bolsista!";
	public static final String TITULO_MESSAGE_BOLSISTA_ALTERADO_ERROR = "Erro ao alterar Bolsista";
	public static final String TITULO_MESSAGE_PARCERIA_ADICIONADO_ERROR = "Erro ao adicionar Parceria!";
	public static final String TITULO_MESSAGE_PARECERISTA_ADICIONADO_ERROR = "Erro ao adicionar Parecerista!";
	public static final String TITULO_MESSAGE_PARECERISTA_ALTERADO_ERROR = "Erro ao altera Parecerista!";
	public static final String TITULO_MESSAGE_RELATOR_ADICIONADO_ERROR = "Erro ao adicionar Relator!";
	public static final String TITULO_MESSAGE_RELATOR_ALTERADO_ERROR = "Erro ao alterar Relator!";
	public static final String TITULO_MESSAGE_PARECER_EMITIDO_ERROR = "Erro ao emitir Parecer";
	public static final String TITULO_MESSAGE_RESOLUCAO_PENDENCIAS_ERROR = "Erro ao resolver as Pendências";
	
	public static final String CONTEUDO_MESSAGE_ACAO_CADASTRADA_ERROR = "Houve um erro ao tentar cadastrar a ação.";
	public static final String CONTEUDO_MESSAGE_ACAO_SUBMETIDA_ERROR = "Houve um erro ao tentar submeter a ação.";
	public static final String CONTEUDO_MESSAGE_ACAO_EDITADA_ERROR = "Houve um erro ao tentar editar a ação.";
	public static final String CONTEUDO_MESSAGE_ACAO_HOMOLOGADA_DATA_MAIOR_ERROR = "A data de homologação não pode ser menor que a data de inicio da ação.";
	public static final String CONTEUDO_MESSAGE_ACAO_HOMOLOGADA_DATA_MENOR_ERROR = "A data de homologação não pode ser menor que a data de inicio da ação.";
	public static final String CONTEUDO_MESSAGE_CADASTRAR_CODIGO_ERROR = "Houve um erro ao tentar cadastrar o código da Ação.";
	public static final String CONTEUDO_MESSAGE_BOLSA_RECEBIDA_ERROR = "Houve um erro ao tentar adicionar as bolsas.";
	public static final String CONTEUDO_MESSAGE_TRANSFERIR_COORDENACAO_DATA_MAIOR_IGUAL_ERROR = "A data de inicio não pode ser igual ou maior que a data de termino da ação.";
	public static final String CONTEUDO_MESSAGE_TRANSFERIR_COORDENACAO_DATA_MENOR_ERROR = "A data de inicio não pode ser menor que a data de inicio da ação.";
	public static final String CONTEUDO_MESSAGE_PARTICIPACAO_STATUS_ERROR = "Não é permitido adicionar participantes para ação com este status.";
	public static final String CONTEUDO_MESSAGE_PARTICIPACAO_DATA_ERROR = "A data informada é inválida.";
	public static final String CONTEUDO_MESSAGE_PARTICIPACAO_PERMISSAO_ERROR = "Você não possui permissão para adicionar participação.";
	public static final String CONTEUDO_MESSAGE_PARTICIPACAO_VALOR_ERROR = "Valor inserirdo no campo função ou Instituição é Inválido.";
	public static final String CONTEUDO_MESSAGE_PARTICIPACAO_QTD_HORAS_ERROR = "A quantidade de horas informadas não é permitida.";
	public static final String CONTEUDO_MESSAGE_PARTICIPACAO_PESSOA_ERROR = "Essa pessoa já participa desse projeto.";
	public static final String CONTEUDO_MESSAGE_PARTICIPAÇÃO_TEMPO_ERROR = "Não é permitida a alteração no tempo da participação para ação com este status.";
	public static final String CONTEUDO_MESSAGE_PARTICIPAÇÃO_ACAO_ENCERRADA_ERROR = "Não é permitido incluir a participação pois a ação já está encerrada";
	public static final String CONTEUDO_MESSAGE_PARTICIPAÇÃO_ALTERACAO_ENCERRADA_ERROR = "Não é permitida a alteração no tempo da participação para ação com este status.";
	public static final String CONTEUDO_MESSAGE_PARTICIPAÇÃO_EXCLUSAO_ENCERRADA_ERROR = "Não é permitido excluir a participação pois a ação já está encerrada";
	public static final String CONTEUDO_MESSAGE_BOLSISTA_DATA_ERROR = "A data informada é inválida.";
	public static final String CONTEUDO_MESSAGE_BOLSISTA_STATUS_ERROR = "Não é permitido adicionar bolsistas para ação com este status.";
	public static final String CONTEUDO_MESSAGE_BOLSISTA_BOLSAS_ERROR = "Essa ação ainda não recebeu bolsas.";
	public static final String CONTEUDO_MESSAGE_BOLSISTA_MAX_ERROR = "Essa ação já possui o número máximo de bolsistas";
	public static final String CONTEUDO_MESSAGE_BOLSISTA_ALUNO_ERROR = "Esse aluno já é bolsista desse projeto";
	public static final String CONTEUDO_MESSAGE_PARCERIA_PERMISSAO_ERROR = "Você não possui permissão para adicionar participação.";
	public static final String CONTEUDO_MESSAGE_PARCERIA_STATUS_ERROR = "Não é permitido adicionar parcerias externas para ação com este status.";
	public static final String CONTEUDO_MESSAGE_PARCERIA_PARCEIRO_ERROR = "O parceiro escolhido já participa dessa ação!";
	public static final String CONTEUDO_MESSAGE_PARECERISTA_EQUIPE_ERROR = "Não foi possível atribuir! O parecerista faz parte da equipe!";
	public static final String CONTEUDO_MESSAGE_PARECERISTA_AGUARDANDO_ERROR = "Não foi possível atribuir! A ação não está aguardando um parecerista!"; 
	public static final String CONTEUDO_MESSAGE_PARECER_EMITIDO_ERROR = "Não foi possível emitir o relatório! Tente novamente.";
	public static final String CONTEUDO_MESSAGE_PARCERIA_ACAO_ENCERRADA_ERROR = "Não é permitido incluir a parceria pois a ação está encerrada";
	public static final String CONTEUDO_MESSAGE_PARCERIA_ACAO_ENCERRADA_EXCLUSAO_ERROR = "Não é permitido excluir a parceria pois a ação está encerrada";
	public static final String CONTEUDO_MESSAGE_BOLSISTA_ADICIONAR_ENCERRADA = "Não é permitido incluir bolsista pois a ação está encerrada";
	public static final String CONTEUDO_MESSAGE_BOLSISTA_ALTERAR_ENCERRADA = "Não é permitido alterar bolsista pois a ação está encerrada";
	
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
	public static final String EMAIL_COORDENACAO_SOLICITACAO_RESOLUCAO_PENDENCIAS = "Foi solicitado que as seguintes pendências:\n#PENDENCIAS#\nexistentes na ação #TITULO_ACAO# sejam resolvidas.";
	public static final String EMAIL_PARECERISTA_RESOLUCAO_PENDENCIAS = "A ação #TITULO_ACAO# do servidor #NOME_PESSOA# passou pela resolução de pendências, e aguarda pelo parecer.";
	public static final String EMAIL_COORDENACAO_ATRIBUICAO_RELATOR = "#NOME_PESSOA# foi encarregado(a) de emitir uma avaliação sobre a sua ação: #TITULO_ACAO#.";
	public static final String EMAIL_RELATOR_ATRIBUICAO_RELATOR = "Você foi atribuído(a) como relator(a) da ação #TITULO_ACAO#, com prazo até #PRAZO#. Sua avaliação sobre a mesma é aguardada.";
	public static final String EMAIL_COORDENACAO_EMISSAO_RELATO = "A avaliação para a sua ação #TITULO_ACAO# foi emitida por #NOME_PESSOA#.";
	public static final String EMAIL_DIRECAO_EMISSAO_RELATO = "A avaliação para a ação #TITULO_ACAO# foi emitida por #NOME_PESSOA#. A ação aguarda a homologação.";
	public static final String EMAIL_RELATOR_RESOLUCAO_PENDENCIAS = "A ação #TITULO_ACAO# do servidor #NOME_PESSOA# passou pela resolução de pendências, e aguarda pela sua avaliação.";
	public static final String EMAIL_COORDENACAO_HOMOLOGACAO = "A sua ação #TITULO_ACAO# foi homologada com o status de #STATUS#.";
	public static final String EMAIL_PARECERISTA_PRAZO = "O prazo para submissão do seu parecer se encerra amanhã, #PRAZO#.";

	// e-mail variable properties
	public static final String EMAIL_TITULO_ACAO = "#TITULO_ACAO#";
	public static final String EMAIL_NOME_PESSOA = "#NOME_PESSOA#";
	public static final String EMAIL_PRAZO = "#PRAZO#";
	public static final String EMAIL_STATUS = "#STATUS#";
	public static final String EMAIL_PENDENCIAS = "#PENDENCIAS#";

	/**
	 * Caminho da pasta de uploads no sistema de arquivos local.
	 * 
	 * Pode ser necessário personalizar para fins de teste, mas ao fazer o
	 * commit deve-se deixar o caminho original.
	 */

	// Homologação

	public static final String PASTA_DOCUMENTOS_GPA = "/mnt/gpa-extensao-uploads";
}