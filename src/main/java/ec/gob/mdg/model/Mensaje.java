package ec.gob.mdg.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "mensaje", schema = "financiero")
public class Mensaje implements Serializable  {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "id_comprobante")
    private Integer id_comprobante;
    
    @Column
    private String identificador;
    
    @Column
    private String mensaje;
    
    @Column(name = "informacion_adicional")
    private String informacionAdicional;
    
    @Column
    private String tipo;

    public Mensaje() {
    }

    public Mensaje(String identificador, String mensaje, String informacionAdicional, String tipo) {
        this.identificador = identificador;
        this.mensaje = mensaje;
        this.informacionAdicional = informacionAdicional;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Mensaje["+identificador+","+mensaje+","+informacionAdicional+","+ tipo+"]";
    }
    
    

    /**
     * @return the identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * @param identificador the identificador to set
     */
    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the informacionAdicional
     */
    public String getInformacionAdicional() {
        return informacionAdicional;
    }

    /**
     * @param informacionAdicional the informacionAdicional to set
     */
    public void setInformacionAdicional(String informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

	public Integer getId_comprobante() {
		return id_comprobante;
	}

	public void setId_comprobante(Integer id_comprobante) {
		this.id_comprobante = id_comprobante;
	}

   
}