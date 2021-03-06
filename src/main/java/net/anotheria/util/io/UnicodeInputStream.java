package net.anotheria.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/**
 * This inputstream will recognize unicode BOM marks
 * and will skip bytes if getEncoding() method is called
 * before any of the read(...) methods.
 *
 * Usage pattern:
 *     String enc = "ISO-8859-1"; // or NULL to use systemdefault
 *     FileInputStream fis = new FileInputStream(file);
 *     UnicodeInputStream uin = new UnicodeInputStream(fis, enc);
 *     enc = uin.getEncoding(); // check and skip possible BOM bytes
 *     InputStreamReader in;
 *     if (enc == null) in = new InputStreamReader(uin);
 *     else in = new InputStreamReader(uin, enc);
 *
 * @author another
 * @version $Id: $Id
 */
public class UnicodeInputStream extends InputStream {
   PushbackInputStream internalIn;
   boolean             isInited = false;
	String              defaultEnc;
	String              encoding;

	private static final int BOM_SIZE = 4;

	UnicodeInputStream(InputStream aIn, String aDefaultEnc) {
		internalIn = new PushbackInputStream(aIn, BOM_SIZE);
		this.defaultEnc = aDefaultEnc;
	}

	/**
	 * <p>getDefaultEncoding.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getDefaultEncoding() {
      return defaultEnc;
   }

   /**
    * <p>Getter for the field <code>encoding</code>.</p>
    *
    * @return a {@link java.lang.String} object.
    */
   public String getEncoding() {
      if (!isInited) {
         try {
            init();
         } catch (IOException ex) {
            throw new IllegalStateException("Init method failed.", ex);
         }
      }
      return encoding;
   }

   /**
    * Read-ahead four bytes and check for BOM marks. Extra bytes are
    * unread back to the stream, only BOM bytes are skipped.
    *
    * @throws java.io.IOException if any.
    */
   protected void init() throws IOException {
      if (isInited) return;

      byte[] bom = new byte[BOM_SIZE];
       int n = internalIn.read(bom, 0, bom.length);

       int unread;
       if ( (bom[0] == (byte)0x00) && (bom[1] == (byte)0x00) &&
                  (bom[2] == (byte)0xFE) && (bom[3] == (byte)0xFF) ) {
         encoding = "UTF-32BE";
         unread = n - 4;
      } else if ( (bom[0] == (byte)0xFF) && (bom[1] == (byte)0xFE) &&
                  (bom[2] == (byte)0x00) && (bom[3] == (byte)0x00) ) {
         encoding = "UTF-32LE";
         unread = n - 4;
      } else if (  (bom[0] == (byte)0xEF) && (bom[1] == (byte)0xBB) &&
            (bom[2] == (byte)0xBF) ) {
         encoding = "UTF-8";
         unread = n - 3;
      } else if ( (bom[0] == (byte)0xFE) && (bom[1] == (byte)0xFF) ) {
         encoding = "UTF-16BE";
         unread = n - 2;
      } else if ( (bom[0] == (byte)0xFF) && (bom[1] == (byte)0xFE) ) {
         encoding = "UTF-16LE";
         unread = n - 2;
      } else {
         // Unicode BOM mark not found, unread all bytes
         encoding = defaultEnc;
         unread = n;
      }      
      //System.out.println("read=" + n + ", unread=" + unread);

      if (unread > 0) internalIn.unread(bom, (n - unread), unread);

      isInited = true;
   }

   /** {@inheritDoc} */
   @Override
   public void close() throws IOException {
      //init();
      isInited = true;
      internalIn.close();
   }

   /** {@inheritDoc} */
   @Override
   public int read() throws IOException {
      //init();
      isInited = true;
      return internalIn.read();
   }
}
