function elemento(numCasilla) {
	return document.getElementById('formulario:cas' + numCasilla);
}

function elementoSec(numCasilla) {
	return document.getElementById('formulario:cas' + numCasilla + "_sec");
}

function mostrarMensajeError(mensaje) {

	document.getElementById('formulario:mensajeError').textContent = mensaje;
	document.getElementById('formulario:mensajeError').innerText = mensaje;
	Richfaces.showModalPanel('panelError');
	return false;
}
function elementoFecha(numCasilla) {
	return document.getElementById('formulario:cas' + numCasilla + 'InputDate');
}

function elementoId(numCasilla) {
	return document.getElementById('formulario:cas' + numCasilla + '_ids');
}

function deshabilitarCasilla(numeroCasilla) {

	elemento(numeroCasilla).className = 'deshabilitado';

	if (elemento(numeroCasilla).type == "checkbox") {
		elemento(numeroCasilla).disabled = true;
		elemento(numeroCasilla).ckecked = false;
	} else if (elemento(numeroCasilla).type == "select-one") {
		elemento(numeroCasilla).disabled = true;
		elemento(numeroCasilla).value = "";
	} else {
		elemento(numeroCasilla).readOnly = true;
		elemento(numeroCasilla).value = "";
	}

}

function deshabilitarCasillaFecha(numeroCasilla) {
	deshabilitarCasilla(numeroCasilla + 'InputDate');
	document.getElementById('formulario:cas' + numeroCasilla + 'PopupButton').style.display = 'none';
}

function deshabilitarCasillaId(numeroCasilla) {

	if (elementoId(numeroCasilla).type == "checkbox") {
		elementoId(numeroCasilla).disabled = true;
		elementoId(numeroCasilla).ckecked = false;
	} else if (elementoId(numeroCasilla).type == "select-one") {
		elementoId(numeroCasilla).disabled = true;
		elementoId(numeroCasilla).value = "";
	} else {
		elementoId(numeroCasilla).readOnly = true;
		elementoId(numeroCasilla).value = "";
	}

}

function deshabilitarCasillaSinBorrar(numeroCasilla) {

	elemento(numeroCasilla).className = 'deshabilitado';

	if (elemento(numeroCasilla).type == "checkbox") {
		elemento(numeroCasilla).disabled = true;
	} else if (elemento(numeroCasilla).type == "select-one") {
		elemento(numeroCasilla).disabled = true;
	} else {
		elemento(numeroCasilla).readOnly = true;
	}

}

function deshabilitarCasillaSinBorrarFecha(numeroCasilla) {
	deshabilitarCasillaSinBorrar(numeroCasilla + 'InputDate');
	document.getElementById('formulario:cas' + numeroCasilla + 'PopupButton').style.display = 'none';
}

function habilitarCasilla(numeroCasilla) {

	elemento(numeroCasilla).className = '';

	if (elemento(numeroCasilla).type == "checkbox") {
		elemento(numeroCasilla).disabled = false;
	} else if (elemento(numeroCasilla).type == "select-one") {
		elemento(numeroCasilla).disabled = false;
	} else {
		elemento(numeroCasilla).readOnly = false;
	}

}

function habilitarCasillaFecha(numeroCasilla) {
	habilitarCasilla(numeroCasilla + 'InputDate');
	document.getElementById('formulario:cas' + numeroCasilla + 'PopupButton').style.display = '';
}

function comprobarNif(obj) {
	var text = obj.value;

	if (text != "") {
		while (text.length < 9)
			text = '0' + text;

		obj.value = text;
	}
}

function redondear(cantidad, decimales) {
	var cantidad = parseFloat(cantidad);
	var decimales = parseFloat(decimales);
	decimales = (!decimales ? 2 : decimales);
	return Math.round(cantidad * Math.pow(10, decimales))
			/ Math.pow(10, decimales);

}

// obtener la fecha de hoy con el formato dd/mm/yyyy
function obtenerFechaActual() {
	var fec = new Date();
	var dia = fec.getDate();
	if (dia < 10)
		dia = '0' + dia;
	var mes = fec.getMonth();
	if (mes < 10)
		mes = '0' + mes;
	var anio = fec.getFullYear();
	var fecha = dia + '/' + mes + '/' + anio;

	return fecha;
}

// NOMBRE: quitarFormato
// FUNCION: Deja el formato del n�mero en (xxxx.xx) un s�lo punto decimal
// PARAMETROS: valor: El n�mero ;; nombre: El nombre del campo.
function quitarFormato(valor) {
	if (valor != '') {
		var aux = replaceTotal(".", "", valor);
		return replaceTotal(",", ".", aux);
	}

}

// NOMBRE: darFormato
// FUNCION: Pone el formato del n�mero en (x.xxx,xx) o sea el formato
// tradicional
// PARAMETROS: valor: El objeto elemento

function darFormato(valor) {
	valor = replaceTotal(".", ",", valor);
	if (valor != "") {
		prefix = '';
		var splitStr = valor.split(',');
		var splitLeft = splitStr[0];
		var splitRight = ",00";
		// var splitRight = splitStr.length > 1 ? ',' + splitStr[1] : '';
		if (splitStr.length > 1) {
			der = splitStr[1];
			if (der.length == 1) {
				splitRight = "," + splitStr[1] + "0";
			} else if (der.length == 2) {
				splitRight = "," + splitStr[1];
			} else {

			}
		}
		var regx = /(\d+)(\d{3})/;
		while (regx.test(splitLeft)) {
			splitLeft = splitLeft.replace(regx, '$1' + '.' + '$2');
		}
		return prefix + splitLeft + splitRight;
	}
}

/**
 * Equivalente a la funcion replaceAll de java Reemplaza el origen por el
 * destino dentro de la cadena tantas veces como aparezca.
 */
function replaceTotal(origen, destino, cadena) {
	out = origen;
	add = destino;
	temp = "" + cadena;

	while (temp.indexOf(out) > -1) {
		pos = temp.indexOf(out);
		temp = ""
				+ (temp.substring(0, pos) + add + temp.substring(
						(pos + out.length), temp.length));
	}
	return temp;
}

function vacio(valor) {
	if (valor == "" || valor == "NaN" || valor == undefined || valor == null)
		return true;
	else
		return false;
}

function DiferenciaFechas(CadenaFecha1, CadenaFecha2) {

	// Obtiene dia, mes y a�o
	var fecha1 = new fecha(CadenaFecha1);
	var fecha2 = new fecha(CadenaFecha2);

	// Obtiene objetos Date
	var miFecha1 = new Date(fecha1.anio, fecha1.mes - 1, fecha1.dia);
	var miFecha2 = new Date(fecha2.anio, fecha2.mes - 1, fecha2.dia);

	// Resta fechas y redondea
	var diferencia = miFecha2.getTime() - miFecha1.getTime();
	var dias = Math.round(diferencia / (1000 * 60 * 60 * 24));

	return (parseInt(dias) + 1);
}

function addTimeToDate(time, unit, objDate, dateReference) {
	var dateTemp = (dateReference) ? objDate : new Date(objDate);
	switch (unit) {
	case 'y':
		dateTemp.setFullYear(objDate.getFullYear() + time);
		break;
	case 'M':
		dateTemp = new Date(objDate).add(time).month();
		break;
	case 'w':
		dateTemp.setTime(dateTemp.getTime() + (time * 7 * 24 * 60 * 60 * 1000));
		break;
	case 'd':
		dateTemp.setTime(dateTemp.getTime() + (time * 24 * 60 * 60 * 1000));
		break;
	case 'h':
		dateTemp.setTime(dateTemp.getTime() + (time * 60 * 60 * 1000));
		break;
	case 'm':
		dateTemp.setTime(dateTemp.getTime() + (time * 60 * 1000));
		break;
	case 's':
		dateTemp.setTime(dateTemp.getTime() + (time * 1000));
		break;
	default:
		dateTemp.setTime(dateTemp.getTime() + time);
		break;
	}
	return dateTemp;
}

function SumarAnos(cadenaFecha, numAnos) {

	var fecha1 = new fecha(cadenaFecha);
	var cadenaFecha = fecha1.mes + "/" + fecha1.dia + "/" + fecha1.anio;

	var resultado = addTimeToDate(numAnos, "y", new Date(cadenaFecha), false);

	return resultado.getDate() + "/" + (resultado.getMonth() + parseInt(1))
			+ "/" + resultado.getFullYear();
}

function SumarMes(cadenaFecha, numMes) {

	var fecha1 = new fecha(cadenaFecha);
	var cadenaFecha = fecha1.mes + "/" + fecha1.dia + "/" + fecha1.anio;

	var resultado = addTimeToDate(numMes, "M", new Date(cadenaFecha), false);

	return resultado.getDate() + "/" + (resultado.getMonth() + parseInt(1))
			+ "/" + resultado.getFullYear();
}

function SumarDias(cadenaFecha, numDias) {

	var fecha1 = new fecha(cadenaFecha);
	var cadenaFecha = fecha1.mes + "/" + fecha1.dia + "/" + fecha1.anio;

	var resultado = addTimeToDate(numDias, "d", new Date(cadenaFecha), false);

	return resultado.getDate() + "/" + (resultado.getMonth() + parseInt(1))
			+ "/" + resultado.getFullYear();
}

function fecha(cadena) {

	// Separador para la introduccion de las fechas
	var separador = "/";

	// Separa por dia, mes y a�o
	if (cadena.indexOf(separador) != -1) {
		var posi1 = 0;
		var posi2 = cadena.indexOf(separador, posi1 + 1);
		var posi3 = cadena.indexOf(separador, posi2 + 1);
		this.dia = cadena.substring(posi1, posi2);
		this.mes = cadena.substring(posi2 + 1, posi3);
		this.anio = cadena.substring(posi3 + 1, cadena.length);
	} else {
		this.dia = 0;
		this.mes = 0;
		this.anio = 0;
	}
}

function sumarValores610Float(inicioRango, finRango, numDestino) {

	var i = parseInt(inicioRango);
	var total = 0;

	while (i <= finRango) {
		total = parseFloat(total)
				+ parseFloat(quitarFormato(elemento(i).value));
		i = i + 3;
	}

	total = redondear(total, 2);
	elemento(numDestino).value = darFormato(total);
}

function sumarValores610Integer(inicioRango, finRango, numDestino) {

	var i = inicioRango;
	var total = 0;

	while (i <= finRango) {
		total = parseInt(total) + parseInt(quitarFormato(elemento(i).value));
		i = i + 3;
	}

	elemento(numDestino).value = darFormato(total);
}

function sumarValores610Impares(origen, destino, producto, sumatorio,
		sumatorio2, inicioRango, finRango) {

	var valorOrigen = origen.value;
	var resultado = parseInt(valorOrigen) * producto;
	destino.value = darFormato(resultado);

	var totalDocs = 0;
	var totalResult = 0;
	var i = inicioRango;
	while (i <= finRango) {
		if (elemento(i).value != "") {
			if (i % 2 != 0) {
				totalDocs = parseInt(totalDocs) + parseInt(elemento(i).value);
			} else {
				totalResult = parseFloat(totalResult)
						+ parseFloat(quitarFormato(elemento(i).value));
			}
		}
		i++;

	}

	var valIntermedio = parseInt(sumatorio.value);
	sumatorio.value = valIntermedio + parseInt(totalDocs);

	valIntermedio = parseFloat(quitarFormato(sumatorio2.value));
	valIntermedio = valIntermedio + parseFloat(totalResult);
	sumatorio2.value = darFormato(valIntermedio);

}

function sumarTotales(origen1, origen2, destino) {
	var valorOrigen1 = parseInt(0);

	if (origen1.value != "") {
		valorOrigen1 = parseInt(origen1.value);
	}

	var valorOrigen2 = parseInt(0);

	if (origen2.value != "") {
		valorOrigen2 = parseInt(origen2.value);
	}

	destino.value = valorOrigen1 + valorOrigen2;

}

function sumarTotalesFloat(origen1, origen2, destino) {
	var valorOrigen1 = parseFloat(0.0);
	if (origen1.value != "") {
		valorOrigen1 = parseFloat(quitarFormato(origen1.value));
	}

	var valorOrigen2 = parseFloat(0.0);
	if (origen2.value != "") {
		valorOrigen2 = parseFloat(quitarFormato(origen2.value));
	}

	destino.value = darFormato(valorOrigen1 + valorOrigen2);

}

function deshabilitarBloque(inicio, fin) {

	for (var i = inicio; i <= fin; i++) {
		if (elemento(i) != null) {
			deshabilitarCasilla(i);

		}
		if (elementoFecha(i) != null) {
			deshabilitarCasillaFecha(i);

		}
		if (elementoId(i) != null) {
			deshabilitarCasillaId(i);
		}
	}
}

function habilitarBloque(inicio, fin) {

	for (var i = inicio; i <= fin; i++) {
		if (elemento(i) != null) {
			habilitarCasilla(i);
		}
		if (elementoFecha(i) != null) {
			habilitarCasillaFecha(i);

		}
	}
}

function habilitarBorrandoBloque(inicio, fin) {

	for (var i = inicio; i <= fin; i++) {
		if (elemento(i) != null) {
			habilitarCasilla(i);
			elemento(i).value = "";
		}
		if (elementoFecha(i) != null) {
			habilitarCasillaFecha(i);
			elemento(i + 'InputDate').value = "";
		}
	}
}

function deshabilitarBloqueSinBorrar(inicio, fin) {

	for (var i = inicio; i <= fin; i++) {
		if (elemento(i) != null) {
			deshabilitarCasillaSinBorrar(i);

		}
		if (elementoFecha(i) != null) {
			deshabilitarCasillaSinBorrarFecha(i);
		}
	}
}
