package me.controllers;

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
	public Response getUser(Http http) {
		return new Json("abc");
	}

}
