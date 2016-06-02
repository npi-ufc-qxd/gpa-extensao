$(document).ready(function() {
	
	$("#selectFuncao, #selectPessoa").select2();
	
	$("#divNomeCpf, #divDescricaoFuncao ,#divNomeInstituicao").hide();
	
	$("#selectFuncao").change(function() {
		var funcao = $("#selectFuncao").val();
		if(funcao == "OUTRO") {
			$("#selectPessoa").val("");
			$("#divSelectPessoa").hide();
			$("#nomeParticipante, #cpfParticipante, #descricaoFuncao").attr('required', 'required');
			$("#divNomeCpf, #divDescricaoFuncao").show();
		} else {
			$("#nomeParticipante, #cpfParticipante, #descricaoFuncao").removeAttr('required').val(undefined);
			$("#divNomeCpf, #divDescricaoFuncao").hide();
			$("#divSelectPessoa").show();
		}
		
		if(funcao == "ALUNO_VOLUNTARIO") {
			$("#cargaHoraria").attr({
				"max" : "12",
				"min" : "12"
			});
		} else {
			$("#cargaHoraria").removeAttr('max').removeAttr('min');
		}
	});
	
	$("#selectInstituicao").change(function() {
		var instituicao = $("#selectInstituicao").val();
		if(instituicao != "UFC") {
			$("#divNomeInstituicao").attr('required', 'required').show();
		} else {
			$("#divNomeInstituicao").removeAttr('required', 'required').val(undefined).hide();
		}
	});
	
	$("#selectPessoa").change(function() {
		var funcao = $("#selectFuncao").val();
		if(funcao == "STA" || funcao == "DOCENTE") {
			$("#cargaHoraria").attr({
				"max" : "16",
				"min" : "4"
			});
		} else {
			$("#cargaHoraria").removeAttr('max').removeAttr('min');
		}
	});
});