package com.project.mechanic.entity;

public class PostTimeline {

	int PostId;
	int PostUserId;
	String PostDescription;
	int PostSeen;
	String PostServerDate;
	int PostSubmit;
	String PostDate;
	int PostseenBefore;
	String PostPhoto;

	int ObjectId;
	String ObjectImagePath2;
	String ObjectName;

	public String getObjectName() {
		return ObjectName;
	}

	public void setObjectName(String objectName) {
		ObjectName = objectName;
	}

	int LikeInObjectId;
	int LikeInObjectUserId;
	int LikeInObjectpaperId;

	int UserId;
	String UserName;
	String UserImagePath;

	public PostTimeline(int PostId, int PostUserId, String PostDescription,
			int PostSeen, String PostServerDate, int PostSubmit,
			String PostDate, int PostseenBefore, String PostPhoto,
			int ObjectId, String ObjectImagePath2, String ObjectName,

			int LikeInObjectId, int LikeInObjectUserId,
			int LikeInObjectpaperId,

			int UserId, String UserName, String UserImagePath) {

		this.PostId = PostId;
		this.PostUserId = PostUserId;
		this.PostDescription = PostDescription;
		this.PostSeen = PostSeen;
		this.PostServerDate = PostServerDate;
		this.PostSubmit = PostSubmit;
		this.PostDate = PostDate;
		this.PostseenBefore = PostseenBefore;
		this.PostPhoto = PostPhoto;

		this.ObjectId = ObjectId;
		this.ObjectImagePath2 = ObjectImagePath2;
		this.ObjectName = ObjectName;

		this.LikeInObjectId = LikeInObjectId;
		this.LikeInObjectUserId = LikeInObjectUserId;
		this.LikeInObjectpaperId = LikeInObjectpaperId;

		this.UserId = UserId;
		this.UserName = UserName;
		this.UserImagePath = UserImagePath;
	}

	public int getPostId() {
		return PostId;
	}

	public void setPostId(int postId) {
		PostId = postId;
	}

	public int getPostUserId() {
		return PostUserId;
	}

	public void setPostUserId(int postUserId) {
		PostUserId = postUserId;
	}

	public String getPostDescription() {
		return PostDescription;
	}

	public void setPostDescription(String postDescription) {
		PostDescription = postDescription;
	}

	public int getPostSeen() {
		return PostSeen;
	}

	public void setPostSeen(int postSeen) {
		PostSeen = postSeen;
	}

	public String getPostServerDate() {
		return PostServerDate;
	}

	public void setPostServerDate(String postServerDate) {
		PostServerDate = postServerDate;
	}

	public int getPostSubmit() {
		return PostSubmit;
	}

	public void setPostSubmit(int postSubmit) {
		PostSubmit = postSubmit;
	}

	public String getPostDate() {
		return PostDate;
	}

	public void setPostDate(String postDate) {
		PostDate = postDate;
	}

	public int getPostseenBefore() {
		return PostseenBefore;
	}

	public void setPostseenBefore(int postseenBefore) {
		PostseenBefore = postseenBefore;
	}

	public String getPostPhoto() {
		return PostPhoto;
	}

	public void setPostPhoto(String postPhoto) {
		PostPhoto = postPhoto;
	}

	public int getObjectId() {
		return ObjectId;
	}

	public void setObjectId(int objectId) {
		ObjectId = objectId;
	}

	public String getObjectImagePath2() {
		return ObjectImagePath2;
	}

	public void setObjectImagePath2(String objectImagePath2) {
		ObjectImagePath2 = objectImagePath2;
	}

	public int getLikeInObjectId() {
		return LikeInObjectId;
	}

	public void setLikeInObjectId(int likeInObjectId) {
		LikeInObjectId = likeInObjectId;
	}

	public int getLikeInObjectUserId() {
		return LikeInObjectUserId;
	}

	public void setLikeInObjectUserId(int likeInObjectUserId) {
		LikeInObjectUserId = likeInObjectUserId;
	}

	public int getLikeInObjectpaperId() {
		return LikeInObjectpaperId;
	}

	public void setLikeInObjectpaperId(int likeInObjectpaperId) {
		LikeInObjectpaperId = likeInObjectpaperId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserImagePath() {
		return UserImagePath;
	}

	public void setUserImagePath(String userImagePath) {
		UserImagePath = userImagePath;
	}

}
