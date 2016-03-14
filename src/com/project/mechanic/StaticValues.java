package com.project.mechanic;

public class StaticValues {

	public static final int TypeRunServerForGetDate = 1;
	public static final int TypeRunServerForRefreshItems = 2;
	public static final int TypeRunServerForAgencyServiceItem = 3;

	/* s مشخص کردن نوع بازدید انجام شده */
	public static final int TypeObjectVisit = 1;
	public static final int TypePaperVisit = 2;
	public static final int TypeTicketVisit = 3;
	public static final int TypeFroumVist = 4;
	public static final int TypePostVisit = 5;
	/* e مشخص کردن نوع بازدید انجام شده */

	/* s نسبت تصاویر نسبت به عرض دستگاه */
	public static final float RateImageRegisterPage = (float) 5.5;
	public static final float RateImageObjectPage = (float) 5.5;
	public static final float RateImagePaperFragmentPage = (float) 5.5;
	public static final float RateImageTitlePaper = (float) 5.5;
	public static final float RateImageTitleFroum = (float) 5.5;
	public static final float RateImageFroumFragmentPage = (float) 5.5;
	public static final float RateImagePostTimelineFragmentPage = (float) 5.5;
	public static final float RateImagePostFragmentPage = (float) 5.5;
	public static final float RateImageDisplayPersonalAdapter = (float) 5.5;
	public static final float RateImageEditAnad = (float) 5.5;
	public static final float RateImageCommentAndReply = (float) 5.5;
	public static final float RateImageUserLikedObject = (float) 5.5;
	/* e نسبت تصاویر نسبت به عرض گوشی */

	/* s نوع چت */
	public static final int TypeFavoriteChat = 1;
	public static final int TypeFamilyChat = 2;
	public static final int TypeFriendChat = 3;
	public static final int TypeGroupChat = 4;
	public static final int TypeChannalChat = 5;
	public static final int TypeRequestionChat = 6;
	public static final int TypeOffersChat = 7;
	public static final int TypeContactChat = 8;
	public static final int TypeInformationGroupAdminChat = 9;

	public static final int CountOfTabInChat = 9;

	public static final String[] nameTabsChat = { "", "علاقه مندی", "خانواده", "دوستان", "گروه", "تالار",
			"درخواست دوستی", "پیشنهاد", "مخاطبین" };

	public static final String LinkSoftwateChat = "لینک نرم افزار اینجا قرار می گیرد";
	/* e نوع چت */

	/* s نوع افرودن به علاقه مندی ها */
	public static final int TypeFavoriteFroum = 1;
	public static final int TypeFavoritePaper = 2;
	public static final int TypeFavoriteTicket = 3;
	public static final int TypeFavoritePost = 4;
	/* e نوع افرودن به علاقه مندی ها */

	/* s نوع لایک در صفحه برند */
	public static final int TypeLikePage = 0;
	public static final int TypeLikeFixedPost = 1;
	public static final int TypeHappyFromPage = 2;
	public static final int TypeSadFromPage = 3;
	/* e نوع لایک در صفحه برند */

	/* s پیغام */
	public static final String MessagePleaseWait = "لطفا منتظر بمانید ..." + "\n " + "\n "
			+ " از شکیبایی شما سپاس گزاریم";
	public static final String MessageError = "با عرض پوزش ، خطایی رخ داد";
	public static final String errorImageSaving = "با عرض پوزش ، خطایی در ذخیره سازی تصویر رخ داد";
	public static final String welcomeMessage = "با ما بهتر دیده شوید";
	public static final String IsRepeatLikeMessage = "شما قبلا نظرتان را در مورد این صفحه بیان کرده اید";
	public static final String selectImagesBrandMessage = "انتخاب عکس های صفحه اجباری است";


	/* e پیغام */

	/* s نوع ارسال تخلف */
	public static final int TypeReportPost = 1;
	public static final int TypeReportPaperTitle = 2;
	public static final int TypeReportPaperFragment = 3;
	public static final int TypeReportAnadFragment = 4;
	public static final int TypeReportShowAdFragment = 5;
	public static final int TypeReportFroumTitle = 6;
	public static final int TypeReportFroumFragment = 7;
	public static final int TypeReportFixedPostIntroduction = 8;
	public static final int TypeReportPostFragment = 9;
	public static final int TypeReportCommentPost = 10;
	public static final int TypeReportReplayPost = 11;
	public static final int TypeReportCommentPaper = 12;
	public static final int TypeReportCommentFroum = 13;
	public static final int TypeReportReplyFroum = 14;
	public static final int TypeReportPage = 15;
	public static final int TypeReportFixedPost = 16;


}
