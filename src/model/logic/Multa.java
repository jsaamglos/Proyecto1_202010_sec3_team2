package model.logic;

public class Multa {

	private Caracteristica properties;
	private Ubicacion geometry;
	private String type;

	public Caracteristica getProperties() {
		return properties;
	}

	public void setProperties(Caracteristica properties) {
		this.properties = properties;
	}

	public Ubicacion getGeometry() {
		return geometry;
	}

	public void setGeometry(Ubicacion geometry) {
		this.geometry = geometry;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}