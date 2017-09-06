package weka.api;

import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Option;

public class SpamFiltering {
	private Instances dataSet;
	private Classifier currentClassifier;
	private Option option;
	
	public void setDataset(Instances inputDataSet) {
		this.dataSet = inputDataSet;
	}
	
	public void setCurrentClassifier(Classifier currentClassifier) {
		this.currentClassifier = currentClassifier;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public void trainData(String classifierName) throws Exception {
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
		cls.buildClassifier(dataSet);
		this.setCurrentClassifier(cls);
	}
		
	public Evaluation testClassifier() throws Exception {
		Evaluation evalResult = new Evaluation(dataSet);
		evalResult.evaluateModel(currentClassifier, dataSet);
		return evalResult;
	}
	
	public static void main(String Args[]) {
		System.out.println(1+222);
	}
}
