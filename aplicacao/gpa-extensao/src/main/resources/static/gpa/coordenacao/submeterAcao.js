$(document).ready(function(){
	checkForm();
	$("#submeter-dataInicio,#submeter-dataTermino").datepicker({
        format: "dd/mm/yyyy",
        maxViewMode: 2,
        todayBtn: true,
        language: "pt-BR",
        autoclose: true,
    });
    
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
        	modalidade:{
        		validators: {
                    callback: {
                        message: 'Selecionar modalidade',
                        callback: function(value, validator) {
                            // Get the selected options
                            var opcao = validator.getFieldElements("modalidade").val();
                            return (opcao!=null);
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
    $("#submeter-modalidadeAcaoExtensao").on('change',function(){
    	checkForm();
    });
    function checkForm(e){
		var selected = $("#submeter-modalidadeAcaoExtensao").find(":selected").val();
		console.log(selected);
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
			$("#submeter-horasPraticas").val(0);
			$("#submeter-horasTeoricas").val(0);
			$("#submeter-cargasHorarias").hide();
			$("#ementaAcaoExtensao").hide();
			$("#programacaoAcaoExtensao").hide();
		}
		return false;
    };
});