package com.mkyong.exception;

public class CustomException extends Exception{
    private String errorDetails;
    public CustomException(String reason,String errorDetails){
        super(reason);
        this.errorDetails=errorDetails;
    }
    public String getFaultInfo(){
        return errorDetails;
    }
}
