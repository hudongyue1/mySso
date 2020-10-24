package database;

import java.util.HashSet;
import java.util.Set;

import com.cqu.model.User;

public class DB {
	private static Set<User> users=new HashSet<User>();
	static {
		addUser("app2u1","王五","F",13);
		addUser("app2u2","赵六","M",14);
	}
	
	public static void addUser(String id,String name,String sex,int age)
	{
		User u=new User();
		u.setId(id);
		u.setName(name);
		u.setSex(sex);
		u.setAge(age);

		
		u.setName(name);
		
		users.add(u);
	}

	public static User getUser(String userId) {
		for(User u:users)
		{
			if(u.getId().equals(userId))
				return u;
		}
		return null;
	}
}
