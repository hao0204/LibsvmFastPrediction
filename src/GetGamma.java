import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class GetGamma {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double m1 = getMax("ijcnn1");
		double m2 = getMax("ijcnn1.t");
		System.out.println("gamma max: " + Math.sqrt(1.0/(16*m1*m2)));
	}
	
	public static double getMax(String filename){
		File file = new File(filename);
		double m = 0;
		try {
			Scanner input = new Scanner(file);
			while(input.hasNextLine()){
				String[] temp = input.nextLine().split(" ");
				double d = compute(temp);
				if (m < d)
					m = d;
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}
	
	public static double compute(String[] temp){
		double sum = 0;
		for (int i = 1; i < temp.length; ++i){
			double t = Double.parseDouble(temp[i].split(":")[1]);
			sum += t*t;
		}
		return sum;
	}
}
