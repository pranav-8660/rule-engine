package com.pranavv51.microservices.rule_engine.service.astparseandexecute;

import com.pranavv51.microservices.rule_engine.model.astnodemodel.ASTNode;
import com.pranavv51.microservices.rule_engine.model.astnodemodel.ConstantNode;
import com.pranavv51.microservices.rule_engine.model.astnodemodel.OperationNode;
import com.pranavv51.microservices.rule_engine.model.astnodemodel.VariableNode;

import java.util.Map;

public class ExpressionEvaluator {

    public double evaluate(ASTNode node, Map<String, Double> variableValues) {
        if (node instanceof ConstantNode) {
            return ((ConstantNode) node).getValue();
        } else if (node instanceof VariableNode) {
            String varName = ((VariableNode) node).getName();
            return variableValues.getOrDefault(varName, 0.0);
        } else if (node instanceof OperationNode) {
            OperationNode operationNode = (OperationNode) node;
            double leftValue = evaluate(operationNode.getLeft(), variableValues);
            double rightValue = evaluate(operationNode.getRight(), variableValues);
            return evaluateOperation(operationNode.getOperator(), leftValue, rightValue);
        }
        throw new IllegalArgumentException("Unknown ASTNode type");
    }

    private static double evaluateOperation(String operator, double leftValue, double rightValue) {
        switch (operator) {
            case "+":
                return leftValue + rightValue;
            case "-":
                return leftValue - rightValue;
            case "*":
                return leftValue * rightValue;
            case "/":
                return leftValue / rightValue;
            case "=":
                return leftValue == rightValue ? 1 : 0; // Boolean logic
            case ">":
                return leftValue > rightValue ? 1 : 0; // Boolean logic
            case "<":
                return leftValue < rightValue ? 1 : 0; // Boolean logic
            case "AND":
                return (leftValue != 0 && rightValue != 0) ? 1 : 0; // Logical AND
            case "OR":
                return (leftValue != 0 || rightValue != 0) ? 1 : 0; // Logical OR
            default:
                throw new UnsupportedOperationException("Unknown operator: " + operator);
        }
    }
}
