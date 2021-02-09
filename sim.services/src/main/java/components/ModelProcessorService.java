package components;

import controllers.S3Controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class ModelProcessorService {

	
	public static HashMap<String,HashMap<String,String>> storedModelSimulations;	
	public static HashMap<Integer,ArrayList<HashMap<Integer, HashMap<String,String>>>> sortParentChildRelationship(ArrayList<HashMap<String,String>> modelsChildren) {
		
		HashMap<Integer,ArrayList<HashMap<Integer, HashMap<String,String>>>> modelsListOfChildren = new HashMap<Integer,ArrayList<HashMap<Integer, HashMap<String,String>>>>();
		if(modelsChildren.size() != 0) {
			for(int i = 0; i < modelsChildren.size(); i++) {
				HashMap<String, String> child = modelsChildren.get(i);
				int childID = Integer.parseInt(child.get("modelid"));
				int parentID = Integer.parseInt(child.get("parentid"));
				ArrayList<HashMap<Integer, HashMap<String,String>>> modelsChildrenList = modelsListOfChildren.get(parentID);
				if(modelsChildrenList == null) {
					modelsChildrenList = new ArrayList<HashMap<Integer, HashMap<String,String>>>();
				}
				
				HashMap<Integer, HashMap<String,String>> childMap = new HashMap<Integer, HashMap<String,String>>();
				childMap.put(childID,child);
				modelsChildrenList.add(childMap);
				modelsListOfChildren.put(parentID, modelsChildrenList);
			}
		}
		
		return modelsListOfChildren;
	}
	
	public static ArrayList<String> parseModelSimulations(HashMap<String,ArrayList<HashMap<String,String>>>  modelSimulations) {
		
		
		storedModelSimulations = new HashMap<String,HashMap<String,String>>();
		ArrayList<String> simulationIds = new ArrayList<String>();
		
		
		for(String key : modelSimulations.keySet()) {
			if(!key.equals("everyThingElse")) {
				ArrayList<HashMap<String,String>> simulationFiles = modelSimulations.get(key);
				JSONArray jsonArray = new JSONArray();
				HashMap<String,String> simulations = new HashMap<String,String>();
				for(HashMap<String,String> simulationFile : simulationFiles) {
					simulations.put(simulationFile.get("name"),simulationFile.get("location"));
				}
				jsonArray.put(simulations);
			
				//storedModelSimulations.put(key,jsonArray.get(0).toString());
				storedModelSimulations.put(key,simulations);
				simulationIds.add(key);
			}
		
		}
		return simulationIds;
	}
	
	public static HashMap<String,String> getSimulation(String id) {
		System.out.println(storedModelSimulations);
		return storedModelSimulations.get(id);
	}

	

}
