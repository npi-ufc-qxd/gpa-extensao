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
		$(".parecer").show();
		$(".relato").hide();
		$(".homologacao").hide();
		$(".homologado").hide();
	})
	
	$("#relato-card").on("click", function(){
		$(".parecer").hide();
		$(".relato").show();
		$(".homologacao").hide();
		$(".homologado").hide();
	})
	
	$("#homologacao-card").on("click", function(){
		$(".parecer").hide();
		$(".relato").hide();
		$(".homologacao").show();
		$(".homologado").hide();
	})
	
	$("#homologado-card").on("click", function(){
		$(".parecer").hide();
		$(".relato").hide();
		$(".homologacao").hide();
		$(".homologado").show();
	})

	$(".table-direcao").DataTable({
		"filter" : false,
		"paginate" : false
	});
	
	$(".table-direcao-homologadas").DataTable({
		"filter" : false
	});

});