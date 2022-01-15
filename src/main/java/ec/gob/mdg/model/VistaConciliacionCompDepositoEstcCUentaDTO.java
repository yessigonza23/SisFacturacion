package ec.gob.mdg.model;

import java.io.Serializable;

import javax.persistence.Column;

public class VistaConciliacionCompDepositoEstcCUentaDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "inst_nombre")
	private String inst_nombre;
	
	@Column(name = "punto_nombre")
	private String punto_nombre;

	@Column(name = "punto_id")
	private Integer punto_id;
	
	@Column(name = "punto_establecimiento")
	private String punto_establecimiento;
	
	@Column(name = "punto_emision")
	private String punto_emision;
	
	@Column(name = "punto_responsablecargo")
	private String punto_responsablecargo;
	
	@Column(name = "usuario_nombre")
	private String usuario_nombre;
	
	@Column(name = "usuario_cargo")
	private String usuario_cargo;
	
	@Column(name = "comp_id")
	private Integer comp_id;
	
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
	
	@Column(name = "comp_anio")
	private String comp_anio;
	
	@Column(name = "comp_mes")
	private String comp_mes;
	
	@Column(name = "deposito_id")
	private Integer deposito_id;
	
	@Column(name = "deposito_numero")
	private String deposito_numero;
	
	@Column(name = "deposito_fecha")
	private String deposito_fecha;
	
	@Column(name = "deposito_anio")
	private String deposito_anio;
	
	@Column(name = "deposito_mescod")
	private String deposito_mescod;
	
	@Column(name = "deposito_mes")
	private String deposito_mes;

	@Column(name = "deposito_valor")
	private Double deposito_valor;
	
	@Column(name = "deposito_id_tmp")
	private Integer deposito_id_tmp;
	
	@Column(name = "deposito_origen")
	private String deposito_origen;
	
	@Column(name = "deposito_tipotransaccion")
	private String deposito_tipotransaccion;
	
	@Column(name = "estado_id")
	private Integer estado_id;
	
	@Column(name = "estadocuenta_deposito")
	private String estadocuenta_deposito;
	
	@Column(name = "estadocuenta_depositofecha")
	private String estadocuenta_depositofecha;
	
	@Column(name = "estadocuenta_valor")
	private Double estadocuenta_valor;
	
	@Column(name = "estadocuenta_bloqueado")
	private Boolean estadocuenta_bloqueado;
	
	@Column(name = "estadocuenta_saldo")
	private Double estadocuenta_saldo;
	
	@Column(name = "estado_anio")
	private Integer estado_anio;
	
	@Column(name = "estado_mes")
	private Integer estado_mes;
	
	@Column(name = "consdepositos_id")
	private Integer consdepositos_id;
	
	@Column(name = "consdepositos_id_comprobantedeposito")
	private Integer consdepositos_id_comprobantedeposito;
	
	@Column(name = "consdepositos_id_estadocuenta")
	private Integer consdepositos_id_estadocuenta;
	
	@Column(name = "consdepositos_saldo")
	private Double consdepositos_saldo;
	
	@Column(name = "consdepositos_estado")
	private Boolean consdepositos_estado;
	
	@Column(name = "consdepositos_tipoconciliacion")
	private String consdepositos_tipoconciliacion;
	
	@Column(name = "consdepositos_tipoconciliaciondesc")
	private String consdepositos_tipoconciliaciondesc;
	
	@Column(name = "consdepositos_fecha")
	private String consdepositos_fecha;
	
	@Column(name = "cliente_nombre")
	private String cliente_nombre;
	
	@Column(name = "cliente_ci")
	private String cliente_ci;


	public String getInst_nombre() {
		return inst_nombre;
	}

	public void setInst_nombre(String inst_nombre) {
		this.inst_nombre = inst_nombre;
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

	public Integer getComp_id() {
		return comp_id;
	}

	public void setComp_id(Integer comp_id) {
		this.comp_id = comp_id;
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

	public String getComp_anio() {
		return comp_anio;
	}

	public void setComp_anio(String comp_anio) {
		this.comp_anio = comp_anio;
	}

	public String getComp_mes() {
		return comp_mes;
	}

	public void setComp_mes(String comp_mes) {
		this.comp_mes = comp_mes;
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

	public String getDeposito_anio() {
		return deposito_anio;
	}

	public void setDeposito_anio(String deposito_anio) {
		this.deposito_anio = deposito_anio;
	}

	public String getDeposito_mescod() {
		return deposito_mescod;
	}

	public void setDeposito_mescod(String deposito_mescod) {
		this.deposito_mescod = deposito_mescod;
	}

	public String getDeposito_mes() {
		return deposito_mes;
	}

	public void setDeposito_mes(String deposito_mes) {
		this.deposito_mes = deposito_mes;
	}

	public Double getDeposito_valor() {
		return deposito_valor;
	}

	public void setDeposito_valor(Double deposito_valor) {
		this.deposito_valor = deposito_valor;
	}

	public Integer getDeposito_id_tmp() {
		return deposito_id_tmp;
	}

	public void setDeposito_id_tmp(Integer deposito_id_tmp) {
		this.deposito_id_tmp = deposito_id_tmp;
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

	public Integer getEstado_id() {
		return estado_id;
	}

	public void setEstado_id(Integer estado_id) {
		this.estado_id = estado_id;
	}

	public String getEstadocuenta_deposito() {
		return estadocuenta_deposito;
	}

	public void setEstadocuenta_deposito(String estadocuenta_deposito) {
		this.estadocuenta_deposito = estadocuenta_deposito;
	}

	public String getEstadocuenta_depositofecha() {
		return estadocuenta_depositofecha;
	}

	public void setEstadocuenta_depositofecha(String estadocuenta_depositofecha) {
		this.estadocuenta_depositofecha = estadocuenta_depositofecha;
	}

	public Double getEstadocuenta_valor() {
		return estadocuenta_valor;
	}

	public void setEstadocuenta_valor(Double estadocuenta_valor) {
		this.estadocuenta_valor = estadocuenta_valor;
	}

	public Boolean getEstadocuenta_bloqueado() {
		return estadocuenta_bloqueado;
	}

	public void setEstadocuenta_bloqueado(Boolean estadocuenta_bloqueado) {
		this.estadocuenta_bloqueado = estadocuenta_bloqueado;
	}

	public Double getEstadocuenta_saldo() {
		return estadocuenta_saldo;
	}

	public void setEstadocuenta_saldo(Double estadocuenta_saldo) {
		this.estadocuenta_saldo = estadocuenta_saldo;
	}

	public Integer getConsdepositos_id() {
		return consdepositos_id;
	}

	public void setConsdepositos_id(Integer consdepositos_id) {
		this.consdepositos_id = consdepositos_id;
	}

	public Integer getConsdepositos_id_comprobantedeposito() {
		return consdepositos_id_comprobantedeposito;
	}

	public void setConsdepositos_id_comprobantedeposito(Integer consdepositos_id_comprobantedeposito) {
		this.consdepositos_id_comprobantedeposito = consdepositos_id_comprobantedeposito;
	}

	public Integer getConsdepositos_id_estadocuenta() {
		return consdepositos_id_estadocuenta;
	}

	public void setConsdepositos_id_estadocuenta(Integer consdepositos_id_estadocuenta) {
		this.consdepositos_id_estadocuenta = consdepositos_id_estadocuenta;
	}

	public Double getConsdepositos_saldo() {
		return consdepositos_saldo;
	}

	public void setConsdepositos_saldo(Double consdepositos_saldo) {
		this.consdepositos_saldo = consdepositos_saldo;
	}

	public Boolean getConsdepositos_estado() {
		return consdepositos_estado;
	}

	public void setConsdepositos_estado(Boolean consdepositos_estado) {
		this.consdepositos_estado = consdepositos_estado;
	}

	public String getConsdepositos_tipoconciliacion() {
		return consdepositos_tipoconciliacion;
	}

	public void setConsdepositos_tipoconciliacion(String consdepositos_tipoconciliacion) {
		this.consdepositos_tipoconciliacion = consdepositos_tipoconciliacion;
	}

	public String getConsdepositos_tipoconciliaciondesc() {
		return consdepositos_tipoconciliaciondesc;
	}

	public void setConsdepositos_tipoconciliaciondesc(String consdepositos_tipoconciliaciondesc) {
		this.consdepositos_tipoconciliaciondesc = consdepositos_tipoconciliaciondesc;
	}

	public String getConsdepositos_fecha() {
		return consdepositos_fecha;
	}

	public void setConsdepositos_fecha(String consdepositos_fecha) {
		this.consdepositos_fecha = consdepositos_fecha;
	}

	public String getCliente_nombre() {
		return cliente_nombre;
	}

	public void setCliente_nombre(String cliente_nombre) {
		this.cliente_nombre = cliente_nombre;
	}

	public String getCliente_ci() {
		return cliente_ci;
	}

	public void setCliente_ci(String cliente_ci) {
		this.cliente_ci = cliente_ci;
	}
	
	
}
