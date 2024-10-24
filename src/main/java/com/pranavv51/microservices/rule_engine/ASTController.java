package com.pranavv51.microservices.rule_engine;


import com.pranavv51.microservices.rule_engine.model.AstMongoRootNode;
import com.pranavv51.microservices.rule_engine.model.InputFromUser;
import com.pranavv51.microservices.rule_engine.model.astnodemodel.ASTNode;
import com.pranavv51.microservices.rule_engine.service.ASTService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class ASTController {

    private final ASTService astServiceInst;

    public ASTController(ASTService astServiceInst) {
        this.astServiceInst = astServiceInst;
    }

    // http://localhost:8150/create-and-save-rule
    @PostMapping(value = "/create-and-save-rule")
    public boolean createAndSaveRule(@RequestBody InputFromUser inputInstance){
        return astServiceInst.storeRuleInDB(inputInstance.getRuleName(),inputInstance.getRule().toString());
    }


    // http://localhost:8150/fetch-variables-from-rule/rule-name/{ruleName}
    @GetMapping(value = "/fetch-variables-from-rule/rule-name/{ruleName}")
    public Optional<AstMongoRootNode> fetchVariablesInTheRule(@PathVariable String ruleName){
        return astServiceInst.fetchRule(ruleName);
    }


    //http://localhost:8150/enter-values-to-variables-and-rootnode
    @GetMapping(value = "/enter-values-to-variables-and-rootnode")
    public boolean evaluateRule(@RequestBody HashMap<String,Double> variableTovalues, @RequestParam("rootNode") ASTNode rootNode){
        return astServiceInst.evaluateExpression(variableTovalues,rootNode);
    }


}
