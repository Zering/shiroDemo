package com.shiro.c6.entity;

/**
 * 权限基本信息
 * 
 * @author zhanghaojie
 *
 */
public class Permission {

	private Long id;
	private String permission;// 权限标识
	private String decription;
	private Boolean available = Boolean.FALSE;

	public Permission() {
	}

	public Permission(String permission, String decribe, Boolean available) {
		super();
		this.permission = permission;
		this.decription = decribe;
		this.available = available;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Permission other = (Permission) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Permission [id=" + id + ", permission=" + permission + ", decribe=" + decription + ", available=" + available + "]";
	}

}
