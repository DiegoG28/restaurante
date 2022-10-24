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
			}
			takeClutery();
		} catch (Exception e) {

		}
	}

	public void takeClutery() throws InterruptedException {
		int seatNumber = table.chairs.indexOf(name);
		Boolean hasCustomerEaten = false;
		while (!hasCustomerEaten) {
			// Si el cliente está en la primera posición
			if (seatNumber == 0) {
				if (table.cluteries[0] && table.cluteries[1]) {
					table.cluteries[0] = false;
					table.cluteries[1] = false;
					System.out
							.println("The clutery 0 and 1 was taken by the customer "
									+ name + ". Position: " + seatNumber);
					eat();
					hasCustomerEaten = true;
				} else {
					sleep(500);
				}
				// Si el cliente está en la última posición
			} else if (seatNumber == 6) {
				if (table.cluteries[6] && table.cluteries[0]) {
					table.cluteries[6] = false;
					table.cluteries[0] = false;
					System.out
							.println("The clutery 6 and 0 was taken by the customer " + name + ". Position: " + seatNumber);
					eat();
					hasCustomerEaten = true;
				} else {
					sleep(500);
				}
				// Si el cliente está en la posición 1-5
			} else {
				if (table.cluteries[seatNumber] && table.cluteries[seatNumber + 1]) {
					table.cluteries[seatNumber] = false;
					table.cluteries[seatNumber + 1] = false;
					System.out.println("The clutery " + seatNumber + " and "
							+ (seatNumber + 1) + " was taken by the customer "
							+ name + ". Position: " + seatNumber);
					eat();
					hasCustomerEaten = true;
				} else {
					sleep(500);
				}
			}
		}
	}

	public void eat() throws InterruptedException {
		int seatNumber = table.chairs.indexOf(name);
		System.out.println("The customer " + name + " is eating.");
		sleep(5000);
		System.out.println("The customer " + name + " has finished eating.");
		table.cluteries[seatNumber] = true;
		table.cluteries[seatNumber + 1] = true;
	}
}
