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
	
	
});