package ec.gob.mdg.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "menu", schema = "financiero")
public class Menu implements Serializable {

	private static final long serialVersionUID = 5561280571115630382L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "codigo_submenu", nullable = true)
	private Menu submenu;

	@Column(name = "nombre", nullable = false, length = 50)
	private String nombre;
	
	//tipo de menuI o S
	@Column(name = "tipo", nullable = false, length = 2)
		private String tipo;
		
	@Column(name = "estado", nullable = false, length = 1)
	private String estado = "A";	

	@Column(name = "link", nullable = false, length = 50)
	private String link;
	
	@Column(name = "nombre_completo")
	private String nombre_completo;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Menu getSubmenu() {
		return submenu;
	}

	public void setSubmenu(Menu submenu) {
		this.submenu = submenu;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	

}
