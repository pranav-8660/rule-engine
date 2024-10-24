package com.pranavv51.microservices.rule_engine.repository;

import com.pranavv51.microservices.rule_engine.model.AstMongoRootNode;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ASTMongoRepo extends MongoRepository<AstMongoRootNode,String> {
}
