
public class IllegalCapacityException extends Exception {
	public IllegalCapacityException() {
		super("Capacity must be specified and cannot be empty");
	}
}
