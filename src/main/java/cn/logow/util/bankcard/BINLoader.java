package cn.logow.util.bankcard;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import cn.logow.util.bankcard.BIN.Type;

/**
 * 加载卡表数据
 * 
 * @author logow5
 * @version 2016年10月7日
 * @since 1.6
 */
class BINLoader {

	static final String BOM = "BIN";
	static final BINStore singleton;
	
	static {
		try {
			String key = loadKey();
			Properties banks = loadBanks();
			List<BIN> bins = loadBin(key, banks);
			singleton = new BINStore(bins);
		} catch (IOException e) {
			throw new IllegalStateException("Could not load bins: " + e.getMessage());
		}
	}
	
	static String loadKey() throws IOException {
		byte[] key = new byte[8];
		InputStream in = BINLoader.class.getResourceAsStream(".key");
		try {
			in.read(key);
		} finally {
			in.close();
		}
		return new String(key);
	}
	
	private static Properties loadBanks() throws IOException {
		InputStream in = BINLoader.class.getResourceAsStream("/banks.properties");
		InputStreamReader reader = new InputStreamReader(in, "UTF-8");
		Properties banks = new Properties();
		try {
			banks.load(reader);
		} finally {
			reader.close();
		}
		return banks;
	}
	
	static List<BIN> loadBin(String key, Properties banks) throws IOException {
		List<BIN> bins = new ArrayList<BIN>(4096);
		InputStream in = BINLoader.class.getResourceAsStream(".bin");
		try {
			byte[] data = new byte[40 * 1024];
			int len = in.read(data);
			decrypt(data, 0, len, key);
			String bom = new String(data, 0, 3);
			if (!BOM.equals(bom)) {
				throw new IOException("invalid data format");
			}
			
			ByteArrayInputStream bais = new ByteArrayInputStream(data, 3, len);
			GZIPInputStream gzip = new GZIPInputStream(bais);
			BufferedReader br = new BufferedReader(new InputStreamReader(gzip, "UTF-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				BIN bin = decode(line, banks);
				if (bin != null) {
					bins.add(bin);
				}
			}
		} finally {
			in.close();
		}
		
		return bins;
	}
	
	private static BIN decode(String line, Properties banks) {
		String[] tokens = line.split(",");
		String issuer = banks.getProperty(tokens[0]);
		if (issuer == null) {
			return null;
		}
		
		String name = tokens.length > 4 ? tokens[4] : null;
		int length = Integer.parseInt(tokens[1]);
		
		return new BIN(tokens[2], issuer, Type.fromCode(tokens[3]), name, length);
	}
	
	private static void decrypt(byte[] data, int offset, int len, String key) {
		byte[] keys = key.getBytes();
		for (int i = offset; i < len; i++) {
			data[i] = (byte) (data[i] ^ keys[i % 8]);
		}
	}
}
