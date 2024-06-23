package com.my.code.rule_engine.web;

import com.my.code.rule_engine.ruleEngine.RuleEngine;
import com.my.code.rule_engine.ruleEngine.discountRuleEngine.DiscountInferenceEngine;
import com.my.code.rule_engine.ruleEngine.discountRuleEngine.DiscountInfo;
import com.my.code.rule_engine.ruleEngine.discountRuleEngine.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscountRestController {

    private final RuleEngine ruleEngine;
    private final DiscountInferenceEngine discountInferenceEngine;

    public DiscountRestController(RuleEngine ruleEngine, DiscountInferenceEngine discountInferenceEngine) {
        this.ruleEngine = ruleEngine;
        this.discountInferenceEngine = discountInferenceEngine;
    }

    @PostMapping(value = "/discounts")
    public ResponseEntity<?> applyDiscounts(@RequestBody UserInfo userInfo) {
        DiscountInfo result = (DiscountInfo) ruleEngine.run(discountInferenceEngine, userInfo);
        return ResponseEntity.ok(result);
    }
}
