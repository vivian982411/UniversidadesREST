package com.ibm.academia.apirest.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "carreras", schema = "universidad")
//@Table(name = "carreras")
public class Carrera implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre", unique = true, nullable = false, length = 80)
	private String nombre;
	
	@Column(name = "cantidad_materias")
	private Integer cantidadMaterias;
	
	@Column(name = "cantidad_anios")
	private Integer cantidadAnios;
	
	@Column(name = "fecha_alta")
	private Date fechaAlta;
	
	@Column(name = "fecha_modificacion")
	private Date fechaModificacion;
	
	@OneToMany(mappedBy = "carrera", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Alumno> alumnos;
	
	@ManyToMany(mappedBy = "carreras", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<Profesor> profesores;
	
	public Carrera(Integer id, String nombre, Integer cantidadMaterias, Integer cantidadAnios) {
		this.id = id;
		this.nombre = nombre;
		this.cantidadMaterias = cantidadMaterias;
		this.cantidadAnios = cantidadAnios;
	}
	
	@Override
	public String toString() {
		return "Carrera [id=" + id + ", nombre=" + nombre + ", cantidadMaterias=" + cantidadMaterias
				+ ", cantidadAnios=" + cantidadAnios + ", fechaAlta=" + fechaAlta + ", fechaModificacion="
				+ fechaModificacion + "]";
	}
	
	@Override
	public int hashCode() 
	{
		return Objects.hash(id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Carrera other = (Carrera) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre);
	}
	
	@PrePersist
	private void antesPersistir()
	{
		this.fechaAlta = new Date();
	}
	
	@PreUpdate
	private void antesActualizar()
	{
		this.fechaModificacion = new Date();
	}

	private static final long serialVersionUID = 7386711690996525949L;
}