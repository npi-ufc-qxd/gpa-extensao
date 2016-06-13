$(document).ready(function(){
    $("#input-codigo").hide();
    $("#input-bolsas").hide();
    $("#salvar-codigo").hide();
    $("#salvar-bolsas").hide();
	
	$("#editar-codigo").click(function(){
        $("#input-codigo").show();
        $("#codigo-acao").hide();
        $("#editar-codigo").hide();
        $("#salvar-codigo").show();
        
    });
	
	$("#editar-bolsas").click(function(){
		$("#input-bolsas").show();
		$("#bolsas-recebidas").hide();
		$("#editar-bolsas").hide();
		$("#salvar-bolsas").show();
	});
	
	$("#salvar-codigo").click(function(){
		$("#salvar-codigo").hide();
		$("#input-codigo").hide();
		$("#editar-codigo").show();
		$("#codigo-acao").show();
		
	});
	
	$("#salvar-bolsas").click(function(){
		$("#salvar-bolsas").hide();
		$("#input-bolsas").hide();
		$("#editar-bolsas").show();
		$("#bolsas-recebidas").show();
		
	});
});