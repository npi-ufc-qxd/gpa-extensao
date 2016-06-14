$(document).ready(function() {
	$(".RESOLVENDO_PENDENCIAS_PARECER").addClass("bg-yellow");
	$(".RESOLVENDO_PENDENCIAS_RELATO").addClass("bg-yellow");
	$(".homologados").hide();
	$(".parecerRelator").hide();
	$(".parecerTecnico").hide();
	$(".participacao").hide();
	$(".card").click(function() {
        $(".card").find("i").removeClass("fa-folder-open-o");
        $(".card").find(".small-box").removeClass("bg-blue");

        $(".card").find("i").addClass("fa-folder-o");
        $(".card").find(".small-box").addClass("bg-aqua");

        $(this).find("i").toggleClass("fa-folder-open-o fa-folder-o");
        $(this).find(".small-box").toggleClass("bg-blue bg-aqua");
    });
	$(".table-acoes").DataTable({
		"filter" : false
	});
});
function showNaoHomologados() {
	$(".tramitacao").show();
	$(".novos").show();
	$(".homologados").hide();
	$(".parecerRelator").hide();
	$(".parecerTecnico").hide();
	$(".participacao").hide();
}
function showHomologados() {
	$(".tramitacao").hide();
	$(".novos").hide();
	$(".homologados").show();
	$(".parecerRelator").hide();
	$(".parecerTecnico").hide();
	$(".participacao").hide();
}
function showParecer() {
	$(".tramitacao").hide();
	$(".novos").hide();
	$(".homologados").hide();
	$(".parecerRelator").show();
	$(".parecerTecnico").show();
	$(".participacao").hide();
}
function showParticipacao() {
	$(".tramitacao").hide();
	$(".novos").hide();
	$(".homologados").hide();
	$(".parecerRelator").hide();
	$(".parecerTecnico").hide();
	$(".participacao").show();
}