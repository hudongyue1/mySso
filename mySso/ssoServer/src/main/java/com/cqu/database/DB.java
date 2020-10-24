package com.cqu.database;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.cqu.domains.Mapping;

import com.cqu.domains.SessionStorage;
import com.cqu.domains.TokenStorage;
import com.cqu.domains.User;

public class DB {
	private static Set<TokenStorage> tokenStorages = new HashSet<>();
	private static Set<User> users = new HashSet<>();
	private static Set<SessionStorage> sessionStorages = new HashSet<>();
	private static Set<Mapping> mappings = new HashSet<>();
	static {
		User u1=addUser("01", "0");
		User u2=addUser("02", "0");
		addMapping(1L,u1,"app1u1","http://localhost:8080/web1");
		addMapping(2L,u1,"app2u1","http://localhost:8080/web2");
		addMapping(1L,u2,"app1u2","http://localhost:8080/web1");
		addMapping(2L,u2,"app2u2","http://localhost:8080/web2");

	}

	public static User addUser(String id, String pwd) {
		User u = new User();
		u.setId(id);
		u.setPwd(pwd);
		users.add(u);
		return u;
	}

	public static TokenStorage findTokenStoragebyToken(String token) {
		for (TokenStorage s : tokenStorages) {
			if (s.getToken().equals(token)) {
				return s;
			}
		}
		return null;
	}

	public static User findUser(String id, String pwd) {
		for (User u : users) {
			if (u.getId().equals(id) && u.getPwd().equals(pwd)) {
				return u;
			}
		}
		return null;
	}

	public static void addTokenStorage(User user, String token) {
		TokenStorage tokenStorage = new TokenStorage();
		tokenStorage.setUser(user);
		tokenStorage.setToken(token);
		tokenStorages.add(tokenStorage);
	}

	public static void addSessionStorage(String backUrl, String token, String sessionId) {
		SessionStorage sessionStorage = new SessionStorage();
		sessionStorage.setBackUrl(backUrl);
		sessionStorage.setToken(token);
		sessionStorage.setSessionId(sessionId);
		sessionStorages.add(sessionStorage);
	}

	public static void addMapping(Long id,User casUser,String localUser, String backUrl)
	{
		Mapping m=new Mapping();
		m.setId(id);
		m.setCasUser(casUser);
		m.setLocalUser(localUser);
		m.setBackUrl(backUrl);
		mappings.add(m);
	}
	
	public static List<String> findBackUrlByUser(User user) {
		List<String> list = new ArrayList<>();
		for (Mapping m : mappings) {
			if(m.getCasUser() == user) {
				list.add(m.getBackUrl().toString());
			}
		}
		return list;
	}

	public static Mapping findMappingByBackUrlAndCasUser(String backUrl, User user) {
		for (Mapping m : mappings) {
			if (m.getBackUrl().equals(backUrl) && m.getCasUser().equals(user)) {
				return m;
			}
		}
		return null;
	}

	public static List<SessionStorage> findSessionStorage(String token) {
		List<SessionStorage> list = new ArrayList<>();
		for (SessionStorage s : sessionStorages) {
			if (s.getToken().equals(token)) {
				list.add(s);
			}
		}
		return list;
	}

	public static void deleteSessionStorage(String token) {
		sessionStorages.removeAll(findSessionStorage(token));

	}

	public static List<TokenStorage> findTokenStorage(String token) {
		List<TokenStorage> list = new ArrayList<>();
		for (TokenStorage s : tokenStorages) {
			if (s.getToken().equals(token)) {
				list.add(s);
			}
		}
		return list;
	}

	public static void deleteServiceTicket(String token) {
		tokenStorages.removeAll(findTokenStorage(token));
	}

}
