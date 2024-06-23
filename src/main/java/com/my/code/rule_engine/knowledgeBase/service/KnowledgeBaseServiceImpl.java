package com.my.code.rule_engine.knowledgeBase.service;

import com.my.code.rule_engine.knowledgeBase.dto.Rule;
import com.my.code.rule_engine.knowledgeBase.entity.RuleEntity;
import com.my.code.rule_engine.knowledgeBase.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgeBaseServiceImpl implements KnowledgeBaseService {

    private final RuleRepository ruleRepository;

    public KnowledgeBaseServiceImpl(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    @Override
    public List<Rule> getAllRules() {
        return ruleRepository.findAll()
                .stream()
                .map(this::mapToRule).collect(Collectors.toList());
    }

    private Rule mapToRule(RuleEntity ruleEntity) {
        Rule rule = new Rule();
        rule.setRuleName(ruleEntity.getRuleName());
        rule.setCondition(ruleEntity.getCondition());
        rule.setAction(rule.getAction());
        rule.setDescription(ruleEntity.getDescription());
        return rule;
    }
}
