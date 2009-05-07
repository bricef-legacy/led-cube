package utils;

public class Statistics {
	long total;
	long num;
	public Statistics(){
		this.total=0;
		this.num=0;
	}
	public void add(long toadd){
		num++;
		total+=toadd;
	}
	public long average(){
		return total/num;
	}
}
