class Square extends Polygon
{
    public final Point center;
    /**
     *  Side length;
     */
    public final double side;
    
    /**
     * In degres°, angle form by the square top point (North, p.y max).
     * Between 0° (include) and 90° (exclude).
     */
    public final double angle;
    
    public Square(Point... points)
    {
        super(points);
        int l = points.length;
        // Must have 4 points.
        if(l != 4)
            throw new IllegalArgumentException("Square must have 4 points");
        
        // All 4 angles must be 90°.
        // All sides must be of ame length.
        if(!checkPoints(points))
             throw new IllegalArgumentException("Points do not form a square");
        
        
        super(points);
    }
    
    public Square(Point center, double side, double angle)
    {
        super(this.createSquarePoints(center, side, angle));
        this.center = center;
        this.side = side;
        this.angle = angle;
    }
    
    private Point[] createSquarePoints(Point center, double side, double angle)
    {
        
    }
    
    private boolean checkPoints(Point[] points)
    {
        
    }
    
    private double getSide(Point[] points)
    {
        
    }
    
    private Point getCenter(Point[] points)
    {
        
    }
}
