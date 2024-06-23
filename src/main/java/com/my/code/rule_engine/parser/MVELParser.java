package com.my.code.rule_engine.parser;

import org.mvel2.MVEL;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MVELParser {

    public boolean parseMvelExpression(String expression, Map<String, Object> inputObjects) {
        try {
            return MVEL.evalToBoolean(expression,inputObjects);
        }catch (Exception e){
            System.out.println("Can not parse Mvel Expression : " + expression + " Error: " + e.getMessage());
        }
        return false;
    }
}

