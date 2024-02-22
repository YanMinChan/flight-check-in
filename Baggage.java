public class Baggage {
    private int baggageDimension;
	private int baggageWeight;
	private int baggageFee;
	public Baggage(int dim,int weight, int fee){
		baggageDimension = dim;
		baggageWeight = weight;
		baggageFee = fee;	
	}
	public int getdim() {
		return baggageDimension;
	}
	public int getweight() {
		return baggageWeight;
	}
	public int getfee() {
		return baggageFee;
	}
	public void setdim(int dims) {
		baggageDimension = dims;
	}
	public void setweight(int weigh) {
		baggageWeight = weigh;
	}
	public void setFee(int fees) {
		baggageFee = fees;
	}
	public int calculateBaggageFee(int dim,int w) {
		int baggageFees = 0;
		if (dim>10) {
			baggageFees = baggageFees + ((dim-10)*20);
		}
		if (w>30) {
			baggageFees = baggageFees + ((w-30)*20);
		}
		return baggageFees;
	}
}
