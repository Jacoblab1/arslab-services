package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class runSimulation {
	
	
	
	public static ArrayList<String> run(){
		ArrayList<String> results = new ArrayList<String>();
		String foldername = "Alternating_Bit_Protocol";
		String function = "RECEIVER_TEST";
		String inputFile = "receiver_input_test.txt";
		String fileContents = "00:00:10 1 1\r\n"
				+ "00:00:30 2 0\r\n"
				+ "00:00:45 3 1\r\n"
				+ "00:00:52 3 1\r\n"
				+ "00:01:25 4 0\r\n"
				+ "00:01:35 4 0\r\n"
				+ "00:01:55 5 1 ";
		
		String resultFiles[] = {"receiver_test_output_messages.txt","receiver_test_output_state.txt"};
		
		
		FileWriter myWriter;
		try {
			myWriter = new FileWriter("C:\\Cadmium-Simulation-Environment\\DEVS-Models\\" + foldername + "\\input_data\\" + inputFile);
			myWriter.write(fileContents);
			myWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Can not find input_data file");
		}
		
		
		Process process;
		try {
			process = Runtime.getRuntime().exec("C:\\cygwin64\\Cygwin.bat -i", null, new File("C:\\Cadmium-Simulation-Environment\\DEVS-Models\\Alternating_Bit_Protocol\\bin"));
			OutputStream stdin = process.getOutputStream();
			PrintWriter pw = new PrintWriter(stdin);
			
			pw.println("cd C://Cadmium-Simulation-Environment//DEVS-Models//" + foldername + "//bin");
			pw.println("./" + function + ".exe");
			pw.close();
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("could not run Cadmium");
		} 
		
		
		for(int i = 0; i < resultFiles.length; i++) {
			String filename = resultFiles[i];
			System.out.println("\n Readin Result file: " + filename + "\n");
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader("C:\\Cadmium-Simulation-Environment\\DEVS-Models\\" + foldername + "\\simulation_results\\" + filename));
				StringBuilder sb = new StringBuilder();
			    String line = br.readLine();
			    
			    while (line != null) {
			        sb.append(line + "<br>");
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    String everything = sb.toString();
			    
			   results.add(everything);
			} catch (FileNotFoundException e) {
				System.out.print("Could not find output file");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
		
		return results;
	}
}
