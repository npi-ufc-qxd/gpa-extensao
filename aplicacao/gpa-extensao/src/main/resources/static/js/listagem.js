$(document).ready(function() {
	$(".homologados").hide();
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
	$(".parecer").hide();
	$(".participacao").hide();
}
function showHomologados() {
	$(".tramitacao").hide();
	$(".novos").hide();
	$(".homologados").show();
	$(".parecer").hide();
	$(".participacao").hide();
}
function showParecer() {
	$(".tramitacao").hide();
	$(".novos").hide();
	$(".homologados").hide();
	$(".parecer").show();
	$(".participacao").hide();
}
function showParticipacao() {
	$(".tramitacao").hide();
	$(".novos").hide();
	$(".homologados").hide();
	$(".parecer").hide();
	$(".participacao").show();
}