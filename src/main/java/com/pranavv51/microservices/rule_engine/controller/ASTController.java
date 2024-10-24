package com.pranavv51.microservices.rule_engine.controller;


import com.pranavv51.microservices.rule_engine.model.AstMongoRootNode;
import com.pranavv51.microservices.rule_engine.model.EvaluatorInput;
import com.pranavv51.microservices.rule_engine.model.InputFromUser;
import com.pranavv51.microservices.rule_engine.model.astnodemodel.ASTNode;
import com.pranavv51.microservices.rule_engine.service.ASTService;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ASTController {

    private final ASTService astServiceInst;

    public ASTController(ASTService astServiceInst) {
        this.astServiceInst = astServiceInst;
    }
    
    
    @GetMapping(value = "/")
    public List<String> giveAllApis(){
    	String [] apis = {"post..../create-and-save-rule....(@RequestBody InputFromUser inputInstance->(inputInstance.getRuleName(),inputInstance.getRule().toString()))",
    			"get..../fetch-variables-from-rule/rule-name/{ruleName}",
    			"post....enter-values-to-variables-and-rootnode...(@RequestBody EvaluatorInput evaluatorInput->(evaluatorInput.getVariableToValue(),evaluatorInput.getRuleNameString()))"};
    	
    			return Arrays.asList(apis);
    }

    // http://localhost:8150/create-and-save-rule
    @PostMapping(value = "/create-and-save-rule")
    @CrossOrigin
    public boolean createAndSaveRule(@RequestBody InputFromUser inputInstance){
        return astServiceInst.storeRuleInDB(inputInstance.getRuleName(),inputInstance.getRule().toString());
    }


    // http://localhost:8150/fetch-variables-from-rule/rule-name/{ruleName}
    @GetMapping(value = "/fetch-variables-from-rule/rule-name/{ruleName}")
    public Optional<AstMongoRootNode> fetchVariablesInTheRule(@PathVariable String ruleName){
        return astServiceInst.fetchRule(ruleName);
    }


    //http://localhost:8150/enter-values-to-variables-and-rootnode
    @PostMapping(value = "/enter-values-to-variables-and-rootnode")
    @CrossOrigin
    public boolean evaluateRule(@RequestBody EvaluatorInput evaluatorInput){
        return astServiceInst.evaluateExpression(evaluatorInput.getVariableToValue(),evaluatorInput.getRuleNameString());
    }


}
