package ac.at.tuwien.s2015.wmpm.g13.provider.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public final class IDProvider {

	public static String generateID() {
		String id = "";

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
		id = sdf.format(cal.getTime());

		Random random = new Random();
		id = id + random.nextInt(999999999);

		return id;
	}
}
