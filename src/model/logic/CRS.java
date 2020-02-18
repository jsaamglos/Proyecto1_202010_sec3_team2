package model.logic;

public class CRS {

	private String type;
	private propiedadesCRS propiedades;

	public class propiedadesCRS {
		private String name;

		public propiedadesCRS(String pName) {
			name = pName;
		}
	}

	public CRS(String pType, propiedadesCRS pPropiedades) {
		type = pType;
		propiedades = pPropiedades;
	}

}
