package com.pranavv51.microservices.rule_engine.model.astnodemodel;

public class ConstantNode implements ASTNode {
    private final double value;

    public ConstantNode(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}