package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.mdg.control.model.Fin_Cal_Ren_DTO;
import ec.gob.mdg.control.model.Fin_Cat_Act_Sit_Veh_Java_DTO;
import ec.gob.mdg.control.model.Fin_Exportaciones_DTO;
import ec.gob.mdg.control.model.Fin_Guias_DTO;
import ec.gob.mdg.control.model.Fin_Importaciones_DTO;
import ec.gob.mdg.control.model.Fin_Importaciones_No_DTO;
import ec.gob.mdg.control.model.Fin_Reptecnicos_DTO;

@Named
@ViewScoped
public class ConsultaServiciosCyfBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Fin_Cal_Ren_DTO> listaFinCalRenDTO;
	private List<Fin_Cat_Act_Sit_Veh_Java_DTO> listaCambioCategoria;
	private List<Fin_Exportaciones_DTO> listaExportaciones;
	private List<Fin_Importaciones_DTO> listaImportaciones;
	private List<Fin_Guias_DTO> listaGuias;
	private List<Fin_Importaciones_No_DTO> listaImportacionesNo;
	private List<Fin_Reptecnicos_DTO> listaRepTecnicos;
	
	////////// IR A DETALLES DE LOS SERVICIOS

	/// IR A DETALLE CALIFICACION RENOVACION
	public String irDetalleCalRen(String codigo) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", codigo);
		return "calificaciondetalle?faces-redirect=true";
	}

	/// IR A DETALLE CAMBIO DE CATEGORIA
	public String irDetalleCambioCategoria(String codigo) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", codigo);
		return "ampliaciondetalle?faces-redirect=true";
	}

	/// IR A DETALLE EXPORTACIONES
	public String irDetalleExportacion(String cod_entidad) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", cod_entidad);
		return "exportaciondetalle?faces-redirect=true";
	}

	/// IR A DETALLE IMPORTACIONES
	public String irDetalleImportacion(String cod_entidad) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", cod_entidad);
		return "importaciondetalle?faces-redirect=true";
	}

	/// IR A DETALLE IMPORTACIONES NO CONTROLADOS
	public String irDetalleImportacionNo(String cod_entidad) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", cod_entidad);
		return "importacionnodetalle?faces-redirect=true";
	}

	/// IR A DETALLE GUIAS
	public String irDetalleGuias(String cod_entidad) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", cod_entidad);
		return "guiasdetalle?faces-redirect=true";
	}
		
	/// IR A DETALLE REPRESENTANTE TECNICO
	public String irDetalleRepTecnico(String idtec) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", idtec);
		return "reptecnicodetalle?faces-redirect=true";
	}

	///// GETTERS Y SETTERS
	public List<Fin_Cal_Ren_DTO> getListaFinCalRenDTO() {
		return listaFinCalRenDTO;
	}

	public void setListaFinCalRenDTO(List<Fin_Cal_Ren_DTO> listaFinCalRenDTO) {
		this.listaFinCalRenDTO = listaFinCalRenDTO;
	}

	public List<Fin_Cat_Act_Sit_Veh_Java_DTO> getListaCambioCategoria() {
		return listaCambioCategoria;
	}

	public void setListaCambioCategoria(List<Fin_Cat_Act_Sit_Veh_Java_DTO> listaCambioCategoria) {
		this.listaCambioCategoria = listaCambioCategoria;
	}

	public List<Fin_Exportaciones_DTO> getListaExportaciones() {
		return listaExportaciones;
	}

	public void setListaExportaciones(List<Fin_Exportaciones_DTO> listaExportaciones) {
		this.listaExportaciones = listaExportaciones;
	}

	public List<Fin_Importaciones_DTO> getListaImportaciones() {
		return listaImportaciones;
	}

	public void setListaImportaciones(List<Fin_Importaciones_DTO> listaImportaciones) {
		this.listaImportaciones = listaImportaciones;
	}

	public List<Fin_Importaciones_No_DTO> getListaImportacionesNo() {
		return listaImportacionesNo;
	}

	public void setListaImportacionesNo(List<Fin_Importaciones_No_DTO> listaImportacionesNo) {
		this.listaImportacionesNo = listaImportacionesNo;
	}

	public List<Fin_Guias_DTO> getListaGuias() {
		return listaGuias;
	}

	public void setListaGuias(List<Fin_Guias_DTO> listaGuias) {
		this.listaGuias = listaGuias;
	}

	public List<Fin_Reptecnicos_DTO> getListaRepTecnicos() {
		return listaRepTecnicos;
	}

	public void setListaRepTecnicos(List<Fin_Reptecnicos_DTO> listaRepTecnicos) {
		this.listaRepTecnicos = listaRepTecnicos;
	}

}
