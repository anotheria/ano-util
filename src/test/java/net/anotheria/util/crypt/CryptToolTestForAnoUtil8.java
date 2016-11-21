package net.anotheria.util.crypt;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 03/10/2016 12:59
 */
public class CryptToolTestForAnoUtil8 {

	private static final String KEY = "ABCDEF12345678900987654321FEDCBA";
	private static final String C1 = "95754CC3E288E7975B668B8282FADA1C2D452A63031EBC60FD741A65821AEC02";
	private static final String C2 = "42FEAC655B93A8B502510E18A7E2327D44FEB9214D2306DC";
	private static final String F1 = "AllTheIWantIsYou-ou-ou-ou-ou";
	private static final String F2 = "Thereissomemagicintheair";

	//executes a single test for correctness.
	@Test
	public void testSingle(){
		CryptTool tool = new CryptTool(KEY);

		assertEquals(C1, tool.encryptToHex(F1));
		assertEquals(C2, tool.encryptToHex(F2));

		assertEquals(F1, tool.decryptFromHexTrim(C1));
		assertEquals(F2, tool.decryptFromHexTrim(C2));

	}


	@Test
	public void testConcurrently(){
		int THREADS = 10;
		CryptTool tool = new CryptTool(KEY);
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch finish = new CountDownLatch(THREADS);
		AtomicLong errorCounter = new AtomicLong(0);
		for (int i = 0; i<THREADS; i++){
			if (i%2==1)
				new TestWorker(tool, C1, F1, start, finish, errorCounter).start();
			else
				new TestWorker(tool, C2, F2, start, finish, errorCounter).start();
		}
		start.countDown();
		try {
			finish.await();
		}catch(Exception any){
			any.printStackTrace();
		}
		assertEquals(0, errorCounter.get());
	}

	class TestWorker extends Thread {
		/**
		 * Chiffre
		 */
		private String C;
		/**
		 * Expected result
		 */
		private String R;

		private CountDownLatch start, finish;

		int mismatch = 0;

		private AtomicLong errorCounter;

		CryptTool cryptTool;


		TestWorker(CryptTool tool, String aC, String aR, CountDownLatch start, CountDownLatch finish, AtomicLong errorCounter){
			cryptTool = tool;
			C = aC;
			R = aR;
			this.start = start;
			this.finish = finish;
			this.errorCounter = errorCounter;
		}

		public void run(){
			try{
				start.await();
				for (int i=0; i<100000; i++){
					try {
						String r = cryptTool.decryptFromHexTrim(C);
						if (!r.equals(R)) {
							mismatch++;
							errorCounter.incrementAndGet();
						}
					}catch(Exception any){
						//any.printStackTrace();
						mismatch++;
						errorCounter.incrementAndGet();
					}
				}
				//System.out.println("Finished testrun with "+mismatch+" mismatches");
				finish.countDown();
			}catch(InterruptedException e){}
		}

	}
}
