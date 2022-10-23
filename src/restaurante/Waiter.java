package restaurante;

public class Waiter extends Thread {
	Table table;

	public Waiter(Table table) {
		this.table = table;
	}

	public synchronized void run() {
		try {
			synchronized (table) {
				table.wait();
				System.out.println("The table is full");
				for (int i = 0; i < table.chairs.size(); i++) {
					System.out.println("The customer " + table.chairs.get(i) + " was served.");
				}
				table.notifyAll();
			}
		} catch (Exception e) {

		}
	}
}
