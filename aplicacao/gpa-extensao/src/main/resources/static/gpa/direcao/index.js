$(document).ready(function() {

	$(".card").click(function() {
		$(".card").find("i").removeClass("fa-folder-open-o");
		$(".card").find(".small-box").removeClass("bg-blue");

		$(".card").find("i").addClass("fa-folder-o");
		$(".card").find(".small-box").addClass("bg-aqua");

		$(this).find("i").toggleClass("fa-folder-open-o fa-folder-o");
		$(this).find(".small-box").toggleClass("bg-blue bg-aqua");
	});
	
	$(".parecer").fadeIn(500);
	
	$("#parecer-card").on("click", function(){
		$(".parecer").fadeIn(500);
		$(".relato").hide();
		$(".homologacao").hide();
		$(".homologado").hide();
	})
	
	$("#relato-card").on("click", function(){
		$(".parecer").hide();
		$(".relato").fadeIn(500);
		$(".homologacao").hide();
		$(".homologado").hide();
	})
	
	$("#homologacao-card").on("click", function(){
		$(".parecer").hide();
		$(".relato").hide();
		$(".homologacao").fadeIn(500);
		$(".homologado").hide();
	})
	
	$("#homologado-card").on("click", function(){
		$(".parecer").hide();
		$(".relato").hide();
		$(".homologacao").hide();
		$(".homologado").fadeIn(500);
	})

	var ptBR_url = "/gpa-extensao/json/Portuguese-Brasil.json";
	
	$(".table-direcao").DataTable({
		"filter" : false,
		"paginate" : false,
		"info": false,
		"language" : {
			"url": ptBR_url
		}
	});
	
	$(".table-direcao-homologadas").DataTable({
		"filter" : false,
		"info": false,
		"language" : {
			"url": ptBR_url
		}
	});
	
	$("#dataHomologacao").datepicker({
		format: "dd/mm/yyyy",
	    language: "pt-BR",
	    autoclose: true,
	    todayHighlight: true,
	    maxViewMode: 2,
	    todayBtn: true
	});
});