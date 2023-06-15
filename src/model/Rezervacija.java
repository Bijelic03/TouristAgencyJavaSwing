package model;

import java.time.LocalDate;

public class Rezervacija {

	private long id;

	private Osoba turista;

	private Aranzman aranzman;
	private int brojPutnika;

	private double cena;

	private LocalDate datumkreiranja;

	private int brojDana;

	private StatusRezervacije statusRezervacije;

	public Rezervacija(long id, Osoba turista, Aranzman aranzman, int brojPutnika, double cena,
			LocalDate datumkreiranja, int brojDana, StatusRezervacije statusRezervacije) {
		super();
		this.id = id;
		this.turista = turista;
		this.aranzman = aranzman;
		this.brojPutnika = brojPutnika;
		this.cena = cena;
		this.brojDana = brojDana;
		this.datumkreiranja = datumkreiranja;
		this.statusRezervacije = statusRezervacije;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Osoba getTurista() {
		return turista;
	}

	public void setTurista(Osoba turista) {
		this.turista = turista;
	}

	public Aranzman getAranzman() {
		return aranzman;
	}

	public void setAranzman(Aranzman aranzman) {
		this.aranzman = aranzman;
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

	public int getBrojDana() {
		return brojDana;
	}

	public void setBrojDana(int brojDana) {
		this.brojDana = brojDana;
	}

	public LocalDate getDatumkreiranja() {
		return datumkreiranja;
	}

	public void setDatumkreiranja(LocalDate datumkreiranja) {
		this.datumkreiranja = datumkreiranja;
	}

	public StatusRezervacije getStatusRezervacije() {
		return statusRezervacije;
	}

	public void setStatusRezervacije(StatusRezervacije statusRezervacije) {
		this.statusRezervacije = statusRezervacije;
	}

}
