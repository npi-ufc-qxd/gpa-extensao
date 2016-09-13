$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$("#select-pessoa, #select-papeis-atuais").select2();

	$("#select-papeis-atuais").change(function() {
		$("#btn-salvar-alteracoes").removeAttr("disabled");
	});
});