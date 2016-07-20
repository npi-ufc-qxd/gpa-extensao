$(document).ready(function(){
	
	$(".select-parecer").select2({
		minimumResultsForSearch: -1
	});
	
	$(".select-posicionamento").select2({
		minimumResultsForSearch: -1
	});
	
	$("#pendencias-body").slimScroll({
		height: "250px"
	});
	
	$("#arquivo-parecerista").fileinput({
		'showUpload':false
	});
	
	$("#arquivo-relator").fileinput({
		'showUpload':false
	});
	
	$(".prazo-parecer").datepicker({
	    format: "dd/mm/yyyy",
	    startDate: "+1d",
	    clearBtn: true,
	    language: "pt-BR",
	    autoclose: true
	});
	
	$("#alterar-parecerista-button").on("click", function(){
		$("#parecer-tecnico-acoes").hide();
		$("#parecer-tecnico-info").hide();
		$("#parecer-tecnico-form").show(1000);
	});
	
	$("#alterar-relator-button").on("click", function(){
		$("#parecer-tecnico-acoes").hide();
		$("#parecer-relator-info").hide();
		$("#parecer-relator-form").show(1000);
	});
	
	$("#cancelar-parecer-button").on("click", function(){
		$("#parecer-tecnico-acoes").show();
		$("#parecer-tecnico-form").hide();
		$("#parecer-tecnico-info").show(1000);
	});
	
	$("#cancelar-relato-button").on("click", function(){
		$("#parecer-tecnico-acoes").show();
		$("#parecer-relator-form").hide();
		$("#parecer-relator-info").show(1000);
	});
	
	$("#pendencias-parecer-button").on("click", function(){
		$("#parecer-tecnico-acoes").hide();
		$("#parecer-tecnico-info").hide();
		$("#pendencias-parecer-form").show(1000);
	});
	
	$("#cancelar-pendencia-parecer-button").on("click", function(){
		$("#parecer-tecnico-acoes").show();
		$("#pendencias-parecer-form").hide();
		$("#parecer-tecnico-info").show(1000);
	});
	
	$("#pendencias-relato-button").on("click", function(){
		$("#parecer-relator-acoes").hide();
		$("#parecer-relator-info").hide();
		$("#pendencias-relato-form").show(1000);
	});
	
	$("#cancelar-pendencia-relato-button").on("click", function(){
		$("#parecer-relator-acoes").show();
		$("#pendencias-relato-form").hide();
		$("#parecer-relator-info").show(1000);
	});
	
	$("#emitir-parecer-button").on("click", function(){
		$("#parecer-tecnico-acoes").hide();
		$("#parecer-tecnico-info").hide();
		$("#emitir-parecer-form").show(1000);
	});
	
	$("#cancelar-emitir-parecer-button").on("click", function(){
		$("#parecer-tecnico-acoes").show();
		$("#emitir-parecer-form").hide();
		$("#parecer-tecnico-info").show(1000);
	});
	
	$("#emitir-relato-button").on("click", function(){
		$("#parecer-relator-acoes").hide();
		$("#parecer-relator-info").hide();
		$("#emitir-relato-form").show(1000);
	});
	
	$("#cancelar-emitir-relato-button").on("click", function(){
		$("#parecer-relator-acoes").show();
		$("#emitir-relato-form").hide();
		$("#parecer-relator-info").show(1000);
	});
	
	$("#homologar-button").on("click", function(){
		$("#homologar-form").show(1000);
		$(this).hide();
	});
	
	$("#cancelar-homologacao-button").on("click", function(){
		$("#homologar-form").fadeOut("slow", function(){
			$("#homologar-button").show("slow");
		});
	});
});