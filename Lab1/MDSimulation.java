/*
 * 
 * 
 * 
 */
package mdsimulation;

import edu.princeton.cs.algs4.StdDraw;
import mdsimulation.PriorityQue.QueItem;

/**
 *
 * @author Ian Hecker
 */
public class MDSimulation
{
    static double collisionTime;
    
    public static void main(String[] args)
    {   
        /* Set up scale */
        StdDraw.setScale(-2, +2);
        StdDraw.enableDoubleBuffering();
        
        /* Initialize partticle(s) */
        PriorityQue PQ = new PriorityQue();
        
        Particle parA = new Particle(0, 0, 0, .5, 5, 0.05);       
        //Particle parB = new Particle(-2, -2, 0, 0, 5, 0.05);
        
        Particle[] pArray = {parA/*, parB*/};
        PQ.setupQue(pArray);//Initialize PQ with collision events                
        
        QueItem nextCollision = findNextCollision(PQ);                
        
        for (double t = 0.00; true; t += 0.01)
        {            
            StdDraw.clear();
            
            System.out.printf("%f, %f\n", t, collisionTime);
                        
            if(Double.compare(collisionTime, t) == 0)//?????
            {            
                parA.setVY();
                findNextCollision(PQ);
            }                        
            
            /* Draw Particles */
            StdDraw.filledCircle(parA.getRX(), parA.getRY(), parA.getRadius());
            //StdDraw.filledCircle(parB.getPosRX(t), parB.getPosRY(t), parB.getRadius());
            
            for(Particle p : pArray)
            {
                p.updatePosition(t);
            }
            
            StdDraw.show();
            StdDraw.pause(20);
        }        
    }
    public static QueItem findNextCollision(PriorityQue PQ)
    {
        PriorityQue.QueItem nextCollision = PQ.prioritize();            
        collisionTime = nextCollision.getTime();
        return nextCollision;
    }    
}