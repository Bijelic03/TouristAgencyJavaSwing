package model;

import java.time.LocalDate;

public class Rezervacija {

	public Rezervacija(long id, long turistaId, int brojPutnika, double cena, LocalDate datum,
			StatusRezervacije statusRezervacije) {
		super();
		this.id = id;
		this.turistaId = turistaId;
		this.brojPutnika = brojPutnika;
		this.cena = cena;
		this.datum = datum;
		this.statusRezervacije = statusRezervacije;
	}

	private long id;
	private long turistaId;
	private int brojPutnika;
	private double cena;
	private LocalDate datum;
	private StatusRezervacije statusRezervacije;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getTuristaId() {
		return turistaId;
	}

	public void setTuristaId(long turistaId) {
		this.turistaId = turistaId;
	}

	public int getBrojPutnika() {
		return brojPutnika;
	}

	public void setBrojPutnika(int brojPutnika) {
		this.brojPutnika = brojPutnika;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public LocalDate getDatum() {
		return datum;
	}

	public void setDatum(LocalDate datum) {
		this.datum = datum;
	}

	public StatusRezervacije getStatusRezervacije() {
		return statusRezervacije;
	}

	public void setStatusRezervacije(StatusRezervacije statusRezervacije) {
		this.statusRezervacije = statusRezervacije;
	}

}
