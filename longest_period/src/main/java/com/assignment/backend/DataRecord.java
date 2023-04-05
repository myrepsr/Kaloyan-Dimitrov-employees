package com.assignment.backend;

import java.time.LocalDate;

public record DataRecord(int empID, int projectID, LocalDate dateFrom, LocalDate dateTo) { }
