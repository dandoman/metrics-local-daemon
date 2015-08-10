package daemon;

public class SimpleThreads {

	// Display a message, preceded by
	// the name of the current thread
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

	private static class MessageLoop implements Runnable {
		public void run() {
			String[] importantInfo = { 
					"dave eats oats",
					"tano eats oat groats", 
					"steven eats MIT" 
			};
			try {
				for (String info : importantInfo) {
					Thread.sleep(4000);
					threadMessage(info);
				}
			} catch (InterruptedException ex) {
				threadMessage("I wasn't duuunnnsss!");
			}
		}

	}

	public static void main(String[] args)
		throws InterruptedException {
		long patience = 1000 * 60; // 1 minute
		threadMessage("Waiting for MessageLoop thread to finish!");
		long startTime = System.currentTimeMillis();
		Thread t = new Thread(new MessageLoop());
		t.start();
		
		//loop until message loop thread exits
		while(t.isAlive()) {
			threadMessage("Still waiting...");
			//wait maximum of 1 second 
			//for message loop thread to finish
			t.join(1000);
			if((System.currentTimeMillis() - startTime) > patience &&
			   t.isAlive()) {
				threadMessage("Tired of waiting!");
				t.interrupt();
				t.join();
			}
		}
		threadMessage("Finally!");
	}

}
