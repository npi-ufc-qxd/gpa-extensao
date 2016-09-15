$(document).ready(function() {
	$("#select-servidor").select2();
	
	$("#table-participacoes").DataTable({
		"order" : [[0, "asc"]],
		"columnDefs" : [
		                {className: "text-center", "targets": [1, 2, 3, 4]},
		                {"defaultContent": "-", "targets": "_all"},
		                {"targets" : 2, "orderable" : false},
		                {"targets" : 3, "orderable" : false},
		                {"targets" : 4, "orderable" : false},
		                ],
		"language": {
		        	"url": "../json/Portuguese-Brasil.json"
		        	},
		"paging": false,
	    "searching": false,
	    "info":     false,
	});
});