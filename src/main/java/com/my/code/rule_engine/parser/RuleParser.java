package com.my.code.rule_engine.parser;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class RuleParser<INPUT_DATA, OUTPUT_RESULT> {

    private final MVELParser mvelParser;

    private final String INPUT_KEYWORD = "input";
    private final String OUTPUT_KEYWORD = "output";

    public RuleParser(MVELParser mvelParser) {
        this.mvelParser = mvelParser;
    }

    public boolean parseCondition(String expression, INPUT_DATA inputData) {
        Map<String, Object> input = new HashMap<>();
        input.put(INPUT_KEYWORD, inputData);
        input.put("now", LocalDate.now());
        return mvelParser.parseMvelExpression(expression, input);
    }

    public OUTPUT_RESULT parseAction(String expression, INPUT_DATA inputData, OUTPUT_RESULT outputResult) {
        Map<String, Object> input = new HashMap<>();
        input.put(INPUT_KEYWORD, inputData);
        input.put(OUTPUT_KEYWORD, outputResult);
        mvelParser.parseMvelExpression(expression, input);
        return outputResult;
    }

}
