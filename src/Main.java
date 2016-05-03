import java.io.IOException;



public class Main {

	public static void main(String[] args) throws IOException, InterruptedException {
//		Thread.sleep(30*1000);
		String[] argvTrain = {
				"a1a", "a1a.model"	
		};
		String[] parg = {
			"ijcnn1.t", "ijcnn1-g0.04.model", "output_file"	
		};
		System.out.println("........SVM¿ªÊ¼ÁË..........");
//		svm_train.main(argvTrain);
		long d = System.currentTimeMillis();
		svm_predict.main(parg);
		System.out.println(System.currentTimeMillis() - d);
	}

}
