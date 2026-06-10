@transactions
Feature: Transaction registration

As an application user
I want to register incomes and expenses indicating the amount, category and transaction date
So that I can control my cash flow

Background:
Given I am authenticated

Scenario: Successful income registration
  Given I have a category "Salario" created
  When I register an income of 500000 COP in category "Salario" with date "2026-03-01"
  Then the response status code is 200
  And the transaction details are:
    | type     | INGRESO   |
    | amount   | 500000    |
    | category | Salario   |
    | date     | 2026-03-01 |

Scenario: Successful expense registration
  Given I have a category "Transporte" created
  When I register an expense of 300000 COP in category "Transporte" with date "2026-03-01"
  Then the response status code is 200
  And the transaction details are:
    | type     | GASTO     |
    | amount   | 300000    |
    | category | Transporte|
    | date     | 2026-03-01 |

Scenario: Expense registration greater than previous income
Given I have a category "Compras" created
When I register an expense of 1500000 COP in category "Compras" with date "2026-03-03"
Then the response status code is 200
And the transaction details are:
    | type     | GASTO     |
    | amount   | 1500000   |
    | category | Compras   |
    | date     | 2026-03-03 |

Scenario Outline: Transaction registration with invalid amount
Given I have a category "<category>" created
When I register a "<type>" transaction with amount <amount> in category "<category>" with date "2026-03-03"
Then the response status code is 400
And the error message for field "monto" is "Debes ingresar un monto válido"

Examples:
    | type    | amount | category   |
    | INGRESO | 0      | Salario    |
    | INGRESO | -100   | Salario    |
    | GASTO   | 0      | Transporte |
    | GASTO   | -50    | Transporte |

Scenario: Transaction registration without date
Given I have a category "Salario" created
When I register an income of 500000 COP in category "Salario" without a date
Then the response status code is 400
And the error message for field "fecha" is "La fecha de la transacción es obligatoria"