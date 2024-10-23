package com.pranavv51.microservices.rule_engine.model;

public class InputFromUser {
    private String ruleName;
    private StringBuilder rule;

    public InputFromUser(String ruleName, StringBuilder rule) {
        this.ruleName = ruleName;
        this.rule = rule;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public StringBuilder getRule() {
        return rule;
    }

    public void setRule(StringBuilder rule) {
        this.rule = rule;
    }
}
