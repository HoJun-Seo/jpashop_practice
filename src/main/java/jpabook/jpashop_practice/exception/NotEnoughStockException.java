package jpabook.jpashop_practice.exception;

public class NotEnoughStockException extends RuntimeException{

	// alt + enter -> override method
	public NotEnoughStockException() {
		super();
	}

	public NotEnoughStockException(String message) {
		super(message);
	}

	public NotEnoughStockException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughStockException(Throwable cause) {
		super(cause);
	}
}
