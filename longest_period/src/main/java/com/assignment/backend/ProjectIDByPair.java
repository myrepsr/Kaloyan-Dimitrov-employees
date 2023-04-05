package com.assignment.backend;

import lombok.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class ProjectIDByPair {
    private int projectID;
    private Set<Integer> pair = new TreeSet<>();

    private LocalDate empOneDateFrom;
    private LocalDate empOneDateTo;
    private LocalDate empTwoDateFrom;
    private LocalDate empTwoDateTo;
    private long projectWorkTime;
    public void addEmployee(int empID){
        this.pair.add(empID);
    }
}
