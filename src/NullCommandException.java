
public class NullCommandException extends Exception {
	public NullCommandException(String input) {
		super("You cannot input null as the command!");
	}
}
