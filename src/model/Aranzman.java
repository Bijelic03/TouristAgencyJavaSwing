package model;

public class Aranzman {

private int id;
private long turistickiAgentId;
private TipoviAranzmana tipAranzmana;
private TipoviSmestaja tipSmestaja;
private String dostupanDatum;
private int kapacitet;
private double cenaPoDanuPoOsobi;
private int sajamskiPopust;
private String putanjaDoSlike;

public Aranzman(int id, long turistickiAgentId, TipoviAranzmana tipAranzmana, TipoviSmestaja tipSmestaja,
		String dostupanDatum, int kapacitet, double cenaPoDanuPoOsobi, int sajamskiPopust, String putanjaDoSlike) {
	super();
	this.id = id;
	this.turistickiAgentId = turistickiAgentId;
	this.tipAranzmana = tipAranzmana;
	this.tipSmestaja = tipSmestaja;
	this.dostupanDatum = dostupanDatum;
	this.kapacitet = kapacitet;
	this.cenaPoDanuPoOsobi = cenaPoDanuPoOsobi;
	this.sajamskiPopust = sajamskiPopust;
	this.putanjaDoSlike = putanjaDoSlike;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public long getTuristickiAgentId() {
	return turistickiAgentId;
}
public void setTuristickiAgentId(long turistickiAgentId) {
	this.turistickiAgentId = turistickiAgentId;
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
public String getDostupanDatum() {
	return dostupanDatum;
}
public void setDostupanDatum(String dostupanDatum) {
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
}
