$( document ).ready(function() {
	
	$(".card").click(function() {
		$(".card").find("i").removeClass("fa-folder-open-o");
		$(".card").find(".small-box").removeClass("bg-aqua");
		
		$(".card").find("i").addClass("fa-folder-o");
		$(".card").find(".small-box").addClass("bg-yellow");
		
		$(this).find("i").toggleClass("fa-folder-open-o fa-folder-o");
		$(this).find(".small-box").addClass("bg-aqua bg-yellow");
	});
	
	$("#table-acoes-parecer").DataTable();

});