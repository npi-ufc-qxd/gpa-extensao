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
	$('#submitBtn').click(function() {
	      checked = $("input[type=checkbox]:checked").length;
	      if(!checked) {
	    	  var selected = $("#selectParceiro").val();
	    	  console.log(selected);
	    	  $("#checkBoxAlert").show();
	        return false;
	      }
	});
	$('#submitBtnParceiro').click(function(){
		if($("input[name=tipo]:checked").length == 0){
			console.log("check radio");
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
