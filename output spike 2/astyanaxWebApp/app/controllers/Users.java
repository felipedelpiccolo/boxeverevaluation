package controllers;

import java.util.List;

import com.netflix.astyanax.connectionpool.exceptions.ConnectionException;

import models.UserDao;
import models.UserDto;
import play.mvc.Controller;

public class Users extends Controller{

	public static void list() throws ConnectionException {
		List<UserDto> users = UserDao.getInstance().getAllUser();
		
		render(users);
	}
	
	public static void create(String firstname, String lastname) throws ConnectionException {
		UserDao.getInstance().store(new UserDto(firstname, lastname));
		list();
	}
	
}
