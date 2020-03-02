package model.logic;

public class CRS {

	private String type;
	private propiedadesCRS propiedades;

	public class propiedadesCRS {
		private String name;

		public propiedadesCRS(String pName) {
			name = pName;
		}

		public String getName()
		{
			return name;
		}
	}

	public CRS(String pType, propiedadesCRS pPropiedades) {
		type = pType;
		propiedades = pPropiedades;
	}

	public propiedadesCRS getPropiedades()
	{
		return propiedades;
	}

	public String getType()
	{
		return type;
	}

}
