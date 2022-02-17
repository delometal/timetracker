package com.perigea.tracker.timesheet.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Condition {

    public Operator operator;
    public Object value;
    public Object valueTo;
    public Class<?> valueType;
    public String field;
    
}