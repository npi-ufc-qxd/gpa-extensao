$(document).ready(function(){
	var data = $("html").data("page");
	
	switch (data) {
		case "index-coordenacao":
			zerarMenu();
			$("#menu-item-acoes-coordenacao").addClass("active");
			break;

		case "index-direcao":
			zerarMenu();
			$("#menu-item-acoes-direcao").addClass("active");
			break;
			
		case "cadastrar-acao":
			zerarMenu();
			$("#menu-item-cadastrar-acao").addClass("active");
			break;
			
		case "cadastrar-acao-retroativa":
			zerarMenu();
			$("#menu-item-cadastrar-acao-retroativa").addClass("active");
			break;
		
		case "buscar-acao":
			zerarMenu();
			$("#menu-item-buscar-acoes").addClass("active");
			break;
			
		case "buscar-servidores":
			zerarMenu();
			$("#menu-item-buscar-servidores").addClass("active");
			break;

		case "listagem-bolsistas":
			zerarMenu();
			$("#menu-item-listagem-bolsistas").addClass("active");
			break;
			
		case "cadastrar-aluno":
			zerarMenu();
			$("#menu-item-cadastro-alunos").addClass("active");
			break;
			
		case "cadastrar-servidor":
			zerarMenu();
			$("#menu-item-cadastro-servidores").addClass("active");
			break;
			
		case "papeis-usuarios":
			zerarMenu();
			$("#menu-item-pepeis-usuarios").addClass("active");
			break;
			
		case "detalhe-acao":
			zerarMenu();
			$("#menu-item-buscar-acoes").addClass("active");
			break;
			
		case "detalhe-bolsista":
			zerarMenu();
			$("#menu-item-listagem-bolsistas").addClass("active");
			break;
		
		case "detalhe-servidor":
			zerarMenu();
			$("#menu-item-buscar-servidores").addClass("active");
			break;
			
		default:
			zerarMenu();
			break;
	}
});

function zerarMenu(){
	$(".menu-item").removeClass("active");
}