$(document).ready(function() {
	$("#select-servidor").select2();
	
	$("#table-participacoes").DataTable({
		"order" : [[0, "asc"]],
		"columnDefs" : [
		                {className: "text-center", "targets": [1, 2, 3, 4]},
		                {"defaultContent": "-", "targets": "_all"},
		                ],
		"language": {
		        	"url": "../json/Portuguese-Brasil.json"
		        	},
	    "searching": false,
	    "info":     false,
	});
});