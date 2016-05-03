import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;


public class Deal {

	private final static double gamma = 0.001;
	
	public static void main(String[] args) {
		writeTofile("ijcnn1-g0.04.model.deal", "gamma:" + gamma);
		writeTofile("ijcnn1-g0.04.model.deal", "rho:" + dealRho("ijcnn1-g0.04.model"));
		writeTofile("ijcnn1-g0.04.model.deal", "c:" + dealC("ijcnn1-g0.04.model"));
		List<Map.Entry<Integer, Double>> v = new ArrayList<Map.Entry<Integer,Double>>(dealV("ijcnn1-g0.05.model").entrySet());
		Collections.sort(v, new Comparator<Map.Entry<Integer, Double>>(){
			@Override
			public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
				return o1.getKey().compareTo(o2.getKey());
			}
		});
		String vStr = "v\n";
		for (int i = 0; i < v.size(); ++i){
			Map.Entry<Integer, Double> node = v.get(i);
			vStr += node.getKey() + ":" + node.getValue() + " ";
		}
		writeTofile("ijcnn1-g0.04.model.deal", vStr);
		String mStr = "M\n";
		double[][] M = dealM("ijcnn1-g0.04.model");
		for (int i = 1; i < M.length; ++i){
//			boolean flag = false;
//			for (int j = 0; j < M[i].length; ++j){
//				if (M[i][j] != 0){
//					flag = true;
//				}
//			}
//			if (flag){
				for (int j = 1; j < M[i].length; ++j){
//					if (M[i][j] != 0){
						mStr += j + ":" + M[i][j] + " ";
//					}
				}
				mStr += "\n";
//			}
		}
		System.out.println("\n".length());
		writeTofile("ijcnn1-g0.04.model.deal", mStr.substring(0, mStr.length()-1));
	}
	
	public static double dealRho(String filename){
		File file = new File(filename);
		double b = 0;
		try {
			Scanner input = new Scanner(file);
			while (input.hasNextLine()){
				String line = input.nextLine();
				if (line.contains("rho")){
					b = Double.parseDouble(line.split(" ")[1]);
					break;
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
	
	public static double[][] dealM(String filename){
		
		double [][] sum = new double[124][124];
		File file = new File(filename);
		try {
			Scanner input = new Scanner(file);
			while (input.hasNextLine()){
				String line = input.nextLine();
				if (line.equals("SV")){
					break;
				}
			}
			while (input.hasNextLine()){
				String line = input.nextLine();
				double sum1 = 0;
				double coe = 0;
				if (line != null && line.length() != 0){
					String temp[] = line.split(" ");
					for (int i = 1; i < temp.length; ++i){
						double value = Double.parseDouble(temp[i].split(":")[1]);
						sum1 += value * value;
					}
					coe = Double.parseDouble(temp[0]) * Math.exp(-gamma*sum1) * 2 * gamma * gamma;
//					coe = 1;
					double[] oneLine = new double[124];
					for (int i = 1; i < temp.length; ++i){
						oneLine[Integer.parseInt(temp[i].split(":")[0])] = Double.parseDouble(temp[i].split(":")[1]);
					}
					for (int i = 0; i < sum.length; ++i){
						for (int j = 0; j < sum[i].length; ++j){
							sum[i][j] += oneLine[i] * oneLine[j] * coe;
						}
					}
				}
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sum;
	}
	
	public static HashMap<Integer, Double> dealV(String filename){
		
		File file = new File(filename);
		HashMap<Integer, Double> sum = new HashMap<Integer, Double>();
		try {
			Scanner input = new Scanner(file);
			while (input.hasNextLine()){
				String line = input.nextLine();
				if (line.equals("SV")){
					break;
				}
			}
			while (input.hasNextLine()){
				String line = input.nextLine();
				double sum1 = 0;
				double coe = 0;
				if (line != null && line.length() != 0){
					String temp[] = line.split(" ");
					for (int i = 1; i < temp.length; ++i){
						double value = Double.parseDouble(temp[i].split(":")[1]);
						sum1 += value * value;
					}
					coe = Double.parseDouble(temp[0]) * Math.exp(-gamma*sum1) * 2 * gamma;
					for (int i = 1; i < temp.length; ++i){
						String[] entry = temp[i].split(":");
						int index = Integer.parseInt(entry[0]);
						double value = Double.parseDouble(entry[1]);
						if (sum.containsKey(index)){
							sum.put(index, sum.get(index) + coe*value);
						}else{
							sum.put(index, coe*value);
						}
					}
				}
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sum;
	}
	
	public static double dealC(String filename){
		
		File file = new File(filename);
		double sum = 0;
		try {
			Scanner input = new Scanner(file);
			while (input.hasNextLine()){
				String line = input.nextLine();
				if (line.equals("SV")){
					break;
				}
			}
			while (input.hasNextLine()){
				String line = input.nextLine();
				double sum1 = 0;
				if (line != null && line.length() != 0){
					String temp[] = line.split(" ");
					for (int i = 1; i < temp.length; ++i){
						double value = Double.parseDouble(temp[i].split(":")[1]);
						sum1 += value * value;
					}
					sum += Double.parseDouble(temp[0]) * Math.exp(-gamma*sum1);
				}
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sum;
	}
	
	public static void writeTofile(String filename, String str){
		
		try {
			FileWriter writer = new FileWriter(filename, true);
			writer.write(str + "\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
