$(document).ready(function() {
	$("#form-cadastro-aluno").on("submit", function() {

		var cpf = $(this).find("input[type=number]").val();
		
		$.ajax({
			type: "GET",
	         url : "../admin/alunos/busca",
	         data : {
	        	 "cpf": cpf
	         }
		})
		.done(function(data, textStatus, jqXHR){
			$("#info-cadastro-aluno").replaceWith(data);
		});
		
	});
});