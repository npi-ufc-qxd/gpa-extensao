$(document).ready(function() {
	$("#selectCoordenador,#modalidadeSelect").select2();
	
	$("#tableAcoes").DataTable({
		"order" : [[ 0, "asc" ]],
		"columnDefs" : [
		                {className: "text-center", "targets": [0, 3, 4]},
		                {"targets" : 0, "orderable" : false},
		                {"targets" : 1, "orderable" : false},
		                {"targets" : 4, "orderable" : false},
		                ],
		"language": {
			"url": "../json/Portuguese-Brasil.json"
					}
		
	});
	
	$("#anoAcao").datepicker({
	    format: "yyyy",
	    startView: 2,
	    minViewMode: 2,
	    maxViewMode: 2,
	    language: "pt-BR",
	    autoclose: true,
	    todayBtn: true
	});
});