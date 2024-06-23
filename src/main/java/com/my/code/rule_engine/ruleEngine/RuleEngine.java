package com.my.code.rule_engine.ruleEngine;

import com.my.code.rule_engine.knowledgeBase.dto.Rule;
import com.my.code.rule_engine.knowledgeBase.service.KnowledgeBaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleEngine {

    private final KnowledgeBaseService knowledgeBaseService;

    public RuleEngine(KnowledgeBaseService knowledgeBaseService) {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    public Object run(InferenceEngine inferenceEngine, Object inputData) {
        List<Rule> rules = knowledgeBaseService.getAllRules();
        Object result = inferenceEngine.run(rules, inputData);
        return result;
    }
}
