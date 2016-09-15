$(document).ready(function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	modalMessage();
    var acaoExtensaoId = $("#acaoExtensaoId").val();
    $(".alert-success").hide();
	
	$("#editar-codigo-acao-extensao").on("click", function(){
        $("#codigo-info").hide();
        $("#div-codigo-acao-extensao").fadeIn(500);
    });
    
    $("#cancelar-salvar-codigo-button").on("click", function(){
        $("#div-codigo-acao-extensao").hide();
        $("#div-codigo-acao-extensao").removeClass("has-error");
        $("#codigo-error").hide();
        $("#codigo-info").fadeIn(500);
    });
	
	$("#submeter-codigo-acao-extensao").click(function(e){
		var codigo = $("#input-codigo-acao-extensao").val();
		if(codigo!=null && codigo!=""){
			$.ajax({
				type:"GET",
				beforeSend: function (request)
		        {
					 request.setRequestHeader(header, token);
		        },
				url: "../salvarCodigo/" + acaoExtensaoId,
				data:{
					codigoAcao:codigo
				},
				success : function(data) {
					console.log(data.result);
					if(data.status=="sucesso"){
						$("#codigo-acao-extensao").text(data.result);
						console.log(data.message);
						$("#div-codigo-acao-extensao").hide();
				        $("#codigo-info").fadeIn(500);
				        $("#div-codigo-acao-extensao").removeClass("has-error");
				        $("#codigo-error").hide();
					}
				}
			});
		}else{
			$("#div-codigo-acao-extensao").addClass("has-error");
			$("#codigo-error").show();
		}
	});
	
	 $("#editar-numero-processo").on("click", function(){
        $("#numero-processo-info").hide();
        $("#div-numero-processo").fadeIn(500);
    });
	
	$("#cancelar-numero-processo").on("click", function(){
        $("#div-numero-processo").hide();
        $("#div-numero-processo").removeClass("has-error");
        $("#numero-error").hide();
        $("#numero-processo-info").fadeIn(500);
    });
	
	$("#submeter-numero-processo").click(function(e){
		var numero = $("#input-numero-processo").val();
		if(numero!=null && numero!=""){
			$.ajax({
				type:"GET",
				beforeSend: function (request)
		        {
					 request.setRequestHeader(header, token);
		        },
				url: "../admin/salvarNumeroProcesso/" + acaoExtensaoId,
				data:{
					numeroProcesso : numero
				},
				success : function(data) {
					console.log(data.result);
					if(data.status=="sucesso"){
						$("#numero-processo").text(data.result);
						console.log(data.message);
						$("#div-numero-processo").hide();
				        $("#numero-processo-info").fadeIn(500);
				        $("#div-numero-processo").removeClass("has-error");
				        $("#numero-error").hide();
					}
				}
			});
		}else{
			$("#div-numero-processo").addClass("has-error");
			$("#numero-error").show();
		}
	});
	
	 $('#dtCoordenadorInicio').datepicker({
		 format: "dd/mm/yyyy",
		 maxViewMode: 2,
 	    language: "pt-BR",
 	    autoclose: true,
 	    todayHighlight: true,
	    startDate: $("#dataInicio").text(),
 	    endDate: $("#dataTermino").text()
	 });
	 
	 $("#listCoordenadores").select2();
	 
	 $("#editar-coordenador").on("shown.bs.modal", function(e) {
		 buscarCoordenadores();
	});
	
	function buscarCoordenadores() {
		var id = $("#coordenadorId").val();
		$.ajax({
			type:"GET",
			 beforeSend: function (request)
	         {
	                request.setRequestHeader(header, token);
	            },
			url: "../buscarCoordenadores/" + id,
			contentType: 'application/json',
			success : function(data) {
				$('#listCoordenadores').empty();
				var newOption = $('<option value="" selected="selected">A Selecionar...</option>');
				$('#listCoordenadores').append(newOption);
				for (var i = 0; i < data.length; i++) {
					var newOption = $('<option value=' + data[i].pessoa.id + ' id="' + data[i].dedicacao + '">'
							+ data[i].pessoa.nome + '</option>');
					$('#listCoordenadores').append(newOption);
				}
			}
		});
	};
	
	var dataAtual = moment();
	var dataTermino = moment($("#dataTermino").text(), "DD-MM-YYYY");
	
	if(moment(dataTermino).isSameOrBefore(dataAtual)) {
		$("#buscarCoordenadores").on("hide");
	}
	
	$("#listCoordenadores").change(function() {
		var dedicacao = $(this).children(":selected").attr("id");
		if(dedicacao == "EXCLUSIVA" || dedicacao == "H40") {
			$("#chNovoCoordenador").attr({"max" : "16", "min" : "4"});
		} else if(dedicacao == "H20") {
			$("#chNovoCoordenador").attr({"max" : "12", "min" : "4"});
		}
	});
	function modalMessage() {
		if ($("#message-sucess").val() != null) {
			var alertDiv = document.getElementById("message-modal-alert");
			$(alertDiv).append("<p>" + $("#message-sucess").val() + "</p>");
			$(alertDiv).show();
			setTimeout(function() {
				$(alertDiv).fadeOut('slow');
			}, 5000);
		}
		
		if ($("#message-error").val() != null) {
			var errorDiv = $("#error-modal-alert")
			$(errorDiv).append("<p>" + $("#message-error").val() + "</p>");
			$(errorDiv).show();
			
			errorDiv.find("button").on("click", function(){
				errorDiv.fadeOut("slow");
			});
		}
	}
});