package com.pranavv51.microservices.rule_engine.service.astparseandexecute;

import com.pranavv51.microservices.rule_engine.model.astnodemodel.ASTNode;
import com.pranavv51.microservices.rule_engine.model.astnodemodel.ConstantNode;
import com.pranavv51.microservices.rule_engine.model.astnodemodel.OperationNode;
import com.pranavv51.microservices.rule_engine.model.astnodemodel.VariableNode;

import java.util.*;

public class ASTParser {
    private static final Set<String> OPERATORS = Set.of("+", "-", "*", "/", ">", "<", "=", "AND", "OR");
    private static final Map<String, Integer> PRECEDENCE = Map.of(
            "=", 1,
            ">", 1,
            "<", 1,
            "AND", 2,
            "OR", 2,
            "+", 3,
            "-", 3,
            "*", 4,
            "/", 4
    );

    public ASTNode parse(String rule) {
        List<String> tokens = tokenize(rule);
        return parseExpression(tokens);
    }

    private List<String> tokenize(String rule) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        for (char c : rule.toCharArray()) {
            if (Character.isWhitespace(c)) {
                continue;
            } else if (OPERATORS.contains(String.valueOf(c)) || c == '(' || c == ')') {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token.setLength(0);
                }
                if (c == '(' || c == ')') {
                    tokens.add(String.valueOf(c));
                } else {
                    // Check for multi-character operators like AND, OR
                    token.append(c);
                    String nextChar = "";
                    if (token.toString().equals("A") || token.toString().equals("O")) {
                        continue; // Skip current iteration for incomplete token
                    }
                    if (tokens.size() > 0 && (tokens.get(tokens.size() - 1).equals("A") || tokens.get(tokens.size() - 1).equals("O"))) {
                        nextChar = String.valueOf(c);
                    }
                    if (nextChar.equals("N") && token.toString().equals("A")) {
                        tokens.remove(tokens.size() - 1); // Remove "A"
                        tokens.add("AND");
                    } else if (nextChar.equals("R") && token.toString().equals("O")) {
                        tokens.remove(tokens.size() - 1); // Remove "O"
                        tokens.add("OR");
                    } else {
                        tokens.add(token.toString());
                    }
                    token.setLength(0);
                }
            } else {
                token.append(c);
            }
        }
        if (token.length() > 0) {
            tokens.add(token.toString());
        }
        return tokens;
    }

    private ASTNode parseExpression(List<String> tokens) {
        Stack<ASTNode> values = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // Remove '('
            } else if (OPERATORS.contains(token)) {
                while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek())) {
                    values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(token);
            } else if (token.matches("\\d+")) {
                values.push(new ConstantNode(Double.parseDouble(token)));
            } else {
                values.push(new VariableNode(token));
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private int precedence(String operator) {
        return PRECEDENCE.getOrDefault(operator, 0);
    }

    private ASTNode applyOperation(String operator, ASTNode right, ASTNode left) {
        return new OperationNode(left, right, operator);
    }

    public List<String> getVariablesInTheRule(String rule) {
        String[] parts = rule.split(" ");
        Set<String> variableSet = new LinkedHashSet<>(); // Use a Set to avoid duplicates
        for (String part : parts) {
            if (!part.matches("\\d+") && !OPERATORS.contains(part) && !part.equals("(") && !part.equals(")")) {
                variableSet.add(part);
            }
        }
        return new ArrayList<>(variableSet); // Convert the Set back to a List
    }
}
