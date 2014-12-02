package cn.berserker.view;

import android.view.View;
import android.view.ViewGroup;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.FrameLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.text.InputType;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import cn.berserker.utils.Tools;
import cn.berserker.platform.ImageCache;
import cn.berserker.platform.ResourceTable;

public class PaymentFace {

	public static View createPaymentFacePort(final Activity activity, ImageCache imageCache) {
		// start to calculate the square size of dialog.
		DisplayMetrics display = activity.getResources().getDisplayMetrics();
		int STATUS_BAR_HEIGHT = (int)Math.ceil(25 * display.density);
		int winWidth = (int)(display.widthPixels * 0.9f);
		int winHeight = (int)((display.heightPixels - STATUS_BAR_HEIGHT) * 0.9f);

		LinearLayout.LayoutParams llp;
		RelativeLayout.LayoutParams rlp;
		FrameLayout.LayoutParams flp;
		Drawable d = null;

		LinearLayout mainLayout = new LinearLayout(activity);
		flp = new FrameLayout.LayoutParams(
					winWidth,
					winHeight
					);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setLayoutParams(flp);

		// title bar. 
		RelativeLayout titleLayout = new RelativeLayout(activity);
		int titleHeight = (int)(0.1 * winHeight);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				titleHeight
				);
		d = imageCache.getImageDrawable(activity,ResourceTable.drawable.payment_dialog_title_bg);
		titleLayout.setBackgroundDrawable(d);
		titleLayout.setLayoutParams(llp);
		mainLayout.addView(titleLayout);

		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_logo);
		ImageView logo = new ImageView(activity);
		logo.setImageDrawable(d);
		logo.setScaleType(ImageView.ScaleType.FIT_XY);
		int logoHeight = (int)(0.8f * titleHeight);
		rlp = new RelativeLayout.LayoutParams(
				(int)(2.65f * logoHeight),
				logoHeight);
		rlp.setMargins((int)(0.05f * winWidth),0,0,0);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		logo.setLayoutParams(rlp);
		titleLayout.addView(logo);

		ImageView logout = new ImageView(activity);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_close);
		logout.setImageDrawable(d);
		logout.setId(ResourceTable.id.ID_DIALOG_EXIT);
		rlp = new RelativeLayout.LayoutParams(
				(int)(titleHeight * 0.75f),
				(int)(titleHeight * 0.75f)
				);
		rlp.rightMargin = (int)(0.05f * winWidth);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		logout.setLayoutParams(rlp);
		titleLayout.addView(logout);
		
		LinearLayout contentLayout = new LinearLayout(activity);
		d = imageCache.getImageDrawable(activity,ResourceTable.drawable.payment_dialog_content_bg);
		contentLayout.setBackgroundDrawable(d);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				winHeight - titleHeight
				);
		contentLayout.setLayoutParams(llp);
		contentLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.addView(contentLayout);

		//item information
		int itemHeight = (int)(0.30f * winHeight);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				itemHeight
				);
		RelativeLayout itemLayout = new RelativeLayout(activity);
		itemLayout.setLayoutParams(llp);
		contentLayout.addView(itemLayout);

		ImageView itemIcon = new ImageView(activity);
		itemIcon.setId(ResourceTable.id.ID_DIALOG_ITEM_THUMB);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_default_item);
		itemIcon.setImageDrawable(d);
		itemIcon.setScaleType(ImageView.ScaleType.FIT_XY);
		int iconSize = (int)(0.33f * winWidth);
		int iconMargin = (int)(0.18f * iconSize);
		rlp = new RelativeLayout.LayoutParams(
				iconSize,
				iconSize
				);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		rlp.setMargins((int)(1.4f * iconMargin), 0, iconMargin, 0);
		itemIcon.setLayoutParams(rlp);
		itemLayout.addView(itemIcon);


		TextView itemName = new TextView(activity);
		float textSize = 0.19f * itemHeight * 0.6f;
		itemName.setId(ResourceTable.id.ID_DIALOG_ITEM_SUBJECT);
		itemName.setText("itemName");
		itemName.setTextColor(0xff000000);
		itemName.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				(int)(0.19f * itemHeight)
				);
		itemName.setSingleLine(true);
		rlp.addRule(RelativeLayout.ALIGN_TOP, ResourceTable.id.ID_DIALOG_ITEM_THUMB);
		rlp.addRule(RelativeLayout.RIGHT_OF, ResourceTable.id.ID_DIALOG_ITEM_THUMB);
		itemName.setLayoutParams(rlp);
		itemLayout.addView(itemName);
		TextView itemPrice = new TextView(activity);
		itemPrice.setId(ResourceTable.id.ID_DIALOG_ITEM_PRICE);
		itemPrice.setText("itemPrice");
		itemPrice.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize * 0.86f);
		//itemPrice.setTextSize(textSize);
		itemPrice.setTextColor(0xffff0000);
		itemPrice.setSingleLine(true);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				(int)(0.19f * itemHeight)
				);
		rlp.addRule(RelativeLayout.ALIGN_LEFT, ResourceTable.id.ID_DIALOG_ITEM_SUBJECT);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_DIALOG_ITEM_SUBJECT);
		itemPrice.setLayoutParams(rlp);
		itemLayout.addView(itemPrice);
		TextView itemDesc = new TextView(activity);
		itemDesc.setId(ResourceTable.id.ID_DIALOG_ITEM_DESC);
		itemDesc.setText("itemDescription");
		itemDesc.setTextColor(0xff000000);
		textSize = 0.19f * itemHeight * 0.4f;
		itemDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		//itemDesc.setTextSize(textSize - 3);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				(int)(0.19f * itemHeight)
				);
		rlp.addRule(RelativeLayout.ALIGN_LEFT, ResourceTable.id.ID_DIALOG_ITEM_PRICE);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_DIALOG_ITEM_PRICE);
		itemDesc.setLayoutParams(rlp);
		itemLayout.addView(itemDesc);


		//content
		int bottomHeight = (int)(0.08f * winHeight);
		int payHeight = winHeight - titleHeight - itemHeight - bottomHeight;// 0.54
		LinearLayout payLayout = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				payHeight
				);
		payLayout.setLayoutParams(llp);
		payLayout.setOrientation(LinearLayout.VERTICAL);
		contentLayout.addView(payLayout);

		TextView payType = new TextView(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				(int)(0.16f * payHeight)
				);
		int typeMargins = (int)(0.05f * payHeight);
		llp.setMargins(typeMargins, typeMargins, typeMargins, 0);
		payType.setLayoutParams(llp);
		payType.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_PAYMENT_DIALOG_PAYLAYOUT_TYPE_TXT));
		payType.setTextColor(0xfffd9229);
		payType.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.07f * payHeight);
		payType.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		payType.setSingleLine(true);
		payLayout.addView(payType);
		//Please add margin top
		int listItemHeight = (int)(0.21f * payHeight);

		//alipay
		RelativeLayout aliLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				listItemHeight
				);
		llp.setMargins(typeMargins,0,typeMargins,5);
		aliLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_list_item_normal);
		aliLayout.setBackgroundDrawable(d);
		payLayout.addView(aliLayout);
		ImageView alipay = new ImageView(activity);
		alipay.setId(ResourceTable.id.ID_DIALOG_PAY_ALIPAY);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_pay_alipay);
		alipay.setImageDrawable(d);
		rlp = new RelativeLayout.LayoutParams(
				(int)(0.7f * listItemHeight * 3.33f),
				(int)(0.7f * listItemHeight)
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		alipay.setLayoutParams(rlp);
		aliLayout.addView(alipay);

		//mmpay
		RelativeLayout mmLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				listItemHeight
				);
		llp.setMargins(typeMargins,0,typeMargins,5);
		mmLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_list_item_normal);
		mmLayout.setBackgroundDrawable(d);
		payLayout.addView(mmLayout);
		ImageView mmpay = new ImageView(activity);
		mmpay.setId(ResourceTable.id.ID_DIALOG_PAY_MMPAY);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_pay_mm);
		mmpay.setImageDrawable(d);
		rlp = new RelativeLayout.LayoutParams(
				(int)(0.7f * listItemHeight * 3.33f),
				(int)(0.7f * listItemHeight)
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		mmpay.setLayoutParams(rlp);
		mmLayout.addView(mmpay);
		mmLayout.setVisibility(View.GONE);


		//phone area.
		RelativeLayout phoneLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				bottomHeight
				);
		phoneLayout.setLayoutParams(llp);
		contentLayout.addView(phoneLayout);
		TextView phone = new TextView(activity);
		phone.setSingleLine(true);
		phone.setText(ResourceTable.getLocaleString(activity,
				   	ResourceTable.string.STR_PAYMENT_DIALOG_PHONELAYOUT_TXT));
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		phone.setLayoutParams(rlp);
		phoneLayout.addView(phone);
		return mainLayout;
	}

	public static View createPaymentFaceLand(final Activity activity, ImageCache imageCache) {
		// start to calculate the square size of dialog.
		DisplayMetrics display = activity.getResources().getDisplayMetrics();
		int STATUS_BAR_HEIGHT = (int)Math.ceil(25 * display.density);
		int winWidth = (int)(display.widthPixels * 0.9f);
		int winHeight = (int)((display.heightPixels - STATUS_BAR_HEIGHT) * 0.9f);

		LinearLayout.LayoutParams llp = null;
		RelativeLayout.LayoutParams rlp = null;
		FrameLayout.LayoutParams flp = null;
		Drawable d = null;

		LinearLayout mainLayout = new LinearLayout(activity);
		flp = new FrameLayout.LayoutParams(
					winWidth,
					winHeight
					);
		mainLayout.setOrientation(LinearLayout.VERTICAL);
		mainLayout.setLayoutParams(flp);

		// title bar. 
		RelativeLayout titleLayout = new RelativeLayout(activity);
		float TITLE_HEIGHT = 0.14f;
		int titleHeight = (int)(TITLE_HEIGHT * winHeight);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				titleHeight
				);
		d = imageCache.getImageDrawable(activity,ResourceTable.drawable.payment_dialog_title_bg_land);
		titleLayout.setBackgroundDrawable(d);
		titleLayout.setLayoutParams(llp);
		mainLayout.addView(titleLayout);

		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_logo);
		ImageView logo = new ImageView(activity);
		logo.setImageDrawable(d);
		logo.setScaleType(ImageView.ScaleType.FIT_XY);
		int logoHeight = (int)(0.8f*titleHeight);
		rlp = new RelativeLayout.LayoutParams(
				(int)(2.65f * logoHeight),
				logoHeight);
		rlp.setMargins((int)(0.05f * winWidth),0,0,0);
		logo.setLayoutParams(rlp);
		titleLayout.addView(logo);

		ImageView logout = new ImageView(activity);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_close);
		logout.setImageDrawable(d);
		logout.setId(ResourceTable.id.ID_DIALOG_EXIT);
		rlp = new RelativeLayout.LayoutParams(
				(int)(titleHeight * 0.75f),
				(int)(titleHeight * 0.75f)
				);
		rlp.rightMargin = (int)(0.05f * winWidth);
		rlp.addRule(RelativeLayout.CENTER_VERTICAL);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		logout.setLayoutParams(rlp);
		titleLayout.addView(logout);
		
		int contentHeight = (int)(winHeight * (1 - TITLE_HEIGHT));

		LinearLayout contentLayout = new LinearLayout(activity);
		d = imageCache.getImageDrawable(activity,ResourceTable.drawable.payment_dialog_content_bg_land);
		contentLayout.setBackgroundDrawable(d);
		llp = new LinearLayout.LayoutParams(
				winWidth,
				contentHeight
				);
		contentLayout.setLayoutParams(llp);
		contentLayout.setOrientation(LinearLayout.HORIZONTAL);
		mainLayout.addView(contentLayout);


		float ITEM_WIDTH = 0.313f;
		int itemWidth = (int)(ITEM_WIDTH * winWidth);
		//item information

		llp = new LinearLayout.LayoutParams(
				itemWidth,
				LinearLayout.LayoutParams.MATCH_PARENT
				);
		llp.leftMargin =(int)(0.0898f * winWidth);
		llp.topMargin = (int)(0.0497f * winWidth);
		RelativeLayout itemLayout = new RelativeLayout(activity);
		itemLayout.setLayoutParams(llp);
		contentLayout.addView(itemLayout);

		int iconSize = (itemWidth * 0.6f) > (contentHeight * 0.4f)?(int)(contentHeight * 0.4f):(int)(itemWidth*0.6f);
		ImageView itemIcon = new ImageView(activity);
		itemIcon.setId(ResourceTable.id.ID_DIALOG_ITEM_THUMB);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_default_item);
		itemIcon.setImageDrawable(d);
		itemIcon.setScaleType(ImageView.ScaleType.FIT_XY);
		rlp = new RelativeLayout.LayoutParams(
				iconSize,
				iconSize
				);
		rlp.setMargins(0,(int)(0.1f * iconSize),0,(int)(0.1f * iconSize));
		rlp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		itemIcon.setLayoutParams(rlp);
		itemLayout.addView(itemIcon);


		int itemMargins = (int)(0.09f * contentHeight);
		TextView itemName = new TextView(activity);
		itemName.setId(ResourceTable.id.ID_DIALOG_ITEM_SUBJECT);
		itemName.setText("itemName");
		itemName.setTextColor(0xff000000);
		itemName.setSingleLine(true);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.setMargins(itemMargins, 0,itemMargins,0);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_DIALOG_ITEM_THUMB);
		itemName.setLayoutParams(rlp);
		itemLayout.addView(itemName);
		TextView itemPrice = new TextView(activity);
		itemPrice.setId(ResourceTable.id.ID_DIALOG_ITEM_PRICE);
		itemPrice.setText("itemPrice");
		itemPrice.setSingleLine(true);
		itemPrice.setTextColor(0xffff0000);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.setMargins(0, 0,itemMargins,0);
		rlp.addRule(RelativeLayout.ALIGN_LEFT, ResourceTable.id.ID_DIALOG_ITEM_SUBJECT);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_DIALOG_ITEM_SUBJECT);
		itemPrice.setLayoutParams(rlp);
		itemLayout.addView(itemPrice);
		TextView itemDesc = new TextView(activity);
		itemDesc.setId(ResourceTable.id.ID_DIALOG_ITEM_DESC);
		itemDesc.setText("itemDescription");
		itemDesc.setTextColor(0xff000000);
		itemDesc.setSingleLine(true);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.setMargins(0, 0,itemMargins,0);
		rlp.addRule(RelativeLayout.ALIGN_LEFT, ResourceTable.id.ID_DIALOG_ITEM_PRICE);
		rlp.addRule(RelativeLayout.BELOW, ResourceTable.id.ID_DIALOG_ITEM_PRICE);
		itemDesc.setLayoutParams(rlp);
		itemLayout.addView(itemDesc);


		//content
		LinearLayout payLayout = new LinearLayout(activity);
		llp = new LinearLayout.LayoutParams(
				((int)(0.8605f * winWidth) - itemWidth),
				contentHeight
				);
		payLayout.setLayoutParams(llp);
		payLayout.setOrientation(LinearLayout.VERTICAL);
		contentLayout.addView(payLayout);

		TextView payType = new TextView(activity);
		payType.setSingleLine(true);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				(int)(0.14f * contentHeight)
				);
		llp.setMargins(0,(int)(0.05f*contentHeight),0,(int)(0.05f*contentHeight));
		payType.setLayoutParams(llp);
		payType.setText(ResourceTable.getLocaleString(activity, ResourceTable.string.STR_PAYMENT_DIALOG_PAYLAYOUT_TYPE_TXT));
		payType.setTextColor(0xfffd9229);
		payType.setGravity(Gravity.CENTER_VERTICAL);
		payType.setTextSize(TypedValue.COMPLEX_UNIT_PX, 0.085f * contentHeight);
		//payType.setTextSize((int)0.2f * squareSize);
		payType.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		payLayout.addView(payType);
		//Please add margin top

		int listItemHeight = (int)(0.16f * contentHeight);
		int listMarginLeft = (int)(0.06f * winWidth);


		//alipay
		RelativeLayout aliLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				listItemHeight
				);
		llp.setMargins(listMarginLeft,0,listMarginLeft,0);
		aliLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_list_item_normal);
		aliLayout.setBackgroundDrawable(d);
		payLayout.addView(aliLayout);
		ImageView alipay = new ImageView(activity);
		alipay.setId(ResourceTable.id.ID_DIALOG_PAY_ALIPAY);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_pay_alipay);
		alipay.setImageDrawable(d);
		rlp = new RelativeLayout.LayoutParams(
				(int)(listItemHeight * 3.33f),
				listItemHeight	
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		alipay.setLayoutParams(rlp);
		aliLayout.addView(alipay);

		//mmpay
		RelativeLayout mmLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				listItemHeight
				);
		llp.setMargins(listMarginLeft,0,listMarginLeft,0);
		mmLayout.setLayoutParams(llp);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_list_item_normal);
		mmLayout.setBackgroundDrawable(d);
		payLayout.addView(mmLayout);
		ImageView mmpay = new ImageView(activity);
		mmpay.setId(ResourceTable.id.ID_DIALOG_PAY_MMPAY);
		d = imageCache.getImageDrawable(activity, ResourceTable.drawable.payment_dialog_pay_mm);
		mmpay.setImageDrawable(d);
		rlp = new RelativeLayout.LayoutParams(
				(int)(listItemHeight * 3.33f),
				listItemHeight	
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		mmpay.setLayoutParams(rlp);
		mmLayout.addView(mmpay);
		mmLayout.setVisibility(View.GONE);


		//phone layout
		RelativeLayout phoneLayout = new RelativeLayout(activity);
		llp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				listItemHeight
				);
		phoneLayout.setLayoutParams(llp);
		payLayout.addView(phoneLayout);
		TextView phone = new TextView(activity);
		phone.setText(ResourceTable.getLocaleString(activity,
				   	ResourceTable.string.STR_PAYMENT_DIALOG_PHONELAYOUT_TXT));
		phone.setSingleLine(true);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		phone.setLayoutParams(rlp);
		phoneLayout.addView(phone);
		return mainLayout;
	}



	public static View getWaitingView(final Activity activity, ImageCache imageCache) {
		DisplayMetrics display = activity.getResources().getDisplayMetrics();
		int winWidth = (int)(display.widthPixels * 0.9f);
		int winHeight = (int)(display.heightPixels * 0.9f);
		RelativeLayout mainLayout = new RelativeLayout(activity);
		mainLayout.setLayoutParams(new ViewGroup.LayoutParams(
					winWidth,
					winHeight
					));
		RelativeLayout.LayoutParams rlp;
		ProgressBar waitView = new ProgressBar(activity);
		rlp = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
				);
		rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
		waitView.setLayoutParams(rlp);
		mainLayout.addView(waitView);
		return mainLayout;
	}
}
