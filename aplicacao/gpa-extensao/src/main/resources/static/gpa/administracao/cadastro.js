$(document).ready(function(){
	
	$("#anexo-acao-cr").fileinput({
		'showUpload':false
	});
	
	$("#select-coordenador-acao-cr").select2();
	
	setLimitesCargaHoraria();
	
	$("#select-coordenador-acao-cr").on("change", function(){
		setLimitesCargaHoraria();
	});
	
	function setLimitesCargaHoraria(){
		var dedicacao = $("#select-coordenador-acao-cr").find(":selected").attr("data-dedicacao");
		
		if(dedicacao == "EXCLUSIVA" || dedicacao == "H40") {
			$("#carga-horaria-coordenador-cr").attr({"max" : "16", "min" : "4"});
		} else if(dedicacao == "H20") {
			$("#carga-horaria-coordenador-cr").attr({"max" : "12", "min" : "4"});
		}
	}
	
	$("#select-modalidade-acao-cr").on('change',function(){
    	checkForm();
    });
	
	function checkForm(){
		var selected = $("#select-modalidade-acao-cr").find(":selected").val();
		if((selected === "CURSO")||(selected === "EVENTO")){
			$("#cargas-horarias-acao-cr").show("slow");
			if((selected === "CURSO")){
				$("#ementa-acao-cr").show("slow");
				$("#programacao-acao-cr").hide("slow");
				$("#textarea-programacao-acao-cr").val("");
			}else{
				$("#programacao-acao-cr").show("slow");
				$("#ementa-acao-cr").hide("slow");
				$("#textarea-ementa-acao-cr").val("");
			}
		} else {
			$("#cargas-horarias-acao-cr").hide();
			$("#ementa-acao-cr").hide();
			$("#programacao-acao-cr").hide();
		}
		return false;
    };
});