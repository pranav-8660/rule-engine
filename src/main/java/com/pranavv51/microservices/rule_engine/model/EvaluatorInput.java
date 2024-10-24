package com.pranavv51.microservices.rule_engine.model;

import java.util.HashMap;


//dto
public class EvaluatorInput {
	
	
	private String ruleNameString;
	private HashMap<String, Double> variableToValue;
	public EvaluatorInput(String ruleNameString, HashMap<String, Double> variableToValue) {
		super();
		this.ruleNameString = ruleNameString;
		this.variableToValue = variableToValue;
	}
	public String getRuleNameString() {
		return ruleNameString;
	}
	public void setRuleNameString(String ruleNameString) {
		this.ruleNameString = ruleNameString;
	}
	public HashMap<String, Double> getVariableToValue() {
		return variableToValue;
	}
	public void setVariableToValue(HashMap<String, Double> variableToValue) {
		this.variableToValue = variableToValue;
	}
	
	
	

}
