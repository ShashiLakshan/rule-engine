package com.my.code.rule_engine.ruleEngine;

import com.my.code.rule_engine.knowledgeBase.dto.Rule;
import com.my.code.rule_engine.parser.RuleParser;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class InferenceEngine<INPUT_DATA, OUTPUT_DATA> {

    private final RuleParser<INPUT_DATA, OUTPUT_DATA> ruleParser;

    protected InferenceEngine(RuleParser<INPUT_DATA, OUTPUT_DATA> ruleParser) {
        this.ruleParser = ruleParser;
    }

    public OUTPUT_DATA run (List<Rule> listOfRules, INPUT_DATA inputData){

        if (ObjectUtils.isEmpty(listOfRules)) {
            return null;
        }
        //MATCH
        List<Rule> conflictSet = match(listOfRules, inputData);
        //RESOLVE
        List<Rule> resolvedRule = resolve(conflictSet);
        if (ObjectUtils.isEmpty(resolvedRule)) {
            return null;
        }
        //EXECUTE
        OUTPUT_DATA outputResult = initializeOutputResult();
        for (Rule rule : resolvedRule) {
            executeRule(rule, inputData, outputResult);
        }
        calculateNetBillAmt(inputData, outputResult);
        return outputResult;
    }

    protected List<Rule> match(List<Rule> listOfRules, INPUT_DATA inputData){
        return listOfRules.stream()
                .filter(
                        rule -> {
                            String condition = rule.getCondition();
                            return ruleParser.parseCondition(condition, inputData);
                        }
                )
                .collect(Collectors.toList());
    }

    protected List<Rule> resolve(List<Rule> conflictSet){
        return conflictSet;
    }

    protected OUTPUT_DATA executeRule(Rule rule, INPUT_DATA inputData, OUTPUT_DATA outputData){
        return ruleParser.parseAction(rule.getAction(), inputData, outputData);
    }

    protected abstract OUTPUT_DATA initializeOutputResult();

    protected abstract void calculateNetBillAmt(INPUT_DATA inputData, OUTPUT_DATA outputResult);

}
