$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var acaoExtensaoId = $("#acaoExtensaoId").val();
	
	$("#formNovaBolsa").hide();
	
	carregarTabelaBolsas();
	
	$("#selectBolsista, #selectTipo").select2();
	
	function carregarTabelaBolsas() {
		var url = "../bolsa/buscarBolsas/" + acaoExtensaoId;
		 $("#resultsBlockBolsas").load(url, function() {
			 $('#table-bolsas').DataTable({
					"order" : [[ 0, "asc" ]],
					"columnDefs" : [ 
					    {className: "text-center", "targets": [1, 2, 3, 4, 5, 6]},
					    {"targets" : 1, "orderable" : false},
					    {"targets" : 3, "orderable" : false},
					    {"targets" : 4, "orderable" : false},
					    {"targets" : 5, "orderable" : false},
					    {"targets" : 6, "orderable" : false},
					],
					"language": {
				        "url": "../json/Portuguese-Brasil.json"
				    },
					"paging":   false,
			        "searching": false,
			        "info":     false,
				});
		 });
	}
	
	$("#buttonAdicionarBolsista").click(function() {
		buscaAlunos();
		$("#formNovaBolsa").fadeIn(500);
		$("#buttonAdicionarBolsista").attr('disabled','disabled');
	});
	
	
	$("#cancelarNovoBolsista").click(function() {
		$("#formNovaBolsa").hide();
		$("#buttonAdicionarBolsista").removeAttr("disabled");
	});
	
	$("#formNovaBolsa").bootstrapValidator({
		group: ".form-item",
        fields: {
        	cargaHoraria: {
        		validators: {
                    notEmpty:{
        				message: "Campo obrigatório"
        			},
        			between: {
        				min: 1,
        				max: 12
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
        		
        	}
        }
	})
	.on('success.form.bv', function(e) {
		 e.preventDefault();
         var baseURL = '../bolsa/cadastrar/';
         $.ajax({
 			url : baseURL + acaoExtensaoId,
 			beforeSend: function (request)
 		    {
 				 request.setRequestHeader(header, token);
 		    },
 			type : 'POST',
 			async: false,
 			data : $("#formNovaBolsa").serialize(),
 			error: function(){
 		        return false;
 		    },
 			success : function(result) {
 				$("#formNovaBolsa").bootstrapValidator('resetForm', true);
 				resetForm();
 				if(result.status=="OK"){
 					var alertDiv = $("#divSucessoBolsa");
 					alertDiv.show();
 					setTimeout(function(){$(alertDiv).fadeOut('slow');}, 3000);
 					carregarTabelaBolsas();
 				}else{
 					for (var i = 0; i < result.result.length; i++) {
 						$("#er").remove();
 						var alertDiv = $("#divErrorBolsa");
 						alertDiv.append("<p id='er'>"+result.result[i].code+"</p>");
 						alertDiv.show();
 				    	setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
 					}
 					return false;
 				}
 			}
 		});
     });
	
	function resetForm() {
		$("#formNovaBolsa").hide();
		$("#buttonAdicionarBolsista").removeAttr("disabled");
		$("#formNovaBolsa")[0].reset();
		setData();
	}
	
	$("#salvar-numero-bolsas-button").click(function(e){
		var bolsas = $("#input-bolsas-recebidas-acao-extensao").val();
		if(bolsas!=null && bolsas!=""){
			$.ajax({
				type:"POST",
				beforeSend: function (request)
		        {
					 request.setRequestHeader(header, token);
		        },
				url: "../bolsa/salvarBolsas/" + acaoExtensaoId,
				data:{
					bolsasRecebidas:bolsas
				},
				success : function(data) {
					if(data.status=="sucesso"){
						$("#bolsas-recebidas").text(data.result);
						$("#editar-bolsas-recebidas-form").hide();
				        $("#numero-bolsas-info").fadeIn(500);
				        $("#editar-bolsas-recebidas-form").removeClass("has-error");
				        $("#bolsas-recebidas-error").hide();
					}
				}
			});
		}else {
			$("#editar-bolsas-recebidas-form").addClass("has-error");
			$("#bolsas-recebidas-error").show();
		}
	});
	
	function buscaAlunos() {
		$.ajax({
			type:"GET",
			 beforeSend: function (request)
	         {
	                request.setRequestHeader(header, token);
	            },
			url: "../bolsa/buscarAlunos",
			contentType: 'application/json',
			success : function(data) {
				$('#selectBolsista').empty();
				for (var i = 0; i < data.length; i++) {
					var newOption = $('<option value=' + data[i].id + '>'
							+ data[i].pessoa.nome + '</option>');
					$('#selectBolsista').append(newOption);
				}
			}
		});
	}
	
	$("#editar-numero-bolsas-button").on("click", function(){
        $("#numero-bolsas-info").hide();
        $("#editar-bolsas-recebidas-form").fadeIn(500);
    });
    
    $("#cancelar-salvar-bolsas-button").on("click", function(){
        $("#editar-bolsas-recebidas-form").hide();
        $("#editar-bolsas-recebidas-form").removeClass("has-error");
        $("#bolsas-recebidas-error").hide();
        $("#numero-bolsas-info").fadeIn(500);
    });
    
    $('#inicioBolsa, #terminoBolsa, #dataTerminoBolsa').datepicker({
        format: "dd/mm/yyyy",
        maxViewMode: 2,
 	    language: "pt-BR",
 	    autoclose: true,
 	    todayHighlight: true,
 	    startDate: $("#dataInicio").text(),
 	    endDate: $("#dataTermino").text()
     }).on("changeDate",function(e){
    	$(this).datepicker("hide");
    	$("#formNovaBolsa").bootstrapValidator("revalidateField", "inicio");
    	$("#formNovaBolsa").bootstrapValidator("revalidateField", "termino");
    });
    
    setData();
    function setData() {
    	var date = $("#dataInicio").text().split("/");
    	$("#inicioBolsa").datepicker("setDate", new Date(date[2], date[1]-1, date[0]));
    
    	var date = $("#dataTermino").text().split("/");
    	$("#terminoBolsa").datepicker("setDate", new Date(date[2], date[1]-1, date[0]));
    	$("#dataTerminoBolsa").datepicker("setDate", new Date(date[2], date[1]-1, date[0]));
    }
    
    $("#confirm-delete-bolsa").on("show.bs.modal", function(e) {
		$("#deleteBolsaHiddenId").val($(e.relatedTarget).data("id"));
	});

	$("#deleteBolsaHiddenBtn").click(function(e) {
		e.preventDefault();
		var bolsaId = $("#deleteBolsaHiddenId").val();
	    $("#confirm-delete-bolsa").modal('hide');
		$.ajax({
			url : '../bolsa/excluir/'+ bolsaId,
			beforeSend: function (request)
            {
				 request.setRequestHeader(header, token);
            },
			type : 'GET',
			async: false,
		});
		carregarTabelaBolsas();
	});
	
	$("#confirm-encerrar-bolsa").on("shown.bs.modal", function(e) {
		$("#encerrarBolsaHiddenId").val($(e.relatedTarget).data("id"));
		$(e.relatedTarget).prop('checked', true);
	});

	$("#encerrarBolsaHiddenBtn").click(function(e) {
		e.preventDefault();
		var bolsaId = $("#encerrarBolsaHiddenId").val();
		var dataTermino = $("#dataTerminoBolsa").val();
	    $("#confirm-encerrar-bolsa").modal('hide');
	    if(dataTermino) {
			$.ajax({
				url : '../bolsa/encerrar/'+ bolsaId,
				data : {dataTermino: dataTermino},
				beforeSend: function (request)
	            {
					 request.setRequestHeader(header, token);
	            },
				type : 'POST',
				async: false,
				success : function(result) {
					console.log(result)
	 				if(result.status=="ERROR"){
	 					$("#er").remove();
 						var alertDiv = $("#divErrorBolsa");
 						alertDiv.append("<p id='er'>"+result.result +"</p>");
 						alertDiv.show();
 				    	setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
 				    	setData();
	 				} else {
	 					carregarTabelaBolsas();
	 				}
	 			}
			});
	    }
	});
});