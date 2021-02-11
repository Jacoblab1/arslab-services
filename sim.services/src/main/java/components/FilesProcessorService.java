package components;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FilesProcessorService {

	private static HashMap<String, byte[]> storedFiles = new HashMap<String, byte[]>();

	public static byte[] zipFiles(ArrayList<HashMap<String, String>> files) {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(byteArrayOutputStream);
			InputStream is;
			for (int i = 0; i < files.size(); i++) {
				// get the files download location from S3 specified by the location key
				
				String location = files.get(i).get("location");
				//open the files location
				URL url = new URL(location);
				is = url.openStream();
				
				
				String fileLocation[] = location.split("files/", 2);
				String fileName[];
				if(fileLocation.length > 1) {
					fileName = fileLocation[1].split("/");
				}else {
					fileName = fileLocation[0].split("/");
				}
				String filepath = String.join("/",(Arrays.copyOfRange(fileName, 1, fileName.length)));
				zip.putNextEntry(new ZipEntry(filepath.replace("%20", " ")));

				int length;
				byte[] b = new byte[2048];
				//input the file 2048 bytes at a time
				while ((length = is.read(b)) > 0) {
					zip.write(b, 0, length);
				}
				zip.closeEntry();
				is.close();
			}
			zip.close();
			return byteArrayOutputStream.toByteArray();

		} catch (Exception e) {
			System.out.println("An error has occurred when attempting to zip files in: fileProcessorService.zipFile()");
			System.out.println(e.toString());
		}
		return null;
	}

	

	public static HashMap<String,ArrayList<HashMap<String,String>>> sortFilesByAttribute(ArrayList<HashMap<String, String>> files, String attribute) {
		HashMap<String,ArrayList<HashMap<String,String>>> sortedFiles = new HashMap<String,ArrayList<HashMap<String,String>>>();
		sortedFiles.put("everyThingElse", new ArrayList<HashMap<String,String>>());
		for(int i = 0; i < files.size(); i++) {
			HashMap<String, String> file = files.get(i);
			if(!Objects.isNull(file.get(attribute))) {
				String simulationId = file.get(attribute);
				ArrayList<HashMap<String, String>> simulationFiles = sortedFiles.get(simulationId);
				if(Objects.isNull(simulationFiles)) {
					simulationFiles = new ArrayList<HashMap<String, String>>();
				}
				simulationFiles.add(file);
				sortedFiles.put(simulationId,simulationFiles);
			}else {
				ArrayList<HashMap<String, String>> otherFiles =  sortedFiles.get("everyThingElse");
				otherFiles.add(file);
			}
		}
		return sortedFiles;
	}

	public static void addStoredFile(String key, byte[] fileData){
		storedFiles.put(key,fileData);
	}

	public static byte[] getStoredFile(String key){
		return storedFiles.get(key);
	}


}
