class Polygon implements GeometricForm
{
    public final Point[] points;
    public final int numberOfPoints;
    /**
     *  X coordinate of the point with the max X.
     */
    public final double max_X;
    
    public Polygon(Point... points)
    {
        int l = points.length;
        if(l < 3)
            throw new IllegalArgumentException("Polygon must have at least 3 points");
            
        this.points = points;
        this.numberOfPoints = l;
        
        // Compute max_X needed for method isInside.
        double x = Double.MIN_VALUE;
        for(Point p : this.points)
        {
            if(p.x > x)
                x = p.x;
        }
        this.max_X = x;
    }
    
    @Override
    public boolean isInside(Point p)
    {
        GeometryServices sh = new GeometryServices(this.max_X);
        return sh.isInside(this.points, this.numberOfPoints, p);
    }
}
