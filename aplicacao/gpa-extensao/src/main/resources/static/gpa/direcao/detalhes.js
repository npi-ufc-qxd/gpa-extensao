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
	
});