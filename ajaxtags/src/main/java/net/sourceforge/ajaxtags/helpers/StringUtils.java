package net.sourceforge.ajaxtags.helpers;

public final class StringUtils {

	private StringUtils() {
	}

	public static String trim2Null(String str) {
		if (str != null && str.trim().length() > 0) {
			return str;
		}
		return null;
	}

}
