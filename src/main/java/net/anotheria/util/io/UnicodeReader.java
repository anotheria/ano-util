package net.anotheria.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.nio.charset.Charset;

/**
 * Generic unicode textreader, which will use BOM mark to identify the encoding
 * to be used. If BOM is not found then use a given default or system encoding.
 */
public class UnicodeReader extends Reader {
	PushbackInputStream internalIn;
	InputStreamReader internalIn2 = null;
	// String defaultEnc;
	private Charset charset;

	private static final int BOM_SIZE = 4;

	/**
	 * 
	 * @param in
	 *            inputstream to be read
	 */
	public UnicodeReader(InputStream in) {
		this(in, Charset.defaultCharset());
	}

	public UnicodeReader(InputStream in, String charsetName) {
		this(in, Charset.forName(charsetName));
	}

	public UnicodeReader(InputStream aIn, Charset aCharset) {
		internalIn = new PushbackInputStream(aIn, BOM_SIZE);
		this.charset = aCharset;
	}

	// public String getDefaultEncoding() {
	// return defaultEnc;
	// }

	/**
	 * Get stream encoding or NULL if stream is uninitialized. Call init() or
	 * read() method to initialize it.
	 */
	public String getEncoding() {
		if (internalIn2 == null)
			return null;
		return internalIn2.getEncoding();
	}

	/**
	 * Read-ahead four bytes and check for BOM marks. Extra bytes are unread
	 * back to the stream, only BOM bytes are skipped.
	 */
	protected void init() throws IOException {
		if (internalIn2 != null)
			return;

        byte[] bom = new byte[BOM_SIZE];
        int n = internalIn.read(bom, 0, bom.length);

        int unread;
        Charset preciseCharset;
        if ((bom[0] == (byte) 0x00) && (bom[1] == (byte) 0x00) && (bom[2] == (byte) 0xFE) && (bom[3] == (byte) 0xFF)) {
			preciseCharset = Charset.forName("UTF-32BE");
			unread = n - 4;
		} else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE) && (bom[2] == (byte) 0x00) && (bom[3] == (byte) 0x00)) {
			preciseCharset = Charset.forName("UTF-32LE");
			unread = n - 4;
		} else if ((bom[0] == (byte) 0xEF) && (bom[1] == (byte) 0xBB) && (bom[2] == (byte) 0xBF)) {
			preciseCharset = Charset.forName("UTF-8");
			unread = n - 3;
		} else if ((bom[0] == (byte) 0xFE) && (bom[1] == (byte) 0xFF)) {
			preciseCharset = Charset.forName("UTF-16BE");
			unread = n - 2;
		} else if ((bom[0] == (byte) 0xFF) && (bom[1] == (byte) 0xFE)) {
			preciseCharset = Charset.forName("UTF-16LE");
			unread = n - 2;
		} else {
			// Unicode BOM mark not found, unread all bytes
			preciseCharset = charset;
			unread = n;
		}
		// System.out.println("read=" + n + ", unread=" + unread);

		if (unread > 0)
			internalIn.unread(bom, (n - unread), unread);

		// Use given encoding
		if (preciseCharset == null) {
			internalIn2 = new InputStreamReader(internalIn);
		} else {
			internalIn2 = new InputStreamReader(internalIn, preciseCharset);
		}
	}

	@Override
	public void close() throws IOException {
		init();
		internalIn2.close();
	}

	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		init();
		return internalIn2.read(cbuf, off, len);
	}

}