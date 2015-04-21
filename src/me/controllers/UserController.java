package me.controllers;

import next.database.DAO;
import next.mapping.annotation.Controller;
import next.mapping.annotation.Mapping;
import next.mapping.constant.Method;
import next.mapping.http.Http;
import next.mapping.response.Json;
import next.mapping.response.Response;

@Controller
@Mapping("/api/user")
public class UserController {

	@Mapping(method = Method.GET)
	public Response getUser(Http http, DAO dao) {
		System.out.println(dao.getRecord("SELECT * FROM User", 1));
		return new Json("abc");
	}

}
