$(document).ready(function() {

	$("#select-pessoa, #select-papeis-atuais").select2();

	$("#select-papeis-atuais").change(function() {
		$("#btn-salvar-alteracoes").removeAttr("disabled");
	});
});