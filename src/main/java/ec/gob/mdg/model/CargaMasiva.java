package ec.gob.mdg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cargamasiva", schema = "financiero")
public class CargaMasiva implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "establecimiento")
	private Integer establecimiento;
	
	@Column(name = "provincia")
	private String provincia;
	
	@Column(name = "canton")
	private String canton;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "correo")
	private String correo;
	
	@Column(name = "recaudacion")
	private String recaudacion;
	
	@Column(name = "tipotramite")
	private String tipotramite;
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "depositonumero")
	private String depositonumero;
	
	@Column(name = "depositofecha")
	private Date depositofecha;
	
	@Column(name = "valor")
	private Double valor;
	
	@Column(name = "codigopaf")
	private String codigopaf;
	
	@Column(name = "cliente")
	private String cliente;
	
	@Column(name = "id_puntorecaudacion")
	private Integer id_puntorecaudacion;
	
	@Column(name = "id_comprobante")
	private Integer id_comprobante;
	
	@Column(name = "tipo")
	private String tipo;

	@Column(name = "fechacarga")
	private Date fechacarga;
	
	@Column(name = "id_usuario")
	private Integer id_usuario;
	
	@Column(name = "tipoidentificacion")
	private String tipoidentificacion;
		
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	
	public Integer getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(Integer establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCanton() {
		return canton;
	}

	public void setCanton(String canton) {
		this.canton = canton;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getRecaudacion() {
		return recaudacion;
	}

	public void setRecaudacion(String recaudacion) {
		this.recaudacion = recaudacion;
	}

	public String getTipotramite() {
		return tipotramite;
	}

	public void setTipotramite(String tipotramite) {
		this.tipotramite = tipotramite;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDepositonumero() {
		return depositonumero;
	}

	public void setDepositonumero(String depositonumero) {
		this.depositonumero = depositonumero;
	}

	public Date getDepositofecha() {
		return depositofecha;
	}

	public void setDepositofecha(Date depositofecha) {
		this.depositofecha = depositofecha;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getCodigopaf() {
		return codigopaf;
	}

	public void setCodigopaf(String codigopaf) {
		this.codigopaf = codigopaf;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Integer getId_puntorecaudacion() {
		return id_puntorecaudacion;
	}

	public void setId_puntorecaudacion(Integer id_puntorecaudacion) {
		this.id_puntorecaudacion = id_puntorecaudacion;
	}

	public Integer getId_comprobante() {
		return id_comprobante;
	}

	public void setId_comprobante(Integer id_comprobante) {
		this.id_comprobante = id_comprobante;
	}
	
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	

	public Date getFechacarga() {
		return fechacarga;
	}

	public void setFechacarga(Date fechacarga) {
		this.fechacarga = fechacarga;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}
	
	public String getTipoidentificacion() {
		return tipoidentificacion;
	}

	public void setTipoidentificacion(String tipoidentificacion) {
		this.tipoidentificacion = tipoidentificacion;
	}

	public CargaMasiva(String ruc, Integer establecimiento, String provincia, String canton, String telefono,
			String correo, String recaudacion, String tipotramite, String direccion, String depositonumero,
			Date depositofecha, Double valor, String codigopaf, String cliente, Integer id_puntorecaudacion,
			Integer id_comprobante,String tipo,Date fechacarga,Integer id_usuario,String tipoidentificacion) {
		super();
		this.ruc = ruc;
		this.establecimiento = establecimiento;
		this.provincia = provincia;
		this.canton = canton;
		this.telefono = telefono;
		this.correo = correo;
		this.recaudacion = recaudacion;
		this.tipotramite = tipotramite;
		this.direccion = direccion;
		this.depositonumero = depositonumero;
		this.depositofecha = depositofecha;
		this.valor = valor;
		this.codigopaf = codigopaf;
		this.cliente = cliente;
		this.id_puntorecaudacion = id_puntorecaudacion;
		this.id_comprobante = id_comprobante;
		this.tipo = tipo;
		this.fechacarga = fechacarga;
		this.id_usuario = id_usuario;
		this.tipoidentificacion = tipoidentificacion;
	}

	public CargaMasiva() {
	
		// TODO Auto-generated constructor stub
	}


}
