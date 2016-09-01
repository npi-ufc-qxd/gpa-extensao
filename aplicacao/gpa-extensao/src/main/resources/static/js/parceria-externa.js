$(document).ready(function() {
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var acaoExtensaoId = $("#acaoExtensaoId").val();
	carregarTabelaParceriasExternas();
	
	$("#formParceriaExterna, #divDescricaoOutrasFormas, #formParceiro").hide();
	
	// Adicionar parceria externa
	$("#adicionarNovaParceriaExterna").click(function(e){
		$("#formParceriaExterna").fadeIn(500);
		$("#adicionarNovaParceriaExterna").attr('disabled','disabled');
		$("#criarNovoParceiro").show();
		buscarParceiros();
		e.preventDefault();
	});
	
	// Cancelar cadastro de parceria
	$("#cancelarAdicaoParceriaExterna").click(function(e){
		$("#formParceriaExterna").hide();
		$("#adicionarNovaParceriaExterna").removeAttr("disabled");
		$("#criarNovoParceiro").hide();
		e.preventDefault();
	});
	
	// Adicionar parceiro
	$("#criarNovoParceiro").click(function(e){
		$("#formParceriaExterna").hide();
		$("#formParceiro").fadeIn(500);
		e.preventDefault();
	});
	
	// Cancelar cadastro de parceiro
	$("#cancelarAdicaoParceiro").click(function(e){
		$("#formParceiro").hide();
		$("#formParceriaExterna").fadeIn(500);
		e.preventDefault();
	});
	
	$("#outrasFormasCheckBox").change(function(){
		if($(this).is(":checked")){
			$("#divDescricaoOutrasFormas").show();
			$("#descricaoOutrasFormas").prop('required',true);
			$("#descricaoOutrasFormas").focus();
		}else{
			$("#divDescricaoOutrasFormas").hide();
			$("#descricaoOutrasFormas").val('');
			$("#descricaoOutrasFormas").prop('required',false);
		}
	});
	$("#submitBtnParceriaExternaForm").click(function(e) {
		checked = $("input[type=checkbox]:checked").length;
		if($("#selectParceiro").val()==""){
			$("#error-parceiro").show();
			setTimeout(function(){$("#error-parceiro").fadeOut('slow');}, 5000);
			return false;
		}
		if(!checked) {
			$("#checkBoxAlert").show();
			setTimeout(function(){
			$("#checkBoxAlert").fadeOut('slow');}, 5000);
			return false;
		}
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		var baseURL = '../parceria/salvar/';
		$.ajax({
			url : baseURL + acaoExtensaoId,
			beforeSend: function (request)
		    {
				 request.setRequestHeader(header, token);
		    },
			type : 'POST',
			async: false,
			data : $("#formParceriaExterna").serialize(),
			error: function(){
		        return false;
		    },
			success : function(data) {
				resetFormParceiria();
				if(data.status=="OK"){
					e.preventDefault();
					carregarTabelaParceriasExternas();
				}else{
					for (var i = 0; i < data.result.length; i++) {
						var alertDiv = document.getElementById("error-"+data.result[i].field);
						$(alertDiv).append("<p>"+data.result[i].defaultMessage+"</p>");
						$(alertDiv).show();
				    	setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
					}
					e.preventDefault();
					return false;
				}
				
			}
		});
	});
	
	function resetFormParceiria() {
		$("#formParceriaExterna").hide();
		$("#adicionarNovaParceriaExterna").removeAttr("disabled");
		$("#criarNovoParceiro").hide();	
		$("#formParceriaExterna")[0].reset();
		$("#divDescricaoOutrasFormas").hide();
		$("#select2-chosen-2").html("A Selecionar...");
	}
	
	$("#submitBtnParceiroForm").click(function(e){
		if($("#nomeParceiro").val()==""){
			$("#error-nome").show();
			$("#error-nome").append("<p>Nome de instituição não pode ficar vazio!</p>");
	    	setTimeout(function(){$("#error-nome").fadeOut('slow');}, 5000);
	    	return false;
		}
		if($("input[name=tipo]:checked").length == 0){
			$("#error-tipo").show();
	    	  setTimeout(function(){$("#error-tipo").fadeOut('slow');}, 5000);
	    	  return false;
		}
		var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");
	    var baseURL = '../parceiro/novo/';
		$.ajax({
			url : baseURL + acaoExtensaoId,
			beforeSend: function (request)
            {
				 request.setRequestHeader(header, token);
            },
			type : 'POST',
			async: false,
			data : $("#formParceiro").serialize(),
			error: function(){
	            return false;
	        },
			success : function(data) {
				resetFormParceiro();
				if(data.status=="OK"){
					e.preventDefault();
					buscarParceiros();
				}else{
					for (var i = 0; i < data.result.length; i++) {
						var alertDiv = document.getElementById("error-"+data.result[i].field);
						$(alertDiv).append("<p>"+data.result[i].defaultMessage+"</p>");
						$(alertDiv).show();
				    	setTimeout(function(){$(alertDiv).fadeOut('slow');}, 5000);
					}
					e.preventDefault();
					return false;
				}
				
			}
		});
	});
	
	function resetFormParceiro() {
		$("#formParceiro").hide();
		$("#formParceriaExterna").fadeIn(500);
		$("#formParceiro")[0].reset();
	}
	
	$(".selectParceiro").select2();
	
	$("#confirm-delete-parceria-externa").on("show.bs.modal", function(e) {
		$(this).find(".btn-ok").attr("href",$(e.relatedTarget).data("href"));
		$("#deleteParceriaTableIndex").val($(e.relatedTarget).data("row"));
		$("#deleteParceriaHiddenId").val($(this).find(".btn-ok").attr("href"));
	});

	$("#deleteParceriaHiddenBtn").click(function(e) {
		e.preventDefault();
		var parceriaId = $("#deleteParceriaHiddenId").val();
	    $("#confirm-delete-parceria-externa").modal('hide');
		$.ajax({
			url : '../parceria/excluir/'+parceriaId,
			beforeSend: function (request)
            {
				 request.setRequestHeader(header, token);
            },
            type : 'GET',
            async: false
		});
		carregarTabelaParceriasExternas();
	});
	
//	Busca no controller todas as parcerias externas atualizadas
	function carregarTabelaParceriasExternas() {
		var url = "../parceria/buscarParceriasExternas/" + acaoExtensaoId;
		$("#tableResultsBlock").load(url, function() {
			$("table-parcerias-externas").DataTable({
				"order" : [[ 0, "asc" ]],
			    "columnDefs" : [
			                    {className: "dt-center", "targets": 8},
			                    {"defaultContent": "-", "targets": "_all"},
			                    {"targets" : [2,3,4,5,6,7,8], "orderable" : false}
			    ],
			    "language": {
			    	"url": "../json/Portuguese-Brasil.json"
			    },
			    "paging":   false,
			    "searching": false,
			    "info":     false
			});
		});
	}
	
//	Busca as instituições parceiras no controller
	function buscarParceiros(){
		$.ajax({
			type:"GET",
			beforeSend: function (request)
	        {
				request.setRequestHeader(header, token);
	        },
			url: "../parceria/buscarParceiros",
			contentType: 'application/json',
			success : function(data) {
				$('#selectParceiro').empty();
				$("#select2-chosen-2").html("A Selecionar...");
				var newOption = $('<option value="" selected="selected">A Selecionar...</option>');
				$('#selectParceiro').append(newOption);
				for (var i = 0; i < data.length; i++) {
					var newOption = $('<option value=' + data[i].id +'>'
							+ data[i].nome
							+ '</option>');
					$('#selectParceiro').append(newOption);
				}
			}
		});
	}
});