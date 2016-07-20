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
			
		case "cadastro-retroativo":
			zerarMenu();
			$("#menu-item-cadastrar-acao").addClass("active");
			break;
			
		case "busca-acao":
			zerarMenu();
			$("#menu-item-busca-acao").addClass("active");
			break;
			
		case "listagem-bolsistas":
			zerarMenu();
			$("#menu-item-listagem-bolsistas").addClass("active");
			break;
			
		default:
			zerarMenu();
			break;
	}
});

function zerarMenu(){
	$(".menu-item").removeClass("active");
}