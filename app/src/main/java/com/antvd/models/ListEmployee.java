package com.antvd.models;

import java.util.ArrayList;

public class ListEmployee {
    private ArrayList<Employee> employees;

    public ListEmployee() {
        employees = new ArrayList<>();
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }
    public void gen_dataset()
    {
        Employee e1=new Employee();
        e1.setId(1);
        e1.setName("admin");
        e1.setEmail("admin@gmail.com");
        e1.setPhone("0123456789");
        e1.setUsername("admin");
        e1.setPassword("admin123");
        employees.add(e1);

        Employee e2=new Employee();
        e2.setId(2);
        e2.setName("ken");
        e2.setEmail("ken@gmail.com");
        e2.setPhone("0987654321");
        e2.setUsername("ken");
        e2.setPassword("456");
        employees.add(e2);

        Employee e3=new Employee();
        e3.setId(3);
        e3.setName("trump");
        e3.setEmail("trump@gmail.com");
        e3.setPhone("0123123123");
        e3.setUsername("trump");
        e3.setPassword("789");
        employees.add(e3);

    }
}

