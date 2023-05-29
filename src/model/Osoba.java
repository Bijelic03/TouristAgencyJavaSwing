package model;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Osoba {
	
	private long id;
	
	private String ime;
	
	private String prezime;
	
	private String brojTelefona;
	
	private String jmbg;
	
	private Boolean pol;
	
	private String adresa;
	
	private String username;
	
	private String password;
	
	private Boolean aktivnost;
	
	private Uloga uloga;
	
	public Osoba() {
		// TODO Auto-generated constructor stub
	}
	
	public Osoba(long id, String ime, String prezime, String brojTelefona, String jmbg, Boolean pol, String adresa,
			String username, String password, Uloga uloga, Boolean aktivnost) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.brojTelefona = brojTelefona;
		this.jmbg = jmbg;
		this.pol = pol;
		this.adresa = adresa;
		this.username = username;
		this.password = password;
		this.uloga = uloga;
		this.aktivnost = aktivnost;
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

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getBrojTelefona() {
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public Boolean getPol() {
		return pol;
	}

	public void setPol(Boolean pol) {
		this.pol = pol;
	}

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	public Boolean getAktivnost() {
		return aktivnost;
	}

	public void setAktivnost(Boolean aktivnost) {
		this.aktivnost = aktivnost;
	}

}