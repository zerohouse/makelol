package me.model;

import next.database.annotation.Key;

public class User {

	@Key(AUTO_INCREMENT=true)
	private Integer id;
	private String nickName;
	private Integer age;
	
}
