package restaurante;

import java.util.concurrent.locks.ReentrantLock;

public class Customer extends Thread {
	Table table;
	ReentrantLock latch;
	String name;

	public Customer(Table table, ReentrantLock latch, String name) {
		this.table = table;
		this.latch = latch;
		this.name = name;
	}

	public void run() {
		try {
			latch.lock();
			table.chairs.add(name);
			System.out.println("The customer " + name + " has sat down.");
			sleep(500);
			synchronized (table) {
				if (table.chairs.size() == 7) {
					table.notify();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			latch.unlock();
		}

		try {
			synchronized (table) {
				table.wait();
				takeClutery();
			}
		} catch (Exception e) {

		}
	}

	public void takeClutery() {
		try {
			int seatNumber = table.chairs.indexOf(name);
			Boolean hasCustomerEaten = false;
			if (seatNumber != 6) {
				while (!hasCustomerEaten) {
					if (table.cluteries[seatNumber] && table.cluteries[seatNumber + 1]) {
						table.cluteries[seatNumber] = false;
						table.cluteries[seatNumber + 1] = false;
						System.out.println("The customer " + name + " is eating.");
						System.out.println("The customer " + name + " has finished eating.");
						table.cluteries[seatNumber] = true;
						table.cluteries[seatNumber + 1] = true;
						hasCustomerEaten = true;
					}
				}
			}
		} catch (Exception e) {

		}
	}
}
