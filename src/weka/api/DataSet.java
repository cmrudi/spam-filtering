package weka.api;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.attribute.Discretize;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class DataSet {
	private final int NUM_DATA = 100;
	private final int NUM_ATTRIBUTES = 2;
	private final String COLLECTION_NAME = "SMSCollection";
	
	private Instances wekaInstances;
	private List<Message> messageList;
	private String filePath;
	
	public DataSet(String filePath) {
		this.filePath = filePath;
		createInstances();
		filterStringToWordVector();
	}
	
	public Instances getInstances() {
		return wekaInstances;
	}
	
	private void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	private void createInstances() {
		FastVector fvNominalVal = new FastVector(2);
		fvNominalVal.addElement(MessageStatus.SPAM.getStatus());
		fvNominalVal.addElement(MessageStatus.HAM.getStatus());
		Attribute attribute1 = new Attribute("label",fvNominalVal);
		Attribute attribute2 = new Attribute("message", (FastVector) null);
		
		FastVector fvWekaAttributes = new FastVector(NUM_ATTRIBUTES);
		fvWekaAttributes.addElement(attribute1);
		fvWekaAttributes.addElement(attribute2);
		wekaInstances = new Instances(COLLECTION_NAME, fvWekaAttributes, NUM_DATA);
		wekaInstances.setClassIndex(0);
		
		if (filePath != null) {
			try {
				FileReader fileReader = new FileReader(filePath);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String message;
				while (( message = bufferedReader.readLine()) != null && message.length() != 0) {
					StringTokenizer st = new StringTokenizer(message , "\t");
					String label = st.nextToken();
					String textMessage = st.nextToken();
					Instance instance = new DenseInstance(NUM_ATTRIBUTES);
					instance.setValue((Attribute)fvWekaAttributes.elementAt(0), label);
					instance.setValue((Attribute)fvWekaAttributes.elementAt(1), textMessage);
					wekaInstances.add(instance);
				}
				bufferedReader.close();
				fileReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void filterStringToWordVector() {
		StringToWordVector filter = new StringToWordVector();
		try {
			//String[] opts = new String[]{"-B","0"};
			//wekaInstances = filterDiscretize(wekaInstances, opts);
			
			filter.setInputFormat(wekaInstances);
			wekaInstances = Filter.useFilter(wekaInstances, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(wekaInstances);
	}
	
	private Instances filterDiscretize(Instances dataset, String[] opts) throws Exception {
		Discretize discretize = new Discretize();
		discretize.setOptions(opts);
		discretize.setInputFormat(dataset);
		Instances numData = Filter.useFilter(dataset, discretize);
		return numData;
	}
	
	
//	public static void main(String[] args) {
//		DataSet dataset = new DataSet("data/SMSSpamCollection");
//		Instances instances = dataset.getInstances();
//	
//	}
	
}
