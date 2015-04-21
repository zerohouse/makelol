package me.controllers;

import me.model.User;
import next.database.DAO;
import next.mapping.annotation.Controller;
import next.mapping.annotation.Mapping;
import next.mapping.annotation.parameters.Parameter;
import next.mapping.annotation.parameters.SessionAttribute;
import next.mapping.constant.Method;
import next.mapping.http.Http;
import next.mapping.response.Json;
import next.mapping.response.Response;

@Controller
@Mapping("/api/user")
public class UserController {

	@Mapping(method = Method.GET)
	public Response getUser(@Parameter("a") String a, @SessionAttribute("user") User user, Http http, DAO dao) {
		System.out.println(dao.getRecordByClass(User.class, 1));
		System.out.println(a);
		System.out.println(user);
		return new Json("abc");
	}

	@Mapping(value = "/user", method = Method.GET)
	public String getsUser(Http http, DAO dao) {
		System.out.println(dao.getRecord("SELECT * FROM User", 1));
		return "abc";
	}

}
