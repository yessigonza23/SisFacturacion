/**
 * 
 */
function mayusculas(elemento) {
	elemento.value = elemento.value.toUpperCase();
}

function minusculas(elemento) {
	elemento.value = elemento.value.toLowerCase();
}

function solonumeros(e){
	key=e.keyCode || e.which;
	teclado=String.fromCharCode(key);
	numeros="0123456789";
	especiales="8-37-38-46";
	teclado_especial=false;
	for(var i in especiales){
		if(key==especiales[i]){
			teclado_especial=true;
		}
	}
	if(numeros.indexOf(teclado)==-1 && !teclado_especial){
		return false;
	}
}

function deshabilitarEnter(e) {
	var k = window.event ? window.event.keyCode : e.which;
	return (k != 13);
}