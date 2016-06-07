$(document).ready(function() {
	
	$("#selectFuncao, #selectPessoa, #selectInstituicao").select2();
	
	$("#divNomeCpf, #divDescricaoFuncao ,#divNomeInstituicao, #spanParticipante").hide();
	
	buscaPessoas("ALUNO_VOLUNTARIO");
	
	$("#cpfParticipante").mask("999.999.999-99");
	
	//Essa funcao oculta e mostra os campos do form conforme a opcao selecionada
	$("#selectFuncao").change(function() {
		var funcao = $("#selectFuncao").val();
		
		if(funcao == "OUTRA") {
			reset();
			$("#selectPessoa").val("");
			$("#divSelectPessoa").hide(1000);
			$("#nomeParticipante, #cpfParticipante, #descricaoFuncao").attr('required', 'required');
			$("#divNomeCpf, #divDescricaoFuncao").show(1000);
		} else if(funcao == "ALUNO_VOLUNTARIO") {
			reset();
			$("#cargaHoraria").attr({"max" : "12", "min" : "12"});
			buscaPessoas(funcao);
		} else if(funcao == "STA" || funcao == "DOCENTE") {
			reset();
			$("#spanParticipante").show();
			buscaPessoas(funcao);
		}
	});
	
	function reset() {
		$("#nomeParticipante, #cpfParticipante, #descricaoFuncao").removeAttr('required').val(null);
		$("#divNomeCpf, #divDescricaoFuncao, #spanParticipante").hide(1000);
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
				for (var i = 0; i < data.length; i++) {
					var newOption = $('<option value=' + data[i].id + ' id="' + data[i].dedicacao + '">'
							+ data[i].pessoa.nome
							+ '</option>');
					$('#selectPessoa').append(newOption);
				}
				if(funcao == "ALUNO_VOLUNTARIO") {
					var newOption = $('<option value=" ">Default</option>');
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
});