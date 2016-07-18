$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
	var acaoExtensaoId = $("#acaoExtensaoId").val();
	
	carregarTabelaBolsas();
	
	carregarTabelaBolsas() {
		var url = "/gpa-extensao/bolsa/buscarBolsas/" + acaoExtensaoId;
		 $("#resultsBlockBolsas").load(url, function() {
			 $('#table-bolsas').DataTable({
					"order" : [[ 0, "asc" ]],
					"columnDefs" : [ 
					    {className: "text-center", "targets": [1, 3, 5]},
					    {"targets" : 4, "orderable" : false},
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
	
	$("#salvar-numero-bolsas-button").click(function(e){
		var bolsas = $("#input-bolsas-recebidas-acao-extensao").val();
		if(bolsas!=null && bolsas!=""){
			$.ajax({
				type:"POST",
				beforeSend: function (request)
		        {
					 request.setRequestHeader(header, token);
		        },
				url: "/gpa-extensao/salvarBolsas/" + acaoExtensaoId,
				data:{
					bolsasRecebidas:bolsas
				},
				success : function(data) {
					if(data.status=="sucesso"){
						$("#bolsas-recebidas").text(data.result);
						$("#editar-bolsas-recebidas-form").hide();
				        $("#numero-bolsas-info").fadeIn(500);
				        $("#editar-bolsas-recebidas-form").removeClass("has-error");
				        $("#bolsas-recebidas-error").hide();
						carregarTabelaParticipacoes();
					}
				}
			});
		}else {
			$("#editar-bolsas-recebidas-form").addClass("has-error");
			$("#bolsas-recebidas-error").show();
		}
	});
	$("#editar-numero-bolsas-button").on("click", function(){
        $("#numero-bolsas-info").hide();
        $("#editar-bolsas-recebidas-form").fadeIn(500);
    });
    
    $("#cancelar-salvar-bolsas-button").on("click", function(){
        $("#editar-bolsas-recebidas-form").hide();
        $("#editar-bolsas-recebidas-form").removeClass("has-error");
        $("#bolsas-recebidas-error").hide();
        $("#numero-bolsas-info").fadeIn(500);
    });
});