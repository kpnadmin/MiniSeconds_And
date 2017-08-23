package com.android.mylibrary.model;

import java.lang.reflect.Type;
import java.util.List;


import com.google.gson.InstanceCreator;

public class Department {

    private String deptName;

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "Department [deptName=" + deptName + "]";
    }

    public Department(String deptName) {
        this.deptName = deptName;
    }
}
