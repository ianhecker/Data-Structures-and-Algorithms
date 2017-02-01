/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mdsimulation;

/**
 *
 * @author Ian
 */
public class PriorityQue
{
    public static double NO_COLLISION = -12345;
    private QueItem[] CollisionQue;
    private Node first;
    private Node last;
    private int count;
    
    public PriorityQue()
    {
        //QueItem[] CollisionQue = new QueItem[50];
    }
    
    public boolean isEmpty()    {return first == null;}
    public int count()          {return count;}
    
    public void findCollisions(Particle[] pArray, double t)
    {
        for(Particle p : pArray)
        {
            addCollision(p, p.collidesX(t), false);
            addCollision(p, p.collidesY(t), false);
            //addCollision(p, p.collides(), true);
        }
    }
    /* Adds collisions in que-like fashion */
    /* WILL NEED to look though for impending collision */
    public void addCollision(Particle p, double time, boolean multipleParCollision)
    {
        if(time != NO_COLLISION)
        {                                    
            QueItem qi = new QueItem(p, time);
            
            Node oldLast = last;
            last = new Node();
            last.qi = qi;
            last.next = null;
            if(isEmpty())   {first = last;}
            else            {oldLast.next = last;}
            count++;
        }        
    }
    public QueItem delCollision()
    {                
        QueItem qi = first.qi;
        
        updateParticles(qi);
        
        first = first.next;
        count--;
        if(isEmpty())   {last = null;}
        return qi;
    }
    //Update velocities of particles involved in a collision
    private void updateParticles(QueItem qi)
    {   
        Particle p = qi.getParticles();
        //Particle[] pArray = qi.getParticles();
        
        //for(Particle p : qi.getParticles())
        //{
            p.updateVelocity();
        //}
    }
    /* Will find item with soonest time; delete items*/
    public QueItem prioritize()
    {
        Node impendingCol = first;
        
        for(int i = count; i < count+1; i++)
        {
            if(first != null)
            {
                if(impendingCol.next != null)
                {
                    if(impendingCol.getTime() > first.next.getTime())
                    {
                        impendingCol = impendingCol.next;
                        delCollision();
                    }
                    else if(impendingCol.getTime() < first.next.getTime())
                    {
                        Node newNext;//Temp holder
                        newNext = impendingCol.next.next;//impendingCol's next item is next item's next
                        impendingCol.next.next = null;//Remove impendingCol's next item's pointer for deletion
                        impendingCol.next = newNext;//move next, next of impendingCol back to next
                        count--;
                    }
                }
            }
        }
        return impendingCol.qi;
    }
    private class Node extends QueItem
    {
        QueItem qi;
        Node next;
    }
    /* Inner class that holds data for each collision in PQ */
    public class QueItem
    {
        private Particle parInvolved; //Particle involved in collision
        private boolean invalidated; //Will event occur or has previous collison rendered invalid
        private double time; //When event will occur                       
        
        private QueItem(){}
        
        private QueItem(Particle p, double time)
        {
            this.parInvolved = p;            
            this.time = time;
            this.invalidated = false;
        }
        public Particle getParticles(){return parInvolved;}
        public double getTime()         {return time;}
        public boolean isInvalidated()  {return invalidated;}
        
    }    
}
