package restaurante;

import java.util.concurrent.locks.ReentrantLock;

public class Main {

	public static void main(String[] args) {
		Boolean[] cluteries = { true, true, true, true, true, true, true };
		ReentrantLock latch = new ReentrantLock();

		Table table = new Table(cluteries);
		Waiter waiter = new Waiter(table);
		Customer c1 = new Customer(table, latch, "C0");
		Customer c2 = new Customer(table, latch, "C1");
		Customer c3 = new Customer(table, latch, "C2");
		Customer c4 = new Customer(table, latch, "C3");
		Customer c5 = new Customer(table, latch, "C4");
		Customer c6 = new Customer(table, latch, "C5");
		Customer c7 = new Customer(table, latch, "C6");

		waiter.start();
		c1.start();
		c2.start();
		c3.start();
		c4.start();
		c5.start();
		c6.start();
		c7.start();
	}

}
