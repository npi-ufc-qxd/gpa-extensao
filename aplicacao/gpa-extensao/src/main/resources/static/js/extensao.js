function validar (input, element) {
	var data = input.val();
	var resData = data.split('/');
	
	var dia = resData[0];
	var mes = resData[1];
	var ano = resData[2];
	
	if(dia == "__" || mes == "__" || ano == "____") {
		input.val("");
		element.addClass('no-display');
	}else if(dia === "00" || mes === "00" || ano === "0000") {
		input.val("");
		element.text("Data Inválida");
		element.removeClass('no-display');
	} else {	
		var parseDia = parseInt(dia);
		var parseMes = parseInt(mes);
		
		if(parseMes <= 12) {
			if(parseMes == 1 || parseMes == 3 || parseMes == 5 || parseMes == 7 || parseMes == 8 
					|| parseMes == 10 || parseMes == 12) {
				if(parseDia > 31) {
					input.val("");
					element.text("Esse mês possui 31 dias");
					element.removeClass('no-display');
				}else {
					element.addClass('no-display');
				}
			} else if(parseMes == 4 || parseMes == 6 || parseMes == 9 || parseMes == 11) {
				if(parseDia > 30) {
					input.val("");
					element.text("Esse mês possui 30 dias");
					element.removeClass('no-display');
				}else {
					element.addClass('no-display');
				}
			} else if(parseMes == 2){
				if(parseDia > 29) {
					input.val("");
					element.text("Esse mês possui 28 ou 29 dias");
					element.removeClass('no-display');
				}else {
					element.addClass('no-display');
				}
			} 
		} else if(parseMes > 12){
			input.val("");
			element.text("Esse mês não existe");
			element.removeClass('no-display');
		} 
	}
	
}