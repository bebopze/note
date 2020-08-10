package com.bebopze.jdk.patterndesign;

import java.util.ArrayList;
import java.util.List;

/**
 * 10. 组合模式
 *
 * @author bebopze
 * @date 2020/8/10
 */
public class _10_Composite {


    // ---------------------------------------------------------------


    public static void main(String[] args) {

    }


    // ----------------------------------实现-----------------------------
}


abstract class HumanResource {

    protected long id;
    protected double salary;

    public HumanResource(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public abstract double calculateSalary();
}


class Employee extends HumanResource {

    public Employee(long id, double salary) {
        super(id);
        this.salary = salary;
    }

    @Override
    public double calculateSalary() {
        return salary;
    }
}


class Department extends HumanResource {
    private List<HumanResource> subNodes = new ArrayList<>();

    public Department(long id) {
        super(id);
    }

    @Override
    public double calculateSalary() {
        double totalSalary = 0;
        for (HumanResource hr : subNodes) {
            totalSalary += hr.calculateSalary();
        }
        this.salary = totalSalary;
        return totalSalary;
    }

    public void addSubNode(HumanResource hr) {
        subNodes.add(hr);
    }
}


/**
 * 构建组织架构的代码
 */
class Demo {

    private static final long ORGANIZATION_ROOT_ID = 1001;

//    // 依赖注入
//    private DepartmentRepo departmentRepo;
//    private EmployeeRepo employeeRepo;


    public void buildOrganization() {
        Department rootDepartment = new Department(ORGANIZATION_ROOT_ID);
        buildOrganization(rootDepartment);
    }

    private void buildOrganization(Department department) {

//        List<Long> subDepartmentIds = departmentRepo.getSubDepartmentIds(department.getId());
//        for (Long subDepartmentId : subDepartmentIds) {
//            Department subDepartment = new Department(subDepartmentId);
//            department.addSubNode(subDepartment);
//            buildOrganization(subDepartment);
//        }
//
//        List<Long> employeeIds = employeeRepo.getDepartmentEmployeeIds(department.getId());
//        for (Long employeeId : employeeIds) {
//            double salary = employeeRepo.getEmployeeSalary(employeeId);
//            department.addSubNode(new Employee(employeeId, salary));
//        }
    }
}