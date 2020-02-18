package model.logic;


public class PrimeraClase {
	private String type;
	private Multa[] features;
	private String nombre;
	private CRS crs;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Multa[] getFeatures() {
		return features;
	}
	public void setFeatures(Multa[] features) {
		this.features = features;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public CRS getCrs() {
		return crs;
	}
	public void setCrs(CRS crs) {
		this.crs = crs;
	}


}
