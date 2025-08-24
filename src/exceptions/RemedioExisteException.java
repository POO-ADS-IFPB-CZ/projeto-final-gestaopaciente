package exceptions;

public class RemedioExisteException extends Exception {
    public RemedioExisteException(String message) {
        super(message);
    }
    public RemedioExisteException() {
        super("Já existe um remédio com este nome e dosagem.");
    }
}