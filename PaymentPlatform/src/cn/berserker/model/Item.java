package cn.berserker.model;

public interface Item {
	// item's id.
	public int getItemId();

	// item's name
	public String getItemSubject();

	//item's thumb.
	public String getImageRes();

	//item's price.
	public int getAmount();

	//item's description
	public String getDescription();

	//paycode from China MM.
	public String getMMPayCode();

	//currencycode. CNY.
	public String getCurrencyCode();
}
