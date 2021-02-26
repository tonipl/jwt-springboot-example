package example.tonipl.jwt;

public class Secret {

    private Secret() {
	throw new IllegalStateException("Secret class");
    }

    public static byte[] getKey() {
	return "secret".getBytes();
    }
}