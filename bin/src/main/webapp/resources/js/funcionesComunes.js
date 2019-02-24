var VALOR_INICIAL_COMBO = "";
// /////////////////////////////////////////////////////////////////////////
// /////////////////////////////////////////////////////////////////////////
// ///////// Limpiar los filtros de busqueda ///////////
// /////////////////////////////////////////////////////////////////////////
// /////////////////////////////////////////////////////////////////////////
function limpiarCamposFormularioBusqueda() {

	// obtener el marco de los filtros de busqueda
	var filtrosBusqueda = $('filtrosBusqueda');
	camposInput = filtrosBusqueda.getElementsByTagName('input');

	// Deseleccionar columna anterior
	for (i = 0; i < camposInput.length; i++) {
		ele = camposInput[i];
		if (ele.className.indexOf('deshabilitado') == -1) {
			if (ele.type == 'text') {
				ele.value = "";
			} else if (ele.type == 'checkbox') {
				ele.checked = false;
			} else if (ele.type == 'radio') {
				ele.checked = false;
				// esto falla cuando se hace el limpiar en una pantalla de
				// busqueda
				// que tiene radio button
				// ele.value = "";
			}
		}
	}

	var camposSelect = filtrosBusqueda.getElementsByTagName('select');

	// Deseleccionar columna anterior
	for (i = 0; i < camposSelect.length; i++) {
		ele = camposSelect[i];
		// seleccionar por el valor que se le ha asignado al valor inicial
		// ele.value = VALOR_INICIAL_COMBO;

		// seleccionar el primer elemento del combo
		if (ele[0] != null) {
			ele[0].selected = true;
		}
	}

	// obtener los textarea
	camposTextArea = filtrosBusqueda.getElementsByTagName('textarea');

	// borrar textarea
	for (i = 0; i < camposTextArea.length; i++) {
		ele = camposTextArea[i];
		if (ele.className.indexOf('deshabilitado') == -1) {
			ele.value = "";
		}
	}
}

/**
 * Limpiar formulario bandeja tareas.
 */
function limpiarFormBandejaTareas() {
	$('formulario:ideRueBusq').value = "";
	$('formulario:idVersionCircuito').value = "";
	$('formulario:ideDocBusq').value = "";
	$('formulario:nifBusq').value = "";
}


// /////////////////////////////////////////////////////////////////////////
// /////////////////////////////////////////////////////////////////////////
// ///////// Limpiar los filtros de busqueda, excepto ids //////////
// /////////////////////////////////////////////////////////////////////////
// /////////////////////////////////////////////////////////////////////////
function limpiarCamposFormularioBusquedaExceptoIds(ids) {

	listaIds = ids.split("#");

	// obtener el marco de los filtros de busqueda
	var filtrosBusqueda = $('filtrosBusqueda');
	camposInput = filtrosBusqueda.getElementsByTagName('input');

	// Deseleccionar columna anterior
	for (i = 0; i < camposInput.length; i++) {

		ele = camposInput[i];
		if (ele.className.indexOf('deshabilitado') == -1) {
			if (ele.type == 'text') {
				ele.value = "";
			}
		}
	}

	var camposSelect = filtrosBusqueda.getElementsByTagName('select');

	// Deseleccionar columna anterior
	for (i = 0; i < camposSelect.length; i++) {
		encontrado = false;
		ele = camposSelect[i];

		for (j = 0; j < listaIds.length; j++) {
			if (ele.id == "formulario:" + listaIds[j]) {
				encontrado = true;
			}
		}
		if (!encontrado) {
			if (ele[0] != null)
				ele[0].selected = true;

		}
	}
}

/**
 * Limpiar los campos de una pantalla alta.
 * 
 * @return
 */
function limpiarCamposPantallaAlta() {
	// obtener los input
	camposInput = document.getElementsByTagName('input');
	for (i = 0; i < camposInput.length; i++) {
		ele = camposInput[i];
		if (ele.type == 'text') {
			if (ele.className.indexOf('deshabilitado') == -1) {
				ele.value = "";
			}
		} else if (ele.type == 'checkbox') {
			if (ele.className.indexOf('deshabilitado') == -1) {
				ele.checked = false;
			}
		} else if (ele.type == 'radio') {
			if (ele.className.indexOf('deshabilitado') == -1) {
				ele.checked = false;
			}
		}

		volverAMarcarObligatoriosActivados(ele);

	}
	// obtener los select
	var camposSelect = document.getElementsByTagName('select');
	for (i = 0; i < camposSelect.length; i++) {
		ele = camposSelect[i];
		// seleccionar el primer elemento del combo
		if (ele[0] != null)
			if (ele[0].className.indexOf('deshabilitado') == -1) {
				ele[0].selected = true;
			}

		volverAMarcarObligatoriosActivados(ele);

	}
	// TEXTAREA
	camposTextarea = document.getElementsByTagName('textarea');
	// Deseleccionar columna anterior
	for (i = 0; i < camposTextarea.length; i++) {
		ele = camposTextarea[i];
		if (ele.className.indexOf('deshabilitado') == -1) {
			ele.value = "";

			volverAMarcarObligatoriosActivados(ele);
		}
	}
	// TODO: faltan pick list, pantallas con pestañas

	// poner en verde los campos en rojo
}

/**
 * Limpiar los campos imput o textarea de una pantalla indicando el id del campo
 * 
 * jjpozo 13/02/2012
 * 
 * @return
 */
function limpiarCampoPorId(id) {
	// TEXTAREA
	camposTextarea = document.getElementsByTagName('textarea');
	// Deseleccionar columna anterior
	for (i = 0; i < camposTextarea.length; i++) {
		ele = camposTextarea[i];
		if (ele.id == id) {
			ele.value = "";
		}
	}
	// imput
	camposInput = document.getElementsByTagName('input');
	// Deseleccionar columna anterior
	for (i = 0; i < camposInput.length; i++) {
		ele = camposInput[i];
		if (ele.id == id) {
			ele.value = "";
		}
	}
}

/**
 * Volver a marcar como obligatorios los campos resaltados por la validacion.
 * 
 * @param ele -
 *            element
 * @return
 */
function volverAMarcarObligatoriosActivados(ele) {
	if (ele.className.indexOf('highlight') != -1) {
		ele.className = "obligatorio";
	}
}

function limpiarFormularioBusquedaCircuito() {
	// $('formulario:idCircuito').disabled=false;
	// $('formulario:idAgrupacion').disabled=false;
	// $('formulario:version').disabled=true;
	limpiarCamposFormularioBusqueda();
}

function limpiarFormularioBusquedaTramitacionBuzon() {
	$('formulario:tramites_circuito').disabled = true;
	limpiarCamposFormularioBusqueda();
}

function limpiarFormularioBusquedaUAResps() {
	$('formulario:tramites').disabled = true;
	limpiarCamposFormularioBusqueda();
}

function limpiarFormularioBusquedaBandejaTareas() {
	$('formulario:version').disabled = true;
	limpiarCamposFormularioBusqueda();
}

function limpiarCamposFormularioBusquedaPestanias(nombreFiltrosBusqueda) {

	// obtener el marco de los filtros de busqueda
	filtrosBusqueda = $(nombreFiltrosBusqueda);
	camposInput = filtrosBusqueda.getElementsByTagName('input');

	// Deseleccionar columna anterior
	for (i = 0; i < camposInput.length; i++) {
		ele = camposInput[i];
		if (ele.type == 'text') {
			ele.value = "";
		}
	}

	camposInput = filtrosBusqueda.getElementsByTagName('select');

	// Deseleccionar columna anterior
	for (i = 0; i < camposInput.length; i++) {
		ele = camposInput[i];
		// ele.value = VALOR_INICIAL_COMBO;
		// seleccionar el primer elemento del combo
		if (ele[0] != null) {
			ele[0].selected = true;
		}
	}
}

function vaciarCampos(nombreDiv) {

	// alert("entra");
	filtrosBusqueda = $(nombreDiv);
	camposInput = filtrosBusqueda.getElementsByTagName('input');
	for (i = 0; i < camposInput.length; i++) {
		// alert("elemento: "+ele.value);
		ele = camposInput[i];
		if (ele.type == 'text') {
			ele.value = "";
		}

	}
}
var nav4 = !window.ActiveXObject ? true : false;
// Impide que se escriban letras.
function acceptNum(evt) {
	// NOTE: Backspace = 8, Enter = 13, '0' = 48, '9' = 57
	var key = nav4 ? evt.which : evt.keyCode;
	return (key <= 13 || (key >= 48 && key <= 57) || (key == 67 && evt.ctrlKey)
			|| (key == 99 && evt.ctrlKey) || (key == 88 && evt.ctrlKey)
			|| (key == 120 && evt.ctrlKey) || (key == 86 && evt.ctrlKey) || (key == 118 && evt.ctrlKey));
}

/**
 * Funcion que se llama al cargar cualquier pagina.
 * 
 * @return
 */
function InicioDefault() {

	// alert("antes del foco");
	// poner el foco en el primer input o select
	ponerFocoPrimerCampo();

	// alert("despues del foco");
	// guardar el form en una variable para las pantallas de alta/modificacion
	// comprobar si en la pantalla existe el campo id --> si existe (pantalla
	// alta o modificacion)
	var valId = document.getElementById("formulario:id");
	if (valId != undefined) {
		// alert("Valor id: " + valId);
		onLoadCargarFormulario();
	}
}

/**
 * Seleccionar todos los check del listado de busqueda.
 */
function selectAllCheck() {

	var valueCheckAll = document
			.getElementById("formulario:tablaResultBusq:checkSelectAll").checked;

	// obtener el marco de los filtros de busqueda
	var tablaResultadoBusq = $('formulario:tablaResultBusq');
	camposCheck = tablaResultadoBusq.getElementsByTagName('input');

	// Deseleccionar columna anterior
	for (i = 0; i < camposCheck.length; i++) {
		ele = camposCheck[i];
		if (ele.type == "checkbox") {
			ele.checked = valueCheckAll;
		}
	}
}

function InicioPA() {

	ponerFocoPrimerCampoPA();

	var valId = document.getElementById("formulario:id");
	if (valId != undefined) {
		onLoadCargarFormulario();
	}
}

/**
 * Pone el foco en el primer text o select que encuentre, sin deshabilitar y sin
 * ocultar.
 * 
 * @return
 */
function ponerFocoPrimerCampo() {
	// alert("ponerFocoPrimerCampo");

	var campos = $("formulario");

	for (i = 0; i < campos.length; i++) {
		var ele = campos[i];
		if ((ele.type == "text" || ele.type == "select-one"
				|| ele.type == "select" || ele.type == "textarea")
				&& (ele.readOnly == false || ele.readOnly == undefined)
				&& (ele.disabled == false || ele.disabled == undefined)
				// comprobar que no este oculto
				&& ele.style.display != "none") {
			// alert("nombre: " + ele.name + " tipo: " + ele.type + " readOnly:
			// " +
			// ele.readOnly + " disabled: " + ele.disabled + " display: " +
			// ele.style.display);
			try {
				ele.focus();
			} catch (err) {
			}
			// if (ele.type == "text" || ele.type == "textarea") {
			// var nav4 = !window.ActiveXObject ? true : false;
			// if (nav4) {
			// ele.setSelectionRange(0,0);
			// }
			// }
			break;
		}
	}
}

function ponerFocoPrimerCampoPA() {
	// alert("ponerFocoPrimerCampo");

	var campos = $("formulario");
	var min = 9999;
	var primero = null;
	for (i = 0; i < campos.length; i++) {
		var ele = campos[i];
		if ((ele.type == "text" || ele.type == "select-one"
				|| ele.type == "select" || ele.type == "textarea")
				&& (ele.readOnly == false || ele.readOnly == undefined)
				&& (ele.disabled == false || ele.disabled == undefined)
				&& ele.style.display != "none") {

			try {
				if (parseInt(ele.tabIndex) < min) {
					primero = ele;
					min = ele.tabIndex;
				}
			} catch (err) {
			}
			if (ele.type == "text" || ele.type == "textarea") {
				var nav4 = !window.ActiveXObject ? true : false;
				if (nav4) {
					ele.setSelectionRange(0, 0);
				}
			}
			break;
		}
	}
	if (primero != null) {
		primero.focus();
	}
}

/**
 * Set focus on the element of the given id.
 * 
 * @param id
 *            The id of the element to set focus on.
 */
function setFocus(id) {
	var element = document.getElementById(id);
	if (element && element.focus) {
		element.focus();
	}
}

/**
 * Set highlight on the elements of the given ids. It basically sets the
 * classname of the elements to 'highlight'. This require at least a CSS style
 * class '.highlight'.
 * 
 * @param ids
 *            The ids of the elements to be highlighted, comma separated.
 */
function setHighlight(ids) {
	var idsArray = ids.split(",");
	for ( var i = 0; i < idsArray.length; i++) {
		var element = document.getElementById(idsArray[i]);
		if (element) {
			element.className = 'highlight';
		}
	}
}

function inicializa(campos) {
	var arrayCampos = campos.split(";");
	for ( var i = 0; i < arrayCampos.length; i++) {
		var datosCampo = arrayCampos[i];
		var arrayDatos = datosCampo.split(",");
		var campo = document.getElementById(arrayDatos[0]);
		var style = arrayDatos[1];
		if (campo) {
			campo.className = style;
		}
	}
}

// ////////////////////////////////////////////////////////////////////////////////////////////
// ////////////////////////////////////////////////////////////////////////////////////////////
// COMPROBAR SALIR SIN GUARDAR
// ////////////////////////////////////////////////////////////////////////////////////////////
// ////////////////////////////////////////////////////////////////////////////////////////////

var texto1 = "";

function onLoadCargarFormulario() {
	texto1 = "";

	texto1 = recogerCadenaCamposForm();

	// alert("texto1:" + texto1);
}

/**
 * Recorre los campos del formulario formando una cadena id=valor;id=valor;...
 * 
 * @return cadena
 */
function recogerCadenaCamposForm() {

	var contenidoCentral = document.getElementById('contenidoCentral');
	var camposInput = contenidoCentral.getElementsByTagName('input');

	var cadena = "";

	// Deseleccionar columna anterior
	for (i = 0; i < camposInput.length; i++) {
		var element = camposInput[i];

		var type = element.type;
		if (element.style.display != 'none') {
			if (type == "checkbox" || type == "radio") {
				cadena += element.id + "=" + element.checked + ";";
			} else if (type == "hidden" || type == "password" || type == "text"
					|| type == "textarea") {

				// alert("entar id: " + element.id + " tipo: " +
				// element.type + " value: " + element.value);
				var comprobar = true;
				// no comprobar los nombre de las pesta�as
				if (type == "hidden" && element.value.indexOf("tabX") != -1) {
					// no comprobar
					comprobar = false;
				}
				// no comprobar el flat de cambiar estado del semaform
				if (type == "hidden"
						&& element.id == "formulario:flatCambiarEstado") {
					// no comprobar
					comprobar = false;
				}

				// no comprobar si es el campo formulario:div_busqueda:s=0 :
				// esto indica el elemento de la lista seleccionado
				if (element.id.indexOf('formulario:div_busqueda:s') != -1) {
					// no comprobar
					comprobar = false;
				}

				if (comprobar) {
					if (element.id == "formulario:fechaBaja") {
						if (element.value == "") {
							cadena += element.id + "= activo;";
						} else {
							cadena += element.id + "= inactivo;";
						}
					} else {
						cadena += element.id + "=" + element.value + ";";
					}
				}
			}
		}
	}

	var camposSelect = contenidoCentral.getElementsByTagName('select');
	for (i = 0; i < camposSelect.length; i++) {
		element = camposSelect[i];
		if (element.style.display != 'none') {
			// alert("element.id: " + element.id +" value:" + element.value
			// + ".");
			cadena += element.id + "=" + element.value + ";";
		}
	}

	var camposTextArea = contenidoCentral.getElementsByTagName('textarea');
	for (i = 0; i < camposTextArea.length; i++) {
		element = camposTextArea[i];
		if (element.style.display != 'none') {
			// alert("element.id: " + element.id +" value:" + element.value
			// + ".");
			cadena += element.id + "=" + element.value + ";";
		}
	}

	return cadena;
}

function formIsDirty() {

	var texto2 = "";

	texto2 = recogerCadenaCamposForm();

	var element = document.getElementById("formulario:modifObjetoNav");
	if (element && element.value == "1") {
		texto2 = texto2 + "MODIFICADO PANTALLA DETALLE NAVEGANDO";
	}

	// alert("texto1:" + texto1);
	// alert("texto2:" + texto2);

	if (texto1 != texto2) {
		// HAY CAMBIOS - mostrar el confirm
		// document.getElementById('formulario:mensajeSalirSinGuardar').component.show();

		// cortar la accion del boton salir
		return true;
	} else {
		// NO HAY CAMBIOS
		return false;
	}
}

function salirSinGuardar() {

	if (formIsDirty()) {
		// HAY CAMBIOS - mostrar el confirm
		document.getElementById('formulario:mensajeSalirSinGuardar').component
				.show();
		// cortar la accion del boton salir
		return true;
	} else {
		// NO HAY CAMBIOS
		return false;
	}
}

/**
 * Primero
 * 
 * @return
 */

function salirSinGuardarPrimero() {

	if (formIsDirty()) {
		// HAY CAMBIOS - mostrar el confirm
		document.getElementById('formulario:mensajeSalirSinGuardarPrimero').component
				.show();
		// cortar la accion del boton salir
		return true;
	} else {
		// NO HAY CAMBIOS
		return false;
	}
}

/**
 * Anterior
 * 
 * @return
 */

function salirSinGuardarAnterior() {

	if (formIsDirty()) {
		// HAY CAMBIOS - mostrar el confirm
		document.getElementById('formulario:mensajeSalirSinGuardarAnterior').component
				.show();
		// cortar la accion del boton salir
		return true;
	} else {
		// NO HAY CAMBIOS
		return false;
	}
}

/**
 * Siguiente
 * 
 * @return
 */
function salirSinGuardarSiguiente() {

	if (formIsDirty()) {
		// HAY CAMBIOS - mostrar el confirm
		document.getElementById('formulario:mensajeSalirSinGuardarSiguiente').component
				.show();
		// cortar la accion del boton salir
		return true;
	} else {
		// NO HAY CAMBIOS
		return false;
	}
}

/**
 * Ultimo
 * 
 * @return
 */
function salirSinGuardarUltimo() {

	if (formIsDirty()) {
		// HAY CAMBIOS - mostrar el confirm
		document.getElementById('formulario:mensajeSalirSinGuardarUltimo').component
				.show();
		// cortar la accion del boton salir
		return true;
	} else {
		// NO HAY CAMBIOS
		return false;
	}
}

/**
 * Devuelve true si hay algun check seleccionado.
 * 
 * @return
 */
function comprobarCheckListaBusqueda() {
	// alert("Entra a comprobar check;");

	var contenidoBusqueda = document.getElementById('contenidoCentral');
	var grupoCheck = contenidoBusqueda.getElementsByTagName('input');

	// var grupoCheck = document.getElementsByName("checkEntry");

	for (i = 0; lcheck = grupoCheck[i]; i++) {
		if (lcheck.type == 'checkbox' && lcheck.id.indexOf('checkEntry') != -1) {
			// alert("i:" + i + "id: " + lcheck.id + "check: " +
			// lcheck.checked);
			if (lcheck.checked) {
				return true;
			}
		}

	}
	return false;
}

/**
 * Devuelve true si hay algun check seleccionado.
 * 
 * @return
 */
function comprobarCheckListaBusquedaAux() {
	// alert("Entra a comprobar check;");

	var contenidoBusqueda = document.getElementById('contenidoCentral');
	var grupoCheck = contenidoBusqueda.getElementsByTagName('input');

	// var grupoCheck = document.getElementsByName("checkEntry");

	for (i = 0; lcheck = grupoCheck[i]; i++) {
		if (lcheck.type == 'checkbox' && lcheck.id.indexOf('Check') != -1) {
			// alert("i:" + i + "id: " + lcheck.id + "check: " +
			// lcheck.checked);
			if (lcheck.checked) {
				return true;
			}
		}

	}
	return false;
}

/**
 * Devuelve true si hay algun check seleccionado.
 * 
 * @return
 */
function comprobarCheckListaBusquedaAlarmas() {
	// alert("Entra a comprobar check;");
	var contenidoBusqueda = document.getElementById('contenidoCentral');
	var grupoCheck = contenidoBusqueda.getElementsByTagName('input');

	for (i = 0; lcheck = grupoCheck[i]; i++) {
		// alert("i:" + i + "id: " + lcheck.id + "check: " + lcheck.checked);
		if (lcheck.type == 'checkbox' && lcheck.id.indexOf('listaProg') != -1) {
			if (lcheck.checked) {
				return true;
			}
		}
	}
	return false;
}


/**
 * Devuelve true si hay solo un check seleccionado.
 * @return
 */
function comprobarCheckUnicoListaAsociacion(lista) {
	if (lista != "") {
		var contenidoBusqueda = document.getElementById('contenidoCentral');
		var grupoCheck = contenidoBusqueda.getElementsByTagName('input');
		var contador = 0;
		for (i = 0; lcheck = grupoCheck[i]; i++) {
			if (lcheck.type == 'checkbox' && lcheck.id.indexOf(lista) != -1) {
				if (lcheck.checked) {
					contador = contador + 1;
				}
			}
		}
		if (contador == 1) {
			return true;
		}
		return false;
	} else {
		return true;
	}
}


/**
 * Devuelve true si hay algun check seleccionado.
 * 
 * @return
 */
function comprobarCheckListaAsociacion(lista) {
	if (lista != "") {
		var contenidoBusqueda = document.getElementById('contenidoCentral');
		var grupoCheck = contenidoBusqueda.getElementsByTagName('input');

		for (i = 0; lcheck = grupoCheck[i]; i++) {
			if (lcheck.type == 'checkbox' && lcheck.id.indexOf(lista) != -1) {
				if (lcheck.checked) {
					return true;
				}
			}
		}
		return false;
	} else {
		return true;
	}
}
// ////////////////////////////////////////////////////////////////////////////////////////////
// ////////////////////////////////////////////////////////////////////////////////////////////
// FIN COMPROBAR SALIR SIN GUARDAR
// ////////////////////////////////////////////////////////////////////////////////////////////
// ////////////////////////////////////////////////////////////////////////////////////////////

function limpiarCamposObligatorios() {
	// alert("1");
	inicializa("#{inicial}");
	// alert("2");
	setHighlight("#{highlight}");
	// alert("3");
	setFocus("#{focus}");
	// alert("4");
}

/**
 * Devuelve true si hay algun check seleccionado.
 * 
 * @return
 */
function limpiarListaCheck(lista) {

	var contenidoBusqueda = document.getElementById('contenidoCentral');
	var grupoCheck = contenidoBusqueda.getElementsByTagName('input');

	for (i = 0; lcheck = grupoCheck[i]; i++) {
		if (lcheck.type == 'checkbox' && lcheck.id.indexOf(lista) != -1) {
			if (lcheck.checked) {
				lcheck.checked = false;
			}
		}

	}
}

/**
 * Dar el foco, al campo que se marcó como obligatorio, una vez pulsa el
 * aceptar del mensaje validación.
 * 
 * @return
 */
function darFoco() {
	// alert("prueba foco");

	// obtener los input
	var camposInput = document.getElementsByTagName('input');
	// alert("camposInput: " + camposInput + " long: " + camposInput.length);
	for (i = 0; i < camposInput.length; i++) {
		var ele = camposInput[i];
		if (ele.className.indexOf('highlight') != -1) {
			// alert("id ele: " + ele.id);
			ele.focus();
		}
	}

	// obtener los select
	var camposSelect = document.getElementsByTagName('select');
	for (i = 0; i < camposSelect.length; i++) {
		ele = camposSelect[i];
		if (ele.className.indexOf('highlight') != -1) {
			ele.focus();
		}
	}
	// TEXTAREA
	var camposTextarea = document.getElementsByTagName('textarea');
	// Deseleccionar columna anterior
	for (i = 0; i < camposTextarea.length; i++) {
		ele = camposTextarea[i];
		if (ele.className.indexOf('highlight') != -1) {
			ele.focus();
		}
	}

}

function activarRueNifInconsistencia() {
	document.getElementById('formulario:nif').disabled = false;
	document.getElementById('formulario:rue').disabled = false;
}

function mostrarCamposPestania() {
	document.getElementById('formulario:mensajeSalirSinGuardar').component
			.show();
}

// Esta funcion se usa para rellenar ceros por la izquierda en el evento ONBLUR,
// xj. Se usa en el evento Onblur
function padZeros(obj, longitud) {

	var text = obj.value;
	if (text != "") {
		while (text.length < longitud)
			text = '0' + text;
		obj.value = text;
	}
}


function padEspacios(obj, longitud) {

	var text = obj.value;
	if (text != "") {
		while (text.length < longitud)
			text = text + ' ';
		obj.value = text;
	}
}
