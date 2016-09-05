$(document).ready(function() {
	$(this).ajaxSend(function(event, jqxhr, settings) {
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

		jqxhr.setRequestHeader(header, token);
	});

	$("#form-cadastro-aluno").on("submit", function() {

		var matricula = $(this).find("input[type=number]").val();
		
		$.ajax({
			type: "GET",
	         url : "../admin/alunos/busca",
	         data : {
	        	 "matricula": matricula
	         }
		})
		.done(function(data, textStatus, jqXHR){
			$("#info-cadastro-aluno").replaceWith(data);
		});
		
	});

});