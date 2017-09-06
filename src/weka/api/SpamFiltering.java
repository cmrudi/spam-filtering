package weka.api;

import java.text.DecimalFormat;
import java.util.Random;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Option;
import weka.filters.Filter;

public class SpamFiltering {
	private Instances dataSetInstances;
	private Classifier currentClassifier;
	private Option option;
	private Filter currentFilter;
	
	public SpamFiltering() {
		
	}
	
	public void setDataset(Instances inputDataSet) {
		this.dataSetInstances = inputDataSet;
	}
	
	public void setCurrentClassifier(Classifier currentClassifier) {
		this.currentClassifier = currentClassifier;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public void setFilter(Filter filter) {
		this.currentFilter = filter;
	}

	public Evaluation trainData(String classifierName) throws Exception {
		long startTime = System.nanoTime();    
		AbstractClassifier cls;
		switch (classifierName) {
			case "nb": {
				cls = new NaiveBayes();
				break;
			}
			case "dtl": {
				cls = new J48();
				break;
			}
			default: {
				cls = new SMO();
				break;
			}
		}
		cls.buildClassifier(dataSetInstances);
		
		Evaluation evalResult = new Evaluation(dataSetInstances);
		evalResult.crossValidateModel(cls, dataSetInstances, 5, new Random(1));
		System.out.println("Processing Time: "+new DecimalFormat("#.###").format((double)(System.nanoTime() - startTime)/(1000000000)) + " Seconds");
		return evalResult;
	}
		
	public String classifyMessage() {
		
		return "";
	}
	
	public static void main(String Args[]) {
		SpamFiltering main = new SpamFiltering();
		
		DataSet dataset = new DataSet("data/SMSSpamCollection");
		Instances instances = dataset.getInstances();
		main.setDataset(instances);
		
		try {
			System.out.println("Naive Bayes Data Training");
			Evaluation result = main.trainData("nb");
			System.out.println(result.toSummaryString());

			System.out.println("Decision-Tree-Learning Data Training");
			result = main.trainData("dtl");
			System.out.println(result.toSummaryString());

			System.out.println("SMO Training");
			result = main.trainData("smo");
			System.out.println(result.toSummaryString());
			
			System.out.println("Data Training Ends");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
