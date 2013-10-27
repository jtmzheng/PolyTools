
public class SteepestDescent {

	private double eps ; // tolerance
	private int maxIter ; // maximum number of iterations
	private double stepSize ; // step size alpha
	private double [] x0; // starting point
	private double [] bestPoint ; // best point found
	private double bestObjVal ; // best obj fn value found
	private double bestGradNorm ; // best gradient norm found
	private long compTime ; // computation time needed
	private int nIter ; // no. of iterations needed
	private boolean resultsExist ; // whether or not results exist

	// constructors
	public SteepestDescent ()
	{

	}
	public SteepestDescent ( double eps , int maxIter , double stepSize , double [] x0 )
	{
		this.eps = eps; 
		this.maxIter = maxIter;
		this.stepSize = stepSize;
		this.x0 = x0;
		this.resultsExist = false;
		this.bestPoint = new double [x0.length];
		this.bestGradNorm = Double.MAX_VALUE; 
		this.bestObjVal = Double.MAX_VALUE;
	}
	
	// getters
	public double getEps ()
	{
		return eps;
	}
	
	public int getMaxIter ()
	{
		return maxIter;
	}
	
	public double getStepSize ()
	{
		return stepSize;
	}
	
	public double [] getX0 ()
	{
		return x0;
	}
	
	public double getBestObjVal ()
	{
		return bestObjVal;
	}
	
	public double getBestGradNorm ()
	{
		return bestGradNorm;
	}
	
	public double [] getBestPoint ()
	{
		return bestPoint;
	}
	
	public int getNIter ()
	{
		return nIter;
	}
	
	public long getCompTime ()
	{
		return compTime;
	}
	
	public boolean hasResults ()
	{
		return resultsExist;
	}

	// setters
	public void setEps ( double a )
	{
		this.eps = Math.abs(a);
	}
	
	public void setMaxIter ( int a )
	{
		if( a < 0 )
		{
			a = 0;
		}
		
		this.maxIter = a;
	}
	
	public void setStepSize ( double a )
	{
		this.stepSize = a;
	}
	
	public void setX0 ( int j, double a )
	{
		this.x0[j] = a; //TODO: Error check
	}
	
	public void setBestObjVal ( double a )
	{
		this.bestObjVal = a;
	}
	
	public void setBestGradNorm ( double a )
	{
		this.bestGradNorm = a;
	}
	
	public void setBestPoint ( double [] a )
	{
		this.bestPoint = a;
	}
	
	public void setCompTime ( long a )
	{
		this.compTime = a;
	}
	
	public void setNIter ( int a )
	{
		this.nIter = a;
	}
	
	public void setHasResults ( boolean a )
	{
		this.resultsExist = a;
	}
	
	// other methods
	public void init ( int n )
	{
		// initialize member arrays to correct size
	}
	
	public void run( Polynomial poly )
	{
		long startTime = System.nanoTime();
		double [] currentX = new double [x0.length];
		System.arraycopy(x0, 0, currentX, 0, x0.length);
		nIter = 0; //reset number of iterations
		
		// run the steepest descent algorithm
		for (int i = 0; i < this.maxIter && bestGradNorm > eps ; i++ , nIter++ )
		{
			double [] grad = poly.gradient(currentX);
			for( int j = 0 ; j < currentX.length ; j++ )
			{
				currentX[j] += -grad[j] * stepSize; 
			}
			
			bestGradNorm = poly.gradientNorm(currentX); //TODO: Make gradientNorm accept gradient
		}
		
		bestPoint = currentX;
		bestObjVal = poly.f( bestPoint );
		compTime = System.nanoTime() - startTime;
	}
	
	public double lineSearch ()
	{
		// find the next step size
		return this.stepSize; //TODO Implement lineSearch
	}
	
	public double [] direction ( Polynomial p, double [] x )
	{
		// find the next direction
		return p.gradient(x);
	}
	
	public void getParamsUser ( int n )
	{
		// get params from user for n- dim polynomial
	}

	public void print ()
	{
		// print algorithm parameters
	}
	
	public void printResults ( boolean rowOnly )
	{
		// print iteration results , c
		for( int i = 0 ; i < bestPoint.length; i++ )
		{
			String strAsciiTab = Character.toString((char) (i + 97));
			System.out.println(strAsciiTab + ": " + bestPoint[i]);
		}
	}
	
	public static void main( String args [] )
	{
		//Test client
		Polynomial poly = new Polynomial( 1 , 2 ); //1 variable , degree 2
		poly.setCoef(0, 0, 5);
		poly.setCoef(0, 1, 8);
		poly.setCoef(0, 2, 6);
		SteepestDescent sd = new SteepestDescent(1e-5, 10000, 0.001, new double [] {0.0});
		sd.run(poly);
		System.out.println("Best obj value: " + sd.getBestObjVal());
		System.out.println("Best grad norm: " + sd.getBestGradNorm());
		System.out.println("Computation time (ms): " + sd.getCompTime() / ((double)1e6));
		System.out.println("Number of iterations: " + sd.getNIter());
		sd.printResults(false);
		poly.print();
	}
}
