package com.example.springbootswagger2.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootswagger2.model.Student;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Swagger2RestController",
        description = "学生服务")
@RestController
public class Swagger2RestController {

    /**
     * 组合所有的学生信息
     */
	List<Student> students = new ArrayList<Student>();
	{
		students.add(new Student("Sajal", "IV", "India"));
		students.add(new Student("Lokesh", "V", "India"));
		students.add(new Student("Kajal", "III", "USA"));
		students.add(new Student("Sukesh", "VI", "USA"));
	}

    /**
     *
     * @return
     */
    @ApiOperation(value = "以列表形式返回学生信息",
			responseContainer="List",
			response = Student.class,
            tags = "getStudents")
    @ApiResponses(value = {
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/getStudents", method = RequestMethod.GET)
	public List<Student> getStudents() {
		return students;
	}


    /**
     *
     * @param name
     * @return
     */
	@ApiOperation(value = "获取指定名字的学生",
            response = Student.class,
            tags = "getStudentByName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/getStudent/{studentName}", method = RequestMethod.GET)
	public Student getStudentByName(@RequestParam @ApiParam(value = "studentName") String name) {
		return students.stream().filter(x -> x.getName().equalsIgnoreCase(name)).collect(Collectors.toList()).get(0);
	}


    /**
     *
     * @param country
     * @return
     */
	@ApiOperation(value = "获取指定国家的学生",
            responseContainer="List",
            response = Student.class,
            tags = "getStudentByCountry")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/getStudentByCountry/{country}", method = RequestMethod.GET)
	public List<Student> getStudentByCountry(@PathVariable(value = "country") String country) {
		System.out.println("Searching Student in country : " + country);
		List<Student> studentsByCountry = students.stream().filter(x -> x.getCountry().equalsIgnoreCase(country))
				.collect(Collectors.toList());
		System.out.println(studentsByCountry);
		return studentsByCountry;
	}


    /**
     *
     * @param cls
     * @return
     */
	@ApiOperation(value = "获取指定班级的学生",
            responseContainer="List",
            response = Student.class,
            tags="getStudentByClass")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Suceess|OK"),
			@ApiResponse(code = 401, message = "not authorized!"),
			@ApiResponse(code = 403, message = "forbidden!!!"),
			@ApiResponse(code = 404, message = "not found!!!") })
	@RequestMapping(value = "/getStudentByClass/{cls}", method = RequestMethod.GET)
	public List<Student> getStudentByClass(@PathVariable(value = "cls") String cls) {
		return students.stream().filter(x -> x.getCls().equalsIgnoreCase(cls)).collect(Collectors.toList());
	}


    @ApiOperation(value = "添加学生",
            tags="addStudent")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @RequestMapping(value = "/addStudent", method = RequestMethod.POST, consumes = {"application/json"}, produces = {"application/json"})
    public Boolean addStudent(@ApiParam(value = "student") @RequestBody Student student) {
        return students.add(student);
    }

    @ApiOperation(value = "添加学生V2",
            tags="addStudentV2")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名", paramType = "query"),
            @ApiImplicitParam(name = "cls", value = "班级", paramType = "query"),
            @ApiImplicitParam(name = "country", value = "国家", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @RequestMapping(value = "/addStudentV2", method = RequestMethod.GET)
    public Boolean addStudentV2(@RequestParam String name,
                                @RequestParam String cls,
                                @RequestParam String country) {
	    Student student = new Student(name, cls, country);
        return students.add(student);
    }

    @ApiOperation(value = "查找指定班级指定名字的学生", tags = "getStudentByNameAndCls")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @RequestMapping(value = "getStudentByNameAndCls", method = RequestMethod.GET)
    public Student getStudentByNameAndCls(@RequestParam String name, @RequestParam String cls) {
	    return students.stream()
                .filter(x -> x.getCls().equals(cls) && x.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList()).get(0);
    }

    @ApiOperation(value = "删除指定名字的学生", tags = "delStudentByName")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @RequestMapping(value = "delStudentByName", method = RequestMethod.GET)
    public Student delStudentByName(@RequestParam String name) {
	    Student tempStudent = null;
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                tempStudent = student;
                break;
            }
        }

        students.remove(tempStudent);

        return tempStudent;
    }
}
