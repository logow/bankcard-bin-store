package cn.logow.util.bankcard;

/**
 * Luhn计算校验数字的步骤如下：<p>
 * 1、从右边第1个数字（逆序）开始每隔一位乘以2。<p>
 * 2、把上一步中的乘积的各位数字之和（即乘积减9）与原号码中未乘2的各位数字相加，得到校验和。<p>
 * 3、用校验和除以10得到余数，若余数为零，则校验数字就是零，否则为10减去余数。<p>
 *
 * 注意：测试发现，17位卡号的交通银行卡未使用Luhn算法。
 *
 * @author logow5
 * @version 2016年10月7日
 * @since 1.6
 */
public abstract class Luhn {

	/**
	 * 验证校验数字
	 * @param accountNo 含校验数字的的账号
	 * @return true表示验证成功
	 */
	public static boolean verify(String accountNo) {
		int last = accountNo.length() - 1;
		int checksum = checksum(accountNo, 0, last);
		checksum += (accountNo.charAt(last) - '0');
		return checksum % 10 == 0;
	}

	/**
	 * 计算校验数字
	 * @param num 不含校验数字的号码
	 * @return 校验数字
	 */
	public static int checkDigit(CharSequence num) {
		int checksum = checksum(num, 0, num.length());
		int rmd = checksum % 10;
		return rmd > 0 ? 10 - rmd : rmd;
	}

	/**计算校验和*/
	private static int checksum(CharSequence num, int start, int end) {
		int checksum = 0;
		int i = end;
		while (--i >= start) {
			int dbl = (num.charAt(i) - '0') * 2;
			if (dbl > 9) {
				dbl -= 9;
			}
			checksum += dbl;
			if (--i >= start) {
				checksum += (num.charAt(i) - '0');
			}
		}
		return checksum;
	}
}
