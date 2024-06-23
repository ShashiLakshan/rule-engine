
INSERT INTO rule (rule_name, condition, action, description)
VALUES ('discount', 'input.userType == ''EMPLOYEE'' && input.isGrocery == false',
        'output.setEmpDiscountRate(30)', 'employee based discount');