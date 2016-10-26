package cn.logow.util.bankcard;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 卡表数据库，可查询卡表信息
 * 
 * @author logow5
 * @version 2016年10月7日
 * @since 1.6
 */
public class BINStore {

	private BIN[] store;
	
	BINStore(List<BIN> bins) {
		if (bins == null) {
			throw new IllegalArgumentException("bins must not be null");
		}
		
		store = bins.toArray(new BIN[bins.size()]);
		Arrays.sort(store, new Comparator<BIN>() {
			public int compare(BIN o1, BIN o2) {
				int cmp = o1.getLength() - o2.getLength();
				if (cmp == 0) {
					return o1.getId().compareTo(o2.getId());
				} else {
					return cmp;
				}
			}
		});
	}
	
	public static BINStore getInstance() {
		return BINLoader.singleton;
	}
	
	public BIN lookup(String cardNo) {
		if (cardNo == null) {
			throw new IllegalArgumentException("cardNo must not be null");
		}
		if (cardNo.length() < 14 || cardNo.length() > 19) {
			throw new IllegalArgumentException("cardNo length must between 14 and 19");
		}
		return lookup(cardNo, cardNo.length());
	}
	
	public BIN lookup(String key, int len) {
		if (key == null) {
			return null;
		}
		
		int idx = -(binarySearch(key, len) + 1);
		if (idx == 0) {
			return null;
		}
		
		BIN bin = store[idx - 1];
		if (key.startsWith(bin.getId())) {
			return bin;
		}
		
		if (idx > 1) {//Fix: 长卡BIN以短卡BIN开头会导致匹配失败
			bin = store[idx - 2];
			if (key.startsWith(bin.getId())) {
				return bin;
			}
		}
		
		return null;
	}
	
	
	private int binarySearch(String key, int len) {
		int low = 0;
        int high = store.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
			BIN bin = store[mid];
			int cmp = bin.getLength() - len;
			if (cmp == 0) {
            	cmp = bin.getId().compareTo(key);
			}
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // key found
            }
        }
        return -(low + 1);  // key not found
	}
}
