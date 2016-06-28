$(document).ready(function() {
	$("#cargaHorarias").hide();
	$("#cadastrarModalidadeAcaoExtensao, #cadastrarVinculoAcaoExtensao").select2();
    $("#cadastrarDataInicio,#cadastrarDataTermino").datepicker({
    	format : "dd/mm/yyyy",
		todayBtn : "linked",
		language : "pt-BR",
		todayHighlight : true
    }).on("changeDate",function(e){
    	$(this).datepicker("hide");
    	$("#formCadastrarAcaoExtensao").bootstrapValidator("revalidateField", "inicio");
    });
    $("#formCadastrarAcaoExtensao").bootstrapValidator({
    	excluded: [':disabled', ':hidden', ':not(:visible)'],
    	live: 'enabled',
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
        		group:'input-group',
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
        	},vinculo:{
        		excluded:true
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
