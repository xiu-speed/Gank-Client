package com.speed.gank.models;

public class GankModel {

	public String createdAt;
	public String desc;
	public String objectId;
	public String type;
	public String updateAt;
	public String url;
	public boolean used;
	public String who;
	
	@Override
	public String toString() {
		return "GankModel [createdAt=" + createdAt + ", desc=" + desc
				+ ", objectId=" + objectId + ", type=" + type + ", updateAt="
				+ updateAt + ", url=" + url + ", used=" + used + ", who=" + who
				+ "]";
	}

}
