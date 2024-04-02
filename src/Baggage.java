public class Baggage {
    private double baggageDimension;
	private double baggageWeight;
	private double baggageFee;
	public Baggage(double dim,double weight){
		baggageDimension = dim;
		baggageWeight = weight;
		baggageFee = 0;	
	}
	public double getDim() {
		return baggageDimension;
	}
	public double getWeight() {
		return baggageWeight;
	}
	public double getFee() {
		return baggageFee;
	}
	public void setDim(double dims) {
		baggageDimension = dims;
	}
	public void setWeight(double weigh) throws IllegalBaggageWeightException {
		if (weigh > 200 || weigh < 0 ) throw new IllegalBaggageWeightException();
		baggageWeight = weigh;
	}
	public void setFee(double fees) {
		baggageFee = fees;
	}
	public double calculateBaggageFee(double dim,double w) {
		double baggageFees = 0;
		if (dim>10) {
			baggageFees = baggageFees + ((dim-10)*20);
		}
		if (w>30) {
			baggageFees = baggageFees + ((w-30)*20);
		}
		return baggageFees;
	}
}
