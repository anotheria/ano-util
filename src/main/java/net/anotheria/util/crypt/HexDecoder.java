package net.anotheria.util.crypt;

/**
 * <p>HexDecoder class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public final class HexDecoder {

	/**
	 * Converts a byte to hex digit and writes to the supplied buffer
	 *
	 * @param b a byte.
	 * @param buf a {@link java.lang.StringBuilder} object.
	 */
	public static void byte2hex(byte b, StringBuilder buf) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		int high = ((b & 0xf0) >> 4);

		buf.append(hexChars[high]);
		int low = (b & 0x0f);
		buf.append(hexChars[low]);
	}

	/**
	 * Converts a byte array to hex string
	 *
	 * @param block a byte.
	 * @return a {@link java.lang.String} object.
	 */
	public static String toHexString(byte... block) {
		StringBuilder buf = new StringBuilder();

        for (byte aBlock : block) {
            byte2hex(aBlock, buf);
        }
		return buf.toString();
	}

	/**
	 * Converts a hex string to a byte array
	 *
	 * @param hex a {@link java.lang.String} object.
	 * @return an array of byte.
	 */
	public static byte[] fromHexString(String hex) {
		if (hex == null) {
			return null;
		}
		if ((hex.length() % 2) != 0) {
			throw new IllegalArgumentException(
					"Hex String length must be a multiple of 2.");
		}

		int length = hex.length() / 2;
		byte[] result = new byte[length];
		String hits = "0123456789ABCDEF";
		String h = hex.toUpperCase();

		for (int i = 0; i < length; i++) {
			char c = h.charAt(2 * i);
			int index = hits.indexOf(c);

			if (index == -1) {
				throw new IllegalArgumentException("Hex String can't contain '"
						+ c + '\'');
			}

			int j = 16 * index;

			c = h.charAt((2 * i) + 1);
			index = hits.indexOf(c);
			if (index == -1) {
				throw new IllegalArgumentException("Hex String can't contain '"
						+ c + '\'');
			}

			j += index; 

			result[i] = (byte) (j & 0xFF);
		}

		return result;
	}

	//prevent invocation
	private HexDecoder(){}
}
