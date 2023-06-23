package org.example;
import com.sun.source.tree.Tree;

import java.util.*;
public class Main {
	public static void main(String[] args) {
		Comparator<Warrior> alt = null;
		boolean doSort;

		if(args[0] == null) {
			System.out.println("Sorting: None.");
			doSort = false;
		}
		if (args[0].equals("Natural")) {
			System.out.println("Sorting: Natural.");
			doSort = true;
		}
		else if (args[0].equals("Alternative")) {
			System.out.println("Sorting: Alternative.");
			alt = new Comparator<Warrior>() {
				@Override
				public int compare(Warrior o1, Warrior o2) {
					if (o1.getStrength() > o2.getStrength()) return 1;
					else if (o1.getStrength() < o2.getStrength()) return -1;

					int result = o1.getName().compareTo(o2.getName());
					if (result != 0) return result;

					if (o1.getHealth() > o2.getHealth()) return 1;
					else if (o1.getHealth() < o2.getHealth()) return 1;
					return 0;
				}
			};
			doSort = true;
		}
		else {
			System.out.println("Sorting: None.");
			doSort = false;
		}
		System.out.println("");

		Warrior master = new Warrior("Greg", 2221, 284, doSort, alt);
		Warrior a = new Warrior("A", 1500, 222, doSort, alt, master);
		Warrior b = new Warrior("B", 1300, 225, doSort, alt, master);
		Warrior c = new Warrior("Anatoly", 1750, 170, doSort, alt, master);
		Warrior a1 = new Warrior("z", 900, 120, doSort, alt, a);
		Warrior a2 = new Warrior("y", 950, 150, doSort, alt, a);
		Warrior a3 = new Warrior("x", 800, 155, doSort, alt, a);
		Warrior b1 = new Warrior("pp", 600, 175, doSort, alt, b);
		Warrior b2 = new Warrior("qq", 700, 180, doSort, alt, b);
		Warrior b3 = new Warrior("rr", 600, 155, doSort, alt, b);
		Warrior c1 = new Warrior("Hans Thomas", 1500, 90, doSort, alt, c);
		Warrior c11 = new Warrior("Hans Thomas Jr.", 1450, 75, doSort, alt, c1);

		System.out.println(master.toString());

		ArrayList<Warrior> list = new ArrayList<Warrior>();
		list.add(master);
		list.add(a);
		list.add(b);
		list.add(c);
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(b1);
		list.add(b2);
		list.add(b3);
		list.add(c1);
		list.add(c11);

		Map<Warrior, Integer> hierarchy = Warrior.getHierarchy(list, doSort, alt);

		for (var item : hierarchy.entrySet()) {
			System.out.println(item.getKey().getName() + ": " + item.getValue().toString());
		}


	}
}