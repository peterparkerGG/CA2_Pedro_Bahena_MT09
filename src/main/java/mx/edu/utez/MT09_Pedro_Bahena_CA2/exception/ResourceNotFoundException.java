package mx.edu.utez.MT09_Pedro_Bahena_CA2.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}