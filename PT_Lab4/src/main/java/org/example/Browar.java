package org.example;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;
@NamedQueries({
		@NamedQuery(name = "Browar.findAll", query = "SELECT t FROM Browar t"),
		@NamedQuery(name = "Browar.find", query = "SELECT t FROM Browar t WHERE t.name LIKE :name"),
		@NamedQuery(name = "Browar.delete", query = "DELETE FROM Browar t WHERE t.name LIKE :name"),
		@NamedQuery(name = "Browar.expensive", query = "SELECT p FROM Piwo p WHERE p.cena > :cena AND p.browar.name LIKE :name")
})
@Entity
@Table(name = "Browar")
public class Browar implements Serializable {
	@Id
	private String name;
	private int wartosc;
	@OneToMany(mappedBy = "browar", cascade = CascadeType.REMOVE)
	private List<Piwo> piwos;
	public Browar(String name, int wartosc) {
		this.name = name;
		this.wartosc = wartosc;
		piwos = new ArrayList<Piwo>();
	}
	public Browar(String name, int wartosc, List<Piwo> piwos) {
		this.name = name;
		this.wartosc = wartosc;
		this.piwos = piwos;
	}
	public Browar() {
		this.name = "";
		this.wartosc = 0;
		piwos = new ArrayList<Piwo>();
	}

	@Override
	public String toString() {
		String result = "Name: " + name + ", Wartosc: " + wartosc + ", Piwos:";
		for (Piwo piwo : piwos) {
			result += "\n\t" + piwo.toString();
		}
		return result;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWartosc() {
		return wartosc;
	}
	public void setWartosc(int wartosc) {
		this.wartosc = wartosc;
	}
	public List<Piwo> getPiwos() {
		return piwos;
	}
	public void setPiwos(List<Piwo> piwos) {
		this.piwos = piwos;
	}
}
