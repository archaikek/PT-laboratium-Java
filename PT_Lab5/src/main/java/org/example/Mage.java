package org.example;

import java.util.Objects;

public class Mage {
	private String name;
	private int level;
	public Mage(String name, int level) {
		this.name = name;
		this.level = level;
	}
	public Mage(String name) {
		this.name = name;
		this.level = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Mage mage)) return false;
		return Objects.equals(name, mage.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
