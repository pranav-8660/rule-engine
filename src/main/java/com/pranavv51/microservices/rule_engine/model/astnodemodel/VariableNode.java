package com.pranavv51.microservices.rule_engine.model.astnodemodel;

public class VariableNode implements ASTNode {
    private final String name;

    public VariableNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}