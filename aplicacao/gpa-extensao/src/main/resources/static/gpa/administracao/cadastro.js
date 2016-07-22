$(document).ready(function(){
	
	$("#anexo-acao-cr").fileinput({
		'showUpload':false
	});
	$(".date").datepicker({
	    format: "dd/mm/yyyy",
	    maxViewMode: 2,
	    language: "pt-BR",
	    autoclose: true,
	    todayHighlight: true
    }).on("changeDate",function(e){
    	$(this).datepicker("hide");
    	$("#form-admin-acao-retroativa").bootstrapValidator("revalidateField", "inicio");
    	$("#form-admin-acao-retroativa").bootstrapValidator("revalidateField", "termino");
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
    $("#form-admin-acao-retroativa").bootstrapValidator({
    	group: ".form-item",
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
                    },
                    notEmpty:{
        				message:"Campo obrigátorio"
        			}
            	}
        	},
        	termino: {
        		validators:{
        			notEmpty:{
        				message:"Campo obrigátorio"
        			}
        		}
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
        	},
        	anexoAcao:{
        		validators:{
        			notEmpty:{
        				message:"Por favor insira um arquivo"
        			}
        		}
        	}
        }
    });
    
    $("#excluirArquivoAdmin").click(function(e) {
    	e.preventDefault();
    	var token = $("meta[name='_csrf']").attr("content");
    	var header = $("meta[name='_csrf_header']").attr("content");
    	idAcao = $("#acaoExtensaoId").val();
    	$.ajax({
			url : '../../documento/excluir/'+ idAcao,
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
});