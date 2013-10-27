
public class Polynomial {
	private boolean isSet; //whether the polynomial is set
	private boolean paramChanged; //whether the polynomial has been changed
	private int n; // no. of variables
	private int degree ; // degree of polynomial
	private double [][] coefs ; // coefficients
	private double gradientNormal; //cache gradient that was calculated
	private int lastGradientHash; //hashcode of the reference
	
	// constructors
	public Polynomial ()
	{
		n = 0;
		degree = 0;
		coefs = null;
		this.isSet = false;
	}
	
	public Polynomial ( int n, int degree , double [][] coefs )
	{
		this.n = n;
		this.degree = degree;
		this.coefs = coefs; //assume coefs is set properly to right dimensions
		this.isSet = true;
		this.paramChanged = false;
		this.gradientNormal = Double.MAX_VALUE;
	}
	
	public Polynomial ( int n, int degree )
	{
		this.n = n;
		this.degree = degree;
		this.coefs = new double [n][degree];
		this.isSet = true; //initialized to all zeros
		this.paramChanged = false;
		this.lastGradientHash = 0;
		this.gradientNormal = Double.MAX_VALUE;
	}
	
	// getters
	public int getN ()
	{
		return n;
	}
	
	public int getDegree ()
	{
		return degree;
	}
	
	public double [][] getCoefs ()
	{
		return coefs;
	}
	
	// setters
	public void setN ( int a )
	{
		if(this.n == a)
		{
			return; //no need to move anything
		}
		
		double [][] newCoefs = new double [a][this.degree];
		
		for( int i = 0 ; i < this.n ; i++ )
		{
			for ( int j = 0 ; j < this.degree; j++ )
			{
				newCoefs[i][j] = this.coefs[i][j]; //TODO: replace with System.copyArray? 
			}
		}
		
		this.n = a;
		
	}
	
	public void setDegree ( int a )
	{
		if( this.degree == a )
		{
			return;
		}
		
		double [][] newCoefs = new double [this.n][a];
		
		for( int i = 0 ; i < this.n ; i++ )
		{
			for ( int j = 0 ; j < this.degree; j++ )
			{
				newCoefs[i][j] = this.coefs[i][j]; //TODO: replace with System.copyArray? 
			}
		}
		
		this.degree = a;
		this.isSet = false;
		this.paramChanged = true;
	}
	
	public void setCoef ( int j, int d, double a)
	{
		if( j > n || d > degree)
		{
			return; //Assume its an error if out of bounds
		}
		
		coefs[j][d] = a;
	}
	
	// other methods
	public void init ()
	{
		// init member arrays to correct size ???????
	}
	public double f( double [] x )
	{
		// calculate function value at point x
		double sum = 0;
		for( int i = 0; i < this.n ; i++ )
		{
			for( int j = 0; j < this.degree ; j++ )
			{
				sum += Math.pow( coefs[i][j] , j );
			}
		}
		
		return sum;
	}
	
	public double [] gradient ( double [] x )
	{
		// calculate gradient at point x
		double [] grad = new double [this.n];
		gradientNormal = 0;

		for ( int i = 0 ; i < this.n ; i++ )
		{
			double gradVar = 0;
			//for each variable i, calculate the partial derivative at x[j]
			for( int j = this.degree - 1; j > 0 ;  j-- )
			{
				gradVar += coefs[i][j] * j * Math.pow( x[i], j - 1 ); 
			}
			grad[i] = gradVar;
			gradientNormal += gradVar * gradVar;
		}
		
		gradientNormal = Math.sqrt(gradientNormal); //store the gradientNormal
		lastGradientHash = x.hashCode();
		paramChanged = false;
		
		return grad;		
	}
	
	
	public double gradientNorm ( double [] x )
	{
		// calculate norm of gradient at point x
		if(paramChanged || x.hashCode() != lastGradientHash  || !isSet )
		{
			gradient(x); //compute the gradient
		}
		
		return gradientNormal;
	}
	public boolean isSet () 
	{
		// indicate whether polynomial is set
		return isSet;
	}
	
	public void print () 
	{
		// print out the polynomial
		for ( int i = 0; i < this.n ; i++ )
		{
			String strAsciiTab = Character.toString((char) (i + 97));
			for( int j = 0; j < this.degree ; j++ )
			{
				System.out.print(coefs[i][j] + strAsciiTab + "^" + j);
				
				if( i < this.n - 1 || j < this.degree - 1)
				{
					System.out.print( " + " );
				}
			}
		}
		
		System.out.println();
	}	

	public static void main( String args [] )
	{
		//Test client
	}
}
