package daemon;

public class SleepMessages {

	public static void main(String[] args) 
		throws InterruptedException {
		String[] importantInfo = {
			"dave eats oats",
			"tano eats oat groats",
			"steven eats MIT"
		};
		
		for(String info : importantInfo) {
			try {
				Thread.sleep(4000);
			} catch(InterruptedException ex) {
				throw new InterruptedException();
			}
			System.out.println(info);
		}
	}

}
