package cphbusiness.ufo.letterfrequencies;

public class Timer {
    private long start, spent = 0;
    private double st= 0.0,sst=0.0;
    int n = 0;
    public Timer() { play(); }
    public double check() {
        double time = (System.nanoTime() - start+spent);
        st += time;
        sst += time * time ;
        n++;
        return (System.nanoTime() - start+spent)/1e6;
    }
    public void play() { start = System.nanoTime(); }
    public double getMean(){
        return (st/n)/1e6;
    }
    public double getSDev(){
        return (Math.sqrt((sst -getRealMean()*getRealMean()-n)/(n-1)))/1e6;
    }
    private double getRealMean(){
        return st/n;
    }
}
