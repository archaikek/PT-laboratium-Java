package org.example;

import java.util.Optional;

public interface IMageRepository {
	public void erase(String key) throws IllegalArgumentException;
	public Optional<Mage> find(String key);
	public void insert(Mage mage) throws IllegalArgumentException;
}
