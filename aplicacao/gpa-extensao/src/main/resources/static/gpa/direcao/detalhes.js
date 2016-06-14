$(document).ready(function(){
	
	$("#select-pareceristas").select2();
	
	$(".prazo-parecer").datepicker({
	    format: "dd/mm/yyyy",
	    startDate: "+1d",
	    clearBtn: true,
	    language: "pt-BR"
	});
	
});