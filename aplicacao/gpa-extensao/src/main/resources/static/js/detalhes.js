$(document).ready(function() {
	var acaoExtensaoId = $("#acaoExtensaoId").val();
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
		var baseURL = '/gpa-extensao/salvarParceria/';
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
				if(data.status=="OK"){
					e.preventDefault();
					location.reload(true);
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
	    var baseURL = '/gpa-extensao/parceiro/novo/';
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
				if(data.status=="OK"){
					e.preventDefault();
					location.reload(true);
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
	$("#criarNovoParceiro").click(function(e){
		$("#parceria-externa-form-div").hide();
		$("#parceiro-form-div").show();
		e.preventDefault();
	});
	$("#cancelarAdicaoParceiro").click(function(e){
		$("#parceiro-form-div").hide();
		$("#parceria-externa-form-div").show();
		e.preventDefault();
	});
	$("#adicionarNovaParceriaExterna").click(function(e){
		$("#parceria-externa-form-div").show();
		e.preventDefault();
	});
	$("#cancelarAdicaoParceriaExterna").click(function(e){
		$("#parceria-externa-form-div").hide();
		e.preventDefault();
	});
	$(".selectParceiro").select2();
	$(".table-parceria-externa").DataTable();
	
	$("#confirm-delete-parceria-externa").on("show.bs.modal", function(e) {
		$(this).find(".btn-ok").attr("href",$(e.relatedTarget).data("href"));
		$("#deleteParceriaHiddenId").val($(this).find(".btn-ok").attr("href"));
	});
	$("#deleteParceriaHiddenBtn").click(function(e) {
		e.preventDefault();
		var parceriaId = $("#deleteParceriaHiddenId").val();
		var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			url : '/gpa-extensao/excluir/' + acaoExtensaoId + '/parceriaExterna/'+parceriaId,
			beforeSend: function (request)
            {
				 request.setRequestHeader(header, token);
            },
			type : 'GET',
			complete: function(){
				window.location.replace(acaoExtensaoId);
			}
		});
	});
});
