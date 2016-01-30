package com.project.mechanic.entity;

public class Object implements Comparable<Object> {

	int Id;
	int ObjectTypeId;
	int ObjectBrandTypeId;
	int Seen;
	int Submit;
	String Name;
	String Phone;
	String Email;
	String Fax;
	String Description;
	String Pdf1;
	String Pdf2;
	String Pdf3;
	String Pdf4;
	String Address;
	String Cellphone;
	String Facebook;
	String Instagram;
	String LinkedIn;
	String ServerDate;
	int Rate;
	int ParentId;
	int MainObjectId;
	String Google;
	String Twitter;
	String Site;
	byte[] Image1;
	byte[] Image2;
	byte[] Image3;
	byte[] Image4;
	int IsActive;
	int UserId;
	int ObjectId;
	String Date;
	String image1ServerDate;
	String image2ServerDate;
	String image3ServerDate;
	int AgencyService;
	String ImagePath1;
	String ImagePath2;
	String ImagePath3;
	String ActiveDate;
	int CountView;

	public int getCountView() {
		return CountView;
	}

	public void setCountView(int countView) {
		CountView = countView;
	}

	public String getActiveDate() {
		return ActiveDate;
	}

	public void setActiveDate(String activeDate) {
		ActiveDate = activeDate;
	}

	public String getImagePath1() {
		return ImagePath1;
	}

	public void setImagePath1(String imagePath1) {
		ImagePath1 = imagePath1;
	}

	public String getImagePath2() {
		return ImagePath2;
	}

	public void setImagePath2(String imagePath2) {
		ImagePath2 = imagePath2;
	}

	public String getImagePath3() {
		return ImagePath3;
	}

	public void setImagePath3(String imagePath3) {
		ImagePath3 = imagePath3;
	}

	public String getImage1ServerDate() {
		return image1ServerDate;
	}

	public void setImage1ServerDate(String image1ServerDate) {
		this.image1ServerDate = image1ServerDate;
	}

	public String getImage2ServerDate() {
		return image2ServerDate;
	}

	public void setImage2ServerDate(String image2ServerDate) {
		this.image2ServerDate = image2ServerDate;
	}

	public String getImage3ServerDate() {
		return image3ServerDate;
	}

	public void setImage3ServerDate(String image3ServerDate) {
		this.image3ServerDate = image3ServerDate;
	}

	public String getFacebook() {
		return Facebook;
	}

	public void setFacebook(String facebook) {
		Facebook = facebook;
	}

	public String getInstagram() {
		return Instagram;
	}

	public void setInstagram(String instagram) {
		Instagram = instagram;
	}

	public String getLinkedIn() {
		return LinkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		LinkedIn = linkedIn;
	}

	public String getGoogle() {
		return Google;
	}

	public void setGoogle(String google) {
		Google = google;
	}

	public String getTwitter() {
		return Twitter;
	}

	public void setTwitter(String twitter) {
		Twitter = twitter;
	}

	public String getSite() {
		return Site;
	}

	public void setSite(String site) {
		Site = site;
	}

	public int getObjectTypeId() {
		return ObjectTypeId;
	}

	public void setObjectTypeId(int objectTypeId) {
		ObjectTypeId = objectTypeId;
	}

	public int getObjectBrandTypeId() {
		return ObjectBrandTypeId;
	}

	public void setObjectBrandTypeId(int objectBrandTypeId) {
		ObjectBrandTypeId = objectBrandTypeId;
	}

	public String getPdf1() {
		return Pdf1;
	}

	public void setPdf1(String pdf1) {
		Pdf1 = pdf1;
	}

	public String getPdf2() {
		return Pdf2;
	}

	public void setPdf2(String pdf2) {
		Pdf2 = pdf2;
	}

	public String getPdf3() {
		return Pdf3;
	}

	public void setPdf3(String pdf3) {
		Pdf3 = pdf3;
	}

	public String getPdf4() {
		return Pdf4;
	}

	public void setPdf4(String pdf4) {
		Pdf4 = pdf4;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCellphone() {
		return Cellphone;
	}

	public void setCellphone(String cellphone) {
		Cellphone = cellphone;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String fax) {
		Fax = fax;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public byte[] getImage1() {
		return Image1;
	}

	public void setImage1(byte[] image1) {
		Image1 = image1;
	}

	public byte[] getImage2() {
		return Image2;
	}

	public void setImage2(byte[] image2) {
		Image2 = image2;
	}

	public byte[] getImage3() {
		return Image3;
	}

	public void setImage3(byte[] image3) {
		Image3 = image3;
	}

	public byte[] getImage4() {
		return Image4;
	}

	public void setImage4(byte[] image4) {
		Image4 = image4;
	}

	public int getRate() {
		return Rate;
	}

	public void setRate(int r) {
		Rate = r;
	}

	public int getParentId() {
		return ParentId;
	}

	public void setParentId(int parentid) {
		ParentId = parentid;
	}

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}

	public String getServerDate() {
		return ServerDate;
	}

	public void setServerDate(String serverdate) {
		ServerDate = serverdate;
	}

	public void setSubmit(int submit) {
		Submit = submit;
	}

	public int getSubmit() {
		return Submit;
	}

	public int getMainObjectId() {
		return MainObjectId;
	}

	public void setMainObjectId(int mainObjectid) {
		MainObjectId = mainObjectid;
	}

	public int getIsActive() {
		return IsActive;
	}

	public void setIsActive(int isActive) {
		IsActive = isActive;

	}

	public Object(int id, String name, String phone, String email, String fax,
			String description, byte[] image1, byte[] image2, byte[] image3,
			byte[] image4, String pdf1, String pdf2, String pdf3, String pdf4,
			String address, String cellphone, int objectTypeId,
			int objectBrandTypeId, String facebook, String instagram,
			String linkedIn, String google, String site, String twitter,
			int parentid, int rate, int seen, String serverdate, int submit,
			int mainObjectid, int IsActive, int UserId, int ObjectId,
			String Date, String img1ServerDate, String img2ServerDate,
			String img3ServerDate, int AgencyService, String ImagePath1,
			String ImagePath2, String ImagePath3, String activeDate , int CounView) {

		super();
		Id = id;
		Name = name;
		Phone = phone;
		Email = email;
		Fax = fax;
		Description = description;
		Image1 = image1;
		Image2 = image2;
		Image3 = image3;
		Image4 = image4;
		Pdf1 = pdf1;
		Pdf2 = pdf2;
		Pdf3 = pdf3;
		Pdf4 = pdf4;
		Address = address;
		Cellphone = cellphone;
		ObjectTypeId = objectTypeId;
		ObjectBrandTypeId = objectBrandTypeId;
		Facebook = facebook;
		Instagram = instagram;
		LinkedIn = linkedIn;
		Google = google;
		Site = site;
		Twitter = twitter;
		ParentId = parentid;
		Rate = rate;
		Seen = seen;
		ServerDate = serverdate;
		Submit = submit;
		MainObjectId = mainObjectid;
		this.IsActive = IsActive;
		this.UserId = UserId;
		this.ObjectId = ObjectId;
		this.Date = Date;
		image1ServerDate = img1ServerDate;
		image2ServerDate = img2ServerDate;
		image3ServerDate = img3ServerDate;
		this.AgencyService = AgencyService;
		this.ImagePath1 = ImagePath1;
		this.ImagePath2 = ImagePath2;
		this.ImagePath3 = ImagePath3;
		ActiveDate = activeDate;
		this.CountView = CounView;
	}

	public int getAgencyService() {
		return AgencyService;
	}

	public void setAgencyService(int agencyService) {
		AgencyService = agencyService;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getObjectId() {
		return ObjectId;
	}

	public void setObjectId(int objectId) {
		ObjectId = objectId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int compareTo(Object compareObject) {
		int compareRate = compareObject.getRate();
		int a = compareRate - this.Rate;
		if (a == 0) {
			long temp = 0, temp1 = 0;
			if (compareObject.getDate() != null)
				temp = Long.valueOf(compareObject.getDate());
			if (this.getDate() != null)
				temp1 = Long.valueOf(this.getDate());
			return (int) (temp - temp1);
		} else
			return a;
	}

}
