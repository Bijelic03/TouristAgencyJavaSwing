package model;

public class TuristickaAgencija {

	private long id;
	private String ime;
	private String adresa;
	private String brojTelefona;

	
	public TuristickaAgencija(long id, String ime, String adresa, String brojTelefona) {
		super();
		this.id = id;
		this.ime = ime;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
	}

	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

}
