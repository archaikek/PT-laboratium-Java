package org.example;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class MageRepository implements IMageRepository{
	private Collection<Mage> collection;
	public MageRepository() {
		this.collection = new HashSet<>();
	}
	public MageRepository(Collection<Mage> collection) {
		this.collection = collection;
	}

	@Override
	public void erase(String name) throws IllegalArgumentException {
		Mage mage = new Mage(name, 0);
		if (!collection.contains(mage)) {
			throw new IllegalArgumentException("No such item in the repository!");
		}
		collection.remove(mage);
	}
	@Override
	public Optional<Mage> find(String name) {
		Mage mage = new Mage(name);
		if (!collection.contains(mage)) {
			return Optional.empty();
		}
		return Optional.of(mage);
	}
	@Override
	public void insert(Mage mage) throws IllegalArgumentException {
		if (collection.contains(mage)) {
			throw new IllegalArgumentException("Item already in the repository!");
		}
		collection.add(mage);
	}
}
