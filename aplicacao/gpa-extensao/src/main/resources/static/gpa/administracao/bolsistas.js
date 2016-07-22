$(document).ready(function(){
	
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$("#toggle-one").bootstrapToggle();
	
	$("#select-mes-listagem-bolsistas").select2({
		minimumResultsForSearch: -1
	});
	$("#select-ano-listagem-bolsistas").select2({
		minimumResultsForSearch: -1
	});
	
	var ptBR_url = "../json/Portuguese-Brasil.json";
	
	selecionarPeriodoBolsas();
	
	$(".select-periodo-bolsas").on("change", function(e){
		e.preventDefault();
		selecionarPeriodoBolsas();
	});
	
	$("#table-listagem-bolsistas").on("change", ".checkbox-frequencia-mes", function(e){
		e.preventDefault();
		
		var acao;
		
		if(this.checked){
			acao = "cadastrar";
		}else{
			acao = "remover";
		}
		
			
		var bolsaId = $(this).attr("data-bolsa");
		var mes = $(this).attr("data-mes");
		var ano = $(this).attr("data-ano");
		
		$.ajax({
			type: "POST",
			beforeSend: function (request)
	         {
				request.setRequestHeader(header, token);
	         },
	         url : "../admin/frequencia",
	         data : {
	        	 bolsaId : bolsaId,
	        	 mes : mes,
	        	 ano : ano,
	        	 acao : acao
	         },
	         statusCode : {
	        	 406 : function(data){
	     			$("#message-pagina-bolsistas").text(data.responseText);
	     			$("#callout-pagina-bolsistas").fadeIn("slow").delay("7000").fadeOut("slow");
	     		}
	         }
		});
	});
	
	function selecionarPeriodoBolsas(){
		var ano = getAno();
		
		if(ano != ""){
			$.ajax({
				type:"POST",
				 beforeSend: function (request)
		         {
		                request.setRequestHeader(header, token);
		            },
				url: "../admin/bolsistas",
				data : {
					ano : ano
				},
				success : function(data) {
					
					$("#table-listagem-bolsistas").html(data).promise().done(function(){
						$("#table-listagem-bolsistas").dataTable().fnDestroy();
						
						carregaTabelaBolsistas();
					});
					
				}
			});
		}
	}
	
	function carregaTabelaBolsistas(){
		$("#table-listagem-bolsistas").DataTable({
			"filter" : false,
			"paginate" : false,
			"info": false,
			"columnDefs" : [
			                {className: "dt-center", "targets": [ 4 ]},
			                {"targets" : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15], "orderable" : false},
			                ],
			"language" : {
				"url": ptBR_url
			}
		});
	}
	
	function getAno(){
		return $("#select-ano-listagem-bolsistas").children(":selected").val();
	}
});