package restaurante;

import java.util.ArrayList;

public class Table {
	ArrayList<String> chairs = new ArrayList<String>();
	Boolean[] cluteries;

	public Table(Boolean[] cluteries) {
		this.cluteries = cluteries;
	}

}
