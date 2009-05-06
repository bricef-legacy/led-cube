
public class Statistics {
	long total;
	long num;
	public Statistics(){
		this.total=0;
		this.num=0;
	}
	void add(long toadd){
		num++;
		total+=toadd;
	}
	long average(){
		return total/num;
	}
}
