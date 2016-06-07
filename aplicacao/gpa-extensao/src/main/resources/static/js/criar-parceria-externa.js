$(document).ready(function() {
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
	$("#submitBtn").click(function() {
	      checked = $("input[type=checkbox]:checked").length;
	      if($("#selectParceiro").val()==""){
	    	  $("#selectAlert").show();
	    	  setTimeout(function(){$("#selectAlert").fadeOut('slow');}, 10000);
	    	  return false;
	      }
	      if(!checked) {
	    	  $("#checkBoxAlert").show();
	    	  setTimeout(function(){
	    		  $("#checkBoxAlert").fadeOut('slow');}, 10000);
	    	  return false;
	      }
	});
	$('#submitBtnParceiro').click(function(){
		if($("#nomeParceiro").val()==""){
			$("#nomeParceiroAlert").show();
	    	  setTimeout(function(){$("#nomeParceiroAlert").fadeOut('slow');}, 10000);
	    	  return false;
		}
		if($("input[name=tipo]:checked").length == 0){
			$("#tipoInstituicaoAlert").show();
	    	  setTimeout(function(){$("#tipoInstituicaoAlert").fadeOut('slow');}, 10000);
	    	  return false;
			return false;
		}
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
