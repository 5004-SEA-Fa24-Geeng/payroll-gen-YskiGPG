# Report for Payroll Generator

This report helps you demonstrate your understanding of the concepts. You should write this report after you have completed the project. 

## Technical Questions

1. What does CSV stand for? 
   1. Comma-Separated Values. It is a simple file format where each line represents a row and each value is separated by a comma.

2. Why would you declare `List<IEmployee>` instead of `ArrayList<HourlyEmployee>`?
   1. Declaring `List<IEmployee>` allows for polymorphism, meaning the list can hold different types of employees, like `HourlyEmployee` and `SalaryEmployee`. This makes the code more flexible and easier to extend in the future. If we used `ArrayList<HourlyEmployee>`, we could only store hourly employees, limiting the design.

3. When you have one class referencing another object, such as storing that object as one of the attributes of the first class - what type of relationship is that called (between has-a and is-a)?
   1. This is a has-a relationship.

4. Can you provide an example of a has-a relationship in your code (if one exists)?
   1. In the `PayStub` class, we store the employee's name and payroll information as attributes. So, the `PayStub` has-a relationship with the employee's data.



5. Can you provide an example of an is-a relationship in your code (if one exists)?

   1.  `HourlyEmployee` is-a `IEmployee` and `SalaryEmployee` is-a `IEmployee` because both classes implement the `IEmployee` interface.



6. What is the difference between an interface and an abstract class?

   1. An **interface** defines a contract that classes must follow without providing any implementation. All methods in an interface are abstract by default.
   2. An **abstract class** can have both fully implemented methods and abstract methods (methods without implementation). Abstract classes are used when you want to share code among related classes.



7. What is the advantage of using an interface over an abstract class?

   1. Interfaces provide more flexibility because a class can implement multiple interfaces but can only inherit from one abstract class. This allows for better separation of concerns and makes the code more modular.



8. Is the following code valid or not? `List<int> numbers = new ArrayList<int>();`, explain why or why not. If not, explain how you can fix it. 


   1. No, the code is not valid because Java does not support primitive types like `int`. 
      Fixed code:

      ```Java
      List<Integer> numbers = new ArrayList<Integer>();
      ```



9. Which class/method is described as the "driver" for your application? 

   1. The `PayrollGenerator` class, specifically the `main(String[] args)` method.


10. How do you create a temporary folder for JUnit Testing? 
    1. Using the `@TempDir` annotation in JUnit 5.



## Deeper Thinking 

Salary Inequality is a major issue in the United States. Even in STEM fields, women are often paid less for [entry level positions](https://www.gsb.stanford.edu/insights/whats-behind-pay-gap-stem-jobs). However, not paying equal salary can hurt representation in the field, and looking from a business perspective, can hurt the company's bottom line has diversity improves innovation and innovation drives profits. 

Having heard these facts, your employer would like data about their salaries to ensure that they are paying their employees fairly. While this is often done 'after pay' by employee surveys and feedback, they have the idea that maybe the payroll system can help them ensure that they are paying their employees fairly. They have given you free reign to explore this idea.

Think through the issue / making sure to cite any resources you use to help you better understand the topic. Then write a paragraph on what changes you would need to make to the system. For example, would there be any additional data points you would need to store in the employee file? Why? Consider what point in the payroll process you may want to look at the data, as different people could have different pretax benefits and highlight that. 

The answer to this is mostly open. We ask that you cite at least two sources to show your understanding of the issue. The TAs will also give feedback on your answer, though will be liberal in grading as long as you show a good faith effort to understand the issue and making an effort to think about how your design to could help meet your employer's goals of salary equity. 

Answer:

Salary inequality is a big issue, even in STEM fields, where women and minority groups often get paid less than men for doing the same job ([Stanford Graduate School of Business](https://www.gsb.stanford.edu/insights/whats-behind-pay-gap-stem-jobs)). This can hurt both the employees and the company because diverse teams are more creative and innovative, which helps businesses grow ([Harvard Business Review](https://hbr.org/2018/01/research-how-gender-diversity-increases-productivity)).

To help fix this, we could make some changes to the payroll system. First, we would need to add more data to the `employee.csv` file, like gender, ethnicity, education level, job title, and years of experience. This would help compare salaries more fairly between people doing similar jobs.

Next, the payroll system could have a salary check before paying employees. It would look for unfair differences in pay and create a report if something seems wrong. HR could then review the report and make adjustments if needed.

We should also remember that pre-tax deductions (like health insurance) can make take-home pay look different, even if salaries are the same. So, the system should compare gross salaries (before deductions) to make sure the comparisons are fair.

These changes would help the company pay employees more fairly and make sure everyone feels valued.
