package agencije;

public class Datum implements Comparable<Datum> {
	
	private int dan, mesec, godina;
	
	public Datum(int dan, int mesec, int godina) {
		
		this.dan = dan;
		this.mesec = mesec;
		this.godina = godina;
	}
	
	public Datum (String datumS) throws NumberFormatException {
		
		String[] datum = datumS.split("\\.");	
		this.dan = Integer.parseInt(datum[0]);
		this.mesec = Integer.parseInt(datum[1]);
		this.godina = Integer.parseInt(datum[2]);

	}
	
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Datum)) {
			return false;
		}
		Datum d = (Datum)obj;
	
		return dan==d.dan && mesec==d.mesec && godina==d.godina;
	}

	@Override
	public String toString() {
		return dan + "." + mesec + "." + godina + ".";
	}

	@Override
	public int compareTo(Datum d) {
		int rez = Integer.compare(this.godina, d.godina);
		if (rez != 0) {
			return rez;
		}
		else {		
			int novRez = Integer.compare(this.mesec, d.mesec);
			if (novRez != 0) {
				return novRez;
			}
			else {
				return Integer.compare(this.dan, d.dan);
			}
		}	
	}

}
