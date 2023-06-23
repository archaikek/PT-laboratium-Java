package org.example;

import jakarta.persistence.*;

import java.io.Serializable;

@NamedQueries({
		@NamedQuery(name = "Piwo.findAll", query = "SELECT m FROM Piwo m"),
		@NamedQuery(name = "Piwo.find", query = "SELECT m FROM Piwo m WHERE m.name LIKE :name"),
		@NamedQuery(name = "Piwo.delete", query = "DELETE FROM Piwo m WHERE m.name LIKE :name"),
		@NamedQuery(name = "Piwo.cheap", query = "SELECT m FROM Piwo m WHERE m.cena < :cena")
})
@Entity
@Table(name = "Piwo")
public class Piwo implements Serializable {
	@Id
	private String name;
	private int cena;
	@ManyToOne
	private Browar browar;
	public Piwo(String name, int cena, Browar browar) {
		this.name = name;
		this.cena = cena;
		this.browar = browar;
	}
	public Piwo() {
		this.name = "";
		this.cena = 0;
		this.browar = null;
	}

	@Override
	public String toString() {
		return "Name: " + name + ", Cena: " + cena + ", Browar: " + browar.getName();
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCena() {
		return cena;
	}
	public void setCena(int cena) {
		this.cena = cena;
	}
	public Browar getBrowar() {
		return browar;
	}
	public void setBrowar(Browar browar) {
		this.browar = browar;
	}
}
