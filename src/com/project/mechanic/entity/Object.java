package com.project.mechanic.entity;

public class Object {

	int Id;
	int ObjectTypeId;
	int ObjectBrandTypeId;
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

	String Google;
	String Twitter;
	String Site;
	
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

	Byte[] Image1;
	Byte[] Image2;
	Byte[] Image3;
	Byte[] Image4;
	

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

	public Byte[] getImage1() {
		return Image1;
	}

	public void setImage1(Byte[] image1) {
		Image1 = image1;
	}

	public Byte[] getImage2() {
		return Image2;
	}

	public void setImage2(Byte[] image2) {
		Image2 = image2;
	}

	public Byte[] getImage3() {
		return Image3;
	}

	public void setImage3(Byte[] image3) {
		Image3 = image3;
	}

	public Byte[] getImage4() {
		return Image4;
	}

	public void setImage4(Byte[] image4) {
		Image4 = image4;
	}

	public Object(int id, String name, String phone, String email, String fax,
			String description, Byte[] image1, Byte[] image2, Byte[] image3,
			Byte[] image4, String pdf1,String pdf2,String pdf3,String pdf4,String address,String cellphone,int objectTypeId,int objectBrandTypeId, String facebook,String instagram,String linkedIn, String google,String site , String twitter ) {
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
		Pdf1   = pdf1;
		Pdf2   = pdf2;
		Pdf3   = pdf3;
		Pdf4   = pdf4;
		Address=address;
		Cellphone= cellphone;
		ObjectTypeId=objectTypeId;
		ObjectBrandTypeId= objectBrandTypeId;
		Facebook=facebook;
		Instagram=instagram;
		LinkedIn=linkedIn;
		Google=google;
		Site=site;
		Twitter=twitter;
		
	}

}
