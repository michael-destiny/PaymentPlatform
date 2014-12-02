package cn.berserker.payment;

import android.app.Dialog;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.app.PendingIntent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.content.Intent;
import android.text.TextUtils;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import cn.berserker.platform.ControlCenter;
import cn.berserker.payment.alipay.AlipayHelper;

import cn.berserker.platform.ImageCache;
import cn.berserker.platform.ResourceTable;
import cn.berserker.model.PurchaseInfo;
import cn.berserker.model.Notification;
import cn.berserker.model.ResultCode;
import cn.berserker.view.PaymentFace;
import cn.berserker.utils.Tools;
import cn.berserker.http.HttpContent;
import cn.berserker.http.HttpRequest;

public class PaymentDialog extends Dialog {
	ControlCenter mCenter = null;
	PaymentInterface mInterface = null;
	View mainLayout = null;
	View waitLayout = null;
	ImageCache imageCache = null;
	View exitBtn = null;
	View aliPay = null;
	View tenPay = null;
	View mmPay = null;

	boolean safeClose = false;
	//used for lock the sync the status of current payment.
	AtomicBoolean payLock = new AtomicBoolean(false);
	boolean supportThird = false;

	PaymentDialog(ControlCenter center, PaymentInterface inter) {
		super(center.getRuntimeEnv());
		mCenter = center;
		mInterface = inter;
		//used to cache the image resource used in dialog.
		imageCache = new ImageCache();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}


	private void initItemInfo(PurchaseInfo p) {
		if(null != mainLayout && null != p) {
			String resName = p.getItemRes();
			if(!TextUtils.isEmpty(resName)) {
				//read the item's thumb.
				int resId = getContext().getResources().getIdentifier(resName, "drawable",getContext().getPackageName());
				if(resId > 0 ) {
					ImageView thumb = (ImageView)mainLayout.findViewById(ResourceTable.id.ID_DIALOG_ITEM_THUMB);
					thumb.setImageResource(resId);
				}
			}
			TextView itemSubject = (TextView)mainLayout.findViewById(ResourceTable.id.ID_DIALOG_ITEM_SUBJECT);
			itemSubject.setText(p.getItemSubject());
			TextView itemPrice = (TextView)mainLayout.findViewById(ResourceTable.id.ID_DIALOG_ITEM_PRICE);
			itemPrice.setText(p.getAmount() / 100.0f + "元");
			TextView itemDesc = (TextView)mainLayout.findViewById(ResourceTable.id.ID_DIALOG_ITEM_DESC);
			itemDesc.setText(p.getItemDesc());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.setCancelable(false);
		getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		waitLayout = PaymentFace.getWaitingView(mCenter.getRuntimeEnv(), imageCache);
		ViewGroup decorView = ((ViewGroup)getWindow().getDecorView());
		decorView.addView(waitLayout);

	}

	protected void resetPayment() {
		payLock.set(false);
		
		if(!supportThird) {
			//this means we are using mm payment directly. but user cancel it. so close dialog without safe option.
			dismiss();
			return ;
		}
	}

	private void initializePaymentFace() {
		ViewGroup decorView = ((ViewGroup)getWindow().getDecorView());
		// read the screen orientation. and choose whether use landscape or portscape.
		Configuration config = getContext().getResources().getConfiguration();
		if(config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			mainLayout = PaymentFace.createPaymentFaceLand(mCenter.getRuntimeEnv(), imageCache);
		}
		else {
			mainLayout = PaymentFace.createPaymentFacePort(mCenter.getRuntimeEnv(), imageCache);
		}

		//hide them in beginning.
		mainLayout.setVisibility(View.GONE);
	
		decorView.addView(mainLayout);
		exitBtn = mainLayout.findViewById(ResourceTable.id.ID_DIALOG_EXIT);
		exitBtn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				//Tools.LOG("I'm back click!");
				if(payLock.get()) {
					Tools.showToast(v.getContext(),
						ResourceTable.getLocaleString(v.getContext(),ResourceTable.string.STR_PAYMENT_DIALOG_TOAST_PAYLOCK_TXT));
					return ;
				}
				payLock.set(true);
				//do not close it directly, just use common api to close the payment dialog.
				Tools.sendHandleMessage(mInterface.mHandler, PaymentConfig.HANDLE_PAY_REPORT, "0#cancel#");
			}
		});

		// MM was hide in 3rd payment.
		mmPay = mainLayout.findViewById(ResourceTable.id.ID_DIALOG_PAY_MMPAY);
		mmPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Tools.LOG("I'm mmPay click!");
				if(payLock.get()) {
					Tools.showToast(v.getContext(),
						ResourceTable.getLocaleString(v.getContext(),ResourceTable.string.STR_PAYMENT_DIALOG_TOAST_PAYLOCK_TXT));
					return ;
				}
				if(!mInterface.checkMMPurchase()) {
					Tools.showToast(v.getContext(),
						ResourceTable.getLocaleString(v.getContext(), ResourceTable.string.STR_PAYMENT_DIALOG_TOAST_MM_UNAVAILABLE));
					return ;
				}
				payLock.set(true);
				mInterface.startMMPurchase();
			}
		});


		aliPay = mainLayout.findViewById(ResourceTable.id.ID_DIALOG_PAY_ALIPAY);
		aliPay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Tools.LOG("I'm alipay click!");
				if(payLock.get()) {
					Tools.showToast(v.getContext(),
						ResourceTable.getLocaleString(v.getContext(),ResourceTable.string.STR_PAYMENT_DIALOG_TOAST_PAYLOCK_TXT));
					return ;
				}
				payLock.set(true);
				AlipayHelper.startPurchase(mInterface.purchaseInfo, mCenter.getRuntimeEnv(), mInterface.mHandler);
			}
		});
		initItemInfo(mInterface.purchaseInfo);
	}

	//called from paymentIntercase.
	protected void startPaymentFace() {
		supportThird = true;
		initializePaymentFace();
		mainLayout.setVisibility(View.VISIBLE);
		waitLayout.setVisibility(View.GONE);
	}

	//close it safely.
	protected void safeDismiss() {
		safeClose = true;
		this.dismiss();
	}

	@Override
	protected void onStop() {
		//Tools.LOG("Game was end");
		imageCache.clear();
		imageCache = null;
		if(!safeClose) {
			mCenter.notifyMessage(Notification.PAY, ResultCode.PAY_CANCEL, "");
		}
		mCenter = null;
		mInterface.mDialog = null;
		mInterface = null;
		super.onStop();
	}
}
