package com.ibm.academia.apirest.models.entities;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import com.ibm.academia.apirest.enums.TipoEmpleado;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "empleados")
@Table(name = "empleados", schema = "universidad")
//@Table(name = "empleados")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Empleado extends Persona {
	@Positive(message = "El sueldo debe ser mayor a cero")
	@Column(name = "sueldo")
	private BigDecimal sueldo;
	
	@Column(name = "tipo_empleado")
	@Enumerated(EnumType.STRING)
	private TipoEmpleado tipoEmpleado;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "pabellon_id", foreignKey = @ForeignKey(name = "FK_PABELLON_ID"))
	private Pabellon pabellon;
	
	public Empleado(Integer id, String nombre, String apellido, String dni, Direccion direccion, BigDecimal sueldo, TipoEmpleado tipoEmpleado) {
		super(id, nombre, apellido, dni, direccion);
		this.sueldo = sueldo;
		this.tipoEmpleado = tipoEmpleado;
	}
	
	@Override
	public String toString() {
		return super.toString() + "\tEmpleado [sueldo=" + sueldo + ", tipoEmpleado=" + tipoEmpleado + "]";
	}

	private static final long serialVersionUID = 1406687831483277829L;
}