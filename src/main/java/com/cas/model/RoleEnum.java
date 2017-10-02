package com.cas.model;

/**
 * Created by wangliucheng on 2017/9/20 0020.
 */
public enum RoleEnum {

    STUDENT(1,"student"),

    TEACHER(2,"teacher"),

    ADMIN(3,"admin");

    private int value;
    private String roleName;

    RoleEnum(int value, String roleName) {
        this.value = value;
        this.roleName = roleName;
    }
    public int value(){
        return this.value;
    }
    public String roleName(){
        return this.roleName;
    }
    public static RoleEnum valueOf(int value){
        return RoleEnum.valueOf(value);
    }
}
