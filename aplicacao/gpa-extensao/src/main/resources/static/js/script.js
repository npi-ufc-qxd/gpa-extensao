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
    
    /*$("#dataTermino").change(function() {
    	var dataInicio = new Object();
    	var dataTermino = new Object();
    	dataInicio.dia = +$("#dataInicio").val().substring(0,2);
    	dataInicio.mes = +$("#dataInicio").val().substring(3,5);
    	dataInicio.ano = +$("#dataInicio").val().substring(6);
    	dataInicio.dia = +$("#dataTermino").val().substring(0,2);
    	dataInicio.mes = +$("#dataTermino").val().substring(3,5);
    	dataInicio.ano = +$("#dataTermino").val().substring(6);
    	
    	
    	alert(dataInicio.dia + " " + dataInicio.mes + " " + dataInicio.ano);
    	alert(typeof dataInicio.dia);
    });
    */
	$("#message").addClass("alert alert-danger alert-dismissible");   
	
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
