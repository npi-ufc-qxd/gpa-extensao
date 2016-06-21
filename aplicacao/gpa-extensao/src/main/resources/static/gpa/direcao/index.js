$(document).ready(function() {

	$(".card").click(function() {
		$(".card").find("i").removeClass("fa-folder-open-o");
		$(".card").find(".small-box").removeClass("bg-aqua");

		$(".card").find("i").addClass("fa-folder-o");
		$(".card").find(".small-box").addClass("bg-yellow");

		$(this).find("i").toggleClass("fa-folder-open-o fa-folder-o");
		$(this).find(".small-box").toggleClass("bg-aqua bg-yellow");
	});
	
	$("#parecer-card").on("click", function(){
		$(".parecer").removeClass("hidden");
		$(".relato").addClass("hidden");
		$(".homologacao").addClass("hidden");
		$(".homologado").addClass("hidden");
	})
	
	$("#relato-card").on("click", function(){
		$(".parecer").addClass("hidden");
		$(".relato").removeClass("hidden");
		$(".homologacao").addClass("hidden");
		$(".homologado").addClass("hidden");
	})
	
	$("#homologacao-card").on("click", function(){
		$(".parecer").addClass("hidden");
		$(".relato").addClass("hidden");
		$(".homologacao").removeClass("hidden");
		$(".homologado").addClass("hidden");
	})
	
	$("#homologado-card").on("click", function(){
		$(".parecer").addClass("hidden");
		$(".relato").addClass("hidden");
		$(".homologacao").addClass("hidden");
		$(".homologado").removeClass("hidden");
	})

	$(".table-direcao").DataTable({
		"filter" : false
	});
	
	$(".table-direcao-acoes").DataTable({
		"filter" : false,
		"columnDefs": [
		    {"targets": 5, "orderable": false}
		]
	});
	
	$( "#dataHomologacao" ).datepicker('setDate', new Date());
});