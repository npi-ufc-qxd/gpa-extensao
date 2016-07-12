$(document).ready(function() {
	$(".card").click(function() {
        $(".card").find("i").removeClass("fa-folder-open-o");
        $(".card").find(".small-box").removeClass("bg-blue");

        $(".card").find("i").addClass("fa-folder-o");
        $(".card").find(".small-box").addClass("bg-aqua");

        $(this).find("i").toggleClass("fa-folder-open-o fa-folder-o");
        $(this).find(".small-box").toggleClass("bg-blue bg-aqua");
    });
	
	var ptBR_url = "/gpa-extensao/json/Portuguese-Brasil.json";
	$(".table-acoes").DataTable({
		"filter" : false,
		"paginate" : false,
		"info": false,
		"language" : {
			"url": ptBR_url
		}
	});
});

$(".tramitacao").fadeIn(500);
$(".novos").fadeIn(500);

function showNaoHomologados() {
	$(".tramitacao").fadeIn(500);
	$(".novos").fadeIn(500);
	$(".homologados").hide();
	$(".parecerRelator").hide();
	$(".parecerTecnico").hide();
	$(".participacao").hide();
}
function showHomologados() {
	$(".tramitacao").hide();
	$(".novos").hide();
	$(".homologados").fadeIn(500);
	$(".parecerRelator").hide();
	$(".parecerTecnico").hide();
	$(".participacao").hide();
}
function showParecer() {
	$(".tramitacao").hide();
	$(".novos").hide();
	$(".homologados").hide();
	$(".parecerRelator").fadeIn(500);
	$(".parecerTecnico").fadeIn(500);
	$(".participacao").hide();
}
function showParticipacao() {
	$(".tramitacao").hide();
	$(".novos").hide();
	$(".homologados").hide();
	$(".parecerRelator").hide();
	$(".parecerTecnico").hide();
	$(".participacao").fadeIn(500);
}