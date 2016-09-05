$(document).ready(function(){
	
	$(".select-parecer").select2();
	
	$(".select-posicionamento").select2({
		minimumResultsForSearch: -1
	});
	
	$("#pendencias-body").slimScroll({
		height: "250px"
	});
	
	$("#arquivo-parecerista").fileinput({
		'showUpload':false,
		"allowedFileTypes ": "application/pdf",
		"allowedPreviewTypes ": "application/pdf"
	}).on("fileclear", function(){
		$("#parecer-form").bootstrapValidator("revalidateField", "arquivo-parecerista");
	});
	
	$("#arquivo-relator").fileinput({
		'showUpload':false,
		"allowedFileTypes ": "application/pdf",
		"allowedPreviewTypes ": "application/pdf"
	}).on("fileclear", function(){
		$("#relato-form").bootstrapValidator("revalidateField", "arquivo-relator");
	});
	
	$(".prazo-parecer").datepicker({
	    format: "dd/mm/yyyy",
	    startDate: "+1d",
	    clearBtn: true,
	    language: "pt-BR",
	    autoclose: true
	});
	
	$("#alterar-parecerista-button").on("click", function(){
		$("#parecer-tecnico-acoes").hide();
		$("#parecer-tecnico-info").hide();
		$("#parecer-tecnico-form").fadeIn(500);
	});
	
	$("#alterar-relator-button").on("click", function(){
		$("#parecer-tecnico-acoes").hide();
		$("#parecer-relator-info").hide();
		$("#parecer-relator-form").fadeIn(500);
	});
	
	$("#cancelar-parecer-button").on("click", function(){
		$("#parecer-tecnico-acoes").show();
		$("#parecer-tecnico-form").hide();
		$("#parecer-tecnico-info").fadeIn(500);
	});
	
	$("#cancelar-relato-button").on("click", function(){
		$("#parecer-tecnico-acoes").show();
		$("#parecer-relator-form").hide();
		$("#parecer-relator-info").fadeIn(500);
	});
	
	$("#pendencias-parecer-button").on("click", function(){
		$("#parecer-tecnico-acoes, #parecer-tecnico-info, #emitir-parecer-form").hide();
		$("#pendencias-parecer-form").fadeIn(500);
	});
	
	$("#cancelar-pendencia-parecer-button").on("click", function(){
		$("#parecer-tecnico-acoes").show();
		$("#pendencias-parecer-form").hide();
		$("#parecer-tecnico-info").fadeIn(500);
	});
	
	$("#pendencias-relato-button").on("click", function(){
		$("#parecer-relator-acoes").hide();
		$("#parecer-relator-info").hide();
		$("#pendencias-relato-form").fadeIn(500);
	});
	
	$("#cancelar-pendencia-relato-button").on("click", function(){
		$("#parecer-relator-acoes").show();
		$("#pendencias-relato-form").hide();
		$("#parecer-relator-info").fadeIn(500);
	});
	
	$("#emitir-parecer-button").on("click", function(){
		$("#parecer-tecnico-acoes, #parecer-tecnico-info, #pendencias-parecer-form").hide();
		$("#emitir-parecer-form").fadeIn(500);
	});
	
	$("#cancelar-emitir-parecer-button").on("click", function(){
		$("#parecer-tecnico-acoes").show();
		$("#emitir-parecer-form").hide();
		$("#parecer-tecnico-info").fadeIn(500);
	});
	
	$("#emitir-relato-button").on("click", function(){
		$("#parecer-relator-acoes").hide();
		$("#parecer-relator-info").hide();
		$("#emitir-relato-form").fadeIn(500);
	});
	
	$("#cancelar-emitir-relato-button").on("click", function(){
		$("#parecer-relator-acoes").show();
		$("#emitir-relato-form").hide();
		$("#parecer-relator-info").fadeIn(500);
	});
	
	$("#homologar-button").on("click", function(){
		$("#homologar-form").fadeIn(500);
		$(this).hide();
	});
	
	$("#cancelar-homologacao-button").on("click", function(){
		$("#homologar-form").fadeOut("slow", function(){
			$("#homologar-button").show();
		});
	});
	
	$("#parecerista-form").bootstrapValidator({
		fields: {
			"parecerTecnico.prazo": {
				validators: {
					date: {
                        format: 'DD/MM/YYYY',
                        message: "Formato incorreto"
                    },
                    notEmpty: {
                        message: "Especifique o prazo"
                    }
                }
			}
		}
	});
	
	$("#parecerista-form").on("change", function(){
		$(this).bootstrapValidator("revalidateField", "parecerTecnico.prazo");
	});
	
	$("#relator-form").bootstrapValidator({
		fields: {
			"parecerRelator.prazo": {
				validators: {
					date: {
                        format: 'DD/MM/YYYY',
                        message: "Formato incorreto"
                    },
                    notEmpty: {
                        message: "Especifique o prazo"
                    }
                }
			}
		}
	});
	
	$("#relator-form").on("change", function(){
		$(this).bootstrapValidator("revalidateField", "parecerRelator.prazo");
	});
	
	$(".pendencias-form").bootstrapValidator({
		fields: {
			descricao:{
				validators:{
					notEmpty:{
						message: "Descreva as pendências"
					}
				}
			}
		}
	});
	
	$("#parecer-form").bootstrapValidator({
		fields:{
			"parecerTecnico.parecer":{
				validators:{
					notEmpty:{
						message: "Especifique o parecer"
					}
				}
			},
			"arquivo-parecerista":{
				validators:{
					file:{
        				extension: "pdf",
                        type: "application/pdf",
                        message:"Somente o formato PDF é suportado"
        			}
				}
			}
		}
	});
	
	$("#relato-form").bootstrapValidator({
		fields: {
			"parecerRelator.parecer":{
				validators:{
					notEmpty:{
						message: "Especifique o parecer"
					}
				}
			},
			"arquivo-relator":{
				validators:{
					file:{
        				extension: "pdf",
                        type: "application/pdf",
                        message:"Somente o formato PDF é suportado"
        			}
				}
			}
		}
	});
	
	$("#formParceriaExterna").bootstrapValidator({
		rules: {
			descricaoOutrasFormas:{required:"#outrasFormasCheckBox:checked"}
		},
		fields: {
			descricaoOutrasFormas:{
				validators:{
					notEmpty:{
						message: "Campo obrigatório"
					}
				}
			}
		}
	});
	
	$("#formParceriaExterna").on("change", "#outrasFormasCheckBox", function(){
		$("#formParceriaExterna").bootstrapValidator("revalidateField", "descricaoOutrasFormas");
	});
	
	$("#form-homologar-acao").bootstrapValidator({
		fields:{
			dataDeHomologacao:{
				validators:{
					notEmpty:{
						message: "Especifique a data de homologação"
					}
				}
			},
			numeroProcesso:{
				validators:{
					notEmpty:{
						message: "Especifique o número do processo"
					}
				}
			}
		}
	});
	
	$("#form-homologar-acao").on("change", function(){
		$("#form-homologar-acao").bootstrapValidator("revalidateField", "dataDeHomologacao");
		$("#form-homologar-acao").bootstrapValidator("revalidateField", "numeroProcesso");
	});
});