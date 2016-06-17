$(document).ready(function(){
	
	$(".select-parecer").select2();
	
	$(".prazo-parecer").datepicker({
	    format: "dd/mm/yyyy",
	    startDate: "+1d",
	    clearBtn: true,
	    language: "pt-BR",
	    autoclose: true
	});
	
	$("#alterar-parecerista-button").on("click", function(){
		$("#parecer-tecnico-info").hide();
		$("#parecer-tecnico-form").show(1000);
	});
	
	$("#alterar-relator-button").on("click", function(){
		$("#parecer-relator-info").hide();
		$("#parecer-relator-form").show(1000);
	});
	
	$("#cancelar-parecer-button").on("click", function(){
		$("#parecer-tecnico-form").hide();
		$("#parecer-tecnico-info").show(1000);
	});
	
	$("#cancelar-relato-button").on("click", function(){
		$("#parecer-relator-form").hide();
		$("#parecer-relator-info").show(1000);
	});
	
});