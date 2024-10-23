package com.pranavv51.microservices.rule_engine;


import com.pranavv51.microservices.rule_engine.model.AstMongoRootNode;
import com.pranavv51.microservices.rule_engine.model.InputFromUser;
import com.pranavv51.microservices.rule_engine.service.ASTService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    // http://localhost:8150/fetch-a-rule/ruleName/{rule}
    @GetMapping(value = "/fetch-a-rule/ruleName/{rule}")
    public ResponseEntity<Optional<AstMongoRootNode>> fetchARule(@PathVariable("rule") String rule){
        return ResponseEntity.ok(astServiceInst.fetchRule(rule));
    }


}
