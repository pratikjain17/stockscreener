package exception;

public class StockServiceException extends RuntimeException {
    public StockServiceException(String message) {
        super(message);
    }
}