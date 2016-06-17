$(document).ready(function() {
	$("#message").hide();
	$("#cargaHorarias").hide();
	$("#botaoCadastrar").attr("disabled","disabled");
	
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
    	
    	if(dataTermino.ano < dataInicio.ano){
    		return false;
    	}else if(dataTermino.mes < dataInicio.mes){
    		return false;
    	}else if(dataTermino.dia < dataInicio.dia){
    		return false;
    	}else return true;
	};
	
    $("#dataTermino").change(function() {
    	var dataValida = verificarData($("#dataInicio"),$("#dataTermino"));
    	if(!dataValida){
    		$("#message").removeClass("alert alert-success alert-dismissible");
    		$("#message").addClass("alert alert-danger alert-dismissible");
    		$("#message").html("A data de início não pode ser posterior a data de Término.").slideDown("slow");
    		$("#botaoCadastrar").attr("disabled",true);
    	}else{
    		$("#message").removeClass("alert alert-danger alert-dismissible");
    		$("#message").addClass("alert alert-success alert-dismissible");
    		$("#message").html("Data Válida.").slideUp("slow");
    		$("#botaoCadastrar").attr("disabled",false);
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
