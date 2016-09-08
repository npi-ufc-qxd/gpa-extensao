$(document).ready(function() {
	
	$("#form-cadastro-servidor").on("submit", function() {

		var cpf = $(this).find("input[type=number]").val();
		
		$.ajax({
			type: "GET",
	         url : "../admin/servidores/busca",
	         data : {
	        	 "cpf": cpf
	         }
		})
		.done(function(data, textStatus, jqXHR){
			console.log(data);
			$("#info-cadastro-servidor").replaceWith(data);
		});
		
	});
	
});