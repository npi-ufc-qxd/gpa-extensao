$(document).ready(function(){
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
	
	$('#excluiracao').click(function() {
		var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({url: "/gpa-extensao/deletar/" + acaoExtensaoId,
			type : 'POST',
			beforeSend: function (request)
            {
				 request.setRequestHeader(header, token);
            },
			complete: function(){
				window.location.replace("http://localhost:8080/gpa-extensao/coordenacao/listagem");
	    }});
			    
	});
	

	
});