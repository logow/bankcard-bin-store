package cn.logow.util.bankcard;

/**
 * 卡表信息pojo
 * 
 * @author logow5
 * @version 2016年10月7日
 * @since 1.6
 */
public class BIN {
	
	private final int length; //卡号长度
	private final String id; //发卡行标识
	private final String issuer; //发卡行名称
	private final String name; //卡名
	private final Type type; //卡种
	
	BIN(String id, String issuer, Type type, String name, int length) {
		this.id = id;
		this.issuer = issuer;
		this.type = type;
		this.name = name;
		this.length = length;
	}

	public String getId() {
		return id;
	}

	public String getIssuer() {
		return issuer;
	}

	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}
	
	public int getLength() {
		return length;
	}
	
	/**
	 * 银行卡种类
	 */
	public enum Type {
		
		UNKOWN("N", "未知卡"),
		DEBIT("D", "借记卡"),
		CREDIT("C", "贷记卡"),
		SEMI_CREDIT("S", "准贷记卡"),
		PREPAID("P", "预付费卡");
		
		private String code;
		private String text;
		
		Type(String code, String text) {
			this.code = code;
			this.text = text;
		}
		
		public String getCode() {
			return code;
		}

		@Override
		public String toString() {
			return text;
		}
		
		public static Type fromCode(String code) {
			for (Type ct : values()) {
				if (ct.code.equals(code)) {
					return ct;
				}
			}
			return UNKOWN;
		}
		
		public static Type fromName(String name) {
			for (Type ct : values()) {
				if (ct.text.equals(name)) {
					return ct;
				}
			}
			return UNKOWN;
		}
	}

	@Override
	public String toString() {
		return "BIN[id=" + id + ", issuer=" + issuer + ", type=" + type
				+ ", name=" + name + ", length=" + length + "]";
	}
}
