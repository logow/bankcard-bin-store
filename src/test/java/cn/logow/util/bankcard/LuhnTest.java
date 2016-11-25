package cn.logow.util.bankcard;

import org.junit.Test;

public class LuhnTest {

	@Test
	public void testVerify() {
		String[] nums = {
				"62122ab311004036786",
				"6228abcdefg8753163870",
				"6227001e731210340834",
				"6217715710097502",
				"49927398716",
				"40551240315836300",
				"60142850252647013",
				"6230520860000239371"
		};
		for (int i = 0; i < nums.length; i++) {
			long t1 = System.nanoTime();
			boolean result = Luhn.verify(nums[i]);
			long t2 = System.nanoTime();
			System.out.println(result + ":" + (t2 - t1));
		}
	}
	
	@Test
	public void testCheckDigit() {
		String[] nums = {
				"621226131100403678",
				"622848229875316387",
				"622700173121034083",
				"621771570109750",
				"6230520860000239371"
		};
		for (int i = 0; i < nums.length; i++) {
			long t1 = System.nanoTime();
			Luhn.checkDigit(nums[i]);
			long t2 = System.nanoTime();
			System.out.println(t2 - t1);
		}
	}
}
