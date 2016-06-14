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
	$("#submitBtn").click(function(e) {
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
			data : $("#parceriaExternaForm2").serialize(),
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
	$('#submitBtnParceiro').click(function(e){
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
			data : $("#parceiroForm2").serialize(),
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
		$("#parceriaExternaForm").hide();
		$("#parceiroForm").show();
	});
	$("#cancelarNovoParceiro").click(function(){
		$("#parceiroForm").hide();
		$("#parceriaExternaForm").show();
	});
	$(".selectParceiro").select2();
});
