$(document).ready(function() {
	$("#message").hide();
	$("#cargaHorarias").hide();
	
    $("#cadastrarDataInicio,#cadastrarDataTermino").datepicker({
        format: "dd/mm/yyyy",
        maxViewMode: 2,
        todayBtn: true,
        language: "pt-BR",
        autoclose: true
    });
    
    $("#formCadastrarAcaoExtensao").bootstrapValidator({
        feedbackIcons: {
            valid: false,
        	invalid: "glyphicon"
        },
        excluded: ['vinculo'],
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
        	cadastrarDataInicio:{
        		validators: {
            		callback: {
                        message: "A data de início deve ser anterior à data de término",
                        callback: function(value, validator) {
                        	var termino = validator.getFieldElements("cadastrarDataTermino").val();
                        	console.log(value);
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
        			min:0,
         		   	message: "Digite um número válido"
         	   }
        	},
        	horasTeoricas:{
        		integer:{
        			min:0,
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
	
	$("#cadastrarModalidadeAcaoExtensao").change(function(){
		var selected = $(this).find(":selected").val();
		if((selected === "CURSO")||(selected === "EVENTO")){
			$("#cargasHorarias").show("slow");
			if((selected === "CURSO")){
				$("#programacaoAcaoExtensao").hide("slow");
				$("#ementaAcaoExtensao").show("slow");
			}else{
				$("#ementaAcaoExtensao").hide("slow");
				$("#programacaoAcaoExtensao").show("slow");
			}
		}else{
			$("#cadastrarHorasPraticas").val("0");
			$("#cadastrarHorasTeoricas").val("0");
			$("#cargasHorarias").slideUp("slow");
			$("#ementaAcaoExtensao").hide("slow");
			$("#programacaoAcaoExtensao").hide("slow");
		}
	});
});
