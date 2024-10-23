package com.pranavv51.microservices.rule_engine.model;


import com.pranavv51.microservices.rule_engine.model.astnodemodel.ASTNode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "Rule-Table")
public class AstMongoRootNode {


    @Id
    private String ruleName;
    private List<String> variables;
    private ASTNode rootNode;

    public AstMongoRootNode(String ruleName, List<String> variables, ASTNode rootNode) {
        this.ruleName = ruleName;
        this.variables = variables;
        this.rootNode = rootNode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public List<String> getVariables() {
        return variables;
    }

    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    public ASTNode getRootNode() {
        return rootNode;
    }

    public void setRootNode(ASTNode rootNode) {
        this.rootNode = rootNode;
    }
}
