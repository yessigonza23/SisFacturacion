package ec.gob.mdg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "comprobante", schema = "financiero")
public class Comprobante implements Serializable {

	private static final long serialVersionUID = -8790047110447593581L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "id_usuariopunto")
	private UsuarioPunto usuarioPunto;
	
	@ManyToOne
	@JoinColumn(name = "id_puntorecaudacion")
	private PuntoRecaudacion puntoRecaudacion;

	@ManyToOne
	@JoinColumn(name = "id_cierre")
	private Cierre cierre;

	@Column(name = "numero", length = 11)
	private Integer numero;

	@Column(name = "tipocomprobante")
	private String tipocomprobante;

	@Column(name = "fechaemision")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaemision;

	@Column(name = "valor")
	private double valor;

	@Column(name = "estado", length = 1)
	private String estado = "A";

	@Column(name = "estadosri")
	private String estadosri = "P";

	@Column(name = "estadoerror")
	private String estadoerror="S" ;

	@Column(name = "detalle")
	private String detalle;

	@Column(name = "claveacceso")
	private String claveacceso;

	@Column(name = "autorizacion")
	private String autorizacion;

	@Column(name = "autorizacionfecha")
	private String autorizacionfecha;

	@Column(name = "xml")
	private String xml;

	@Column(name = "xmlsri")
	private String xmlsri;

	@Column(name = "clientenombre")
	private String clientenombre;

	@Column(name = "clientedireccion")
	private String clientedireccion;

	@Column(name = "clienteruc")
	private String clienteruc;

	@Column(name = "clientetelefono")
	private String clientetelefono;

	@Column(name = "fechaanulacion")
	private Date fechaanulacion;

	@Column(name = "comprobantereemplazo")
	private Integer comprobantereemplazo;

	@Column(name = "comprobantemod")
	private Integer comprobantemod;

	@Column(name = "comprobantepto" )
	private Integer comprobantepto;

	@Column(name = "motivonotacredito")
	private String motivonotacredito;
	
	@Column(name = "fechacomprmodificado")
	private Date fechacomprmodificado;

	@Column(name = "tipopago")
	private String tipopago = "1";

	@Column(name = "cierre_b")
	private boolean cierre_b= true;
	
	@Column(name = "last_send")
    private Date last_send;
    
	@Column(name = "last_email")
    private Date last_email;
    
    @Column
    private String send_email;
    
	//

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comprobante other = (Comprobante) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
    public String toString() {
        return "Comprobante[ id=" + id + " ]";
    }
		
	//////////////////////////////////

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getTipocomprobante() {
		return tipocomprobante;
	}

	public void setTipocomprobante(String tipocomprobante) {
		this.tipocomprobante = tipocomprobante;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getClaveacceso() {
		return claveacceso;
	}

	public void setClaveacceso(String claveacceso) {
		this.claveacceso = claveacceso;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public String getAutorizacionfecha() {
		return autorizacionfecha;
	}

	public void setAutorizacionfecha(String autorizacionfecha) {
		this.autorizacionfecha = autorizacionfecha;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getXmlsri() {
		return xmlsri;
	}

	public void setXmlsri(String xmlsri) {
		this.xmlsri = xmlsri;
	}

	public String getClientenombre() {
		return clientenombre;
	}

	public void setClientenombre(String clientenombre) {
		this.clientenombre = clientenombre;
	}

	public String getClientedireccion() {
		return clientedireccion;
	}

	public void setClientedireccion(String clientedireccion) {
		this.clientedireccion = clientedireccion;
	}

	public String getClienteruc() {
		return clienteruc;
	}

	public void setClienteruc(String clienteruc) {
		this.clienteruc = clienteruc;
	}

	public String getClientetelefono() {
		return clientetelefono;
	}

	public void setClientetelefono(String clientetelefono) {
		this.clientetelefono = clientetelefono;
	}

	public Date getFechaanulacion() {
		return fechaanulacion;
	}

	public void setFechaanulacion(Date fechaanulacion) {
		this.fechaanulacion = fechaanulacion;
	}

	public Date getFechacomprmodificado() {
		return fechacomprmodificado;
	}

	public void setFechacomprmodificado(Date fechacomprmodificado) {
		this.fechacomprmodificado = fechacomprmodificado;
	}
	
	public String getMotivonotacredito() {
		return motivonotacredito;
	}

	public void setMotivonotacredito(String motivonotacredito) {
		this.motivonotacredito = motivonotacredito;
	}

	public String getTipopago() {
		return tipopago;
	}

	public void setTipopago(String tipopago) {
		this.tipopago = tipopago;
	}

	public Date getFechaemision() {
		return fechaemision;
	}

	public void setFechaemision(Date fechaemision) {
		this.fechaemision = fechaemision;
	}

	public UsuarioPunto getUsuarioPunto() {
		return usuarioPunto;
	}

	public void setUsuarioPunto(UsuarioPunto usuarioPunto) {
		this.usuarioPunto = usuarioPunto;
	}

	public Cierre getCierre() {
		return cierre;
	}

	public void setCierre(Cierre cierre) {
		this.cierre = cierre;
	}

	public String getEstadosri() {
		return estadosri;
	}

	public void setEstadosri(String estadosri) {
		this.estadosri = estadosri;
	}

	public String getEstadoerror() {
		return estadoerror;
	}

	public void setEstadoerror(String estadoerror) {
		this.estadoerror = estadoerror;
	}

	public boolean isCierre_b() {
		return cierre_b;
	}

	public void setCierre_b(boolean cierre_b) {
		this.cierre_b = cierre_b;
	}

	public PuntoRecaudacion getPuntoRecaudacion() {
		return puntoRecaudacion;
	}

	public void setPuntoRecaudacion(PuntoRecaudacion puntoRecaudacion) {
		this.puntoRecaudacion = puntoRecaudacion;
	}

	public Integer getComprobantereemplazo() {
		return comprobantereemplazo;
	}

	public void setComprobantereemplazo(Integer comprobantereemplazo) {
		this.comprobantereemplazo = comprobantereemplazo;
	}

	public Integer getComprobantemod() {
		return comprobantemod;
	}

	public void setComprobantemod(Integer comprobantemod) {
		this.comprobantemod = comprobantemod;
	}

	public Integer getComprobantepto() {
		return comprobantepto;
	}

	public void setComprobantepto(Integer comprobantepto) {
		this.comprobantepto = comprobantepto;
	}

	public Date getLast_send() {
		return last_send;
	}

	public void setLast_send(Date last_send) {
		this.last_send = last_send;
	}

	public Date getLast_email() {
		return last_email;
	}

	public void setLast_email(Date last_email) {
		this.last_email = last_email;
	}

	public String getSend_email() {
		return send_email;
	}

	public void setSend_email(String send_email) {
		this.send_email = send_email;
	}

}
