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
					Osoba Osoba = new Osoba(idLong, ime, prezime, brojTelefona, jmbg, polBoolean, adresa, username,
							sifra, uloga);
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

	public ArrayList<Osoba> getKorisnici() {
		return Osobe;
	}

	public void setKorisnici(ArrayList<Osoba> korisnici) {
		this.Osobe = korisnici;
	}
}