$(document).ready(function(){
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	
    $("#input-codigo").hide();
    $("#input-bolsas").hide();
    var acaoExtensaoId = $("#acaoExtensaoId").val();
    $(".alert-success").hide();
	
	$("#editar-codigo").click(function(){
        $("#input-codigo").show();
        $("#codigo-acao").hide();
        $("#editar-codigo").hide();
    });
	
	$("#editar-bolsas").click(function(){
		$("#input-bolsas").show();
		$("#bolsas-recebidas").hide();
		$("#editar-bolsas").hide();

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
	
	$("#buscarCoordenadores").click(function(e) {
		e.preventDefault();
		var id = $("#coordenadorId").val();
		$.ajax({
			type:"GET",
			 beforeSend: function (request)
	         {
	                request.setRequestHeader(header, token);
	            },
			url: "/gpa-extensao/buscarCoordenadores/" + id,
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
	});
	
	var dataAtual = moment();
	var dataTermino = moment($("#dataTermino").text(), "DD-MM-YYYY");
	
	if(moment(dataTermino).isSameOrBefore(dataAtual)) {
		$("#buscarCoordenadores").hide();
	}
	
	$("#listCoordenadores").change(function() {
		var dedicacao = $(this).children(":selected").attr("id");
		if(dedicacao == "EXCLUSIVA" || dedicacao == "H40") {
			$("#chNovoCoordenador").attr({"max" : "16", "min" : "4"});
		} else if(dedicacao == "H20") {
			$("#chNovoCoordenador").attr({"max" : "12", "min" : "4"});
		}
	});
});