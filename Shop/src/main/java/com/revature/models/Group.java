package com.revature.models;

public class Group {
	private Integer group_id;
	private String group_name;
	
	//groups: group_id, group_name


	public Group(Integer group_id, String group_name) {
		super();
		this.group_id = group_id;
		this.group_name = group_name;
	}

	public Integer getGroup_id() {
		return group_id;
	}


	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public String getGroup_name() {
		return group_name;
	}

	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}

	@Override
	public String toString() {
		return "Group [group_name=" + group_name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group_id == null) ? 0 : group_id.hashCode());
		result = prime * result + ((group_name == null) ? 0 : group_name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Group other = (Group) obj;
		if (group_id == null) {
			if (other.group_id != null) {
				return false;
			}
		} else if (!group_id.equals(other.group_id)) {
			return false;
		}
		if (group_name == null) {
			if (other.group_name != null) {
				return false;
			}
		} else if (!group_name.equals(other.group_name)) {
			return false;
		}
		return true;
	}

}
