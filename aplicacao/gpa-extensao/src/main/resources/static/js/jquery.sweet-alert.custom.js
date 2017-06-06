
!function($) {
    "use strict";
    var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
    var SweetAlert = function() {};

    //examples 
    SweetAlert.prototype.init = function() {
        
    //Basic
    $('#sa-basic').click(function(){
        swal("Here's a message!");
    });

    //A title with a text under
    $('#sa-title').click(function(){
        swal("Here's a message!", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed lorem erat eleifend ex semper, lobortis purus sed.")
    });

    //Warning Message
    $('#sa-warning').click(function(){
        swal({   
            title: "Are you sure?",   
            text: "You will not be able to recover this imaginary file!",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "Yes, delete it!",   
            closeOnConfirm: false 
        }, function(){   
            swal("Deleted!", "Your imaginary file has been deleted.", "success"); 
        });
    });
    
    $('.sa-warning').click(function(){
		var x = document.querySelector(".confirm");
		var link = this.getAttribute("link");
		var action = this.getAttribute("action");
		var redirect = this.getAttribute("redirect");
		console.log(link);
		if(action === "excluir"){
		
			swal({   
				title: "Você tem certeza?",   
				text: "Esta ação é irrevesível!",   
				type: "warning",   
				showCancelButton: true,   
				confirmButtonColor: "#DD6B55",   
				confirmButtonText: "Sim, apagar!", 
				cancelButtonText:"Cancelar",
				closeOnConfirm: false
			});
    	
			var list = document.getElementsByClassName("confirm");
        
			list[0].onclick = function(){
				$.ajax({
					url : link,
					beforeSend: function (request)
					{
						request.setRequestHeader(header, token);
					},
					type : 'POST',
					error: function(){
     		        return false;
					},
					success : function(result) {
						if(result.erro != null){
							swal("Erro!", result.erro, "error");
						}else{
							swal("Apagado!", "O item selecionado foi removido com sucesso.", "success"); 	
							var list = document.getElementsByClassName("confirm");
							list[0].onclick = function(){
								if(redirect != null){
									window.location.replace("/");
								}else{
									location.reload();
								}
							};
						}
					}
				});
			};
		}else if(action === "encerrar"){
			swal({   
				title: "Você tem certeza?",   
				text: "Esta ação é irrevesível!",   
				type: "warning",   
				showCancelButton: true,   
				confirmButtonColor: "#DD6B55",   
				confirmButtonText: "Sim, encerrar!", 
				cancelButtonText:"Cancelar",
				closeOnConfirm: false
			});
    	
			var list = document.getElementsByClassName("confirm");
        
			list[0].onclick = function(){
				$.ajax({
					url : link,
					beforeSend: function (request)
					{
						request.setRequestHeader(header, token);
					},
					type : 'POST',
					error: function(){
     		        return false;
					},
					success : function(result) {
						if(result.erro != null){
							swal("Erro!", result.erro, "error");
						}else{
							swal("Encerrada!", "A ação foi encerrada com sucesso.", "success"); 	
							var list = document.getElementsByClassName("confirm");
							list[0].onclick = function(){
								location.reload(); 	
							};
						}
					}
				});
			};
		}
    });
    
    
    //Parameter
    $('#sa-params').click(function(){
        swal({   
            title: "Are you sure?",   
            text: "You will not be able to recover this imaginary file!",   
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",   
            confirmButtonText: "Yes, delete it!",   
            cancelButtonText: "No, cancel plx!",   
            closeOnConfirm: false,   
            closeOnCancel: false 
        }, function(isConfirm){   
            if (isConfirm) {     
                swal("Deleted!", "Your imaginary file has been deleted.", "success");   
            } else {     
                swal("Cancelled", "Your imaginary file is safe :)", "error");   
            } 
        });
    });

    //Custom Image
    $('#sa-image').click(function(){
        swal({   
            title: "Govinda!",   
            text: "Recently joined twitter",   
            imageUrl: "../plugins/images/users/govinda.jpg" 
        });
    });

    //Auto Close Timer
    $('#sa-close').click(function(){
         swal({   
            title: "Auto close alert!",   
            text: "I will close in 2 seconds.",   
            timer: 2000,   
            showConfirmButton: false 
        });
    });
    
    },
    //init
    $.SweetAlert = new SweetAlert, $.SweetAlert.Constructor = SweetAlert
}(window.jQuery),

//initializing 
function($) {
    "use strict";
    $.SweetAlert.init()
}(window.jQuery);