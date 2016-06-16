$(document).ready(function() {
	
	var acaoExtensaoId = $("#acaoExtensaoId").val();
	carregarTable(acaoExtensaoId);
	
	$("#formAdicionarParticipacao").hide();
	
	$("#buttonAdicionarParticipacao").click(function() {
		$("#formAdicionarParticipacao").show(1500);
		$("#buttonAdicionarParticipacao").attr('disabled','disabled');
	});
	
	$("#cancelarNovaParticipacao").click(function() {
		$("#formAdicionarParticipacao").hide(1500);
		$("#buttonAdicionarParticipacao").removeAttr("disabled");
	});
	
	$("#selectFuncao, #selectPessoa, #selectInstituicao").select2();
	
	$("#divNomeCpf, #divDescricaoFuncao ,#divNomeInstituicao").hide();
	
	$("#cpfParticipante").mask("999.999.999-99");
	
	//Essa funcao oculta e mostra os campos do form conforme a opcao selecionada
	$("#selectFuncao").change(function() {
		var funcao = $("#selectFuncao").val();
		
		if(funcao == "OUTRA") {
			reset();
			$("#selectPessoa").attr("selectedIndex", -1);
			$("#divSelectPessoa").hide(1000);
			$("#nomeParticipante, #cpfParticipante, #descricaoFuncao").attr('required', 'required');
			$("#divNomeCpf, #divDescricaoFuncao").show(1000);
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
		$("#divNomeCpf, #divDescricaoFuncao").hide(1000);
		$("#divSelectPessoa").show(1000);
		$("#cargaHoraria").removeAttr('max').removeAttr('min');
	}
	
	function buscaPessoas(funcao) {
		//token e header são do cabeçalho para a requisição não ser rejeitada
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
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
	
	$("#submitParticipacao").click(function(e) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
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
			success : function(data) {
				if(data.status=="OK"){
					var alertDiv = $("#divSucesso");
					alertDiv.show();
					setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
					carregarTable(acaoExtensaoId);
					e.preventDefault();
				}else{
					for (var i = 0; i < data.result.length; i++) {
						var alertDiv = $("#divError");
						alertDiv.append("<p>"+data.result[i].code+"</p>");
						alertDiv.show();
				    	setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
					}
					e.preventDefault();
					return false;
				}
				
			}
		});
	});
	
	function carregarTable(acaoExtensaoId) {
		var url = "/gpa-extensao/buscarParticipacoes/" + acaoExtensaoId;;
	    
	    $("#resultsBlock").load(url);
	}
});