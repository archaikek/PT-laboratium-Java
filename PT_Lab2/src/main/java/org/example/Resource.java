package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class Resource {
	private Queue<Integer> numbers;
	public Resource() {
		numbers = new LinkedList<Integer>();
	}
	public synchronized int pop() {
		while(numbers.isEmpty()) {
			try {
				wait();
			}
			catch (InterruptedException ex) {
				System.out.println("Interrupted!");
				return -1;
			}
		}
		return numbers.remove();
	}
	public synchronized void push(int x) {
		boolean success = numbers.add(x);
		notifyAll();
	}
	public synchronized void clear() {
		numbers.clear();
	}
}
