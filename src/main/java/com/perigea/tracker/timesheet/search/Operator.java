package com.perigea.tracker.timesheet.search;

public enum Operator {
    // equal
    eq,
    // greaterThan
    gt,
    // greaterThanEqual
    gte,
    // lowerThan
    lt,
    // lowerThanEqual
    lte,
    // not equal
    ne,
    // is null
    isNull,
    // in
    in,
    // in
    notIn,
    // like
    like,
    //contains is alias for like
    contains,
    //between
    between,
    //startsWith
    startsWith,
    //endsWith
    endsWith
    
}