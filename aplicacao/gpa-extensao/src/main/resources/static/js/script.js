$(document).ready(function() {
	//$("#cadastrar").attr("disabled","disabled");
	$("#message").hide();
	$("#cargaHorarias").hide();
	
    $('#dataInicio,#dataTermino').datepicker({
        format: "dd/mm/yyyy",
        maxViewMode: 2,
        todayBtn: true,
        language: "pt-BR",
        autoclose: true
    });
    
    var verificarData = function(Inicio,Termino){
    	var dataInicio = new Object();
    	var dataTermino = new Object();
    	dataInicio.dia = +Inicio.val().substring(0,2);
    	dataInicio.mes = +Inicio.val().substring(3,5);
    	dataInicio.ano = +Inicio.val().substring(6);
    	dataTermino.dia = +Termino.val().substring(0,2);
    	dataTermino.mes = +Termino.val().substring(3,5);
    	dataTermino.ano = +Termino.val().substring(6);
    	dataInicio.Total = (dataInicio.dia)+((dataInicio.mes)*30)+((dataInicio.ano)*365);
		dataTermino.Total = (dataTermino.dia)+((dataTermino.mes)*30)+((dataTermino.ano)*365);
		if(dataTermino.Total >= dataInicio.Total){
			return true;
    	}else{
    		return false;
    	}
	};
	
    $("#dataTermino").change(function() {
    	var dataValida = verificarData($("#dataInicio"),$("#dataTermino"));
    	if(!dataValida){
    		$("#message").removeClass("alert alert-success alert-dismissible");
    		$("#message").addClass("alert alert-danger alert-dismissible");
    		$("#message").html("A data de início não pode ser posterior a data de Término.").slideDown("slow");
    		
    	}else{
    		$("#message").removeClass("alert alert-danger alert-dismissible");
    		$("#message").addClass("alert alert-success alert-dismissible");
    		$("#message").html("Data Válida.").slideUp("slow");
    	}
    });
    
	   
	
	$("#modalidade").change(function(){
			var selected = $(this).find(":selected").val();
			if((selected === "CURSO")||(selected === "EVENTO")){
				$("#cargaHorarias").show("slow");
				if((selected === "CURSO")){
					$("#ementa").show("slow");
					$("#programacao").hide("slow");
				}else{
					$("#programacao").show("slow");
					$("#ementa").hide("slow");
				}
			}else{
				$("#horasTeoricas").val("0");
				$("#horasPraticas").val("0");
				$("#cargaHorarias").slideUp("slow");
			}
	});
	
	
});
