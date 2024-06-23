package com.my.code.rule_engine.knowledgeBase.repository;

import com.my.code.rule_engine.knowledgeBase.entity.RuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleRepository extends JpaRepository<RuleEntity, String> {

    List<RuleEntity> findAll();

}
