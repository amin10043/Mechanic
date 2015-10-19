package com.project.mechanic.entity;

public class PersonalData {

	int Id;

	int ObjectId;
	String NameObject;
	String ImagePathObject;
	String DateObject;

	int FroumId;
	String NameFroum;
	String DescriptionFroum;
	String DateFroum;
	int seenBeforeFroum;
	int UserIdFroum;

	int PaperId;
	String NamePaper;
	String DescriptonPaper;
	String DatePaper;
	int seenBeforePaper;
	int UserIdPaper;

	int TicketId;
	String NameTicket;
	String DescriptonTicket;
	String ImagePathTicket;
	String DateTicket;
	int SeenBefore;
	int DayTicket;

	public PersonalData(int Id, int ObjectId, String NameObject,
			String ImagePathObject, String DateObject) {

		this.Id = Id;
		this.ObjectId = ObjectId;
		this.NameObject = NameObject;
		this.ImagePathObject = ImagePathObject;
		this.DateObject = DateObject;
	}

	public PersonalData(int Id, int ObjectId, String NameObject,
			String ImagePathObject, String DateObject, int FroumId,
			String NameFroum, String DescriptionFroum, String DateFroum,
			int PaperId, String NamePaper, String DescriptonPaper,
			String DatePaper, int TicketId, String NameTicket,
			String DescriptonTicket, String ImagePathTicket, String DateTicket,
			int SeenBefore, int DayTicket, int seenBeforePaper,
			int UserIdPaper, String DataPaper) {

		this.Id = Id;

		this.ObjectId = ObjectId;
		this.NameObject = NameObject;
		this.ImagePathObject = ImagePathObject;
		this.DateObject = DateObject;

		this.FroumId = FroumId;
		this.NameFroum = NameFroum;
		this.DescriptionFroum = DescriptionFroum;
		this.DateFroum = DateFroum;

		this.PaperId = PaperId;
		this.NamePaper = NamePaper;
		this.DescriptonPaper = DescriptonPaper;
		this.DatePaper = DatePaper;

		this.TicketId = TicketId;
		this.NameTicket = NameTicket;
		this.DescriptonTicket = DescriptonTicket;
		this.ImagePathTicket = ImagePathTicket;
		this.DateTicket = DateTicket;
		this.SeenBefore = SeenBefore;
		this.DayTicket = DayTicket;

		this.seenBeforePaper = seenBeforePaper;
		this.UserIdPaper = UserIdPaper;

	}

	public int getSeenBeforeFroum() {
		return seenBeforeFroum;
	}

	public void setSeenBeforeFroum(int seenBeforeFroum) {
		this.seenBeforeFroum = seenBeforeFroum;
	}

	public int getUserIdFroum() {
		return UserIdFroum;
	}

	public void setUserIdFroum(int userIdFroum) {
		UserIdFroum = userIdFroum;
	}

	public int getUserIdPaper() {
		return UserIdPaper;
	}

	public void setUserIdPaper(int userIdPaper) {
		UserIdPaper = userIdPaper;
	}

	public int getSeenBeforePaper() {
		return seenBeforePaper;
	}

	public void setSeenBeforePaper(int seenBeforePaper) {
		this.seenBeforePaper = seenBeforePaper;
	}

	public int getDayTicket() {
		return DayTicket;
	}

	public void setDayTicket(int dayTicket) {
		DayTicket = dayTicket;
	}

	public int getSeenBefore() {
		return SeenBefore;
	}

	public void setSeenBefore(int seenBefore) {
		SeenBefore = seenBefore;
	}

	public PersonalData() {
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getObjectId() {
		return ObjectId;
	}

	public void setObjectId(int objectId) {
		ObjectId = objectId;
	}

	public String getNameObject() {
		return NameObject;
	}

	public void setNameObject(String nameObject) {
		NameObject = nameObject;
	}

	public String getImagePathObject() {
		return ImagePathObject;
	}

	public void setImagePathObject(String imagePathObject) {
		ImagePathObject = imagePathObject;
	}

	public String getDateObject() {
		return DateObject;
	}

	public void setDateObject(String dateObject) {
		DateObject = dateObject;
	}

	public int getFroumId() {
		return FroumId;
	}

	public void setFroumId(int froumId) {
		FroumId = froumId;
	}

	public String getNameFroum() {
		return NameFroum;
	}

	public void setNameFroum(String nameFroum) {
		NameFroum = nameFroum;
	}

	public String getDescriptionFroum() {
		return DescriptionFroum;
	}

	public void setDescriptionFroum(String descriptionFroum) {
		DescriptionFroum = descriptionFroum;
	}

	public String getDateFroum() {
		return DateFroum;
	}

	public void setDateFroum(String dateFroum) {
		DateFroum = dateFroum;
	}

	public int getPaperId() {
		return PaperId;
	}

	public void setPaperId(int paperId) {
		PaperId = paperId;
	}

	public String getNamePaper() {
		return NamePaper;
	}

	public void setNamePaper(String namePaper) {
		NamePaper = namePaper;
	}

	public String getDescriptonPaper() {
		return DescriptonPaper;
	}

	public void setDescriptonPaper(String descriptonPaper) {
		DescriptonPaper = descriptonPaper;
	}

	public String getDatePaper() {
		return DatePaper;
	}

	public void setDatePaper(String datePaper) {
		DatePaper = datePaper;
	}

	public int getTicketId() {
		return TicketId;
	}

	public void setTicketId(int ticketId) {
		TicketId = ticketId;
	}

	public String getNameTicket() {
		return NameTicket;
	}

	public void setNameTicket(String nameTicket) {
		NameTicket = nameTicket;
	}

	public String getDescriptonTicket() {
		return DescriptonTicket;
	}

	public void setDescriptonTicket(String descriptonTicket) {
		DescriptonTicket = descriptonTicket;
	}

	public String getImagePathTicket() {
		return ImagePathTicket;
	}

	public void setImagePathTicket(String imagePathTicket) {
		ImagePathTicket = imagePathTicket;
	}

	public String getDateTicket() {
		return DateTicket;
	}

	public void setDateTicket(String dateTicket) {
		DateTicket = dateTicket;
	}

}
