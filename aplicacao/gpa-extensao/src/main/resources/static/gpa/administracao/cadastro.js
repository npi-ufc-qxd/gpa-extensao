$(document).ready(function(){
	
	$("#anexo-acao").fileinput({
		'showUpload':false
	});
	
	$("#select-coordenador-acao").select2();
	
	setLimitesCargaHoraria();
	
	$("#select-coordenador-acao").on("change", function(){
		setLimitesCargaHoraria();
	});
	
	function setLimitesCargaHoraria(){
		var dedicacao = $("#select-coordenador-acao").find(":selected").attr("data-dedicacao");
		
		if(dedicacao == "EXCLUSIVA" || dedicacao == "H40") {
			$("#carga-horaria-coordenador").attr({"max" : "16", "min" : "4"});
		} else if(dedicacao == "H20") {
			$("#carga-horaria-coordenador").attr({"max" : "12", "min" : "4"});
		}
	}
	
	$("#select-modalidade-acao").on('change',function(){
    	checkForm();
    });
	
	function checkForm(){
		var selected = $("#select-modalidade-acao").find(":selected").val();
		if((selected === "CURSO")||(selected === "EVENTO")){
			$("#cargas-horarias-acao").show("slow");
			if((selected === "CURSO")){
				$("#ementa-acao").show("slow");
				$("#programacao-acao").hide("slow");
				$("#textarea-programacao-acao").val("");
			}else{
				$("#programacao-acao").show("slow");
				$("#ementa-acao").hide("slow");
				$("#textarea-ementa-acao").val("");
			}
		} else {
			$("#cargas-horarias-acao").hide();
			$("#ementa-acao").hide();
			$("#programacao-acao").hide();
		}
		return false;
    };
});