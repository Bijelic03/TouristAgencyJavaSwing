package model;

import java.time.LocalDate;
import java.util.Date;

public class Aranzman {

	private long id;

	private Osoba turistickiAgent;

	private TipoviAranzmana tipAranzmana;

	private TipoviSmestaja tipSmestaja;

	private LocalDate dostupanDatum;

	private int kapacitet;

	private double cenaPoDanuPoOsobi;

	private int sajamskiPopust;

	private String putanjaDoSlike;
	
	private boolean aktivnost;

	public Aranzman(long id, Osoba turistickiAgent, TipoviAranzmana tipAranzmana, TipoviSmestaja tipSmestaja,
			LocalDate dostupanDatum, int kapacitet, double cenaPoDanuPoOsobi, int sajamskiPopust,
			String putanjaDoSlike, boolean aktivnost) {
		super();
		this.id = id;
		this.turistickiAgent = turistickiAgent;
		this.tipAranzmana = tipAranzmana;
		this.tipSmestaja = tipSmestaja;
		this.dostupanDatum = dostupanDatum;
		this.kapacitet = kapacitet;
		this.cenaPoDanuPoOsobi = cenaPoDanuPoOsobi;
		this.sajamskiPopust = sajamskiPopust;
		this.putanjaDoSlike = putanjaDoSlike;
		this.aktivnost = aktivnost;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Osoba getTuristickiAgent() {
		return turistickiAgent;
	}

	public void setTuristickiAgent(Osoba turistickiAgent) {
		this.turistickiAgent = turistickiAgent;
	}

	public TipoviAranzmana getTipAranzmana() {
		return tipAranzmana;
	}

	public void setTipAranzmana(TipoviAranzmana tipAranzmana) {
		this.tipAranzmana = tipAranzmana;
	}

	public TipoviSmestaja getTipSmestaja() {
		return tipSmestaja;
	}

	public void setTipSmestaja(TipoviSmestaja tipSmestaja) {
		this.tipSmestaja = tipSmestaja;
	}

	public LocalDate getDostupanDatum() {
		return dostupanDatum;
	}

	public void setDostupanDatum(LocalDate dostupanDatum) {
		this.dostupanDatum = dostupanDatum;
	}

	public int getKapacitet() {
		return kapacitet;
	}

	public void setKapacitet(int kapacitet) {
		this.kapacitet = kapacitet;
	}

	public double getCenaPoDanuPoOsobi() {
		return cenaPoDanuPoOsobi;
	}

	public void setCenaPoDanuPoOsobi(double cenaPoDanuPoOsobi) {
		this.cenaPoDanuPoOsobi = cenaPoDanuPoOsobi;
	}

	public int getSajamskiPopust() {
		return sajamskiPopust;
	}

	public void setSajamskiPopust(int sajamskiPopust) {
		this.sajamskiPopust = sajamskiPopust;
	}

	public String getPutanjaDoSlike() {
		return putanjaDoSlike;
	}

	public void setPutanjaDoSlike(String putanjaDoSlike) {
		this.putanjaDoSlike = putanjaDoSlike;
	}

	public boolean getAktivnost() {
		return aktivnost;
	}

	public void setAktivnost(boolean aktivnost) {
		this.aktivnost = aktivnost;
	}
}
