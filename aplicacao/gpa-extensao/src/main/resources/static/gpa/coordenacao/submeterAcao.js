$(document).ready(function(){
	
	if($("#action").val() == "editar") {
		$("#submeter-dataInicio, #submeter-dataTermino").removeAttr('required');
		validator();
	} else if($("#action").val() == "submeter") {
		validator();
	}
	
	function validator() {
		$("#submeterAcaoExtensaoForm").bootstrapValidator({
	        feedbackIcons: {
	            valid: false,
	        	invalid: "glyphicon"
	        },
	        fields:{
	        	titulo:{
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			},
	        			stringLength:{
	        				min:5,
	        				message:"Mínimo 5 caracteres"
	        			}
	        		}
	        	},
	        	resumo:{
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			},
	        			stringLength:{
	        				min:5,
	        				message:"Mínimo 5 caracteres"
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
	        	horasPraticas:{
	        		integer:{
	         		   message: "Digite um número válido"
	         	   }
	        	},
	        	horasTeoricas:{
	        		integer:{
	         		   message: "Digite um número válido"
	         	   }
	        	},
	        	ementa:{
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			},
	        			stringLength:{
	        				min:5,
	        				message:"Mínimo 5 caracteres"
	        			}
	        		}
	        	},
	        	programacao:{
	        		validators:{
	        			notEmpty:{
	        				message:"Campo obrigátorio"
	        			},
	        			stringLength:{
	        				min:5,
	        				message:"Mínimo 5 caracteres"
	        			}
	        		}
	        	}
	        }
	    });
	}

	checkForm();
	$("#submeter-dataInicio,#submeter-dataTermino").datepicker({
        format: "dd/mm/yyyy",
        maxViewMode: 2,
        todayBtn: true,
        language: "pt-BR",
        autoclose: true,
    }).on("changeDate",function(e){
    	$(this).datepicker("hide");
    	$("#submeterAcaoExtensaoForm").bootstrapValidator("revalidateField", "inicio");
    });;
    
    $("#submeter-modalidadeAcaoExtensao").on('change',function(){
    	console.log("MUDOU");
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
		}else{
			$("#submeter-horasPraticas").val(null);
			$("#submeter-horasTeoricas").val(null);
			$("#submeter-cargasHorarias").hide();
			$("#ementaAcaoExtensao").hide();
			$("#programacaoAcaoExtensao").hide();
		}
		return false;
    };
});