package com.cqu.domains;


public class Mapping {
	private Long id;
	private User casUser;
	private String localUser;
	private String backUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getCasUser() {
		return casUser;
	}
	public void setCasUser(User casUser) {
		this.casUser = casUser;
	}
	public String getLocalUser() {
		return localUser;
	}
	public void setLocalUser(String localUser) {
		this.localUser = localUser;
	}
	public String getBackUrl() {
		return backUrl;
	}
	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((backUrl == null) ? 0 : backUrl.hashCode());
		result = prime * result + ((casUser == null) ? 0 : casUser.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mapping other = (Mapping) obj;
		if (backUrl == null) {
			if (other.backUrl != null)
				return false;
		} else if (!backUrl.equals(other.backUrl))
			return false;
		if (casUser == null) {
			if (other.casUser != null)
				return false;
		} else if (!casUser.equals(other.casUser))
			return false;
		return true;
	}
	
}
