package com.hodvidar.formation.java8.streams;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * https://medium.com/@bhaskarsharan/practice-java-streams-questions-8a76cbfee1be
 * <p>
 * From the employee list got from EmployeeFactory:
 * <ol>
 *     <li>List all distinct project in non-ascending order.</li>
 *     <li>Print full name of any employee whose firstName starts with ‘A’.</li>
 *     <li>List of all employee who joined in year 2023
 *     (year to be extracted from employee id i.e., 1st 4 characters)</li>
 *     <li>Sort employees based on firstName, for same firstName sort by salary.</li>
 *     <li>Print names of all employee with 3rd highest salary. (generalise it for nth highest salary).</li>
 *     <li>Print min salary.</li>
 *     <li>Print list of all employee with min salary.</li>
 *     <li>List of people working on more than 2 projects.</li>
 *     <li>Count of total laptops assigned to the employees.</li>
 *     <li>Count of all projects with Robert Downey Jr as PM.</li>
 *     <li>List of all projects with Robert Downey Jr as PM.</li>
 *     <li>List of all people working with Robert Downey Jr.</li>
 *     <li>Create a map based on this data, they key should be the year of joining, and
 *     value should be list of all the employees who joined the particular year.</li>
 *     <li>Create a map based on this data, the key should be year of joining and value should be the count of people
 *     joined in that particular year.</li>
 * </ol>
 */
class StreamTests {

    private static final List<Employee> employees = new EmployeeFactory().getAllEmployee();

    // 1. List all distinct project in non-ascending order.
    @Test
    void listDistinctProjectsInNonAscendingOrder() {
        final List<String> distinctProjectsInNonAscendingOrder
                = employees.stream()
                .flatMap(employee -> employee.getProjects().stream())
                .map(Project::getName)
                .distinct()
                .sorted((a, b) -> String.CASE_INSENSITIVE_ORDER.compare(b, a))
                .collect(Collectors.toList());

        assertThat(distinctProjectsInNonAscendingOrder).hasSize(11);
        assertThat(distinctProjectsInNonAscendingOrder.get(0)).isEqualTo("Two Phase Deployment");
        assertThat(distinctProjectsInNonAscendingOrder.get(10)).isEqualTo("Beta Enhancement");
    }

    // 2. Print full name of any employee whose firstName starts with ‘A’.
    @Test
    void listFullNameOfAnyEmployeeWhoseFirstNameStartsWithA() {
        final List<String> fullNameOfAnyEmployeeWhoseFirstNameStartsWithA
                = employees.stream()
                .filter(employee -> employee.getFirstName().startsWith("A"))
                .map(employee -> employee.getFirstName() + " " + employee.getLastName())
                .toList(); // (=Collectors.toList() but sorter, since java16)

        assertThat(fullNameOfAnyEmployeeWhoseFirstNameStartsWithA).hasSize(1);
        assertThat(fullNameOfAnyEmployeeWhoseFirstNameStartsWithA.get(0)).isEqualTo("Akshay Kumar");
    }

    // 3. List of all employee who joined in year 2023
    //    (year to be extracted from employee id i.e., 1st 4 characters)
    @Test
    void listOfEmployeeJoinedIn2023() {
        final List<Employee> employeeJoinedIn2023
                = employees.stream()
                .filter(employee -> employee.getId().startsWith("2023"))
                .toList();

        assertThat(employeeJoinedIn2023).hasSize(2);
        assertThat(employeeJoinedIn2023.get(0).getId()).isEqualTo("2023Emp0934");
        assertThat(employeeJoinedIn2023.get(1).getId()).isEqualTo("2023Emp1033");
    }

    // 4. Sort employees based on firstName, for same firstName sort by salary.
    @Test
    void listOfEmployeeSortedByFirstNameAndSalary() {
        final List<Employee> employeeSortedByFirstNameAndSalary
                = employees.stream()
                .sorted((a, b) ->
                        (a.getFirstName().compareTo(b.getFirstName()) == 0)
                                ? a.getSalary() - b.getSalary()
                                : a.getFirstName().compareTo(b.getFirstName()))
                .toList();

        assertThat(employeeSortedByFirstNameAndSalary).hasSize(10);
        assertThat(employeeSortedByFirstNameAndSalary.get(0).getLastName()).isEqualTo("Kumar");
        assertThat(employeeSortedByFirstNameAndSalary.get(1).getLastName()).isEqualTo("Rao");
        assertThat(employeeSortedByFirstNameAndSalary.get(2).getLastName()).isEqualTo("Dutt");
        assertThat(employeeSortedByFirstNameAndSalary.get(3).getLastName()).isEqualTo("Sharan");
        assertThat(employeeSortedByFirstNameAndSalary.get(4).getLastName()).isEqualTo("Martin");
        assertThat(employeeSortedByFirstNameAndSalary.get(5).getLastName()).isEqualTo("Anand");
        assertThat(employeeSortedByFirstNameAndSalary.get(6).getLastName()).isEqualTo("Sharma");
    }


    // 5. Print names of all employee with 3rd highest salary. (generalise it for nth highest salary
    @Test
    void listOfEmployeeWithNthHighestSalary() {
        int nthHighestSalaries = 3;

        Optional<Integer> theNthHighestSalaries = employees.stream()
                .map(Employee::getSalary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(nthHighestSalaries - 1)
                .findFirst();

        final Optional<Integer> finalTheNthHighestSalaries1 = theNthHighestSalaries;
        List<Employee> employeesWithNthHighestSalary
                = employees.stream()
                .filter(employee -> finalTheNthHighestSalaries1.isPresent() && employee.getSalary() == finalTheNthHighestSalaries1.get())
                .toList();

        assertThat(employeesWithNthHighestSalary).hasSize(2);
        assertThat(employeesWithNthHighestSalary.get(0).getLastName()).isEqualTo("Martin");
        assertThat(employeesWithNthHighestSalary.get(1).getLastName()).isEqualTo("Singhania");

        nthHighestSalaries = 2;

        theNthHighestSalaries = employees.stream()
                .map(Employee::getSalary)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(nthHighestSalaries - 1)
                .findFirst();

        final Optional<Integer> finalTheNthHighestSalaries = theNthHighestSalaries;
        employeesWithNthHighestSalary
                = employees.stream()
                .filter(employee -> finalTheNthHighestSalaries.isPresent() && employee.getSalary() == finalTheNthHighestSalaries.get())
                .toList();

        assertThat(employeesWithNthHighestSalary).hasSize(1);
        assertThat(employeesWithNthHighestSalary.get(0).getLastName()).isEqualTo("Dutt");
    }

    // 6. Print min salary
    @Test
    void findMinSalary() {
        final int minSalary = employees.stream()
                .mapToInt(Employee::getSalary)
                .min()
                .orElseThrow();
        assertThat(minSalary).isEqualTo(900000);
    }

    // 7. Print list of all employee with min salary
    @Test
    void findMinSalaryEmployees() {
        final int minSalary = employees.stream()
                .mapToInt(Employee::getSalary)
                .min()
                .orElseThrow();
        final List<Employee> employeeWithMinSalary = employees.stream()
                .filter(employee -> employee.getSalary() == minSalary)
                .toList();
        assertThat(minSalary).isEqualTo(900000);
        assertThat(employeeWithMinSalary).hasSize(2);
        assertThat(employeeWithMinSalary.get(0).getLastName()).isEqualTo("Sharan");
        assertThat(employeeWithMinSalary.get(1).getLastName()).isEqualTo("Rao");
    }


    // 8. List of people working on more than 2 projects.
    @Test
    void listOfEmployeeWorkingOnMoreThan2Projects() {
        final int minimumOfProjectEmployeeIsWorkingOn = 3;
        final List<Employee> employeeWithMinDifferentProjects = employees.stream()
                .filter(employee -> employee.getProjects().size() >= minimumOfProjectEmployeeIsWorkingOn)
                .toList();
        assertThat(employeeWithMinDifferentProjects).hasSize(4);
        assertThat(employeeWithMinDifferentProjects.get(0).getLastName()).isEqualTo("Sharma");
        assertThat(employeeWithMinDifferentProjects.get(1).getLastName()).isEqualTo("Kabir");
        assertThat(employeeWithMinDifferentProjects.get(2).getLastName()).isEqualTo("Singhania");
        assertThat(employeeWithMinDifferentProjects.get(3).getLastName()).isEqualTo("Dutt");
    }

    // 9. Count of total laptops assigned to the employees.
    @Test
    void countOfTotalLaptopsAssigned() {
        final int countOfTotalLaptopsAssigned = employees.stream()
                .mapToInt(Employee::getTotalLaptopsAssigned)
                .sum();
        assertThat(countOfTotalLaptopsAssigned).isEqualTo(17);
    }

    // 10. Count of all projects with Robert Downey Jr as PM
    // 11. List of all projects with Robert Downey Jr as PM.
    @Test
    void numberOfProjectsWithRD() {
        List<Project> projectsWithRobertDowney = employees.stream()
                .flatMap(employee -> employee.getProjects().stream())
                .distinct()
                .filter(project -> project.getProjectManager().equals("Robert Downey Jr"))
                .toList();
        assertThat(projectsWithRobertDowney).hasSize(2);
        assertThat(projectsWithRobertDowney.get(0).getName()).isEqualTo("Delta Model");
        assertThat(projectsWithRobertDowney.get(1).getName()).isEqualTo("Common UI");
    }

    // 12. List of all people working with Robert Downey Jr.
    // 13. Create a map based on this data, they key should be the year of joining, and
    //      value should be list of all the employees who joined the particular year.
    // 14. Create a map based on this data, the key should be year of joining and value should be the count of people
    //      joined in that particular year.
    @Test
    void employeeWorkingWithRD() {
        final String nameOfPM = "Robert Downey Jr";
        final List<Employee> employeesWorkingWithPM = employees.stream()
                .filter(employee -> employee.getProjects().stream().anyMatch(p -> p.getProjectManager().equals(nameOfPM)))
                .toList();
        assertThat(employeesWorkingWithPM).hasSize(6);
        final Map<Integer, List<Employee>> employeesWorkingWithPmPerYear = employeesWorkingWithPM.stream()
                .collect(Collectors.groupingBy(
                        employee -> Integer.parseInt(employee.getId().substring(0, 4)),
                        HashMap::new,
                        Collectors.toList()));
        assertThat(employeesWorkingWithPmPerYear.values()).hasSize(5);
        assertThat(employeesWorkingWithPmPerYear.get(2013)).hasSize(1);
        assertThat(employeesWorkingWithPmPerYear.get(2017)).hasSize(2);
        assertThat(employeesWorkingWithPmPerYear.get(2019)).hasSize(1);
        assertThat(employeesWorkingWithPmPerYear.get(2020)).hasSize(1);
        assertThat(employeesWorkingWithPmPerYear.get(2023)).hasSize(1);
        final Map<Integer, Long> numberOfEmployeePerYearForPM = employeesWorkingWithPM.stream()
                .collect(Collectors.groupingBy(
                        employee -> Integer.parseInt(employee.getId().substring(0, 4)),
                        HashMap::new,
                        (Collectors.counting())));
        assertThat(numberOfEmployeePerYearForPM.values()).hasSize(5);
        assertThat(numberOfEmployeePerYearForPM.get(2013)).isEqualTo(1L);
        assertThat(numberOfEmployeePerYearForPM.get(2017)).isEqualTo(2L);
        assertThat(numberOfEmployeePerYearForPM.get(2019)).isEqualTo(1L);
        assertThat(numberOfEmployeePerYearForPM.get(2020)).isEqualTo(1L);
        assertThat(numberOfEmployeePerYearForPM.get(2023)).isEqualTo(1L);
    }

}
