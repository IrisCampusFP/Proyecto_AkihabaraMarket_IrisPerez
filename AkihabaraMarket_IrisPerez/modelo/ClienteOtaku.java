package modelo;

import java.util.Date;

public class ClienteOtaku {
	private int id;
	private String nombre;
	private String email;
	private String telefono;
	private Date fecha_registro;
	
	// Constructor con ID
	public ClienteOtaku(int id, String nombre, String email, String telefono, Date fecha_registro) {
		this.setId(id);
		this.setNombre(nombre);
		this.setEmail(email);
		this.setTelefono(telefono);
		this.setFechaRegistro(fecha_registro);
	}
	
	// Constructor sin ID
	public ClienteOtaku(String nombre, String email, String telefono) {
		this.setNombre(nombre);
		this.setEmail(email);
		this.setTelefono(telefono);
	}
	
	// Getters y setters
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaRegistro() {
		return fecha_registro;
	}

	public void setFechaRegistro(Date fecha_registro) {
		this.fecha_registro = fecha_registro;
	}
	
	
}
