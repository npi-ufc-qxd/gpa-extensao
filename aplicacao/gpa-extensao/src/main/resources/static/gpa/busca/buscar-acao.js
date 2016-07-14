$(document).ready(function() {
	$("#selectCoordenador,#kkkkkkk").select2();
	
	$("#tableAcoes").DataTable({
		"order" : [[ 0, "asc" ]],
		"columnDefs" : [
		                {className: "text-center", "targets": [0, 3]},
		                {"targets" : 0, "orderable" : false},
		                {"targets" : 1, "orderable" : false},
		                ],
		"language": {
			"url": "/gpa-extensao/js/Portuguese-Brasil.json"
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