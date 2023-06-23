package org.example;
import jakarta.persistence.*;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		// Create entity manager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("guildPu");
		EntityManager manager = factory.createEntityManager();

		// Data for the database
		Browar eternalCore 		= new Browar("Eternal Core",		9);
		Browar viciousLabyrinth 	= new Browar("Vicious Labyrinth",	5);
		Browar luminousSky 		= new Browar("Luminous Sky",		5);
		Piwo hikari 			= new Piwo("Fatalis Hikari",		294, luminousSky);
		Piwo lagrange 			= new Piwo("Lagrange",			77, luminousSky);
		Piwo eto 				= new Piwo("Eto",					48, luminousSky);
		Piwo tairitsu 			= new Piwo("Tempest Tairitsu",	131, viciousLabyrinth);
		Piwo lethe 				= new Piwo("Apophenia Lethe",		72, viciousLabyrinth);
		Piwo saya 				= new Piwo("Saya",				68, viciousLabyrinth);
		Piwo ilith 				= new Piwo("Ilith",				71, eternalCore);
		Piwo penguin 			= new Piwo("Chuni Penguin",		33, eternalCore);
		Piwo nemesis 			= new Piwo("Pandora Nemesis",		65, eternalCore);
		Piwo kanae 				= new Piwo("Kanae",				75, eternalCore);
		LinkedList<Browar> browars = new LinkedList<Browar>();
		browars.add(eternalCore);
		browars.add(viciousLabyrinth);
		browars.add(luminousSky);
		LinkedList<Piwo> piwos = new LinkedList<Piwo>();
		piwos.add(hikari);
		piwos.add(lagrange);
		piwos.add(eto);
		piwos.add(tairitsu);
		piwos.add(lethe);
		piwos.add(saya);
		piwos.add(ilith);
		piwos.add(penguin);
		piwos.add(nemesis);
		piwos.add(kanae);

		// Insert data into the database
		for (Browar browar : browars) {
			persistBrowar(manager, browar);
		}

		for (Piwo piwo : piwos) {
			persistPiwo(manager, piwo);
		}

		printDatabase(manager);
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("[EXIT/INSERT/DELETE/CHEAP/EXPENSIVE] [Piwo/Browar] Name [Next parameters if INSERT]");
			String query = sc.nextLine();
			if (query.equalsIgnoreCase("exit")) {
				break;
			}
			if (query.equalsIgnoreCase("insert")) {
				String className = sc.nextLine();
				String name = sc.nextLine();
				if (className.equalsIgnoreCase("piwo")) {
					int cena = Integer.parseInt(sc.nextLine());
					String browarName = sc.nextLine();
					Query query1 = manager.createNamedQuery("Browar.find")
							.setParameter("name", browarName);
					Browar browar = (Browar)query1.getSingleResult();
					if (browar == null) {
						System.out.println("No such browar!");
					}
					else {
						Piwo piwo = new Piwo(name, cena, browar);
						persistPiwo(manager, piwo);
					}
				}
				else { // Browar
					int wartosc = Integer.parseInt(sc.nextLine());
					Browar browar = new Browar(name, wartosc);
					persistBrowar(manager, browar);
				}
				printDatabase(manager);
			}
			else if (query.equalsIgnoreCase("delete")) { // DELETE
				String className = sc.nextLine();
				String name = sc.nextLine();
				if (className.equalsIgnoreCase("piwo")) {
					Query query1 = manager.createNamedQuery("Piwo.find")
							.setParameter("name", name);
					Piwo piwo = (Piwo)query1.getSingleResult();
					if (piwo != null) {
						removePiwo(manager, piwo);
					}
				}
				else { // browar
					Query query1 = manager.createNamedQuery("Browar.find")
							.setParameter("name", name);
					Browar browar = (Browar)query1.getSingleResult();
					if (browar != null) {
						removeBrowar(manager, browar);
					}
				}
				printDatabase(manager);
			}
			else if (query.equalsIgnoreCase("cheap")) {
				int cena = Integer.parseInt(sc.nextLine());
				Query cheap = manager.createNamedQuery("Piwo.cheap")
						.setParameter("cena", cena);
				ArrayList<Piwo> results = (ArrayList<Piwo>)cheap.getResultList();
				for (Piwo piwo : results) {
					System.out.println(piwo.toString());
				}
			}
			else { // expensive
				int cena = Integer.parseInt(sc.nextLine());
				String browarName = sc.nextLine();
				Query expensive = manager.createNamedQuery("Browar.expensive")
						.setParameter("cena", cena)
						.setParameter("name", browarName);
				ArrayList<Piwo> results = (ArrayList<Piwo>)expensive.getResultList();
				for (Piwo piwo : results) {
					System.out.println(piwo.toString());
				}
			}
		}


//		// Print all the contents
//		System.out.println("\n\n---------- Data insertion");
//		printDatabase(manager);
//
//		// Data deletion demo
//		System.out.println("\n\n---------- Data removal");
//
//		System.out.println("Removing Saya");
//		removePiwo(manager, saya);
//		printDatabase(manager);
//		System.out.println("\n\n");
//
//		System.out.println("Removing Nemesis");
//		removePiwo(manager, nemesis);
//		printDatabase(manager);
//		System.out.println("\n\n");
//
//		System.out.println("Removing Eternal Core");
//		removeBrowar(manager, eternalCore);
//		printDatabase(manager);
//		System.out.println("\n\n");

		manager.close();
		factory.close();
	}
	private static void persistBrowar(EntityManager manager, Browar browar) {
		manager.getTransaction().begin();
		manager.persist(browar);
		manager.getTransaction().commit();
	}
	private static void removeBrowar(EntityManager manager, Browar browar) {
		manager.getTransaction().begin();
		try {
			for (Piwo piwo : browar.getPiwos()) {
				manager.remove(piwo);
			}
			manager.remove(browar);
		}
		catch (IllegalArgumentException ex) {
			System.out.println("Failed to remove the browar from the database! Details\n" + ex.toString());
			manager.getTransaction().rollback();
			return;
		}
		manager.getTransaction().commit();
		browar.getPiwos().clear();
	}
	private static void persistPiwo(EntityManager manager, Piwo piwo) {
		Browar browar = piwo.getBrowar();
		manager.getTransaction().begin();
		try {
			manager.persist(piwo);
			if (!browar.getPiwos().contains(piwo)) {
				browar.getPiwos().add(piwo);
				manager.refresh(browar);
			}
		}
		catch (IllegalStateException ex) {
			System.out.println("Failed to add the piwo to the database! Details\n" + ex.toString());
			manager.getTransaction().rollback();
			return;
		}
		manager.getTransaction().commit();
	}
	private static void removePiwo(EntityManager manager, Piwo piwo) {
		Browar browar = piwo.getBrowar();
		manager.getTransaction().begin();
		try {
			if (browar.getPiwos().contains(piwo)) {
				browar.getPiwos().remove(piwo);
				manager.refresh(browar);
			}
			manager.remove(piwo);
		}
		catch (IllegalArgumentException ex) {
			System.out.println("Failed to remove the piwo from the database! Details\n" + ex.toString());
			manager.getTransaction().rollback();
			return;
		}
		manager.getTransaction().commit();
	}

	private static void printDatabase(EntityManager manager) {
		Query piwoQuery = manager.createNamedQuery("Piwo.findAll");
		Query browarQuery = manager.createNamedQuery("Browar.findAll");

		ArrayList<Piwo> piwoResults = (ArrayList<Piwo>)piwoQuery.getResultList();
		ArrayList<Browar> browarResults = (ArrayList<Browar>)browarQuery.getResultList();

		System.out.println("\tPiwos:");
		for (Piwo piwo : piwoResults) {
			System.out.println(piwo.toString());
		}
		System.out.println("\tBrowars:");
		for (Browar browar : browarResults) {
			System.out.println(browar.toString());
		}
	}
}