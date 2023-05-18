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
					String aktivnost = lineSplit[10];
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
			} else {
				podaci[i][5] = "Zensko";

			}
			podaci[i][6] = osoba.getAdresa();
			podaci[i][7] = osoba.getUsername();
			podaci[i][8] = osoba.getPassword();
			podaci[i][9] = osoba.getUloga().toString();
			podaci[i][10] = osoba.getAktivnost().toString();

		}

		return podaci;
	}
	
	public static String[][] getPodaciOKorisnicimaTabela() {
	    int aktivneOsobe = 0;

	    for (Osoba osoba : Osobe) {
	        if (osoba.getAktivnost()) {
	        	aktivneOsobe++;
	        }
	    }
	    String[][] podaci = new String[aktivneOsobe][10];
	    int indeks = 0;

	    for (Osoba osoba : Osobe) {
	        if (osoba.getAktivnost()) {
	            podaci[indeks][0] = osoba.getId() + "";
	            podaci[indeks][1] = osoba.getIme();
	            podaci[indeks][2] = osoba.getPrezime();
	            podaci[indeks][3] = osoba.getBrojTelefona();
	            podaci[indeks][4] = osoba.getJmbg();
	            if (osoba.getPol()) {
	                podaci[indeks][5] = "Musko";
	            } else {
	                podaci[indeks][5] = "Zensko";
	            }
	            podaci[indeks][6] = osoba.getAdresa();
	            podaci[indeks][7] = osoba.getUsername();
	            podaci[indeks][8] = osoba.getPassword();
	            podaci[indeks][9] = osoba.getUloga().toString();
            indeks++;
	        }
	    }

	    return podaci;
	}

	public void dodajKorisnika(Osoba osoba) {

		try {
			File osobeFile = new File("podaci/osobe.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(osobeFile, true));
			writer.write("\n" + osoba.getId() + "|" + osoba.getIme() + "|" + osoba.getPrezime() + "|"
					+ osoba.getBrojTelefona() + "|" + osoba.getJmbg() + "|" + osoba.getPol() + "|" + osoba.getAdresa()
					+ "|" + osoba.getUsername() + "|" + osoba.getPassword() + "|" + osoba.getUloga() + "|"
					+ osoba.getAktivnost());
			writer.newLine();
			writer.close();
			Osobe.add(osoba);
			String[][] podaci = getPodaciOKorisnicima();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisivanja korisnika u datoteku: " + e.getMessage());
		}
	}
	
	public Osoba pronadjiKorisnikaPoID(long id) {
	    for (Osoba osoba : Osobe) {
	        if (osoba.getId() == id) {
	            return osoba;
	        }
	    }
	    return null;
	}
	
	
	
	
	
	public void editKorisnika(int selectedRow, String... vrednosti) {
		Osoba osobaZaIzmenu = Osobe.get(selectedRow);
		System.out.println(vrednosti.length);
	    if (vrednosti.length == 0) {
	    	osobaZaIzmenu.setAktivnost(false);
	        System.out.println("aktivnost promenjena na false");
	    }
	    else {
	        osobaZaIzmenu.setIme(vrednosti[0]);
	        osobaZaIzmenu.setPrezime(vrednosti[1]);
	        osobaZaIzmenu.setBrojTelefona(vrednosti[2]);
	        osobaZaIzmenu.setJmbg(vrednosti[3]);
	        osobaZaIzmenu.setPol(Boolean.parseBoolean(vrednosti[4]));
	        osobaZaIzmenu.setAdresa(vrednosti[5]);
	        osobaZaIzmenu.setUsername(vrednosti[6]);
	        osobaZaIzmenu.setPassword(vrednosti[7]);
	        osobaZaIzmenu.setUloga(Uloga.valueOf(vrednosti[8]));
	    }
	    
	    try {
	        File osobeFile = new File("podaci/osobe.txt");
	        File tempFile = new File("podaci/osobe_temp.txt");
	        
	        BufferedReader reader = new BufferedReader(new FileReader(osobeFile));
	        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

	        String line;
	        int currentLine = 0;

	        while ((line = reader.readLine()) != null) {
	            if (currentLine == selectedRow) {
	                // Izmena linije
	                writer.write(osobaZaIzmenu.getId() + "|" + osobaZaIzmenu.getIme() + "|" + osobaZaIzmenu.getPrezime() + "|" + osobaZaIzmenu.getBrojTelefona() + "|" + osobaZaIzmenu.getJmbg() + "|" + osobaZaIzmenu.getPol() + "|" + osobaZaIzmenu.getAdresa() + "|" + osobaZaIzmenu.getUsername() + "|" + osobaZaIzmenu.getPassword() + "|" + osobaZaIzmenu.getUloga() + "|" + osobaZaIzmenu.getAktivnost());
	                writer.newLine();
	            } else {
	                // Ostale linije se kopiraju nepromenjene
	                writer.write(line);
	                writer.newLine();
	            }
	            currentLine++;
	        }

	        reader.close();
	        writer.close();
	        
	        // Zamena originalnog fajla sa privremenim fajlom
	        osobeFile.delete();
	        tempFile.renameTo(osobeFile);
	        
	        System.out.println("Korisnik je izmenjen.");
	    } catch (IOException e) {
	        System.out.println("Greska prilikom izmene korisnika: " + e.getMessage());
	    }
	    
	    
	    
	}
	
	
	
	
	

}