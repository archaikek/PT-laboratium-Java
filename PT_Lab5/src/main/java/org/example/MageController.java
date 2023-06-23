package org.example;

import java.util.Optional;

public class MageController implements IMageController {
	private MageRepository repository;
	public MageController(MageRepository repository) {
		this.repository = repository;
	}

	@Override
	public String erase(String key) {
		try {
			repository.erase(key);
		}
		catch (IllegalArgumentException ex) {
			return "not found";
		}
		return "done";
	}

	@Override
	public String find(String key) {
		Optional<Mage> item = repository.find(key);
		if (item.equals(Optional.empty())) {
			return "not found";
		}
		return item.get().getName();
	}

	@Override
	public String insert(String name, int level) {
		Mage mage = new Mage(name, level);
		try {
			repository.insert(mage);
		}
		catch (IllegalArgumentException ex) {
			return "bad request";
		}
		return "done";
	}
}
