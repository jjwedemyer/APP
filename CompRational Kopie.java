package app.exercise.algebra;
public class CompRational
	extends Rational
	implements Comparable<Rational> {
	/**
	 * Default empty CompRational constructor
	 */
	public CompRational()
	{
		super();
	}

	/**
	 * Default CompRational constructor
	 */
	public CompRational(long zaehler, long nenner)
	{
		super(zaehler, nenner);
	}

	public CompRational(CompRational a)
	{
		super(a.getZaehler(), a.getNenner());
	}

	@Override
	public int compareTo(CompRational a)
	{
		// Wähle Vergleich von brüchen da genauer;)
		// Erstellen des kleinsten gemeinsamen Vielfachen
		int kgv = kgV(this.getNenner(), a.getNenner());

		// auf gemeinsamen nenner bringen...
		this.setNenner((kgv / this.getNenner()) * this.getNenner());
		this.setZaehler(this.getZaehler() * (kgv / this.getNenner()));
		a.setNenner((kgv / a.getNenner()) * a.getNenner());
		a.setZaehler(a.getZaehler() * (kgv / a.getNenner()));

		// Vergleiche Rationals
		if (this.getZaehler() > a.getZaehler())
		{
			this.kuerzen();
			a.kuerzen();
			return 1;
		}
		if (this.getZaehler() < a.getZaehler())
		{
			this.kuerzen();
			a.kuerzen();
			return -1;
		}
		this.kuerzen();
		a.kuerzen();
		return 0;
	}
}
