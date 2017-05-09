package app.exercise.algebra;
class Rational implements Arithmetic {
	//long int Variablen für größtmöglichen Zahlenbereich,
	private long nenner;
	private long zaehler;

	public Rational(long zaehler, long nenner)
	{
		this.nenner  = nenner;
		this.zaehler = zaehler;
	}

	public Rational(Rational c)
	{
		this.nenner  = c.getNenner();
		this.zaehler = c.getZaehler();
	}

	public Rational()
	{
		this.nenner  = 1;
		this.zaehler = 0;
	}

	//andere Rationale Zahl aufaddieren
	@Override
	public void add(Rational bruch)
	{
		long nenner_new  = kgV(bruch.getNenner(), this.nenner);
		long zaehler_new = this.zaehler * (nenner_new / this.nenner)
		                   + bruch.getZaehler() * (nenner_new / bruch.getNenner());

		this.setNenner(nenner_new);
		this.setZaehler(zaehler_new);
	}

	//andere Rationale Zahl subtrahieren
	@Override
	public void sub(Rational bruch)
	{
		bruch.setZaehler(bruch.getZaehler() * (-1));
		add(bruch);
	}

	//mit anderer rat. Zahl multiplizieren
	@Override
	public void mul(Rational bruch)
	{
		this.kuerzen();
		bruch.kuerzen();

		long zaehler_new = this.getZaehler() * bruch.getZaehler();
		long nenner_new  = this.getNenner() * bruch.getNenner();

		this.setNenner(nenner_new);
		this.setZaehler(zaehler_new);
	}

	//durch andere rat. Zahl dividieren
	@Override
	public void div(Rational bruch)
	{
		long nenner_old  = bruch.getNenner();
		long zaehler_old = bruch.getZaehler();

		// kehrwert multiplizieren
		this.mul(new Rational(nenner_old, zaehler_old));
	}

	//Bruch kürzen
	public void kuerzen()
	{
		long long_ggT = ggT(nenner, zaehler);

		this.zaehler = zaehler / long_ggT;
		this.nenner  = nenner / long_ggT;
	}

	//Ermittlung des kleinsten Vielfachen nach der Methode aus der Vorlesung
	protected long kgV(long zahl1, long zahl2)
	{
		return (zahl1 / ggT(zahl1, zahl2)) * zahl2;
	}

	//euklidischer Algorithmus zur Ermittlung des ggT
	private long ggT(long zahl1, long zahl2)
	{
		long rest = zahl1 % zahl2;

		return (rest == 0) ? zahl2 : ggT(zahl2, rest);
	}

	//Zähler zurückgeben
	public long getZaehler()
	{
		return this.zaehler;
	}

	//Nenner zurückgeben
	public long getNenner()
	{
		return this.nenner;
	}

	//Zähler ändern
	public void setZaehler(long zahl)
	{
		this.zaehler = zahl;
	}

	//Nenner ändern
	public void setNenner(long zahl)
	{
		this.nenner = zahl;
	}

	@Override
	public Rational clone()
	{
		return new Rational(this);
	}

	public String toString()
	{
		return this.zaehler + "/" + this.nenner;
	}

	public Boolean equals(Rational c)
	{
		if (c == null) return false;
		this.kuerzen();
		c.kuerzen();
		return((this.nenner == c.getNenner()) && (this.zaehler == c.getZaehler()));
	}

	// hashCode consistent with equals() and compareTo()
	public int hashCode()
	{
		return this.toString().hashCode();
	}
}
