package agencije;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Ponuda implements Comparable<Ponuda>{
	
	private String agencija, destinacija;
	private int brNocenja, cena;
	private Datum datumPolaska;
	
	public Ponuda (String agencija, String destinacija,
					int brNocenja, int cena, Datum datumPolaska) {
		
		this.agencija = agencija;
		this.destinacija = destinacija;
		this.brNocenja = brNocenja;
		this.cena = cena;
		this.datumPolaska = datumPolaska;
	}

	public String getAgencija() {
		return agencija;
	}
	public String getDestinacija() {
		return destinacija;
	}
	public int getBrNocenja() {
		return brNocenja;
	}
	public int getCena() {
		return cena;
	}
	public Datum getDatumPolaska() {
		return datumPolaska;
	}

	@Override
	public String toString() {
		return destinacija + ": " + agencija + " " + datumPolaska + " "
				+ brNocenja + " " + cena;
	}
	
	public static ArrayList<Ponuda> ucitajPonude() {
		
		ArrayList<Ponuda> ponude = new ArrayList<Ponuda>();
		
		Path putanja = Paths.get("ponuda.txt");
		Charset cs = StandardCharsets.UTF_8;
	
		try {
			List<String> linije = Files.readAllLines(putanja, cs);
			
			for (String linija : linije) {
				String[] podaci = linija.split(", ");
				String agencija = podaci[0];
				String destinacija = podaci[1];
				Datum datum = new Datum(podaci[2]);
				int brNocenja = Integer.parseInt(podaci[3]);
				int cena = Integer.parseInt(podaci[4]);
				
				ponude.add(new Ponuda(agencija, destinacija, brNocenja, 
										cena, datum));
			}
		} catch (IOException e) {
			System.err.print("Greska prilikom ucitavanja datoteke.");
			System.exit(1);
		}
		 catch (NumberFormatException e) {
				System.err.print("Greska prilikom ucitavanja datoteke.\n"
								+ "Datum u pogresnom obliku.");
				System.exit(1);
			}		
		return ponude;
	}

	@Override
	public int compareTo(Ponuda p) {
		int rez = Integer.compare(this.cena, p.cena);	
		if (rez == 0) {
			return this.getDatumPolaska().compareTo(p.getDatumPolaska());
		}	
		return rez;
	}
	
	public static String listajPonude(List<Ponuda> ponude) {
		
		StringBuilder sb = new StringBuilder();
		for (Ponuda ponuda: ponude) {
			sb.append(ponuda + "\n");
		}		
		return sb.toString();
	}

}
