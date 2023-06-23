package org.example;

import java.util.Collection;

public interface IMageRepositoryInjector {
	public MageController getController();
	public MageController getController(Collection<Mage> collection);
}
