package exceptions;

public class ArenaTooSmallException extends Exception {

    public ArenaTooSmallException(){
    }

    public ArenaTooSmallException(String message){
        super(message);
    }

}
