package model.logic;

public class Ubicacion 
{
	private String type;
	private Double[] coordinates;

	public Ubicacion(String pType, Double[] pCoord) {

		type = pType;
		coordinates = pCoord;

	}

	public String getType()
	{
		return type;
	}

	public Double[] getCoord()
	{
		return coordinates;
	}

}
