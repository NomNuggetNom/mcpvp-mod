package us.mcpvpmod.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MCPVPMath {

	/**
	 * Code by Jonik of stackoverflow via http://stackoverflow.com/a/2808648
	 */

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

}
