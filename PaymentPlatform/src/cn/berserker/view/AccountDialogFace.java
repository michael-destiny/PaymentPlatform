package cn.berserker.view;

import android.view.View;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.text.InputType;
import android.os.Handler;
import android.util.TypedValue;
import android.util.DisplayMetrics;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import cn.berserker.utils.Tools;
import cn.berserker.platform.ImageCache;
import cn.berserker.platform.ResourceTable;

public class AccountDialogFace {

	public static View createAccountFacePort(final Activity activity, ImageCache imageCache) {
		//get devices config first.
		DisplayMetrics display = activity.getResources().getDisplayMetrics();
		int STATUS_BAR_HEIGHT = (int)Math.ceil(25 * display.density);
		int winWidth = (int)(display.widthPixels * 0.9f);
		int winHeight = (int)((display.heightPixels - STATUS_BAR_HEIGHT) * 0.9f);

		LinearLayout.LayoutParams llp = null;
		FrameLayout.LayoutParams flp = null;
		RelativeLayout.LayoutParams rlp = null;
		Drawable d = null;

		int titleHeight = (int)(0.1f * winHeight);
		int logoHeight = (int)(0.8f * titleHeight);

		int subjectHeight = (int) (0.16f * (winHeight - titleHeight));
		int inputAreaHeight = (int)(0.1f * (winHeight - titleHeight));
		int inputAreaWidth = (int)(0.83f * winWidth);
		int inputAreaMarginTop = (int)(0.02f * (winHeight - titleHeight));
		int inputAreaLabelWidth = (int)(0.29f * inputAreaWidth);

		int submitLayoutMarginTop = (int)(0.05f * (winHeight - titleHeight));
		int submitLayoutBtnWidth = (int)(0.4f * winWidth);
		int submitLayoutHeight = (int)(0.12f * (winHeight - titleHeight));

		//main container.
		FrameLayout mainLayout = new FrameLayout(activity);
		flp  = new FrameLayout.LayoutParams(
					winWidth,
					winHeight);
		mainLayout.setLayoutParams(flp);
		//login face
		LinearLayout loginLayout = new LinearLayout(activity);
		loginLayout.setId(ResourceTable.id.ID_LOGIN_LAYOUT);
		loginLayout.setOrientation(LinearLayout.VERTICAL);
		flp = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.MATCH_PARENT);
		loginLayout.setLayoutParams(flp);
		mainLayout.addView(loginLayout);

		//login title.
		RelativeLayout loginTitleLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				winWidth,
				titleHeight
				);
		loginTitleLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_title_bg);
		loginTitleLayout.setBackgroundDrawable(d);
		loginLayout.addView(loginTitleLayout);

		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_logo);
		ImageView loginLogo = new ImageView(activity);
		loginLogo.setImageDrawable(d);
		loginLogo.setScaleType(ImageView.ScaleType.FIT_XY);
		rlp = new RelativeLayout.LayoutParams(
				(int)(2.65f * logoHeight),
				logoHeight
				);
		rlp.setMargins((int)(0.05f * winWidth), 0, 0, 0);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		loginLogo.setLayoutParams(rlp);
		loginTitleLayout.addView(loginLogo);

		ImageView loginLogout = new ImageView(activity);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_close);
		loginLogout.setImageDrawable(d);
		loginLogout.setId(ResourceTable.id.ID_LOGIN_LAYOUT_EXIT);
		rlp = new RelativeLayout.LayoutParams(
				(int)(titleHeight * 0.75f),
				(int)(titleHeight * 0.75f)
				);
		rlp.rightMargin = (int)(0.05f * winWidth);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		loginLogout.setLayoutParams(rlp);
		loginTitleLayout.addView(loginLogout);

		//login content.
		RelativeLayout loginContentLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				winHeight - titleHeight
				);
		loginContentLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_content_bg);
		loginContentLayout.setBackgroundDrawable(d);
		loginLayout.addView(loginContentLayout);

		//login subject.
		RelativeLayout loginSubjectArea = new RelativeLayout(activity);
		loginSubjectArea.setId(ResourceTable.id.ID_LOGIN_SUBJECT_AREA);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				subjectHeight
				);
		rlp.setMargins((int)(0.05f * winWidth), 0, 0, 0);
		loginSubjectArea.setLayoutParams(rlp);
		loginContentLayout.addView(loginSubjectArea);
		TextView loginSubjectLabel = new TextView(activity);
		loginSubjectLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_LOGIN_SUBJECT_TXT));
		loginSubjectLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.31f * subjectHeight);
		loginSubjectLabel.setTextColor(0xfff08002);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		loginSubjectLabel.setLayoutParams(rlp);
		loginSubjectArea.addView(loginSubjectLabel);
		
		LinearLayout loginInputArea = new LinearLayout(activity);
		loginInputArea.setOrientation(LinearLayout.VERTICAL);
		loginInputArea.setId(ResourceTable.id.ID_LOGIN_INPUT_AREA);
		rlp = new RelativeLayout.LayoutParams(
				inputAreaWidth,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_LOGIN_SUBJECT_AREA);
		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		loginInputArea.setLayoutParams(rlp);
		loginContentLayout.addView(loginInputArea);
		
		//login name.
		LinearLayout loginNameArea = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth,
				inputAreaHeight
				);
		loginNameArea.setLayoutParams(llp);
		loginInputArea.addView(loginNameArea);
		TextView loginNameLabel = new TextView(activity);
		loginNameLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_USERNAME_TXT));
		loginNameLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		llp = new LinearLayout.LayoutParams(
				inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		loginNameLabel.setLayoutParams(llp);
		loginNameArea.addView(loginNameLabel);
		EditText loginNameEdit = new EditText(activity);
		loginNameEdit.setId(ResourceTable.id.ID_LOGIN_LAYOUT_USERNAME);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth - inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		loginNameEdit.setLayoutParams(llp);
		loginNameEdit.setSingleLine(true);
		loginNameArea.addView(loginNameEdit);

		//login password.
		LinearLayout loginPswArea = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				inputAreaHeight
				);
		llp.setMargins(0, inputAreaMarginTop, 0, 0);
		loginPswArea.setLayoutParams(llp);
		loginInputArea.addView(loginPswArea);
		TextView loginPswLabel = new TextView(activity);
		loginPswLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_PASSWORD_TXT));
		loginPswLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		llp = new LinearLayout.LayoutParams(
				inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		loginPswLabel.setLayoutParams(llp);
		loginPswArea.addView(loginPswLabel);
		EditText loginPswEdit = new EditText(activity);
		loginPswEdit.setId(ResourceTable.id.ID_LOGIN_LAYOUT_PASSWORD);
		loginPswEdit.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth - inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		loginPswEdit.setLayoutParams(llp);
		loginPswArea.addView(loginPswEdit);

		LinearLayout loginSubmitLayout = new LinearLayout(activity);
		loginSubmitLayout.setId(ResourceTable.id.ID_LOGIN_SUBMIT_AREA);
		rlp = new RelativeLayout.LayoutParams(
				submitLayoutBtnWidth * 2,
				submitLayoutHeight
				);
		rlp.setMargins(0, submitLayoutMarginTop, 0, 0);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_LOGIN_INPUT_AREA);
		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		loginSubmitLayout.setLayoutParams(rlp);
		loginContentLayout.addView(loginSubmitLayout);

		//login subject
		RelativeLayout loginSubmitRegion = new RelativeLayout(activity);
		loginSubmitRegion.setId(ResourceTable.id.ID_LOGIN_LAYOUT_SUBMIT);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_btn_submit);
		loginSubmitRegion.setBackgroundDrawable(d);
		llp = new LinearLayout.LayoutParams(
				submitLayoutBtnWidth,
				submitLayoutHeight
				);
		loginSubmitRegion.setLayoutParams(llp);
		loginSubmitLayout.addView(loginSubmitRegion);

		TextView loginSubmitBtn = new TextView(activity);
		loginSubmitBtn.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_BTN_SUBMIT_TXT));
		loginSubmitBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.34f * submitLayoutHeight);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		loginSubmitBtn.setLayoutParams(rlp);
		loginSubmitRegion.addView(loginSubmitBtn);

		//login register
		RelativeLayout loginRegisterRegion = new RelativeLayout(activity);
		loginRegisterRegion.setId(ResourceTable.id.ID_LOGIN_LAYOUT_REGISTER);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_btn_submit);
		loginRegisterRegion.setBackgroundDrawable(d);
		llp = new LinearLayout.LayoutParams(
				submitLayoutBtnWidth,
				submitLayoutHeight
				);
		loginRegisterRegion.setLayoutParams(llp);
		loginSubmitLayout.addView(loginRegisterRegion);
		TextView loginRegisterBtn = new TextView(activity);
		loginRegisterBtn.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_BTN_REGISTER_TXT));
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		loginRegisterBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.34f * submitLayoutHeight);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		loginRegisterBtn.setLayoutParams(rlp);
		loginRegisterRegion.addView(loginRegisterBtn);
		loginLayout.setVisibility(View.VISIBLE);


		//register face
		LinearLayout registerLayout = new LinearLayout(activity);
		registerLayout.setId(ResourceTable.id.ID_REGISTER_LAYOUT);
		registerLayout.setOrientation(LinearLayout.VERTICAL);
		flp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT
				);
		registerLayout.setLayoutParams(flp);
		mainLayout.addView(registerLayout);
		//register title.
		RelativeLayout registerTitleLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				winWidth,
				titleHeight
				);
		registerTitleLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_title_bg);
		registerTitleLayout.setBackgroundDrawable(d);
		registerLayout.addView(registerTitleLayout);

		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_logo);
		ImageView registerLogo = new ImageView(activity);
		registerLogo.setImageDrawable(d);
		registerLogo.setScaleType(ImageView.ScaleType.FIT_XY);
		rlp = new RelativeLayout.LayoutParams(
				(int)(2.65f * logoHeight),
				logoHeight
				);
		rlp.setMargins((int)(0.05f * winWidth), 0, 0, 0);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		registerLogo.setLayoutParams(rlp);
		registerTitleLayout.addView(registerLogo);

		ImageView registerLogout = new ImageView(activity);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_close);
		registerLogout.setImageDrawable(d);
		registerLogout.setId(ResourceTable.id.ID_REGISTER_LAYOUT_EXIT);
		rlp = new RelativeLayout.LayoutParams(
				(int)(titleHeight * 0.75f),
				(int)(titleHeight * 0.75f)
				);
		rlp.rightMargin = (int)(0.05f * winWidth);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		registerLogout.setLayoutParams(rlp);
		registerTitleLayout.addView(registerLogout);

		RelativeLayout registerContentLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				winHeight - titleHeight
				);
		registerContentLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_content_bg);
		registerContentLayout.setBackgroundDrawable(d);
		registerLayout.addView(registerContentLayout);
		registerLayout.setVisibility(View.GONE);

		//register subject.
		RelativeLayout registerSubjectArea = new RelativeLayout(activity);
		registerSubjectArea.setId(ResourceTable.id.ID_REGISTER_SUBJECT_AREA);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				subjectHeight
				);
		rlp.setMargins((int)(0.05f * winWidth), 0, 0, 0);
		registerSubjectArea.setLayoutParams(rlp);
		registerContentLayout.addView(registerSubjectArea);
		TextView registerSubjectLabel = new TextView(activity);
		registerSubjectLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_REGISTER_SUBJECT_TXT));
		registerSubjectLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.31f * subjectHeight);
		registerSubjectLabel.setTextColor(0xfff08002);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		registerSubjectLabel.setLayoutParams(rlp);
		registerSubjectArea.addView(registerSubjectLabel);
		
		LinearLayout registerInputArea = new LinearLayout(activity);
		registerInputArea.setId(ResourceTable.id.ID_REGISTER_INPUT_AREA);
		registerInputArea.setOrientation(LinearLayout.VERTICAL);
		rlp = new RelativeLayout.LayoutParams(
				inputAreaWidth,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_REGISTER_SUBJECT_AREA);
		registerInputArea.setLayoutParams(rlp);
		registerContentLayout.addView(registerInputArea);

		LinearLayout registerNameLayout = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				inputAreaHeight
				);
		registerNameLayout.setLayoutParams(llp);
		registerInputArea.addView(registerNameLayout);

		//register name.
		TextView registerNameLabel = new TextView(activity);
		registerNameLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_USERNAME_TXT));
		llp = new LinearLayout.LayoutParams(
				inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerNameLabel.setLayoutParams(llp);
		registerNameLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		registerNameLayout.addView(registerNameLabel);

		EditText registerNameEdit = new EditText(activity);
		registerNameEdit.setId(ResourceTable.id.ID_REGISTER_LAYOUT_USERNAME);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth - inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerNameEdit.setLayoutParams(llp);
		registerNameEdit.setSingleLine(true);

		registerNameLayout.addView(registerNameEdit);

		//register password.
		LinearLayout registerPswLayout = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				inputAreaHeight
				);
		llp.setMargins(0, inputAreaMarginTop, 0, 0);
		registerPswLayout.setLayoutParams(llp);
		registerInputArea.addView(registerPswLayout);

		TextView registerPswLabel = new TextView(activity);
		registerPswLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_PASSWORD_TXT));
		llp = new LinearLayout.LayoutParams(
				inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerPswLabel.setLayoutParams(llp);
		registerPswLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		registerPswLayout.addView(registerPswLabel);
		EditText registerPswEdit = new EditText(activity);
		registerPswEdit.setId(ResourceTable.id.ID_REGISTER_LAYOUT_PASSWORD);
		registerPswEdit.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth - inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerPswEdit.setLayoutParams(llp);
		registerPswLayout.addView(registerPswEdit);

		//register mobile
		LinearLayout registerMobileLayout = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				inputAreaHeight
				);
		llp.setMargins(0, inputAreaMarginTop, 0, 0);
		registerMobileLayout.setLayoutParams(llp);
		registerInputArea.addView(registerMobileLayout);

		TextView registerMobileLabel = new TextView(activity);
		registerMobileLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_MOBILE_TXT));
		llp = new LinearLayout.LayoutParams(
				inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerMobileLabel.setLayoutParams(llp);
		registerMobileLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		registerMobileLayout.addView(registerMobileLabel);
		EditText registerMobileEdit = new EditText(activity);
		registerMobileEdit.setId(ResourceTable.id.ID_REGISTER_LAYOUT_MOBILE);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth - inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerMobileEdit.setLayoutParams(llp);
		registerMobileLayout.addView(registerMobileEdit);

		LinearLayout registerSubmitLayout = new LinearLayout(activity);
		rlp = new RelativeLayout.LayoutParams(
				submitLayoutBtnWidth * 2,
				submitLayoutHeight
				);
		rlp.setMargins(0, submitLayoutMarginTop, 0, 0);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_REGISTER_INPUT_AREA);
		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		registerSubmitLayout.setLayoutParams(rlp);
		registerContentLayout.addView(registerSubmitLayout);

		//register submit.
		RelativeLayout registerSubmitRegion = new RelativeLayout(activity);
		registerSubmitRegion.setId(ResourceTable.id.ID_REGISTER_LAYOUT_SUBMIT);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_btn_submit);
		registerSubmitRegion.setBackgroundDrawable(d);
		llp = new LinearLayout.LayoutParams(
				submitLayoutBtnWidth,
				submitLayoutHeight
				);
		registerSubmitRegion.setLayoutParams(llp);
		registerSubmitLayout.addView(registerSubmitRegion);

		TextView registerSubmitBtn = new TextView(activity);
		registerSubmitBtn.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_BTN_SUBMIT_TXT));
		registerSubmitBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.34f * submitLayoutHeight);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		registerSubmitBtn.setLayoutParams(rlp);
		registerSubmitRegion.addView(registerSubmitBtn);

		//register login.
		RelativeLayout registerLoginRegion = new RelativeLayout(activity);
		registerLoginRegion.setId(ResourceTable.id.ID_REGISTER_LAYOUT_LOGIN);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_btn_submit);
		registerLoginRegion.setBackgroundDrawable(d);
		llp = new LinearLayout.LayoutParams(
				submitLayoutBtnWidth,
				submitLayoutHeight
				);
		registerLoginRegion.setLayoutParams(llp);
		registerSubmitLayout.addView(registerLoginRegion);

		TextView registerLoginBtn = new TextView(activity);
		registerLoginBtn.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_BTN_LOGIN_TXT));
		registerLoginBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.34f * submitLayoutHeight);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		registerLoginBtn.setLayoutParams(rlp);
		registerLoginRegion.addView(registerLoginBtn);

		//wait Layout
		RelativeLayout waitLayout = new RelativeLayout(activity);
		waitLayout.setId(ResourceTable.id.ID_WAIT_LAYOUT);
		flp = new FrameLayout.LayoutParams(
					winWidth,
					winHeight
				);
		waitLayout.setBackgroundColor(0x88000000);
		waitLayout.setLayoutParams(flp);
		waitLayout.setVisibility(View.GONE);
		mainLayout.addView(waitLayout);

		ProgressBar waitView = new ProgressBar(activity);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		waitView.setLayoutParams(rlp);
		waitLayout.addView(waitView);
		
		return mainLayout;
	}

	//nearly same as account face portscape.
	public static View createAccountFaceLand(final Activity activity, ImageCache imageCache) {
		DisplayMetrics display = activity.getResources().getDisplayMetrics();
		int STATUS_BAR_HEIGHT = (int)Math.ceil(25 * display.density);
		int winWidth = (int)(display.widthPixels * 0.9f);
		int winHeight = (int)((display.heightPixels - STATUS_BAR_HEIGHT) * 0.9f);

		LinearLayout.LayoutParams llp = null;
		FrameLayout.LayoutParams flp = null;
		RelativeLayout.LayoutParams rlp = null;
		Drawable d = null;

		int titleHeight = (int)(0.14f * winHeight);
		int logoHeight = (int)(0.8f * titleHeight);

		int subjectHeight = (int) (0.16f * (winHeight - titleHeight));
		int inputAreaHeight = (int)(0.14f * (winHeight - titleHeight));
		int inputAreaWidth = (int)(0.66f * winWidth);
		int inputAreaMarginTop =  (int)(0.02f * (winHeight - titleHeight));
		int inputAreaLabelWidth = (int)(0.29f * inputAreaWidth);

		int submitLayoutMarginTop = (int)(0.05f * (winHeight - titleHeight));
		int submitLayoutBtnWidth = (int)(0.3f * winWidth);
		int submitLayoutHeight = (int)(0.18f * (winHeight - titleHeight));

		FrameLayout mainLayout = new FrameLayout(activity);
		flp  = new FrameLayout.LayoutParams(
					winWidth,
					winHeight);
		mainLayout.setLayoutParams(flp);
		//login face
		LinearLayout loginLayout = new LinearLayout(activity);
		loginLayout.setId(ResourceTable.id.ID_LOGIN_LAYOUT);
		loginLayout.setOrientation(LinearLayout.VERTICAL);
		flp = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.MATCH_PARENT);
		loginLayout.setLayoutParams(flp);
		mainLayout.addView(loginLayout);

		//login title.
		RelativeLayout loginTitleLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				winWidth,
				titleHeight
				);
		loginTitleLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_title_bg);
		loginTitleLayout.setBackgroundDrawable(d);
		loginLayout.addView(loginTitleLayout);

		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_logo);
		ImageView loginLogo = new ImageView(activity);
		loginLogo.setImageDrawable(d);
		loginLogo.setScaleType(ImageView.ScaleType.FIT_XY);
		rlp = new RelativeLayout.LayoutParams(
				(int)(2.65f * logoHeight),
				logoHeight
				);
		rlp.setMargins((int)(0.05f * winWidth), 0, 0, 0);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		loginLogo.setLayoutParams(rlp);
		loginTitleLayout.addView(loginLogo);

		ImageView loginLogout = new ImageView(activity);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_close);
		loginLogout.setImageDrawable(d);
		loginLogout.setId(ResourceTable.id.ID_LOGIN_LAYOUT_EXIT);
		rlp = new RelativeLayout.LayoutParams(
				(int)(titleHeight * 0.75f),
				(int)(titleHeight * 0.75f)
				);
		rlp.rightMargin = (int)(0.05f * winWidth);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		loginLogout.setLayoutParams(rlp);
		loginTitleLayout.addView(loginLogout);

		RelativeLayout loginContentLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				winHeight - titleHeight
				);
		loginContentLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_content_bg);
		loginContentLayout.setBackgroundDrawable(d);
		loginLayout.addView(loginContentLayout);

		//login subject
		RelativeLayout loginSubjectArea = new RelativeLayout(activity);
		loginSubjectArea.setId(ResourceTable.id.ID_LOGIN_SUBJECT_AREA);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				subjectHeight
				);
		rlp.setMargins((int)(0.05f * winWidth), 0, 0, 0);
		loginSubjectArea.setLayoutParams(rlp);
		loginContentLayout.addView(loginSubjectArea);
		TextView loginSubjectLabel = new TextView(activity);
		loginSubjectLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_LOGIN_SUBJECT_TXT));
		loginSubjectLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.61f * subjectHeight);
		loginSubjectLabel.setTextColor(0xfff08002);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		loginSubjectLabel.setLayoutParams(rlp);
		loginSubjectArea.addView(loginSubjectLabel);
		
		LinearLayout loginInputArea = new LinearLayout(activity);
		loginInputArea.setOrientation(LinearLayout.VERTICAL);
		loginInputArea.setId(ResourceTable.id.ID_LOGIN_INPUT_AREA);
		rlp = new RelativeLayout.LayoutParams(
				inputAreaWidth,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_LOGIN_SUBJECT_AREA);
		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		loginInputArea.setLayoutParams(rlp);
		loginContentLayout.addView(loginInputArea);
		
		//login name.
		LinearLayout loginNameArea = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth,
				inputAreaHeight
				);
		loginNameArea.setLayoutParams(llp);
		loginInputArea.addView(loginNameArea);
		TextView loginNameLabel = new TextView(activity);
		loginNameLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_USERNAME_TXT));
		loginNameLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		llp = new LinearLayout.LayoutParams(
				inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		loginNameLabel.setLayoutParams(llp);
		loginNameArea.addView(loginNameLabel);
		EditText loginNameEdit = new EditText(activity);
		loginNameEdit.setId(ResourceTable.id.ID_LOGIN_LAYOUT_USERNAME);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth - inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		loginNameEdit.setLayoutParams(llp);
		loginNameEdit.setSingleLine(true);
		loginNameArea.addView(loginNameEdit);

		//login password.
		LinearLayout loginPswArea = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				inputAreaHeight
				);
		llp.setMargins(0, inputAreaMarginTop, 0, 0);
		loginPswArea.setLayoutParams(llp);
		loginInputArea.addView(loginPswArea);
		TextView loginPswLabel = new TextView(activity);
		loginPswLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_PASSWORD_TXT));
		loginPswLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		llp = new LinearLayout.LayoutParams(
				inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		loginPswLabel.setLayoutParams(llp);
		loginPswArea.addView(loginPswLabel);
		EditText loginPswEdit = new EditText(activity);
		loginPswEdit.setId(ResourceTable.id.ID_LOGIN_LAYOUT_PASSWORD);
		loginPswEdit.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth - inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		loginPswEdit.setLayoutParams(llp);
		loginPswArea.addView(loginPswEdit);

		LinearLayout loginSubmitLayout = new LinearLayout(activity);
		loginSubmitLayout.setId(ResourceTable.id.ID_LOGIN_SUBMIT_AREA);
		rlp = new RelativeLayout.LayoutParams(
				submitLayoutBtnWidth * 2,
				submitLayoutHeight
				);
		rlp.setMargins(0, inputAreaHeight /2 + submitLayoutMarginTop, 0, 0);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_LOGIN_INPUT_AREA);
		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		loginSubmitLayout.setLayoutParams(rlp);
		loginContentLayout.addView(loginSubmitLayout);

		//login submit.
		RelativeLayout loginSubmitRegion = new RelativeLayout(activity);
		loginSubmitRegion.setId(ResourceTable.id.ID_LOGIN_LAYOUT_SUBMIT);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_btn_submit);
		loginSubmitRegion.setBackgroundDrawable(d);
		llp = new LinearLayout.LayoutParams(
				submitLayoutBtnWidth,
				submitLayoutHeight
				);
		loginSubmitRegion.setLayoutParams(llp);
		loginSubmitLayout.addView(loginSubmitRegion);

		TextView loginSubmitBtn = new TextView(activity);
		loginSubmitBtn.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_BTN_SUBMIT_TXT));
		loginSubmitBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.34f * submitLayoutHeight);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		loginSubmitBtn.setLayoutParams(rlp);
		loginSubmitRegion.addView(loginSubmitBtn);

		//login register.
		RelativeLayout loginRegisterRegion = new RelativeLayout(activity);
		loginRegisterRegion.setId(ResourceTable.id.ID_LOGIN_LAYOUT_REGISTER);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_btn_submit);
		loginRegisterRegion.setBackgroundDrawable(d);
		llp = new LinearLayout.LayoutParams(
				submitLayoutBtnWidth,
				submitLayoutHeight
				);
		loginRegisterRegion.setLayoutParams(llp);
		loginSubmitLayout.addView(loginRegisterRegion);
		TextView loginRegisterBtn = new TextView(activity);
		loginRegisterBtn.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_BTN_REGISTER_TXT));
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		loginRegisterBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.34f * submitLayoutHeight);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		loginRegisterBtn.setLayoutParams(rlp);
		loginRegisterRegion.addView(loginRegisterBtn);
		loginLayout.setVisibility(View.VISIBLE);


		//register face
		LinearLayout registerLayout = new LinearLayout(activity);
		registerLayout.setId(ResourceTable.id.ID_REGISTER_LAYOUT);
		registerLayout.setOrientation(LinearLayout.VERTICAL);
		flp = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT
				);
		registerLayout.setLayoutParams(flp);
		mainLayout.addView(registerLayout);
		//register title
		RelativeLayout registerTitleLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				winWidth,
				titleHeight
				);
		registerTitleLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_title_bg);
		registerTitleLayout.setBackgroundDrawable(d);
		registerLayout.addView(registerTitleLayout);

		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_logo);
		ImageView registerLogo = new ImageView(activity);
		registerLogo.setImageDrawable(d);
		registerLogo.setScaleType(ImageView.ScaleType.FIT_XY);
		rlp = new RelativeLayout.LayoutParams(
				(int)(2.65f * logoHeight),
				logoHeight
				);
		rlp.setMargins((int)(0.05f * winWidth), 0, 0, 0);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		registerLogo.setLayoutParams(rlp);
		registerTitleLayout.addView(registerLogo);

		ImageView registerLogout = new ImageView(activity);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_close);
		registerLogout.setImageDrawable(d);
		registerLogout.setId(ResourceTable.id.ID_REGISTER_LAYOUT_EXIT);
		rlp = new RelativeLayout.LayoutParams(
				(int)(titleHeight * 0.75f),
				(int)(titleHeight * 0.75f)
				);
		rlp.rightMargin = (int)(0.05f * winWidth);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		registerLogout.setLayoutParams(rlp);
		registerTitleLayout.addView(registerLogout);

		RelativeLayout registerContentLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				winHeight - titleHeight
				);
		registerContentLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_content_bg);
		registerContentLayout.setBackgroundDrawable(d);
		registerLayout.addView(registerContentLayout);
		registerLayout.setVisibility(View.GONE);

		//register subject.
		RelativeLayout registerSubjectArea = new RelativeLayout(activity);
		registerSubjectArea.setId(ResourceTable.id.ID_REGISTER_SUBJECT_AREA);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				subjectHeight
				);
		rlp.setMargins((int)(0.05f * winWidth), 0, 0, 0);
		registerSubjectArea.setLayoutParams(rlp);
		registerContentLayout.addView(registerSubjectArea);
		TextView registerSubjectLabel = new TextView(activity);
		registerSubjectLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_REGISTER_SUBJECT_TXT));
		registerSubjectLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.61f * subjectHeight);
		registerSubjectLabel.setTextColor(0xfff08002);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		registerSubjectLabel.setLayoutParams(rlp);
		registerSubjectArea.addView(registerSubjectLabel);
		
		LinearLayout registerInputArea = new LinearLayout(activity);
		registerInputArea.setId(ResourceTable.id.ID_REGISTER_INPUT_AREA);
		registerInputArea.setOrientation(LinearLayout.VERTICAL);
		rlp = new RelativeLayout.LayoutParams(
				inputAreaWidth,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_REGISTER_SUBJECT_AREA);
		registerInputArea.setLayoutParams(rlp);
		registerContentLayout.addView(registerInputArea);

		//register name
		LinearLayout registerNameLayout = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				inputAreaHeight
				);
		registerNameLayout.setLayoutParams(llp);
		registerInputArea.addView(registerNameLayout);

		TextView registerNameLabel = new TextView(activity);
		registerNameLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_USERNAME_TXT));
		llp = new LinearLayout.LayoutParams(
				inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerNameLabel.setLayoutParams(llp);
		registerNameLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		registerNameLayout.addView(registerNameLabel);

		EditText registerNameEdit = new EditText(activity);
		registerNameEdit.setId(ResourceTable.id.ID_REGISTER_LAYOUT_USERNAME);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth - inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerNameEdit.setLayoutParams(llp);
		registerNameEdit.setSingleLine(true);

		registerNameLayout.addView(registerNameEdit);

		//register password.
		LinearLayout registerPswLayout = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				inputAreaHeight
				);
		llp.setMargins(0, inputAreaMarginTop, 0, 0);
		registerPswLayout.setLayoutParams(llp);
		registerInputArea.addView(registerPswLayout);

		TextView registerPswLabel = new TextView(activity);
		registerPswLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_PASSWORD_TXT));
		llp = new LinearLayout.LayoutParams(
				inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerPswLabel.setLayoutParams(llp);
		registerPswLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		registerPswLayout.addView(registerPswLabel);
		EditText registerPswEdit = new EditText(activity);
		registerPswEdit.setId(ResourceTable.id.ID_REGISTER_LAYOUT_PASSWORD);
		registerPswEdit.setInputType(InputType.TYPE_CLASS_TEXT |InputType.TYPE_TEXT_VARIATION_PASSWORD);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth - inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerPswEdit.setLayoutParams(llp);
		registerPswLayout.addView(registerPswEdit);

		//register mobile.
		LinearLayout registerMobileLayout = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				inputAreaHeight
				);
		llp.setMargins(0, inputAreaMarginTop, 0, 0);
		registerMobileLayout.setLayoutParams(llp);
		registerInputArea.addView(registerMobileLayout);

		TextView registerMobileLabel = new TextView(activity);
		registerMobileLabel.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_MOBILE_TXT));
		llp = new LinearLayout.LayoutParams(
				inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerMobileLabel.setLayoutParams(llp);
		registerMobileLabel.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
		registerMobileLayout.addView(registerMobileLabel);
		EditText registerMobileEdit = new EditText(activity);
		registerMobileEdit.setId(ResourceTable.id.ID_REGISTER_LAYOUT_MOBILE);
		llp = new LinearLayout.LayoutParams(
				inputAreaWidth - inputAreaLabelWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		registerMobileEdit.setLayoutParams(llp);
		registerMobileLayout.addView(registerMobileEdit);

		LinearLayout registerSubmitLayout = new LinearLayout(activity);
		rlp = new RelativeLayout.LayoutParams(
				submitLayoutBtnWidth * 2,
				submitLayoutHeight
				);
		rlp.setMargins(0, submitLayoutMarginTop, 0, 0);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_REGISTER_INPUT_AREA);
		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		registerSubmitLayout.setLayoutParams(rlp);
		registerContentLayout.addView(registerSubmitLayout);


		RelativeLayout registerSubmitRegion = new RelativeLayout(activity);
		registerSubmitRegion.setId(ResourceTable.id.ID_REGISTER_LAYOUT_SUBMIT);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_btn_submit);
		registerSubmitRegion.setBackgroundDrawable(d);
		llp = new LinearLayout.LayoutParams(
				submitLayoutBtnWidth,
				submitLayoutHeight
				);
		registerSubmitRegion.setLayoutParams(llp);
		registerSubmitLayout.addView(registerSubmitRegion);

		//register submit.
		TextView registerSubmitBtn = new TextView(activity);
		registerSubmitBtn.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_BTN_SUBMIT_TXT));
		registerSubmitBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.34f * submitLayoutHeight);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		registerSubmitBtn.setLayoutParams(rlp);
		registerSubmitRegion.addView(registerSubmitBtn);

		//register login.
		RelativeLayout registerLoginRegion = new RelativeLayout(activity);
		registerLoginRegion.setId(ResourceTable.id.ID_REGISTER_LAYOUT_LOGIN);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_btn_submit);
		registerLoginRegion.setBackgroundDrawable(d);
		llp = new LinearLayout.LayoutParams(
				submitLayoutBtnWidth,
				submitLayoutHeight
				);
		registerLoginRegion.setLayoutParams(llp);
		registerSubmitLayout.addView(registerLoginRegion);

		TextView registerLoginBtn = new TextView(activity);
		registerLoginBtn.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_ACCOUNT_DIALOG_BTN_LOGIN_TXT));
		registerLoginBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.34f * submitLayoutHeight);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		registerLoginBtn.setLayoutParams(rlp);
		registerLoginRegion.addView(registerLoginBtn);

		//wait Layout
		RelativeLayout waitLayout = new RelativeLayout(activity);
		waitLayout.setId(ResourceTable.id.ID_WAIT_LAYOUT);
		flp = new FrameLayout.LayoutParams(
					winWidth,
					winHeight
				);
		waitLayout.setBackgroundColor(0x88000000);
		waitLayout.setLayoutParams(flp);
		waitLayout.setVisibility(View.GONE);
		mainLayout.addView(waitLayout);

		ProgressBar waitView = new ProgressBar(activity);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		waitView.setLayoutParams(rlp);
		waitLayout.addView(waitView);
		
		return mainLayout;
	}
}
