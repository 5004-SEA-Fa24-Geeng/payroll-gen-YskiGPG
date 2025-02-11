# Payroll Generator Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram

Place your class diagram below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

```mermaid
---
Payroll Generator Design
---
classDiagram
    IEmployee <|-- HourlyEmployee
    IEmployee <|-- SalaryEmployee
    IPayStub <|-- PayStub
    PayrollGenerator o-- IEmployee
    PayrollGenerator o-- ITimeCard
    PayrollGenerator o-- IPayStub
    FileUtil <.. PayrollGenerator : uses
    Builder <.. PayrollGenerator : uses

    class IEmployee{
        <<interface>>
        +String getName()
        +String getID()
        +double getPayRate()
        +String getEmployeeType()
        +double getYTDEarnings()
        +double getYTDTaxesPaid()
        +double getPretaxDeductions()
        +IPayStub runPayroll(double hoursWorked)
        +String toCSV()
    }

    class IPayStub{
        <<interface>>
        +double getPay()
        +double getTaxesPaid()
        +String toCSV()
    }
    
    class ITimeCard{
    	<<interface>>
    	+String getEmployeeID()
    	+double gethoursWorked()
    }

    class HourlyEmployee{
        +HourlyEmployee(String name, String id, double payRate, double ytdEarnings, double ytdTaxesPaid, double pretaxDeductions)
        +runPayroll(double hoursWorked)
    }

    class SalaryEmployee{
        +SalaryEmployee(String name, String id, double payRate, double ytdEarnings, double ytdTaxesPaid, double pretaxDeductions)
        +runPayroll(double hoursWorked)
    }

    class PayStub{
        +PayStub(String employeeName, double netPay, double taxesPaid, double ytdEarnings, double ytdTaxesPaid)
        +toCSV()
    }

    class PayrollGenerator{
        +main(String[] args)
    }

    class FileUtil{
        +List<String> readFile(String path)
        +void writeFile(String path, List<String> data)
    }

    class Builder{
        +IEmployee buildEmployee(String csvLine)
        +ITimeCard buildTimeCard(String csvLine)
    }

```

## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

1. Test that the `Employee` class properly returns `name` from `getName()`
2. Test that the `Employee` class properly returns `id` from `getId()`
3. Test that `runPayroll()` for `HourlyEmployee` correctly calculates pay without overtime.
4. Test that `runPayroll()` for `HourlyEmployee` correctly calculates pay with overtime.
5. Test that `runPayroll()` for `SalaryEmployee` correctly calculates bi-monthly pay.
6. Test that `PayStub` correctly formats output to CSV.
7. Test that `Builder` properly builds `IEmployee` objects from CSV lines.
8. Test that `PayrollGenerator` correctly matches time cards with employees and generates pay stubs.

## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.

```mermaid
---
Final Payroll Generator Design
---
classDiagram
    IEmployee <|-- HourlyEmployee
    IEmployee <|-- SalaryEmployee
    IPayStub <|-- PayStub
    PayrollGenerator o-- IEmployee
    PayrollGenerator o-- ITimeCard
    PayrollGenerator o-- IPayStub
    FileUtil <.. PayrollGenerator : uses
    Builder <.. PayrollGenerator : uses

    class IEmployee{
        <<interface>>
        +String getName()
        +String getID()
        +BigDecimal getPayRate()
        +String getEmployeeType()
        +BigDecimal getYTDEarnings()
        +BigDecimal getYTDTaxesPaid()
        +BigDecimal getPretaxDeductions()
        +IPayStub runPayroll(double hoursWorked)
        +String toCSV()
    }

    class IPayStub{
        <<interface>>
        +BigDecimal getPay()
        +BigDecimal getTaxesPaid()
        +String toCSV()
    }
    
    class ITimeCard{
    	<<interface>>
    	+String getEmployeeID()
    	+BigDecimal getHoursWorked()
    }

    class HourlyEmployee{
        +HourlyEmployee(String name, String id, BigDecimal payRate, BigDecimal ytdEarnings, BigDecimal ytdTaxesPaid, BigDecimal pretaxDeductions)
        +runPayroll(BigDecimal hoursWorked)
    }

    class SalaryEmployee{
        +SalaryEmployee(String name, String id, BigDecimal payRate, BigDecimal ytdEarnings, BigDecimal ytdTaxesPaid, BigDecimal pretaxDeductions)
        +runPayroll(BigDecimal hoursWorked)
    }

    class PayStub{
        +PayStub(String employeeName, BigDecimal netPay, BigDecimal taxesPaid, BigDecimal ytdEarnings, BigDecimal ytdTaxesPaid)
        +toCSV()
    }

    class PayrollGenerator{
        +main(String[] args)
    }

    class FileUtil{
        +List<String> readFile(String path)
        +void writeFile(String path, List<String> data)
    }

    class Builder{
        +IEmployee buildEmployee(String csvLine)
        +ITimeCard buildTimeCard(String csvLine)
    }

```





## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning new information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 

At first, I thought using `double` for financial calculations would be fine, but I quickly realized that floating-point errors were causing my payroll calculations to be slightly off. Switching to `BigDecimal` fixed these issues and made the output accurate. Another big change was how I handled time cards. Initially, I planned to match employees and time cards directly, but handling missing or negative time cards required more careful logic to prevent crashes and incorrect data.

The most challenging part was getting the payroll calculations to match the expected test results exactly. Even small rounding errors led to failing tests, which was frustrating at first. However, it taught me the importance of precision in financial software and how to debug issues by isolating each step of the calculation.
