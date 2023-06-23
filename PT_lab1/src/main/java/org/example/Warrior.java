package org.example;
import java.util.*;

public class Warrior implements Comparable<Warrior>, Comparator<Warrior>
{
	private String name;
	private int health;
	private int strength;
	private Set<Warrior> apprentices;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public Set<Warrior> getApprentices() {
		return apprentices;
	}

	public void setApprentices(Set<Warrior> apprentices) {
		this.apprentices = apprentices;
	}

	public Warrior(String _name, int _health, int _strength, boolean doSort, Comparator<Warrior> comparator) {
		name = _name;
		health = _health;
		strength = _strength;
		if (!doSort) {
			apprentices = new HashSet<Warrior>();
		}
		if (comparator == null) {
			apprentices = new TreeSet<Warrior>();
		}
		else {
			apprentices = new TreeSet<Warrior>(comparator);
		}
	}
	public Warrior(String _name, int _health, int _strength, boolean doSort, Comparator<Warrior> comparator, Warrior master) {
		name = _name;
		health = _health;
		strength = _strength;
		if (!doSort) {
			apprentices = new HashSet<Warrior>();
		}
		if (comparator == null) {
			apprentices = new TreeSet<Warrior>();
		}
		else {
			apprentices = new TreeSet<Warrior>(comparator);
		}
		master.addApprentice(this);
	}

	public boolean addApprentice(Warrior apprentice) {
		return apprentices.add(apprentice);
	}
	public boolean removeApprentice(Warrior apprentice) {
		return apprentices.remove(apprentice);
	}
	public int apprenticeCount() {
		if (apprentices == null || apprentices.isEmpty()) {
			return 0;
		}
		int result = 0;
		for (Warrior apprentice : apprentices) {
			result += 1 + apprentice.apprenticeCount();
		}
		return result;
	}
	@Override
	public int compareTo(Warrior other) {
		int result = name.compareTo(other.name);
		if (result != 0) return result;

		if (strength > other.strength) return 1;
		else if (strength < other.strength) return -1;

		if (health > other.health) return 1;
		else if (health < other.health) return 1;
		return 0;
	}

	@Override
	public int compare(Warrior o1, Warrior o2) {
		return o1.compareTo(o2);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Warrior warrior)) return false;
		return health == warrior.health && strength == warrior.strength && name.equals(warrior.name) && Objects.equals(apprentices, warrior.apprentices);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, health, strength, apprentices);
	}

	@Override
	public String toString() {
		return toString("");
	}
	private String toString(String padding) {
		String result = padding +
				"Warrior: " +
				"name='" + name + '\'' +
				", health=" + health +
				", strength=" + strength +
				", apprentices: ";
		if (apprentices != null && !apprentices.isEmpty()) {
			for (Warrior apprentice : apprentices) {
				result += "\n" + apprentice.toString(padding + "|\t");
			}
		}
		else {
			result += "None";
		}
		return result;
	}

	public static Map<Warrior, Integer> getHierarchy(List<Warrior> list, boolean doSort, Comparator<Warrior> comparator) {
		Map<Warrior, Integer> result;
		if (!doSort) {
			result = new HashMap<Warrior, Integer>();
		}
		if (comparator == null) {
			result = new TreeMap<Warrior, Integer>();
		}
		else {
			result = new TreeMap<Warrior, Integer>(comparator);
		}
		for(Warrior warrior : list) {
			result.put(warrior, warrior.apprenticeCount());
		}
		return result;
	}
}
