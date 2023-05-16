package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import model.Osoba;
import model.Uloga;

public class UpravljanjeKorisnicima {
	private static ArrayList<Osoba> Osobe;

	public UpravljanjeKorisnicima() {
		Osobe = new ArrayList<Osoba>();

	}

	public void ucitajKorisnike() {
		try {
			File osobeFile = new File("podaci/osobe.txt");
			try (BufferedReader reader = new BufferedReader(new FileReader(osobeFile))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] lineSplit = line.split("\\|");
					String id = lineSplit[0];
					long idLong = Long.parseLong(id);
					String ime = lineSplit[1];
					String prezime = lineSplit[2];
					String brojTelefona = lineSplit[3];
					String jmbg = lineSplit[4];
					String pol = lineSplit[5];
					boolean polBoolean = Boolean.parseBoolean(pol);
					String adresa = lineSplit[6];
					String username = lineSplit[7];
					String sifra = lineSplit[8];
					String ulogaStr = lineSplit[9];
					Uloga uloga = Uloga.valueOf(ulogaStr);
					String aktivnost = lineSplit[9];
					boolean aktivnostBoolean = Boolean.parseBoolean(aktivnost);
					Osoba Osoba = new Osoba(idLong, ime, prezime, brojTelefona, jmbg, polBoolean, adresa, username,
							sifra, uloga, aktivnostBoolean);
					this.Osobe.add(Osoba);
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Greska prilikom ucitavanja datoteke: " + e.getMessage());
		}
	}

	public static Osoba Login(String korisnickoIme, String sifra) {
		for (Osoba osoba : Osobe) {
			if (osoba.getUsername().equals(korisnickoIme) && osoba.getPassword().equals(sifra)) {
				return osoba;
			}
		}
		return null;

	}

	public static ArrayList<Osoba> getKorisnici() {
		return Osobe;
	}
	

	
	public void setKorisnici(ArrayList<Osoba> korisnici) {
		this.Osobe = korisnici;
	}
	
	
	public static String[][] getPodaciOKorisnicima() {
	    String[][] podaci = new String[Osobe.size()][11];
	    
	    for (int i = 0; i < Osobe.size(); i++) {
	        Osoba osoba = Osobe.get(i);
	        podaci[i][0] = osoba.getId() + "";
	        podaci[i][1] = osoba.getIme();
	        podaci[i][2] = osoba.getPrezime();
	        podaci[i][3] = osoba.getBrojTelefona();
	        podaci[i][4] = osoba.getJmbg();
	        if (osoba.getPol()) {
		        podaci[i][5] = "Musko";
	        }
	        else {
		        podaci[i][5] = "Zensko";

	        }
	        podaci[i][6] = osoba.getAdresa();
	        podaci[i][7] = osoba.getUsername();
	        podaci[i][8] = osoba.getPassword();
	        podaci[i][9] = osoba.getUloga().toString();
	        if(osoba.getAktivnost()) {
		        podaci[i][10] = "Aktivan";
	        }
	        else {
		        podaci[i][10] = "Neaktivan";
	        }
	    }
	    
	    return podaci;
	}
	
	
	public void dodajKorisnika(Osoba osoba) {
        System.out.println(osoba.getIme());

	    try {
	        File osobeFile = new File("podaci/osobe.txt");
	        BufferedWriter writer = new BufferedWriter(new FileWriter(osobeFile, true));
	        writer.write("\n" + 
	        			 osoba.getId() + "|" +
	                     osoba.getIme() + "|" +
	                     osoba.getPrezime() + "|" +
	                     osoba.getBrojTelefona() + "|" +
	                     osoba.getJmbg() + "|" +
	                     osoba.getPol() + "|" +
	                     osoba.getAdresa() + "|" +
	                     osoba.getUsername() + "|" +
	                     osoba.getPassword() + "|" +
	                     osoba.getUloga() + "|" +
	                     osoba.getAktivnost());
	        writer.newLine();
	        writer.close();
	        Osobe.add(osoba);
	        String[][] podaci = getPodaciOKorisnicima();
	    } catch (IOException e) {
	        System.out.println("Greska prilikom upisivanja korisnika u datoteku: " + e.getMessage());
	    }
	}
	
	
	
}