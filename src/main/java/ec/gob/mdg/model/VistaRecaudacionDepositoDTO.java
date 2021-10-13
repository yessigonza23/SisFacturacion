package ec.gob.mdg.model;

import java.io.Serializable;

import javax.persistence.Column;

public class VistaRecaudacionDepositoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "inst_nombre")
	private String inst_nombre;

	@Column(name = "inst_direccion")
	private String inst_direccion;

	@Column(name = "inst_ruc")
	private String inst_ruc;

	@Column(name = "punto_nombre")
	private String punto_nombre;

	@Column(name = "punto_id")
	private Integer punto_id;

	@Column(name = "punto_establecimiento")
	private String punto_establecimiento;

	@Column(name = "punto_emision")
	private String punto_emision;

	@Column(name = "punto_direcci�n")
	private String punto_direccion;

	@Column(name = "punto_responsable")
	private String punto_responsable;

	@Column(name = "punto_responsablecargo")
	private String punto_responsablecargo;

	@Column(name = "usuario_nombre")
	private String usuario_nombre;

	@Column(name = "usuario_cargo")
	private String usuario_cargo;

	@Column(name = "cliente_ci")
	private String cliente_ci;

	@Column(name = "cliente_tipoid")
	private String cliente_tipoid;

	@Column(name = "cliente_nombre")
	private String cliente_nombre;

	@Column(name = "cliente_direccion")
	private String cliente_direccion;

	@Column(name = "cliente_correo")
	private String cliente_correo;

	@Column(name = "cliente_telefono")
	private String cliente_telefono;

	@Column(name = "comp_numero")
	private Integer comp_numero;

	@Column(name = "comp_fechaemision")
	private String comp_fechaemision;

	@Column(name = "comp_valor")
	private Double comp_valor;

	@Column(name = "comp_estado")
	private String comp_estado;

	@Column(name = "comp_tipo")
	private String comp_tipo;

	@Column(name = "comp_numero_completo")
	private String comp_numero_completo;

	@Column(name = "deposito_id")
	private Integer deposito_id;

	@Column(name = "deposito_numero")
	private String deposito_numero;

	@Column(name = "deposito_fecha")
	private String deposito_fecha;

	@Column(name = "deposito_valor")
	private Double deposito_valor;
	
	@Column(name = "deposito_id_tmp")
	private Integer deposito_id_tmp;

	@Column(name = "deposito_origen")
	private String deposito_origen;

	@Column(name = "deposito_tipotransaccion")
	private String deposito_tipotransaccion;

	@Column(name = "deposito_observacion")
	private String deposito_observacion;

	/// campos para sacar los datos de conciliacion comprobantes vs estado de cuenta

	@Column(name = "anio")
	private Integer anio;

	@Column(name = "mes")
	private Integer mes;

	@Column(name = "estadocuenta_valor")
	private Double estadocuenta_valor;

	@Column(name = "estadocuenta_anio")
	private Integer estadocuenta_anio;

	@Column(name = "estadocuenta_id_comprobante")
	private Integer estadocuenta_id_comprobante;

	public String getInst_nombre() {
		return inst_nombre;
	}

	public void setInst_nombre(String inst_nombre) {
		this.inst_nombre = inst_nombre;
	}

	public String getInst_direccion() {
		return inst_direccion;
	}

	public void setInst_direccion(String inst_direccion) {
		this.inst_direccion = inst_direccion;
	}

	public String getInst_ruc() {
		return inst_ruc;
	}

	public void setInst_ruc(String inst_ruc) {
		this.inst_ruc = inst_ruc;
	}

	public String getPunto_nombre() {
		return punto_nombre;
	}

	public void setPunto_nombre(String punto_nombre) {
		this.punto_nombre = punto_nombre;
	}

	public Integer getPunto_id() {
		return punto_id;
	}

	public void setPunto_id(Integer punto_id) {
		this.punto_id = punto_id;
	}

	public String getPunto_establecimiento() {
		return punto_establecimiento;
	}

	public void setPunto_establecimiento(String punto_establecimiento) {
		this.punto_establecimiento = punto_establecimiento;
	}

	public String getPunto_emision() {
		return punto_emision;
	}

	public void setPunto_emision(String punto_emision) {
		this.punto_emision = punto_emision;
	}

	public String getPunto_direccion() {
		return punto_direccion;
	}

	public void setPunto_direccion(String punto_direccion) {
		this.punto_direccion = punto_direccion;
	}

	public String getPunto_responsable() {
		return punto_responsable;
	}

	public void setPunto_responsable(String punto_responsable) {
		this.punto_responsable = punto_responsable;
	}

	public String getPunto_responsablecargo() {
		return punto_responsablecargo;
	}

	public void setPunto_responsablecargo(String punto_responsablecargo) {
		this.punto_responsablecargo = punto_responsablecargo;
	}

	public String getUsuario_nombre() {
		return usuario_nombre;
	}

	public void setUsuario_nombre(String usuario_nombre) {
		this.usuario_nombre = usuario_nombre;
	}

	public String getUsuario_cargo() {
		return usuario_cargo;
	}

	public void setUsuario_cargo(String usuario_cargo) {
		this.usuario_cargo = usuario_cargo;
	}

	public String getCliente_ci() {
		return cliente_ci;
	}

	public void setCliente_ci(String cliente_ci) {
		this.cliente_ci = cliente_ci;
	}

	public String getCliente_tipoid() {
		return cliente_tipoid;
	}

	public void setCliente_tipoid(String cliente_tipoid) {
		this.cliente_tipoid = cliente_tipoid;
	}

	public String getCliente_nombre() {
		return cliente_nombre;
	}

	public void setCliente_nombre(String cliente_nombre) {
		this.cliente_nombre = cliente_nombre;
	}

	public String getCliente_direccion() {
		return cliente_direccion;
	}

	public void setCliente_direccion(String cliente_direccion) {
		this.cliente_direccion = cliente_direccion;
	}

	public String getCliente_correo() {
		return cliente_correo;
	}

	public void setCliente_correo(String cliente_correo) {
		this.cliente_correo = cliente_correo;
	}

	public String getCliente_telefono() {
		return cliente_telefono;
	}

	public void setCliente_telefono(String cliente_telefono) {
		this.cliente_telefono = cliente_telefono;
	}

	public Integer getComp_numero() {
		return comp_numero;
	}

	public void setComp_numero(Integer comp_numero) {
		this.comp_numero = comp_numero;
	}

	public String getComp_fechaemision() {
		return comp_fechaemision;
	}

	public void setComp_fechaemision(String comp_fechaemision) {
		this.comp_fechaemision = comp_fechaemision;
	}

	public Double getComp_valor() {
		return comp_valor;
	}

	public void setComp_valor(Double comp_valor) {
		this.comp_valor = comp_valor;
	}

	public String getComp_estado() {
		return comp_estado;
	}

	public void setComp_estado(String comp_estado) {
		this.comp_estado = comp_estado;
	}

	public String getComp_tipo() {
		return comp_tipo;
	}

	public void setComp_tipo(String comp_tipo) {
		this.comp_tipo = comp_tipo;
	}

	public String getComp_numero_completo() {
		return comp_numero_completo;
	}

	public void setComp_numero_completo(String comp_numero_completo) {
		this.comp_numero_completo = comp_numero_completo;
	}

	public Integer getDeposito_id() {
		return deposito_id;
	}

	public void setDeposito_id(Integer deposito_id) {
		this.deposito_id = deposito_id;
	}

	public String getDeposito_numero() {
		return deposito_numero;
	}

	public void setDeposito_numero(String deposito_numero) {
		this.deposito_numero = deposito_numero;
	}

	public String getDeposito_fecha() {
		return deposito_fecha;
	}

	public void setDeposito_fecha(String deposito_fecha) {
		this.deposito_fecha = deposito_fecha;
	}

	public Double getDeposito_valor() {
		return deposito_valor;
	}

	public void setDeposito_valor(Double deposito_valor) {
		this.deposito_valor = deposito_valor;
	}

	public String getDeposito_origen() {
		return deposito_origen;
	}

	public void setDeposito_origen(String deposito_origen) {
		this.deposito_origen = deposito_origen;
	}

	public String getDeposito_tipotransaccion() {
		return deposito_tipotransaccion;
	}

	public void setDeposito_tipotransaccion(String deposito_tipotransaccion) {
		this.deposito_tipotransaccion = deposito_tipotransaccion;
	}

	public Double getEstadocuenta_valor() {
		return estadocuenta_valor;
	}

	public void setEstadocuenta_valor(Double estadocuenta_valor) {
		this.estadocuenta_valor = estadocuenta_valor;
	}

	public Integer getEstadocuenta_anio() {
		return estadocuenta_anio;
	}

	public void setEstadocuenta_anio(Integer estadocuenta_anio) {
		this.estadocuenta_anio = estadocuenta_anio;
	}

	public Integer getEstadocuenta_id_comprobante() {
		return estadocuenta_id_comprobante;
	}

	public void setEstadocuenta_id_comprobante(Integer estadocuenta_id_comprobante) {
		this.estadocuenta_id_comprobante = estadocuenta_id_comprobante;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public String getDeposito_observacion() {
		return deposito_observacion;
	}

	public void setDeposito_observacion(String deposito_observacion) {
		this.deposito_observacion = deposito_observacion;
	}

	public Integer getDeposito_id_tmp() {
		return deposito_id_tmp;
	}

	public void setDeposito_id_tmp(Integer deposito_id_tmp) {
		this.deposito_id_tmp = deposito_id_tmp;
	}

}
