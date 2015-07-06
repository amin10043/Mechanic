package com.project.mechanic.entity;

public class Ticket implements Comparable<Ticket> {

	int Id;
	String Title;
	String Desc;
	int UserId;
	byte[] Image;
	String Date;
	int TypeId;
	int Name;
	int Email;
	int Mobile;
	int Phone;
	int Fax;
	int ProvinceId;
	String UName;
	String UEmail;
	String UPhone;
	String UFax;
	String UAdress;
	byte[] UImage;
	String UMobile;
	int Seen;
	int Submit;
	int seenBefore;
	int Day;

	public int getSeenBefore() {
		return seenBefore;
	}

	public void setSeenBefore(int seenBefore) {
		this.seenBefore = seenBefore;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDesc() {
		return Desc;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userid) {
		UserId = userid;
	}

	public byte[] getImage() {
		return Image;
	}

	public void setImage(byte[] image) {
		Image = image;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getTypeId() {
		return TypeId;
	}

	public void setTypeId(int typeid) {
		TypeId = typeid;
	}

	public int getName() {
		return Name;
	}

	public void setName(int name) {
		Name = name;
	}

	public int getEmail() {
		return Email;
	}

	public void setEmail(int email) {
		Email = email;
	}

	public int getMobile() {
		return Mobile;
	}

	public void setMobile(int mobile) {
		Mobile = mobile;
	}

	public int getPhone() {
		return Phone;
	}

	public void setPhone(int phone) {
		Phone = phone;
	}

	public int getFax() {
		return Fax;
	}

	public void setFax(int fax) {
		Fax = fax;
	}

	public int getProvinceId() {
		return ProvinceId;
	}

	public void setProvinceId(int provinceid) {
		ProvinceId = provinceid;
	}

	public String getUName() {
		return UName;
	}

	public void setUName(String uname) {
		UName = uname;
	}

	public String getUEmail() {
		return UEmail;
	}

	public void setUEmail(String uemail) {
		UEmail = uemail;
	}

	public String getUPhone() {
		return UPhone;

	}

	public void setUPhone(String uphone) {
		UPhone = uphone;
	}

	public String getUFax() {
		return UFax;

	}

	public void setUFax(String ufax) {
		UFax = ufax;
	}

	public String getUAdress() {
		return UAdress;
	}

	public void setUAdress(String uadress) {
		UAdress = uadress;
	}

	public byte[] getUImage() {
		return UImage;
	}

	public void setUImage(byte[] uimage) {
		UImage = uimage;
	}

	public String getUMobile() {
		return UMobile;
	}

	public void setUMobile(String umobile) {
		UMobile = umobile;
	}

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}

	public int getSubmit() {
		return Submit;
	}

	public void setSubmit(int submit) {
		Submit = submit;
	}
	public int getDay() {
		return Day;
	}

	public void setDay(int day) {
		Day = day;
	}

	public Ticket(int id, String title, String desc, int Userid, byte[] imge,
			String date, int Typeid, int name, int email, int mobile,
			int phone, int fax, int provinceid, String uname, String uemail,
			String uphone, String ufax, String uadress, byte[] uimage,
			String umobile, int seen, int submit, int seenBefore,int day) {
		super();
		Id = id;
		Title = title;
		Desc = desc;
		UserId = Userid;
		Image = imge;
		Date = date;
		TypeId = Typeid;
		Name = name;
		Email = email;
		Mobile = mobile;
		Phone = phone;
		Fax = fax;
		ProvinceId = provinceid;
		UName = uname;
		UEmail = uemail;
		UPhone = uphone;
		UFax = ufax;
		UAdress = uadress;
		UImage = uimage;
		UMobile = umobile;
		Seen = seen;
		Submit = submit;
		this.seenBefore = seenBefore;
		Day=day;
		
	}

	@Override
	public int compareTo(Ticket compareTicket) {
		try {
			long temp = Long.valueOf(getDate());
			long comparetemp = Long.valueOf(compareTicket.getDate());
			return (int) (comparetemp - temp);
		} catch (Exception ex) {
			return 0;
		}

	}
}
