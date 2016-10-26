package cn.logow.util.bankcard;

import java.io.IOException;

import org.junit.Test;

public class BINStoreTest {

	@Test
	public void testLoad() throws IOException {
		long t1 = System.currentTimeMillis();
		BINStore.getInstance();
		long t2 = System.currentTimeMillis();
		System.out.println((t2 - t1));
	}
	
	@Test
	public void testLookup() {
		BINStore bs = BINStore.getInstance();
		String[] nums = {
				"6212261311004036786", //工商
				"6228482298753163870",//农业
				"6227001731210340834", //建设
				"6217715701097502", //中信
				"9559981090975553819",
				"28823892983829838"
		};
		
		for (int i = 0; i < nums.length; i++) {
			long t1 = System.nanoTime();
			BIN bin = bs.lookup(nums[i]);
			long t2 = System.nanoTime();
			System.out.println(nums[i] + ":" + (t2 - t1) + ":" + bin);
		}
	}
}
