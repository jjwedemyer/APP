public class Complex {
  /**
  *
  * constructor section
  *
  */

	/**
	* Default empty Complex constructor
	*/
	public Complex() {
		super();
    this.x = 0;
    this.i = 0;
	}

	/**
	* Default Complex constructor
	*/
	public Complex(double x, double i) {
		super();
    this.x = x;
    this.i = i;
	}

  /**
  * Default Copy constructor
  */
  public Complex(Complex x) {
    super();
    this.x = x.getX();
    this.i = x.getI();
  }

  /**
  * Clone method
  */
  public Complex clone() {
    return new Complex(this);
  }

  /**
  * toString override
  * @return
  */
  public String toString() {
    return this.x+" + "+this.i+"i";
  }

  /**
  * Getter and Setter
  */

	/**
	* Returns value of real part x
	* @return
	*/
	public double getX() {
		return this.x;
	}

	/**
	* Sets new value of x
	* @param
	*/
	public void setX(double x) {
		this.x = x;
	}
  /**
  * Returns value of the imaginary part i
  * @return
  */
  public double getI() {
    return this.i;
  }

  /**
  * Sets new value of x
  * @param
  */
  public void setI(double x) {
    this.i = x;
  }
 /**
 * Varsection
 */
  private double x , i;
}
