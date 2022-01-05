package ec.gob.mdg.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.model.UploadedFile;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;

import ec.gob.mdg.model.CargaMasiva;
import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ComprobanteDepositos;
import ec.gob.mdg.model.ComprobanteDetalle;
import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.ICargaMasivaService;
import ec.gob.mdg.service.IClienteService;
import ec.gob.mdg.service.IComprobanteDepositosService;
import ec.gob.mdg.service.IComprobanteDetalleService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IRecaudacionDetalleService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.util.CedulaRuc;
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.util.ValorMod11;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class CargaMasivaAcuerdoBean implements Serializable {

	private static final long serialVersionUID = -6490646968982098807L;

	@Inject
	private ICargaMasivaService serviceCargaMasiva;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IComprobanteDetalleService serviceComprobanteDetalle;

	@Inject
	private IComprobanteDepositosService serviceComprobanteDepositos;

	@Inject
	private Funciones fun;

	@Inject
	private IClienteService serviceCliente;

	@Inject
	private IRecaudacionDetalleService serviceRecaudacionDetalle;

	@Inject
	private IInstitucionService serviceInstitucion;

	@Inject
	private GenerarDOMBean genXml;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	private CargaMasiva cargaMasiva = new CargaMasiva();
	private UsuarioPunto usuPunto = new UsuarioPunto();
	private Cliente cliente = new Cliente();
	private PuntoRecaudacion punto;
	private Institucion institucion;
	private RecaudacionDetalle recaudacionDetalle;
	PuntoRecaudacion nombre;
	List<CargaMasiva> listaCargaMasivaAcuerdo = new ArrayList<CargaMasiva>();
	List<RecaudacionDetalle> listaRecaudacionDetalle;
	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	Date fechaActual;
	String path;
	Integer contadorRuci = 0;
	String tipo = null;
	Integer num1 = 0;
	String claveA;
	Integer id_comprobante = 0;
	Boolean respuesta = null;
	double total = 0.0;
	Integer c = 0;
	Integer contador = 0;
	Integer contadorLineas = 0;

	private UploadedFile file;

	@PostConstruct
	public void init() {
		try {
			eliminarCarga("A");
			cargaListaAcuerdo();
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
			punto = usuPunto.getPuntoRecaudacion();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upload(String tipo) {
		String nombreArchivo = null;
		try {
			if (file != null) {

				nombreArchivo = "ArchivoAcuerdo" + punto.getId() + ".csv";
				UtilsArchivos.upload(file, nombreArchivo);

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Seleccione un archivo"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Transactional
	public String importarCSV(String tipo) {
    System.out.println("entra en importar ");
		List<CargaMasiva> listaCargaMasivaAcuerdo = new ArrayList<CargaMasiva>();
		usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
		fechaActual = UtilsDate.fechaActual();
		System.out.println("entra try ");
		try {
			System.out.println("entra try 1");
			path = UtilsArchivos.getRutaCargaMasiva() + "SArchivoAcuerdo" + punto.getId() + ".csv";

			String line = "";
			String cvsSplitBy = ";";
			System.out.println("entra try 12");
			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
				c = 1;
				while ((line = br.readLine()) != null && contador == 0) {
					contadorLineas++;
					String[] leerArchivo = line.split(cvsSplitBy);

					String ruc = leerArchivo[0];
					String tipoidentificacion = leerArchivo[14];

					String validaIdentificacion = CedulaRuc.comprobacion(ruc, tipoidentificacion);
					System.out.println("entra try 2");
					if (!validaIdentificacion.equals("T")) {
						contador++;

						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR,
										"El Ruc: " + ruc + " contiene errores, en la línea " + contadorLineas,
										validaIdentificacion));
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
				if (contador == 0) {

					while ((line = br.readLine()) != null) {

						String[] leerArchivo = line.split(cvsSplitBy);

						String ruc = leerArchivo[0];
System.out.println("1");
						String establecimientotmp = leerArchivo[1];
						Integer establecimiento = Integer.parseInt(establecimientotmp);

						String provincia = leerArchivo[2];
						if (provincia.trim().length() > 100) {
							provincia = provincia.substring(0, 9);
						}
						System.out.println("2");
						String canton = leerArchivo[3];
						if (canton.trim().length() > 100) {
							canton = canton.substring(0, 99);
						}
						System.out.println("3");
						String telefono = leerArchivo[4];
						if (telefono.trim().length() > 10) {
							telefono = telefono.substring(0, 9);
						}
						System.out.println("4");

						String correo = leerArchivo[5];
						if (correo.trim().length() > 100) {
							correo = correo.substring(0, 99);
						}
						System.out.println("5");
						String recaudacion = leerArchivo[6];
						if (recaudacion.trim().length() > 100) {
							recaudacion = recaudacion.substring(0, 99);
						}
						System.out.println("6");
						String tipotramite = leerArchivo[7];
						if (tipotramite.trim().length() > 200) {
							tipotramite = tipotramite.substring(1, 199);
						}
						System.out.println("7");
						String direccion = leerArchivo[8];
						if (direccion.trim().length() > 150) {
							direccion = direccion.substring(1, 199);
						}
						System.out.println("8");
						String depositonumero = leerArchivo[9];
						System.out.println("9");
						String fecha_tmp = leerArchivo[10];
						java.text.DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
						Date fechadeposito = (Date) formatter.parse(fecha_tmp);
						double valor = Double.parseDouble(leerArchivo[11].replace(",", "."));

						String codigopaf = leerArchivo[12];
						System.out.println("10");
						String cliente = leerArchivo[13];
						if (cliente.trim().length() > 150) {
							cliente = cliente.substring(0, 149);
						}

						String tipoidentificacion = leerArchivo[14];

						Integer id_puntorecaudacion = usuPunto.getPuntoRecaudacion().getId();
						Integer id_comprobante = 0;

						Date fechacarga = fechaActual;
						Integer id_usuario = us.getId();

						listaCargaMasivaAcuerdo.add(new CargaMasiva(ruc, establecimiento, provincia, canton, telefono,
								correo, recaudacion, tipotramite, direccion, depositonumero, fechadeposito, valor,
								codigopaf, cliente, id_puntorecaudacion, id_comprobante, tipo, fechacarga, id_usuario,
								tipoidentificacion));

						c++;

					}
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Se cargaron los datos", "Exitoso"));
				}
			
				serviceCargaMasiva.registrarLista(listaCargaMasivaAcuerdo);

				cargaListaAcuerdo();
				
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"No se cargaron los datos revise su archivo en la línea No.: " + c + "\r\n Error:" + e, "Fallido"));

		}
		return "Registro " + c;
	}

	// CARGAR LISTA DE ACUERDO
	public void cargaListaAcuerdo() {
		String tipo = "A";
		usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
		listaCargaMasivaAcuerdo = serviceCargaMasiva
				.listarCargaMasivaSinComprobante(usuPunto.getPuntoRecaudacion().getId(), tipo);
		total(tipo);
	}

	// SUMA TOTAL
	public void total(String tipo) {
		total = 0.0;

		for (CargaMasiva c : listaCargaMasivaAcuerdo) {
			total = total + c.getValor();
		}

	}

	// ELIMINAR CARGA MASIVA CON COMPROBANTE CERO PENDIENTE DE FACTURAR
	public void eliminarCarga(String tipo) {
		usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
		this.serviceCargaMasiva.eliminarCargaMasivaPendiente(usuPunto.getPuntoRecaudacion().getId(), tipo);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Se eliminaron los datos", "Exitosamente"));

		cargaListaAcuerdo();

	}

	// GENERAR FACTURAS
	@Transactional
	public void generarFacturas(String Tipo) {
		try {
			// VALIDAR QUE EXISTA EL CIERRE DE CAJA DEL DIA ANTERIOR
			Integer contador = 0;
			contador = (Integer) serviceComprobante.validaCierre(usuPunto);
			if (contador > 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error, debe realizar el cierre del día anterior, no puede facturar", "Error"));
			} else {
				contador=0;
				// VALIDAR LA RECAUDACION DETALLE
				for (CargaMasiva c : listaCargaMasivaAcuerdo) {

					String validaIdentificacion = CedulaRuc.comprobacion(c.getRuc(), c.getTipoidentificacion());
					if (!validaIdentificacion.equals("T")) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Tipo de identificacion erronea verificar: " + c.getRuc(), validaIdentificacion));
					}

					respuesta = serviceRecaudacionDetalle.hayRecaudacionDetallePorNombre(c.getRecaudacion());
					String codigoPaf = c.getCodigopaf();
					Integer validaPaf = serviceCargaMasiva.validarCodigoPaf(codigoPaf);

					if (!respuesta && contador==0 ) {
						contador++;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Tipo de recaudacion erronea verificar: " + c.getRecaudacion(), "Error"));
					} else if (validaPaf == 1 && contador==0) {
						contador++;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"El codigo de Acuerdo: " + codigoPaf + " ya fue facturado", "Error"));
					} else  if (contador==0){
						Long a = fun.buscarCliente(c.getRuc());
						if (a == 0) {

							Cliente cn = new Cliente();
							cn.setCi(c.getRuc());
							cn.setCorreo(c.getCorreo());
							cn.setDireccion(c.getDireccion());
							cn.setNombre(c.getCliente());
							cn.setTelefono(c.getTelefono());
							cn.setTipoid(c.getTipoidentificacion());
							String clave = c.getRuc();
							String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
							cn.setClave(claveHash);
							serviceCliente.registrar(cn);

						} else {
							Cliente cn = new Cliente();
							cn = serviceCliente.ClientePorCiParametro(c.getRuc());
							cn.setCorreo(c.getCorreo());
							cn.setDireccion(c.getDireccion());
							cn.setNombre(c.getCliente());
							cn.setTelefono(c.getTelefono());
							serviceCliente.modificar(cn);
						}

						cliente = serviceCliente.ClientePorCiParametro(c.getRuc());
						usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
						punto = usuPunto.getPuntoRecaudacion();
						institucion = serviceInstitucion.institucionPorPunto(punto.getInstitucion().getId());
						// saca el secuencial +1
						num1 = (Integer) fun.secFactura(usuPunto);
						Comprobante comp = new Comprobante();
						comp.setNumero(num1);

						comp.setClienteruc(cliente.getCi());

						comp.setClientenombre(cliente.getNombre());
						comp.setClientedireccion(cliente.getDireccion());
						comp.setClientetelefono(cliente.getTelefono());

						comp.setValor(c.getValor());
						fechaActual = UtilsDate.fechaActual();
						comp.setFechaemision(fechaActual);

						comp.setDetalle(c.getTipotramite() + " - " + c.getRecaudacion());
						comp.setCliente(cliente);
						comp.setUsuarioPunto(usuPunto);
						comp.setTipocomprobante("F");
						comp.setPuntoRecaudacion(punto);
						comp.setCliente(cliente);

						//// GENERAR CLAVE ACCESO
						Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
						calendar.setTime(fechaActual);
						String anio = String.valueOf(calendar.get(Calendar.YEAR));
						String mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
						String dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

						if (calendar.get(Calendar.MONTH) < 10) {
							mes = "0" + mes;
						}

						if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
							dia = "0" + dia;
						}
						claveA = dia + mes + anio + "01" + institucion.getRuc() + institucion.getAmbiente()
								+ punto.getEstablecimiento() + punto.getPuntoemision()
								+ StringUtils.leftPad(String.valueOf(num1), 9, "0") + "12345678" + "1";
						String verificador = String.valueOf(ValorMod11.mod11(claveA));
						claveA = claveA + verificador;
						comp.setClaveacceso(claveA);
						////////////////////////////////////////////
						id_comprobante = serviceComprobante.registrar(comp);

						comp = serviceComprobante.listarComprobantePorId(id_comprobante);

						// actualiza la secuencia
						fun.actualizaSecuencialFactura(usuPunto, num1);

						/// GRABAR DETALLE DE COMPROBANTE
						recaudacionDetalle = serviceRecaudacionDetalle
								.mostrarRecaudacionDetallePorNombre(c.getRecaudacion());
						ComprobanteDetalle det = new ComprobanteDetalle();
						det.setComprobante(comp);
						det.setCantidad(1);
						det.setRecaudaciondetalle(recaudacionDetalle);
						det.setSubtotal(c.getValor());
						det.setValorcero(c.getValor());
						det.setValoriva(0.00);
						serviceComprobanteDetalle.registrar(det);

						// REGISTRAR DEPOSITO DEL COMPROBANTE
						ComprobanteDepositos dep = new ComprobanteDepositos();
						dep.setComprobante(comp);
						dep.setNum_deposito(c.getDepositonumero());
						dep.setFecha(c.getDepositofecha());
						dep.setValor(c.getValor());
						dep.setOrigen("P");
						dep.setTipotransaccion("D");
						dep.setIdentificacion(c.getRuc());
						serviceComprobanteDepositos.registrar(dep);

						/// GENERAR XML PARA LA FACTURA
						genXml.generarXmlArchivo(punto.getId(), num1);

						c.setId_comprobante(id_comprobante);
						serviceCargaMasiva.modificar(c);

						//// FIN DE GENERACIoN DE COMPROBANTES
						cargaListaAcuerdo();

					}
					
				}
				if (contador ==0) {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha generado satisfactoriamente", "Aviso"));
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// getters y setters

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public CargaMasiva getCargaMasiva() {
		return cargaMasiva;
	}

	public void setCargaMasiva(CargaMasiva cargaMasiva) {
		this.cargaMasiva = cargaMasiva;
	}

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<CargaMasiva> getListaCargaMasivaAcuerdo() {
		return listaCargaMasivaAcuerdo;
	}

	public void setListaCargaMasivaAcuerdo(List<CargaMasiva> listaCargaMasivaAcuerdo) {
		this.listaCargaMasivaAcuerdo = listaCargaMasivaAcuerdo;
	}

	public Funciones getFun() {
		return fun;
	}

	public void setFun(Funciones fun) {
		this.fun = fun;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public PuntoRecaudacion getPunto() {
		return punto;
	}

	public void setPunto(PuntoRecaudacion punto) {
		this.punto = punto;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public RecaudacionDetalle getRecaudacionDetalle() {
		return recaudacionDetalle;
	}

	public void setRecaudacionDetalle(RecaudacionDetalle recaudacionDetalle) {
		this.recaudacionDetalle = recaudacionDetalle;
	}

	public List<RecaudacionDetalle> getListaRecaudacionDetalle() {
		return listaRecaudacionDetalle;
	}

	public void setListaRecaudacionDetalle(List<RecaudacionDetalle> listaRecaudacionDetalle) {
		this.listaRecaudacionDetalle = listaRecaudacionDetalle;
	}

	public Integer getContadorRuci() {
		return contadorRuci;
	}

	public void setContadorRuci(Integer contadorRuci) {
		this.contadorRuci = contadorRuci;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getNum1() {
		return num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	public String getClaveA() {
		return claveA;
	}

	public void setClaveA(String claveA) {
		this.claveA = claveA;
	}

	public Integer getId_comprobante() {
		return id_comprobante;
	}

	public void setId_comprobante(Integer id_comprobante) {
		this.id_comprobante = id_comprobante;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Boolean getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Boolean respuesta) {
		this.respuesta = respuesta;
	}
}
