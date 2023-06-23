package org.example;

import java.util.Collection;
import java.util.HashSet;

public class MageRepositoryInjector implements IMageRepositoryInjector {
	@Override
	public MageController getController() {
		return new MageController(new MageRepository());
	}
	@Override
	public MageController getController(Collection<Mage> collection) {
		return new MageController(new MageRepository(collection));
	}
}
