package com.example.springbootswagger2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "学生实体类",value = "学生对象")
public class Student {
    /**
     * 姓名
     */
	@ApiModelProperty(notes = "Name of the Student",name="name",required=true,value="姓名")
	private String name;

    /**
     * 班级
     */
	@ApiModelProperty(notes = "Class of the Student",name="cls",required=true,value="班级")
	private String cls;

    /**
     * 国家
     */
	@ApiModelProperty(notes = "Country of the Student",name="country",required=true,value="国家")
	private String country;

	public Student(){

	}

	public Student(String name, String cls, String country) {
		super();
		this.name = name;
		this.cls = cls;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public String getCls() {
		return cls;
	}

	public String getCountry() {
		return country;
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", cls=" + cls + ", country=" + country + "]";
	}

}
