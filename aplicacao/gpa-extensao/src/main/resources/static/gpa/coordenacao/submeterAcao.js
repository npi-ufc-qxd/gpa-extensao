$(document).ready(function(){
	
	if($("#action").val() != "submeter") {
		$("#submeter-dataInicio, #submeter-dataTermino").removeAttr('required');
		validator();
	} else if($("#action").val() == "submeter") {
		validator();
	}
	
	function validator() {
		$("#submeterAcaoExtensaoForm").bootstrapValidator({
			group: ".form-item",
			row: {
		        invalid: 'has-error',
	        	valid: 'valid'
		    },
		    fields:{
	        	titulo:{
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			}
	        		}
	        	},
	        	resumo:{
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			}
	        		}
	        	},
	        	cargaHoraria: {
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			}
	        		}
	        	},
	        	bolsasSolicitadas: {
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			}
	        		}
	        	},
	        	inicio:{
	        		validators: {
	            		callback: {
	                        message: "A data de início deve ser anterior à data de término",
	                        callback: function(value, validator) {
	                        	var termino = validator.getFieldElements("termino").val();
	                        	if(value != "" && termino != "") {
	                        		termino = moment(termino, "DD/MM/YYYY").format("DD/MM/YYYY");
		                        	var inicio = moment(value, "DD/MM/YYYY").format("DD/MM/YYYY");
		                        	if(moment(termino, "DD/MM/YYYY").isBefore(moment(inicio, "DD/MM/YYYY"))) {
		                        		return false;
		                        	}
	                        	}
	                        	return true;
	                        }
	                    }
	            	}
	        	},
	        	termino: {
	        		
	        	},
	        	horasPraticas:{
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			},
	        			integer:{
	 	         		   message: "Digite um número válido"
	 	         	   	}
	        		}
	        		
	        	},
	        	horasTeoricas:{
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			},
	        			integer:{
	 	         		   message: "Digite um número válido"
	        			}
	        		}
	        	},
	        	ementa:{
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			}
	        		}
	        	},
	        	programacao:{
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			}
	        		}
	        	}
	        }
	    });
	}

	checkForm();
	$(".date").datepicker({
	    format: "dd/mm/yyyy",
	    maxViewMode: 2,
	    language: "pt-BR",
	    autoclose: true,
	    todayHighlight: true
    }).on("changeDate",function(e){
    	$(this).datepicker("hide");
    	$("#submeterAcaoExtensaoForm").bootstrapValidator("revalidateField", "inicio");
    	$("#submeterAcaoExtensaoForm").bootstrapValidator("revalidateField", "termino");
    });;
    
    $("#submeter-modalidadeAcaoExtensao").on('change',function(){
    	checkForm();
    });
    function checkForm(e){
		var selected = $("#submeter-modalidadeAcaoExtensao").find(":selected").val();
		if((selected === "CURSO")||(selected === "EVENTO")){
			$("#submeter-cargasHorarias").show("slow");
			if((selected === "CURSO")){
				$("#ementaAcaoExtensao").show("slow");
				$("#programacaoAcaoExtensao").hide("slow");
				$("#submeter-programacaoAcaoExtensao").val("");
			}else{
				$("#programacaoAcaoExtensao").show("slow");
				$("#ementaAcaoExtensao").hide("slow");
				$("#submeter-ementaAcaoExtensao").val("");
			}
		} else {
			$("#submeter-cargasHorarias").hide();
			$("#ementaAcaoExtensao").hide();
			$("#programacaoAcaoExtensao").hide();
		}
		return false;
    };
    
    $("#excluirArquivo").click(function(e) {
    	e.preventDefault();
    	var token = $("meta[name='_csrf']").attr("content");
    	var header = $("meta[name='_csrf_header']").attr("content");
    	idAcao = $("#acaoExtensaoId").val();
    	$.ajax({
			url : '/gpa-extensao/documento/excluir/'+ idAcao,
			beforeSend: function (request)
            {
				 request.setRequestHeader(header, token);
            },
			type : 'POST',
			success: function(result) {
				location.reload();
			}
		});
    });
    
    var dedicacao = $("#dedicacaoID").val();
	if(dedicacao == "EXCLUSIVA" || dedicacao == "H40") {
		$("#cargaHoraria").attr({"max" : "16", "min" : "4"});
	} else if(dedicacao == "H20") {
		$("#cargaHoraria").attr({"max" : "12", "min" : "4"});
	}
	
	$("#anexoAcao").fileinput({
		'showUpload':false
	});
});