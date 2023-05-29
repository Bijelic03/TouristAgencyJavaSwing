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
	
	private static ArrayList<Osoba> osobe;
	
	private static Long currentId;

	public static void ucitajKorisnike() {
		osobe = new ArrayList<Osoba>();
		try {
			File osobeFile = new File("podaci/osobe.txt");
			osobe.clear();
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
					Osoba osoba = new Osoba(idLong, ime, prezime, brojTelefona, jmbg, polBoolean, adresa, username,
							sifra, uloga, aktivnostBoolean);
					osobe.add(osoba);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			System.out.println("Greska prilikom ucitavanja datoteke: " + e.getMessage());
		}
	}

	public static Osoba login(String korisnickoIme, String sifra) {
		for (Osoba osoba : osobe) {
			if (osoba.getUsername().equals(korisnickoIme) && osoba.getPassword().equals(sifra)) {
				if (osoba.getAktivnost()) {
					return osoba;
				}
			}
		}
		return null;
	}

	public static ArrayList<Osoba> getKorisnici() {
		return osobe;
	}

	public void setKorisnici(ArrayList<Osoba> korisnici) {
		osobe = korisnici;
	}

	public static String[][] getPodaciOKorisnicima() {
		String[][] podaci = new String[osobe.size()][11];

		for (int i = 0; i < osobe.size(); i++) {
			Osoba osoba = osobe.get(i);
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
		for (Osoba osoba : osobe) {
			if (osoba.getAktivnost()) {
				aktivneOsobe++;
			}
		}

		String[][] podaci = new String[aktivneOsobe][10];
		int indeks = 0;

		for (Osoba osoba : osobe) {
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

	public static void dodajKorisnika(Osoba osoba) {
		try {
			File osobeFile = new File("podaci/osobe.txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(osobeFile, true));
			writer.write(osoba.getId() + "|" + osoba.getIme() + "|" + osoba.getPrezime() + "|" + osoba.getBrojTelefona()
					+ "|" + osoba.getJmbg() + "|" + osoba.getPol() + "|" + osoba.getAdresa() + "|" + osoba.getUsername()
					+ "|" + osoba.getPassword() + "|" + osoba.getUloga() + "|" + osoba.getAktivnost());
			writer.newLine();
			writer.close();
			osobe.add(osoba);
			String[][] podaci = getPodaciOKorisnicima();
		} catch (IOException e) {
			System.out.println("Greska prilikom upisivanja korisnika u datoteku: " + e.getMessage());
		}
	}

	public static Osoba getKorisnikById(long id) {
		for (Osoba osoba : osobe) {
			if (osoba.getId() == id) {
				return osoba;
			}
		}
		return null;
	}

	public static void disableKorisnik(Long id) {
		Osoba osobaZaIzmenu = getKorisnikById(id);
		osobaZaIzmenu.setAktivnost(false);
		System.out.println("aktivnost promenjena na false");
		try {
			File osobeFile = new File("podaci/osobe.txt");
			File tempFile = new File("podaci/osobe_temp.txt");

			BufferedReader reader = new BufferedReader(new FileReader(osobeFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String line;
			int currentLine = 0;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                currentId = Long.parseLong(tokens[0]);
                System.out.println(currentId);

                if (currentId == osobaZaIzmenu.getId()) {
                    // Izmena linije
                    line = osobaZaIzmenu.getId() + "|" + osobaZaIzmenu.getIme() + "|" + osobaZaIzmenu.getPrezime()
                            + "|" + osobaZaIzmenu.getBrojTelefona() + "|" + osobaZaIzmenu.getJmbg() + "|"
                            + osobaZaIzmenu.getPol() + "|" + osobaZaIzmenu.getAdresa() + "|"
                            + osobaZaIzmenu.getUsername() + "|" + osobaZaIzmenu.getPassword() + "|"
                            + osobaZaIzmenu.getUloga() + "|" + osobaZaIzmenu.getAktivnost();
                }
                writer.write(line);
                writer.newLine();
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

	public static void editKorisnika(Osoba osoba) {
        Osoba osobaZaIzmenu = osoba;

        try {
            File osobeFile = new File("podaci/osobe.txt");
            File tempFile = new File("podaci/osobe_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(osobeFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            int currentLine = 0;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("\\|");
                currentId = Long.parseLong(tokens[0]);
                System.out.println(currentId);

                if (currentId == osoba.getId()) {
                    // Izmena linije
                    line = osobaZaIzmenu.getId() + "|" + osobaZaIzmenu.getIme() + "|" + osobaZaIzmenu.getPrezime()
                            + "|" + osobaZaIzmenu.getBrojTelefona() + "|" + osobaZaIzmenu.getJmbg() + "|"
                            + osobaZaIzmenu.getPol() + "|" + osobaZaIzmenu.getAdresa() + "|"
                            + osobaZaIzmenu.getUsername() + "|" + osobaZaIzmenu.getPassword() + "|"
                            + osobaZaIzmenu.getUloga() + "|" + osobaZaIzmenu.getAktivnost();
                }
                writer.write(line);
                writer.newLine();
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