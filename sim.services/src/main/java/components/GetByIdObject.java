package components;

import java.util.ArrayList;
import java.util.HashMap;

public class GetByIdObject {
	
	
	private int id;
	private ArrayList<HashMap<String,String>> results;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public ArrayList<HashMap<String,String>> getResults() {
		return results;
	}

	public void setResults(ArrayList<HashMap<String,String>> results) {
		this.results = results;
	}
}
