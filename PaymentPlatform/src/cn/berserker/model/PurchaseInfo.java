package cn.berserker.model;

public class PurchaseInfo {
	//orderid
	String purchaseId;
	//item's id.
	String itemId;
	//item's name
	String itemSubject;
	//item's price.
	int itemAmount;
	//item's description.
	String itemDesc;
	//order type.
	int purchaseType = -1;
	//mm's paycode.
	String mmCode;
	String itemRes;
	long dealTime;
	//use to indicate the status of current order.
	int status = -1;

	public PurchaseInfo(Item item) {
		itemId = (item.getItemId() + "");
		itemSubject = item.getItemSubject();
		itemRes = item.getImageRes();
		mmCode = item.getMMPayCode();
		itemDesc = item.getDescription();
		itemAmount = item.getAmount();
	}
	
	public String getPurchaseId() { return purchaseId; } 
	public String getItemId() { return itemId; } 
	public String getItemSubject() { return itemSubject; } 
	public int getAmount() { return itemAmount; } 
	public String getItemDesc() { return itemDesc; } 
	public int getPurchaseType() { return purchaseType; } 
	public String getMMPayCode() { return mmCode; } 
	public String getItemRes() { return itemRes; } 
	public long getDealTime() { return dealTime; } 
	public int getStatus() { return status; } 

	public void setPurchaseId(String orderId) { purchaseId = orderId; } 
	public void setPurchaseType(int type) { purchaseType = type; } 
	public void setDealTime(long time) { dealTime = time; } 
	public void setStatus(int s) { status = s; }

	//When system report was end. told user. This means order was not synchronize.
	//Need to call orderstatusquery.
	public static final int REPORT_PAY = 0;
	//When now told user ,if user do not response this, just retell him next time.
	public static final int REPORT_USER = 1;
	//When now User has report, now just reportPayedPurchase
	public static final int REPORT_SYS = 2;


	//purchase type
	public static final int TYPE_YIDONG_MM = 601;
	public static final int TYPE_YIDONG_JD = 602;
	public static final int TYPE_ALI = 1;
	public static final int TYPE_TEN = 2;
	public static final int TYPE_QCK = 3;
	public static final int TYPE_UNI = 5;
}
