package ec.gob.mdg.model;

import java.io.Serializable;

import javax.persistence.Column;

public class VistaRecaudacionDTO implements Serializable{

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
	
	@Column(name = "punto_direccion")
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
	
	@Column(name = "comp_id")
	private Integer comp_id;
	
	@Column(name = "comp_numero")
	private Integer comp_numero;
	
	@Column(name = "comp_fechaemision")
	private String comp_fechaemision;
	
	@Column(name = "comp_anio")
	private String comp_anio;
	
	@Column(name = "comp_mes")
	private String comp_mes;
	
	@Column(name = "comp_mescodigo")
	private String comp_mescodigo;
	
	@Column(name = "comp_dia")
	private String comp_dia;
	
	@Column(name = "comp_valor")
	private Double comp_valor;
	
	@Column(name = "comp_estado")
	private String comp_estado;
	
	@Column(name = "comp_tipo")
	private String comp_tipo;
	
	@Column(name = "comp_autorizacion")
	private String comp_autorizacion;
	
	@Column(name = "comp_detalle")
	private String comp_detalle;
	
	@Column(name = "proceso_nombre")
	private String proceso_nombre;
	
	@Column(name = "tipo_proceso")
	private String tipo_proceso;
	
	@Column(name = "proceso_tipo")
	private String proceso_tipo;
	
	@Column(name = "comp_claveacceso")
	private String comp_claveacceso;
	
	
	@Column(name = "recaudacion_codigobanco")
	private String recaudacion_codigobanco;
	
	@Column(name = "recaudacion_nombre")
	private String recaudacion_nombre;
	
	@Column(name = "recaudacion_id")
	private Integer recaudacion_id;
	
	@Column(name = "plancontable_codigo")
	private String plancontable_codigo;
	
	@Column(name = "planconttable_nombre")
	private String planconttable_nombre;

	@Column(name = "recdetalle_codigo")
	private String recdetalle_codigo;
	
	@Column(name = "recdetalle_nombre")
	private String recdetalle_nombre;
	
	@Column(name = "comp_numero_completo")
	private String comp_numero_completo;
	
	@Column(name = "compdetalle_cantidad")
	private Integer compdetalle_cantidad;
	
	@Column(name = "compdetalle_valoriva")
	private Double compdetalle_valoriva;
	
	@Column(name = "compdetalle_valorcero")
	private Double compdetalle_valorcero;
	
	@Column(name = "importe")
	private Double importe;
	
	@Column(name = "subt_vdoc")
	private Double subt_vdoc;
	
	@Column(name = "subt_iva")
	private Double subt_iva;
	
	@Column(name = "subt_vcer")
	private Double subt_vcer;
	
	@Column(name = "comdetalle_valor")
	private Double comdetalle_valor;
	
	@Column(name = "comp_modificado")
	private Integer comp_modificado;
	
	@Column(name = "recdetalle_codigo")
	private String comp_notacreditomotivo;
	
	@Column(name = "comp_fechacompmodificado")
	private String comp_fechacompmodificado;
	
	@Column(name = "comp_modificado_pto")
	private Integer comp_modificado_pto;
	
	@Column(name = "comp_modificado_punto_nombre")
	private String comp_modificado_punto_nombre;

	@Column(name = "factura_mod")
	private String factura_mod;
	
	@Column(name = "contador")
	private Integer contador;
	
	@Column(name = "numero_factura")
	private Integer numero_factura;
	
	@Column(name = "comp_id_cierre")
	private Integer comp_id_cierre;
	
	@Column(name = "comp_check_cierre")
	private boolean comp_check_cierre;
	
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

	public String getComp_mescodigo() {
		return comp_mescodigo;
	}

	public void setComp_mescodigo(String comp_mescodigo) {
		this.comp_mescodigo = comp_mescodigo;
	}

	public String getComp_dia() {
		return comp_dia;
	}

	public void setComp_dia(String comp_dia) {
		this.comp_dia = comp_dia;
	}

	public String getComp_autorizacion() {
		return comp_autorizacion;
	}

	public void setComp_autorizacion(String comp_autorizacion) {
		this.comp_autorizacion = comp_autorizacion;
	}

	public String getComp_detalle() {
		return comp_detalle;
	}

	public void setComp_detalle(String comp_detalle) {
		this.comp_detalle = comp_detalle;
	}

	public String getProceso_nombre() {
		return proceso_nombre;
	}

	public void setProceso_nombre(String proceso_nombre) {
		this.proceso_nombre = proceso_nombre;
	}

	public String getTipo_proceso() {
		return tipo_proceso;
	}

	public void setTipo_proceso(String tipo_proceso) {
		this.tipo_proceso = tipo_proceso;
	}

	public String getProceso_tipo() {
		return proceso_tipo;
	}

	public void setProceso_tipo(String proceso_tipo) {
		this.proceso_tipo = proceso_tipo;
	}

	public String getComp_claveacceso() {
		return comp_claveacceso;
	}

	public void setComp_claveacceso(String comp_claveacceso) {
		this.comp_claveacceso = comp_claveacceso;
	}

	public String getPlancontable_codigo() {
		return plancontable_codigo;
	}

	public void setPlancontable_codigo(String plancontable_codigo) {
		this.plancontable_codigo = plancontable_codigo;
	}

	public String getPlanconttable_nombre() {
		return planconttable_nombre;
	}

	public void setPlanconttable_nombre(String planconttable_nombre) {
		this.planconttable_nombre = planconttable_nombre;
	}

	public Integer getComp_modificado() {
		return comp_modificado;
	}

	public void setComp_modificado(Integer comp_modificado) {
		this.comp_modificado = comp_modificado;
	}

	public String getComp_notacreditomotivo() {
		return comp_notacreditomotivo;
	}

	public void setComp_notacreditomotivo(String comp_notacreditomotivo) {
		this.comp_notacreditomotivo = comp_notacreditomotivo;
	}

	public String getComp_fechacompmodificado() {
		return comp_fechacompmodificado;
	}

	public void setComp_fechacompmodificado(String comp_fechacompmodificado) {
		this.comp_fechacompmodificado = comp_fechacompmodificado;
	}

	public Integer getComp_modificado_pto() {
		return comp_modificado_pto;
	}

	public void setComp_modificado_pto(Integer comp_modificado_pto) {
		this.comp_modificado_pto = comp_modificado_pto;
	}

	public String getComp_modificado_punto_nombre() {
		return comp_modificado_punto_nombre;
	}

	public void setComp_modificado_punto_nombre(String comp_modificado_punto_nombre) {
		this.comp_modificado_punto_nombre = comp_modificado_punto_nombre;
	}

	public String getFactura_mod() {
		return factura_mod;
	}

	public void setFactura_mod(String factura_mod) {
		this.factura_mod = factura_mod;
	}

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

	public String getRecaudacion_codigobanco() {
		return recaudacion_codigobanco;
	}

	public void setRecaudacion_codigobanco(String recaudacion_codigobanco) {
		this.recaudacion_codigobanco = recaudacion_codigobanco;
	}

	public String getRecaudacion_nombre() {
		return recaudacion_nombre;
	}

	public void setRecaudacion_nombre(String recaudacion_nombre) {
		this.recaudacion_nombre = recaudacion_nombre;
	}

	public String getRecdetalle_codigo() {
		return recdetalle_codigo;
	}

	public void setRecdetalle_codigo(String recdetalle_codigo) {
		this.recdetalle_codigo = recdetalle_codigo;
	}

	public String getRecdetalle_nombre() {
		return recdetalle_nombre;
	}

	public void setRecdetalle_nombre(String recdetalle_nombre) {
		this.recdetalle_nombre = recdetalle_nombre;
	}

	public String getComp_numero_completo() {
		return comp_numero_completo;
	}

	public void setComp_numero_completo(String comp_numero_completo) {
		this.comp_numero_completo = comp_numero_completo;
	}

	public Integer getCompdetalle_cantidad() {
		return compdetalle_cantidad;
	}

	public void setCompdetalle_cantidad(Integer compdetalle_cantidad) {
		this.compdetalle_cantidad = compdetalle_cantidad;
	}

	public Double getCompdetalle_valoriva() {
		return compdetalle_valoriva;
	}

	public void setCompdetalle_valoriva(Double compdetalle_valoriva) {
		this.compdetalle_valoriva = compdetalle_valoriva;
	}

	public Double getCompdetalle_valorcero() {
		return compdetalle_valorcero;
	}

	public void setCompdetalle_valorcero(Double compdetalle_valorcero) {
		this.compdetalle_valorcero = compdetalle_valorcero;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Double getSubt_vdoc() {
		return subt_vdoc;
	}

	public void setSubt_vdoc(Double subt_vdoc) {
		this.subt_vdoc = subt_vdoc;
	}

	public Double getSubt_iva() {
		return subt_iva;
	}

	public void setSubt_iva(Double subt_iva) {
		this.subt_iva = subt_iva;
	}

	public Double getSubt_vcer() {
		return subt_vcer;
	}

	public void setSubt_vcer(Double subt_vcer) {
		this.subt_vcer = subt_vcer;
	}

	public Double getComdetalle_valor() {
		return comdetalle_valor;
	}

	public void setComdetalle_valor(Double comdetalle_valor) {
		this.comdetalle_valor = comdetalle_valor;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public Integer getNumero_factura() {
		return numero_factura;
	}

	public void setNumero_factura(Integer numero_factura) {
		this.numero_factura = numero_factura;
	}

	public Integer getComp_id_cierre() {
		return comp_id_cierre;
	}

	public void setComp_id_cierre(Integer comp_id_cierre) {
		this.comp_id_cierre = comp_id_cierre;
	}

	public Integer getComp_id() {
		return comp_id;
	}

	public void setComp_id(Integer comp_id) {
		this.comp_id = comp_id;
	}

	public boolean isComp_check_cierre() {
		return comp_check_cierre;
	}

	public void setComp_check_cierre(boolean comp_check_cierre) {
		this.comp_check_cierre = comp_check_cierre;
	}

	public Integer getRecaudacion_id() {
		return recaudacion_id;
	}

	public void setRecaudacion_id(Integer recaudacion_id) {
		this.recaudacion_id = recaudacion_id;
	}


	
	
}
