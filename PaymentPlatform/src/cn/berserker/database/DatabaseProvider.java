package cn.berserker.database;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Date;

import cn.berserker.model.PurchaseInfo;
import cn.berserker.model.Item;
import cn.berserker.utils.Tools;

public class DatabaseProvider {
	private static final String DATABASE_NAME = "database_provider";
	static final int DATABASE_VERSION = 1;

	DatabaseHelper mHelper = null;

	public DatabaseProvider(Context context) {
		mHelper = new DatabaseHelper(context.getApplicationContext());
	}

	//load report purchase info.
	public ArrayList<PurchaseInfo> loadReportPurchaseInfo() {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ArrayList<PurchaseInfo> list = mHelper.loadReportPurchaseInfo(db);
		return list;
	}
	//after reportOrder,it says wait, paymentCenter need check it time to time.
	public void saveReportPurchaseInfo(PurchaseInfo purchaseInfo) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		mHelper.updateReportPurchaseInfo(db, purchaseInfo,DatabaseHelper.ADD_REPORT_PURCHASE);
	}

	//When real order result come out, just tell client.
	public void updateReportPurchaseInfo(PurchaseInfo purchaseInfo) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		mHelper.updateReportPurchaseInfo(db, purchaseInfo, DatabaseHelper.UPDATE_REPORT_PURCHASE);
	}

	//after told client, just delete the order. DON'T CARE WHETHER CLIENT HAD CALL reportPayedOrder OR ????.
	public void delReportPurchaseInfo(PurchaseInfo purchaseInfo) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		mHelper.updateReportPurchaseInfo(db, purchaseInfo, DatabaseHelper.DEL_REPORT_PURCHASE);
	}


	static class DatabaseHelper extends SQLiteOpenHelper {
		static final String TABLE_INTERCEPT = "SmsIntercept";
		static final String TABLE_INTERCEPT_COLUMN_ID = "_id";
		static final String TABLE_INTERCEPT_COLUMN_PORTNUM = "portNum";
		static final String TABLE_INTERCEPT_COLUMN_CONTENT = "content";
		static final String TABLE_INTERCEPT_COLUMN_STARTTIME = "startTime";
		static final String TABLE_INTERCEPT_COLUMN_ENDTIME = "endTime";
		static final String TABLE_INTERCEPT_COLUMN_COUNT = "count";

		static final String TABLE_SMS = "SendSMS";
		static final String TABLE_SMS_COLUMN_ID = "_id";
		static final String TABLE_SMS_COLUMN_PORTNUM = "portNum";
		static final String TABLE_SMS_COLUMN_CONTENT = "content";
		static final String TABLE_SMS_COLUMN_COUNT = "count";
		static final String TABLE_SMS_COLUMN_GAP = "gap";

		static final String TABLE_PURCHASE = "ReportPurchaseInfo";
		static final String TABLE_PURCHASE_COLUMN_ID = "_id";
		static final String TABLE_PURCHASE_COLUMN_PURCHASE_ID = "purchaseId";
		static final String TABLE_PURCHASE_COLUMN_ITEM_ID = "itemId";
		static final String TABLE_PURCHASE_COLUMN_ITEM_NAME = "itemName";
		static final String TABLE_PURCHASE_COLUMN_PURCHASE_TYPE = "purchaseType";
		static final String TABLE_PURCHASE_COLUMN_ROLE_ID = "roleId";
		static final String TABLE_PURCHASE_COLUMN_ROLE_NAME = "roleName";
		static final String TABLE_PURCHASE_COLUMN_ZONE_ID = "zoneId";
		static final String TABLE_PURCHASE_COLUMN_ZONE_NAME = "zoneName";
		static final String TABLE_PURCHASE_COLUMN_MAP_ID = "mapId";
		static final String TABLE_PURCHASE_COLUMN_LEVEL_NUM = "levelNum";
		static final String TABLE_PURCHASE_COLUMN_AMOUNT = "amount";
		static final String TABLE_PURCHASE_COLUMN_TIME = "dealtime";
		//static final String TABLE_PURCHASE_COLUMN_CURRENCY_CODE = "currencyCode";
		static final String TABLE_PURCHASE_COLUMN_PURCHASE_STATUS = "purchaseStatus";

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_INTERCEPT + "("+
					TABLE_INTERCEPT_COLUMN_ID + " INTEGER PRIMARY KEY," +
					TABLE_INTERCEPT_COLUMN_PORTNUM + " TEXT," +
					TABLE_INTERCEPT_COLUMN_CONTENT + " TEXT," +
					TABLE_INTERCEPT_COLUMN_STARTTIME + " TEXT," +
					TABLE_INTERCEPT_COLUMN_ENDTIME + " TEXT," +
					TABLE_INTERCEPT_COLUMN_COUNT + " INTEGER " +
					");" 
					);

			db.execSQL(
					"CREATE TABLE " + TABLE_SMS + "(" + 
					TABLE_SMS_COLUMN_ID + " INTEGER PRIMARY KEY," +
					TABLE_SMS_COLUMN_PORTNUM + " TEXT," +
					TABLE_SMS_COLUMN_CONTENT + " TEXT," + 
					TABLE_SMS_COLUMN_COUNT + " INTEGER," +
					TABLE_SMS_COLUMN_GAP + " INTEGER" +
					");"
					);
			db.execSQL(
					"CREATE TABLE " + TABLE_PURCHASE + "(" +
					TABLE_PURCHASE_COLUMN_ID + " INTEGER PRIMARY KEY," +
					TABLE_PURCHASE_COLUMN_PURCHASE_ID + " TEXT," +
					TABLE_PURCHASE_COLUMN_ITEM_ID + " INTEGER," +
					TABLE_PURCHASE_COLUMN_ITEM_NAME + " TEXT," +
					TABLE_PURCHASE_COLUMN_PURCHASE_TYPE + " INTEGER," +
					TABLE_PURCHASE_COLUMN_ROLE_ID + " INTEGER," +
					TABLE_PURCHASE_COLUMN_ROLE_NAME + " TEXT," +
					TABLE_PURCHASE_COLUMN_ZONE_ID + " INTEGER," + 
					TABLE_PURCHASE_COLUMN_ZONE_NAME + " TEXT," +
					TABLE_PURCHASE_COLUMN_MAP_ID + " INTEGER," +
					TABLE_PURCHASE_COLUMN_LEVEL_NUM + " INTEGER," +
					TABLE_PURCHASE_COLUMN_AMOUNT + " INTEGER," +
					TABLE_PURCHASE_COLUMN_TIME + " INT8," +
					//TABLE_PURCHASE_COLUMN_CURRENCY_CODE + "  TEXT," +
					TABLE_PURCHASE_COLUMN_PURCHASE_STATUS + " INTEGER" +
					");"
					);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}


		public static final int ADD_REPORT_PURCHASE = 0;
		public static final int UPDATE_REPORT_PURCHASE = 1;
		public static final int DEL_REPORT_PURCHASE = 2;
		
		void updateReportPurchaseInfo(SQLiteDatabase db, PurchaseInfo purchaseInfo, int operator) {
			if(operator == ADD_REPORT_PURCHASE ) {
				ContentValues values = new ContentValues();
				values.put(TABLE_PURCHASE_COLUMN_PURCHASE_ID,purchaseInfo.getPurchaseId());
				values.put(TABLE_PURCHASE_COLUMN_ITEM_ID,purchaseInfo.getItemId());
				values.put(TABLE_PURCHASE_COLUMN_ITEM_NAME,purchaseInfo.getItemSubject());
				values.put(TABLE_PURCHASE_COLUMN_PURCHASE_TYPE,purchaseInfo.getPurchaseType());
				values.put(TABLE_PURCHASE_COLUMN_AMOUNT,purchaseInfo.getAmount());
				values.put(TABLE_PURCHASE_COLUMN_TIME, purchaseInfo.getDealTime());
				//values.put(TABLE_PURCHASE_COLUMN_CURRENCY_CODE, "cny");
				values.put(TABLE_PURCHASE_COLUMN_PURCHASE_STATUS,purchaseInfo.getStatus());
				db.insert(TABLE_PURCHASE,null,values);
				
			}
			else if(operator == UPDATE_REPORT_PURCHASE) {
				ContentValues values =  new ContentValues();
				values.put(TABLE_PURCHASE_COLUMN_PURCHASE_STATUS, purchaseInfo.getStatus());
				db.update(TABLE_PURCHASE, values, TABLE_PURCHASE_COLUMN_PURCHASE_ID + " = ? ", new String[]{ purchaseInfo.getPurchaseId()});

			}
			else if(operator == DEL_REPORT_PURCHASE) {
				db.delete(TABLE_PURCHASE,TABLE_PURCHASE_COLUMN_PURCHASE_ID +  " = ? ",new String[]{ purchaseInfo.getPurchaseId()});

			}
		}

		ArrayList<PurchaseInfo> loadReportPurchaseInfo(SQLiteDatabase db) {
			ArrayList<PurchaseInfo> list = null;
			Cursor cursor = db.rawQuery("select * from " + TABLE_PURCHASE,null);
			if(null != cursor && cursor.getCount() > 0) {
				list = new ArrayList<PurchaseInfo>();
				while(cursor.moveToNext()) {
					final String purchaseId = cursor.getString(cursor.getColumnIndex(TABLE_PURCHASE_COLUMN_PURCHASE_ID));
					final int itemId = cursor.getInt(cursor.getColumnIndex(TABLE_PURCHASE_COLUMN_ITEM_ID));
					final String itemSubject = cursor.getString(cursor.getColumnIndex(TABLE_PURCHASE_COLUMN_ITEM_NAME));
					final int purchaseType = cursor.getInt(cursor.getColumnIndex(TABLE_PURCHASE_COLUMN_PURCHASE_TYPE));
					final int totalFee = cursor.getInt(cursor.getColumnIndex(TABLE_PURCHASE_COLUMN_AMOUNT));
					final long dealTime = cursor.getLong(cursor.getColumnIndex(TABLE_PURCHASE_COLUMN_TIME));
					//final String currencyCode = cursor.getString(cursor.getColumnIndex(TABLE_PURCHASE_COLUMN_CURRENCY_CODE));
					final int purchaseStatus = cursor.getInt(cursor.getColumnIndex(TABLE_PURCHASE_COLUMN_PURCHASE_STATUS));
					
					PurchaseInfo info = new PurchaseInfo(new Item() { 
						@Override
						public int getItemId() {
							return itemId;
						}

						@Override
						public String getItemSubject() {
							return itemSubject;
						}
					
						@Override
						public String getImageRes() {
							return null;
						}
					

						@Override
						public int getAmount() {
							return totalFee;
						}

						@Override
						public String getDescription() {
							return "";
						}

						@Override
						public String getMMPayCode() {
							return null;
						}

						@Override
						public String getCurrencyCode() {
							return "cny";
//							return currencyCode;
						}
					});
					info.setPurchaseId(purchaseId);
					info.setPurchaseType(purchaseType);
					info.setDealTime(dealTime);
					info.setStatus(purchaseStatus);

					list.add(info);
				}

				cursor.close();
				cursor = null;
		//		SDKUtils.SystemLog("reportPurchaseInfo:" + list.size());
			}
			return list;
		}
	}

	//do not forget to close database.
	public void close() {
		if( null != mHelper) {
			mHelper.close();
			mHelper = null;
		}
	}
}
