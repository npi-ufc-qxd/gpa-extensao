$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var acaoExtensaoId = $("#acaoExtensaoId").val();
	
	$("#formNovaBolsa").hide();
	
	carregarTabelaBolsas();
	
	$("#selectBolsista, #selectTipo").select2();
	
	function carregarTabelaBolsas() {
		var url = "/gpa-extensao/bolsa/buscarBolsas/" + acaoExtensaoId;
		 $("#resultsBlockBolsas").load(url, function() {
			 $('#table-bolsas').DataTable({
					"order" : [[ 0, "asc" ]],
					"columnDefs" : [ 
					    {className: "text-center", "targets": [1, 3, 5]},
					    {"targets" : 4, "orderable" : false},
					],
					"language": {
				        "url": "/gpa-extensao/js/Portuguese-Brasil.json"
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
        	}
        }
	})
	.on('success.form.bv', function(e) {
		 e.preventDefault();
         var baseURL = '/gpa-extensao/bolsa/cadastrar/';
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
 					var alertDiv = $("#divSucesso");
 					alertDiv.show();
 					setTimeout(function(){$(alertDiv).fadeOut('slow');}, 3000);
 					carregarTabelaParticipacoes();
 				}else{
 					for (var i = 0; i < result.result.length; i++) {
 						$("#er").remove();
 						var alertDiv = $("#divError");
 						alertDiv.append("<p id='er'>"+result.result[i].code+"</p>");
 						alertDiv.show();
 				    	setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
 					}
 					return false;
 				}
 			}
 		});
     });
	
	$("#salvar-numero-bolsas-button").click(function(e){
		var bolsas = $("#input-bolsas-recebidas-acao-extensao").val();
		if(bolsas!=null && bolsas!=""){
			$.ajax({
				type:"POST",
				beforeSend: function (request)
		        {
					 request.setRequestHeader(header, token);
		        },
				url: "/gpa-extensao/bolsa/salvarBolsas/" + acaoExtensaoId,
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
						carregarTabelaParticipacoes();
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
			url: "/gpa-extensao/bolsa/buscarAlunos",
			contentType: 'application/json',
			success : function(data) {
				$('#selectBolsista').empty();
				for (var i = 0; i < data.length; i++) {
					console.log(data[i].pessoa.id);
					var newOption = $('<option value=' + data[i].id + '>'
							+ data[i].pessoa.nome
							+ '</option>');
					$('#selectBolsista').append(newOption);
				}
			}
		});
	}
	
	$("#editar-numero-bolsas-button").on("click", function(){
		console.log("Ta clicando?");
        $("#numero-bolsas-info").hide();
        $("#editar-bolsas-recebidas-form").fadeIn(500);
    });
    
    $("#cancelar-salvar-bolsas-button").on("click", function(){
        $("#editar-bolsas-recebidas-form").hide();
        $("#editar-bolsas-recebidas-form").removeClass("has-error");
        $("#bolsas-recebidas-error").hide();
        $("#numero-bolsas-info").fadeIn(500);
    });
});