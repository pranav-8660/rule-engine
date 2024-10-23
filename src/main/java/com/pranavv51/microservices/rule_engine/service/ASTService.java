package com.pranavv51.microservices.rule_engine.service;

import com.pranavv51.microservices.rule_engine.model.AstMongoRootNode;
import com.pranavv51.microservices.rule_engine.model.astnodemodel.ASTNode;
import com.pranavv51.microservices.rule_engine.repository.ASTMongoRepo;
import com.pranavv51.microservices.rule_engine.service.astparseandexecute.ASTParser;
import com.pranavv51.microservices.rule_engine.service.astparseandexecute.ExpressionEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class ASTService {

    @Autowired
    private ASTMongoRepo astMongoRepoInst;


    public boolean storeRuleInDB(String ruleName, String rule) {
        ASTParser astParser = new ASTParser(); // Create an instance of ASTParser
        ASTNode rootNode = astParser.parse(rule); // Parse the rule to get the root node

        // Assuming you have a method to extract variable names from the rule
        List<String> variables = astParser.getVariablesInTheRule(rule); // You need to implement this method in ASTParser

        // Save the parsed rule into the database
        astMongoRepoInst.save(new AstMongoRootNode(ruleName, variables, rootNode));

        return true; // Return true after successful operation
    }


    private boolean evaluateExpression(HashMap<String,Double> values,ASTNode rootNode){
        ExpressionEvaluator expressionEvaluatorInst = new ExpressionEvaluator();
        double res = expressionEvaluatorInst.evaluate(rootNode,values);
        if(res==1.0) return true;
        return false;
    }

    private Optional<AstMongoRootNode> fetchRule(String ruleName){
        return astMongoRepoInst.findById(ruleName);
    }

    private void fetchAndEvaluate(String ruleName){
        Optional<AstMongoRootNode> astMongoFetchedVal = fetchRule(ruleName);
        if(astMongoFetchedVal.isPresent()){
            List<String> variables = astMongoFetchedVal.get().getVariables();
            ASTNode rootNode = astMongoFetchedVal.get().getRootNode();
        }
    }
}
