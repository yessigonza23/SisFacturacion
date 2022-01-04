package ec.gob.mdg.controller;


import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.TimeZone;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ComprobanteDetalle;
import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IClienteService;
import ec.gob.mdg.service.IComprobanteDetalleService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.util.ValorMod11;

@Named
@ViewScoped
public class GenerarDOMBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private IInstitucionService serviceInstitucion;
	@Inject
	private IUsuarioPuntoService serviceUsuPunto;
	@Inject
	private IComprobanteService serviceComprobante;
	@Inject
	private IClienteService serviceCliente;
	@Inject
	private IComprobanteDetalleService serviceComprobanteDetalle;

	private List<Institucion> lista;
	private List<ComprobanteDetalle> listaComprobanteDet = new ArrayList<ComprobanteDetalle>();
	private Institucion institucion;
	private Comprobante comprobante;
	private ComprobanteDetalle comprobanteDetalle = new ComprobanteDetalle();
	private Cliente cliente = new Cliente();
	private Document document;
	private Document archivoXML;
	PuntoRecaudacion punto;
	Integer inst;
	String secuencial;

	private UsuarioPunto usuPunto = new UsuarioPunto();
	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	String claveA;

	@PostConstruct
	public void init() {
		try {

			DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factoria.newDocumentBuilder();
			document = builder.newDocument();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generarXmlArchivo(Integer id_punto, Integer numeroComprobante) {
		Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);	
			punto = usuPunto.getPuntoRecaudacion();			
			inst = punto.getInstitucion().getId();			
			institucion = serviceInstitucion.institucionPorPunto(inst);			
			comprobante = serviceComprobante.comprobantePorPtoPorId(id_punto, numeroComprobante);			
			this.listaComprobanteDet = this.serviceComprobanteDetalle.listarComDetPorIdComp(comprobante.getId());			
			generarDocument();
			actualizaXml();
		
		} catch (Exception e) {
			System.out.println("mensaje error " + e.getMessage());
		}
	}

	public void generarDocument() throws ParserConfigurationException {
		DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factoria.newDocumentBuilder();
		document = builder.newDocument();

		Element facturas = document.createElement("factura");
		document.appendChild(facturas);
		facturas.setAttribute("id", "comprobante");
		facturas.setAttribute("version", "1.0.0");

		Element infoTributaria = document.createElement("infoTributaria");
		facturas.appendChild(infoTributaria);

		Element ambiente = document.createElement("ambiente");
		ambiente.appendChild(document.createTextNode(institucion.getAmbiente()));
		infoTributaria.appendChild(ambiente);

		Element tipoEmision = document.createElement("tipoEmision");
		tipoEmision.appendChild(document.createTextNode("1"));
		infoTributaria.appendChild(tipoEmision);

	
		Element razonSocial = document.createElement("razonSocial");
		razonSocial.appendChild(document.createTextNode(institucion.getNombre().replaceAll("[^a-zA-Z0-9]", " ")));
		infoTributaria.appendChild(razonSocial);

		Element ruc = document.createElement("ruc");
		ruc.appendChild(document.createTextNode(institucion.getRuc()));
		infoTributaria.appendChild(ruc);

		///////// GENERAR CLAVE DE ACCESO //////////////////////////

		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(comprobante.getFechaemision());

		String anio = String.valueOf(calendar.get(Calendar.YEAR));
		String mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

		if (calendar.get(Calendar.MONTH) + 1 < 10) {
			mes = "0" + mes;
		}
		if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
			dia = "0" + dia;
		}

		claveA = dia + mes + anio + "01" + institucion.getRuc() + institucion.getAmbiente()
				+ comprobante.getUsuarioPunto().getPuntoRecaudacion().getEstablecimiento()
				+ comprobante.getUsuarioPunto().getPuntoRecaudacion().getPuntoemision()
				+ StringUtils.leftPad(String.valueOf(comprobante.getNumero()), 9, "0") + "12345678" + "1";
		String verificador = String.valueOf(ValorMod11.mod11(claveA));
		claveA = claveA + verificador;

		//////////////////////////////////////////////////////////

		Element claveAcceso = document.createElement("claveAcceso");
		claveAcceso.appendChild(document.createTextNode(claveA));
		infoTributaria.appendChild(claveAcceso);

		Element codDoc = document.createElement("codDoc");
		codDoc.appendChild(document.createTextNode("01"));
		infoTributaria.appendChild(codDoc);

		Element estab = document.createElement("estab");
		estab.appendChild(document.createTextNode(punto.getEstablecimiento()));
		infoTributaria.appendChild(estab);

		Element ptoEmi = document.createElement("ptoEmi");
		ptoEmi.appendChild(document.createTextNode(punto.getPuntoemision()));
		infoTributaria.appendChild(ptoEmi);

		secuencial = String.format("%09d", 4);
		Element secuencial = document.createElement("secuencial");
		secuencial.appendChild(document.createTextNode(String.format("%09d", comprobante.getNumero())));
		infoTributaria.appendChild(secuencial);

		Element dirMatriz = document.createElement("dirMatriz");
		dirMatriz.appendChild(
				document.createTextNode(institucion.getDireccion().replaceAll("[^a-zA-Z0-9]", " ").trim()));
		infoTributaria.appendChild(dirMatriz);

		////////

		Element infoFactura = document.createElement("infoFactura");
		facturas.appendChild(infoFactura);

		Element fechaEmision = document.createElement("fechaEmision");
		fechaEmision.appendChild(
				document.createTextNode(UtilsDate.fechaFormatoString(comprobante.getFechaemision(), "dd/MM/yyyy")));
		infoFactura.appendChild(fechaEmision);

		Element dirEstablecimiento = document.createElement("dirEstablecimiento");
		dirEstablecimiento
				.appendChild(document.createTextNode(punto.getDireccion().replaceAll("[^a-zA-Z0-9]", " ").trim()));
		infoFactura.appendChild(dirEstablecimiento);



		Element obligadoContabilidad = document.createElement("obligadoContabilidad");
		obligadoContabilidad.appendChild(document.createTextNode("SI"));
		infoFactura.appendChild(obligadoContabilidad);

		////////////////////// TIPIFICAR EL TIPO DE IDENTIFICACIN
		////////////////////// ///////////////////////////////////

		String tipoIdentificacion = null;
		if (comprobante.getCliente().getTipoid().equals("C")) {
			tipoIdentificacion = "05";
		} else if (comprobante.getCliente().getTipoid().equals("R")) {
			tipoIdentificacion = "04";
		} else if (comprobante.getCliente().getTipoid().equals("P")) {
			tipoIdentificacion = "06";
		}

		///////////////////////////////////////////////////////////////

		Element tipoIdentificacionComprador = document.createElement("tipoIdentificacionComprador");
		tipoIdentificacionComprador.appendChild(document.createTextNode(tipoIdentificacion));
		infoFactura.appendChild(tipoIdentificacionComprador);

		Element razonSocialComprador = document.createElement("razonSocialComprador");
		razonSocialComprador.appendChild(
				document.createTextNode(comprobante.getClientenombre().replaceAll("[^a-zA-Z0-9]", " ").trim()));
		infoFactura.appendChild(razonSocialComprador);

		Element identificacionComprador = document.createElement("identificacionComprador");
		identificacionComprador.appendChild(document.createTextNode(comprobante.getClienteruc()));
		infoFactura.appendChild(identificacionComprador);

		/////////////////////////////////////////////////////////////

        //////////FORMATO PARA CANTIDADES 0.00 ///////////////////////////////////////

		String patron = "#.00";
		DecimalFormat objDF = new DecimalFormat(patron);
		objDF.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ROOT));

		
		
		// Sumamos valor cero y valor iva del detalle
		Double sumValorCero = 0.00;
		Double sumValorIva = 0.00;
		Double sumIva = 0.00;

		for (ComprobanteDetalle det : listaComprobanteDet) {
			sumValorCero = sumValorCero + (det.getValorcero() * det.getCantidad());
			sumValorIva = sumValorIva + (det.getValoriva() * det.getCantidad());
		}

		String valores = objDF.format(sumValorCero+sumValorIva);
		
		String sumValorCeroS = objDF.format(sumValorCero);

		String sumValorIvaS = objDF.format(sumValorIva);

		sumIva = (sumValorIva * 0.12);

		String sumIvaS = objDF.format(sumIva);

		Element totalSinImpuestos = document.createElement("totalSinImpuestos");
		totalSinImpuestos.appendChild(document.createTextNode(valores));
		infoFactura.appendChild(totalSinImpuestos);

		// -----------------------------------------------------------------------------------------------------
		Element totalDescuento = document.createElement("totalDescuento");
		totalDescuento.appendChild(document.createTextNode("0"));
		infoFactura.appendChild(totalDescuento);

		/////
		Element totalConImpuesto = document.createElement("totalConImpuestos");
		infoFactura.appendChild(totalConImpuesto);

		if (sumValorCero != 0) {

			Element totalImpuesto = document.createElement("totalImpuesto");
			totalConImpuesto.appendChild(totalImpuesto);

			Element codigo = document.createElement("codigo");
			codigo.appendChild(document.createTextNode("2"));
			totalImpuesto.appendChild(codigo);

			Element codigoPorcentaje = document.createElement("codigoPorcentaje");
			codigoPorcentaje.appendChild(document.createTextNode("0"));
			totalImpuesto.appendChild(codigoPorcentaje);

			Element baseImponible = document.createElement("baseImponible");
			baseImponible.appendChild(document.createTextNode(sumValorCeroS.trim()));
			totalImpuesto.appendChild(baseImponible);

			Element valor = document.createElement("valor");
			valor.appendChild(document.createTextNode("0.00"));
			totalImpuesto.appendChild(valor);
		}

		if (sumValorIva != 0) {

			Element totalImpuesto = document.createElement("totalImpuesto");
			totalConImpuesto.appendChild(totalImpuesto);

			Element codigo = document.createElement("codigo");
			codigo.appendChild(document.createTextNode("2"));
			totalImpuesto.appendChild(codigo);

			Element codigoPorcentaje = document.createElement("codigoPorcentaje");
			codigoPorcentaje.appendChild(document.createTextNode("2"));
			totalImpuesto.appendChild(codigoPorcentaje);

			Element baseImponible = document.createElement("baseImponible");
			baseImponible.appendChild(document.createTextNode(sumValorIvaS.trim()));
			totalImpuesto.appendChild(baseImponible);

			Element valor = document.createElement("valor");
			valor.appendChild(document.createTextNode(sumIvaS.trim()));
			totalImpuesto.appendChild(valor);

		}
		
		////
		Element propina = document.createElement("propina");
		propina.appendChild(document.createTextNode("0.00"));
		infoFactura.appendChild(propina);

		String importeTotalS = objDF.format((comprobante.getValor()));

		Element importeTotal = document.createElement("importeTotal");
		importeTotal.appendChild(document.createTextNode(importeTotalS.trim()));
		infoFactura.appendChild(importeTotal);

		Element moneda = document.createElement("moneda");
		moneda.appendChild(document.createTextNode("DOLAR"));
		infoFactura.appendChild(moneda);

		/////
		Element pagos = document.createElement("pagos");
		infoFactura.appendChild(pagos);

		Element pago = document.createElement("pago");
		pagos.appendChild(pago);

		Element formaPago = document.createElement("formaPago");
		formaPago.appendChild(document.createTextNode("20"));
		pago.appendChild(formaPago);

		Element total = document.createElement("total");
		total.appendChild(document.createTextNode(importeTotalS.trim()));
		pago.appendChild(total);

		////////

		Element detalles = document.createElement("detalles");
		facturas.appendChild(detalles);

		for (ComprobanteDetalle detd : listaComprobanteDet) {

			Element detalle = document.createElement("detalle");
			detalles.appendChild(detalle);

			Element codigoPrincipal = document.createElement("codigoPrincipal");
			codigoPrincipal.appendChild(
					document.createTextNode(detd.getRecaudaciondetalle().getRecaudacion().getCodigobanco()));
			detalle.appendChild(codigoPrincipal);

			Element codigoAuxiliar = document.createElement("codigoAuxiliar");
			codigoAuxiliar.appendChild(document.createTextNode(detd.getRecaudaciondetalle().getCodigo()));
			detalle.appendChild(codigoAuxiliar);

			Element descripcion = document.createElement("descripcion");
			descripcion.appendChild(document
					.createTextNode(detd.getRecaudaciondetalle().getNombre().replaceAll("[^a-zA-Z0-9]", " ").trim()));
			detalle.appendChild(descripcion);

			String cantidadS = String.valueOf(detd.getCantidad());

			Element cantidad = document.createElement("cantidad");
			cantidad.appendChild(document.createTextNode(cantidadS));
			detalle.appendChild(cantidad);


			String precioUnitarioS = objDF.format((detd.getValorcero() + detd.getValoriva()));

			Element precioUnitario = document.createElement("precioUnitario");
			precioUnitario.appendChild(document.createTextNode(precioUnitarioS));
			detalle.appendChild(precioUnitario);

			Element descuento = document.createElement("descuento");
			descuento.appendChild(document.createTextNode("0.00"));
			detalle.appendChild(descuento);

			String precioTotalSinImpuestoS = objDF
					.format(((detd.getValorcero() + detd.getValoriva()) * detd.getCantidad()));

			Element precioTotalSinImpuesto = document.createElement("precioTotalSinImpuesto");
			precioTotalSinImpuesto.appendChild(document.createTextNode(precioTotalSinImpuestoS));
			detalle.appendChild(precioTotalSinImpuesto);

			///
			Element impuestos = document.createElement("impuestos");
			detalle.appendChild(impuestos);

			///
			Element impuesto = document.createElement("impuesto");
			impuestos.appendChild(impuesto);

			Element codigo1 = document.createElement("codigo");
			codigo1.appendChild(document.createTextNode("2"));
			impuesto.appendChild(codigo1);

			if (detd.getValorcero() != 0) {

				Element codigoPorcentaje1 = document.createElement("codigoPorcentaje");
				codigoPorcentaje1.appendChild(document.createTextNode("0"));
				impuesto.appendChild(codigoPorcentaje1);

				Element tarifa = document.createElement("tarifa");
				tarifa.appendChild(document.createTextNode("0.00"));
				impuesto.appendChild(tarifa);

				String baseImponible1S = objDF.format((detd.getValorcero() * detd.getCantidad()));

				Element baseImponible1 = document.createElement("baseImponible");
				baseImponible1.appendChild(document.createTextNode(baseImponible1S));
				impuesto.appendChild(baseImponible1);

				Element valor1 = document.createElement("valor");
				valor1.appendChild(document.createTextNode("0.00"));
				impuesto.appendChild(valor1);

			}

			if (detd.getValoriva() != 0) {

				Element codigoPorcentaje1 = document.createElement("codigoPorcentaje");
				codigoPorcentaje1.appendChild(document.createTextNode("2"));
				impuesto.appendChild(codigoPorcentaje1);

				Element tarifa = document.createElement("tarifa");
				tarifa.appendChild(document.createTextNode("12.00"));
				impuesto.appendChild(tarifa);

				String baseImponible1S = objDF.format((detd.getValoriva() * detd.getCantidad()));

				Element baseImponible1 = document.createElement("baseImponible");
				baseImponible1.appendChild(document.createTextNode(baseImponible1S));
				impuesto.appendChild(baseImponible1);

				String valor1S = objDF.format((detd.getValoriva() * detd.getCantidad() * 0.12));

				Element valor1 = document.createElement("valor");
				valor1.appendChild(document.createTextNode(valor1S));
				impuesto.appendChild(valor1);

			}
		}

		Element infoAdicional = document.createElement("infoAdicional");
		facturas.appendChild(infoAdicional);

		Element campoAdicional = document.createElement("campoAdicional");
		infoAdicional.appendChild(campoAdicional);
		Attr attr = document.createAttribute("nombre");
		attr.setValue("Direccion");
		campoAdicional.setAttributeNode(attr);
		campoAdicional.appendChild(
				document.createTextNode(comprobante.getClientedireccion().replaceAll("[^a-zA-Z0-9]", " ")));
		infoAdicional.appendChild(campoAdicional);

		Element campoAdicional1 = document.createElement("campoAdicional");
		infoAdicional.appendChild(campoAdicional1);
		Attr attr1 = document.createAttribute("nombre");
		attr1.setValue("Telefono");
		campoAdicional1.setAttributeNode(attr1);
		campoAdicional1.appendChild(document.createTextNode(comprobante.getClientetelefono().trim()));
		infoAdicional.appendChild(campoAdicional1);

		cliente = serviceCliente.ClientePorCiParametro(comprobante.getClienteruc());

		Element campoAdicional2 = document.createElement("campoAdicional");
		infoAdicional.appendChild(campoAdicional2);
		Attr attr2 = document.createAttribute("nombre");
		attr2.setValue("Correo");
		campoAdicional2.setAttributeNode(attr2);
		campoAdicional2.appendChild(document.createTextNode(cliente.getCorreo()));
		infoAdicional.appendChild(campoAdicional2);

	}



	public void actualizaXml() throws Exception {
		
		String xmlString = convertDocumentToString(document);
		///GUARDA EL REGISTRO EN EL CAMPO COMPROBANTE XML
		comprobante.setXml(xmlString);
		
		serviceComprobante.modificar(comprobante);
		
	}

	public String convertDocumentToString(Document doc) {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = tf.newTransformer();
			// below code to remove XML declaration
			// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(doc), new StreamResult(writer));
			String output = writer.getBuffer().toString();
			return output;
		} catch (TransformerException e) {
			e.printStackTrace();
		}

		return null;
	}

	//////

	public Document getArchivoXML() {
		return archivoXML;
	}

	public void setArchivoXML(Document archivoXML) {
		this.archivoXML = archivoXML;
	}

	public String getClaveA() {
		return claveA;
	}

	public void setClaveA(String claveA) {
		this.claveA = claveA;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

	public PuntoRecaudacion getPunto() {
		return punto;
	}

	public void setPunto(PuntoRecaudacion punto) {
		this.punto = punto;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public List<Institucion> getLista() {
		return lista;
	}

	public void setLista(List<Institucion> lista) {
		this.lista = lista;
	}

	public List<ComprobanteDetalle> getListaComprobanteDet() {
		return listaComprobanteDet;
	}

	public void setListaComprobanteDet(List<ComprobanteDetalle> listaComprobanteDet) {
		this.listaComprobanteDet = listaComprobanteDet;
	}

	public ComprobanteDetalle getComprobanteDetalle() {
		return comprobanteDetalle;
	}

	public void setComprobanteDetalle(ComprobanteDetalle comprobanteDetalle) {
		this.comprobanteDetalle = comprobanteDetalle;
	}

	public Integer getInst() {
		return inst;
	}

	public void setInst(Integer inst) {
		this.inst = inst;
	}

	public String getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(String secuencial) {
		this.secuencial = secuencial;
	}

	public Usuario getP() {
		return p;
	}

	public void setP(Usuario p) {
		this.p = p;
	}
}
