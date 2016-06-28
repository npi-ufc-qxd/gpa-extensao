$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var acaoExtensaoId = $("#acaoExtensaoId").val();
	carregarTabelaParticipacoes();
	
	$("#formNovaParticipacao").hide();
	
	$("#buttonAdicionarParticipacao").click(function() {
		$("#formNovaParticipacao").show(1500);
		$("#buttonAdicionarParticipacao").attr('disabled','disabled');
	});
	
	$("#cancelarNovaParticipacao").click(function() {
		$("#formNovaParticipacao").hide(1500);
		$("#buttonAdicionarParticipacao").removeAttr("disabled");
	});
	
	$("#selectPessoa, #selectInstituicao, #selectBolsista").select2();
	
	$(".funcaoOutra ,#divNomeInstituicao").hide();
	
	
	//Essa funcao oculta e mostra os campos do form conforme a opcao selecionada
	$("#selectFuncao").change(function() {
		var funcao = $("#selectFuncao").val();
		
		if(funcao == "OUTRA") {
			reset();
			$("#selectPessoa").attr("selectedIndex", -1);
			$("#divSelectPessoa").hide(1000);
			$("#nomeParticipante, #cpfParticipante, #descricaoFuncao").attr('required', 'required');
			$(".funcaoOutra").show(1000);
			$("#cargaHoraria").attr({"min" : "1"});
		} else if(funcao == "ALUNO_VOLUNTARIO") {
			reset();
			$("#cargaHoraria").attr({"max" : "12", "min" : "12"});
			buscaPessoas(funcao);
		} else if(funcao == "STA" || funcao == "DOCENTE") {
			reset();
			buscaPessoas(funcao);
		}
	});
	
	function reset() {
		$("#nomeParticipante, #cpfParticipante, #descricaoFuncao").removeAttr('required').val(null);
		$(".funcaoOutra").hide(1000);
		$("#divSelectPessoa").show(1000);
	}
	
	function buscaPessoas(funcao) {
		
		if(funcao == "ALUNO_VOLUNTARIO") {
			var caminho = "/gpa-extensao/buscarAlunos";
		} else {
			var caminho = "/gpa-extensao/buscarServidores";
		}
		
		$.ajax({
			type:"GET",
			 beforeSend: function (request)
	         {
	                request.setRequestHeader(header, token);
	            },
			url: caminho,
			contentType: 'application/json',
			success : function(data) {
				$('#selectPessoa').empty();
				var newOption = $('<option value="" selected="selected">A Selecionar...</option>');
				$('#selectPessoa').append(newOption);
				for (var i = 0; i < data.length; i++) {
					var newOption = $('<option value=' + data[i].pessoa.id + ' id="' + data[i].dedicacao + '">'
							+ data[i].pessoa.nome
							+ '</option>');
					$('#selectPessoa').append(newOption);
				}
			}
		});
	}
	
	//Exibe o campo nome da instituicao caso seja diferente de UFC
	$("#selectInstituicao").change(function() {
		var instituicao = $("#selectInstituicao").val();
		if(instituicao != "UFC") {
			$("#divNomeInstituicao").attr('required', 'required').show(1000);
		} else {
			$("#divNomeInstituicao").removeAttr('required').hide(1000);
			$("#nomeInstituicao").val(null);
		}
	});
	
	//Faz a validação do valor min e max da carga horaria quando um servidor é selecionado
	$("#selectPessoa").change(function() {
		var funcao = $("#selectFuncao").val();
		var dedicacao = $(this).children(":selected").attr("id");
		if(funcao == "STA" || funcao == "DOCENTE") {
			if(dedicacao == "EXCLUSIVA" || dedicacao == "H40") {
				$("#cargaHoraria").attr({"max" : "16", "min" : "4"});
			} else if(dedicacao == "H20") {
				$("#cargaHoraria").attr({"max" : "12", "min" : "4"});
			}
		}
	});
	
	$("#formNovaParticipacao").bootstrapValidator({
		feedbackIcons: {
            invalid: 'glyphicon'
         },
        fields: {
        	descricaoFuncao: {
                validators: {
                    notEmpty:{
        				message: "Campo obrigatório"
        			}
                }
            },
            nomeParticipante: {
                validators: {
                    notEmpty:{
        				message: "Campo obrigatório"
        			}
                }
            },
            cpfParticipante: {
                validators: {
                	stringLength: {
                        min: 11,
                        max: 11,
                        message: "O CPF deve ter 11 números"
                    },
                    notEmpty:{
        				message: "Campo obrigatório"
        			}
                }
            },
            nomeInstituicao: {
                validators: {
                    notEmpty:{
        				message: "Campo obrigatório"
        			}
                }
            }
        }
	})
	.on('success.form.bv', function(e) {
		 e.preventDefault();
         var baseURL = '/gpa-extensao/participacoes/';
         $.ajax({
 			url : baseURL + acaoExtensaoId,
 			beforeSend: function (request)
 		    {
 				 request.setRequestHeader(header, token);
 		    },
 			type : 'POST',
 			async: false,
 			data : $("#formNovaParticipacao").serialize(),
 			error: function(){
 		        return false;
 		    },
 			success : function(result) {
 				if(result.status=="OK"){
 					var alertDiv = $("#divSucesso");
 					alertDiv.show();
 					setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
 					e.preventDefault();
 					carregarTabelaParticipacoes();
 				}else{
 					for (var i = 0; i < result.result.length; i++) {
 						var alertDiv = $("#divError");
 						alertDiv.append("<p>"+result.result[i].code+"</p>");
 						alertDiv.show();
 				    	setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
 					}
 					e.preventDefault();
 					return false;
 				}
 			}
 		});
     });
	
	function carregarTabelaParticipacoes() {
		 var url = "/gpa-extensao/buscarParticipacoes/" + acaoExtensaoId;
		 $("#resultsBlock").load(url, function() {
			 $('#table-participacoes').DataTable({
					"order" : [[ 0, "asc" ]],
					"columnDefs" : [ 
					    {className: "dt-center", "targets": [1, 4, 5, 6]},            
				        {"targets" : 4, "orderable" : false},
				        {"targets" : 5, "orderable" : false},
				        {"targets" : 6, "orderable" : false},
				        { "width": "15%", "targets":4 },
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
	
	$("#confirm-delete-participacao").on("show.bs.modal", function(e) {
		$("#deleteParticipacaoHiddenId").val($(e.relatedTarget).data("id"));
	});

	$("#deleteParticipacaoHiddenBtn").click(function(e) {
		e.preventDefault();
		var participacaoId = $("#deleteParticipacaoHiddenId").val();
	    $("#confirm-delete-participacao").modal('hide');
		$.ajax({
			url : '/gpa-extensao/excluir/participacao/'+ participacaoId,
			beforeSend: function (request)
            {
				 request.setRequestHeader(header, token);
            },
			type : 'GET',
			async: false,
		});
		carregarTabelaParticipacoes();
	});
	
	$("#vincular-bolsista").on("show.bs.modal", function(e) {
		$("#participacaoHiddenId").val($(e.relatedTarget).data("id"));
		var caminho = "/gpa-extensao/buscarAlunos";
		$.ajax({
			type:"GET",
			 beforeSend: function (request)
	         {
	                request.setRequestHeader(header, token);
	            },
			url: caminho,
			contentType: 'application/json',
			success : function(data) {
				$('#selectBolsista').empty();
				for (var i = 0; i < data.length; i++) {
					var newOption = $('<option value=' + data[i].pessoa.id + '>'
							+ data[i].pessoa.nome
							+ '</option>');
					$('#selectBolsista').append(newOption);
				}
			}
		});
	});
	
	$("#vincularBolsistaHiddenBtn").click(function(e) {
		e.preventDefault();
        var baseURL = '/gpa-extensao/vincularBolsistas/';
        var idParticipacao = $("#participacaoHiddenId").val();
        var idAluno = $("#selectBolsista").val();
        $("#vincular-bolsista").modal('hide');
        $.ajax({
			url : baseURL + idParticipacao + "/" + idAluno,
			beforeSend: function (request)
		    {
				 request.setRequestHeader(header, token);
		    },
			type : 'POST',
			async: false,
			error: function(){
		        return false;
		    },
			success : function(result) {
				if(result.status=="OK"){
					console.log(result);
					var alertDiv = $("#divSucesso");
					alertDiv.show();
					setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
					carregarTabelaParticipacoes();
				}else{
					for (var i = 0; i < result.result.length; i++) {
						var alertDiv = $("#divError");
						console.log(result);
						alertDiv.append("<p>"+result.result[i].code+"</p>");
						alertDiv.show();
				    	setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
					}
					return false;
				}
			}
		});
	});
});