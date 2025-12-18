package com.example.backend.error;

public class BusinessException extends RuntimeException{

    /*

       예외에 들어갈 status , errorCode , msg 를 생성하는 곳

       Throwble > Exception > RuntimeException > BusinessException

       Throwable에는 이미 message를 저장하는 필드가 있다 (생성자를 이용하여 수정 가능 )
        => 오류 메세지를 모두 Throwble 내부 getMessage() 메서드를 사용함

       enum errorCode 가 들어오면 미리 만들어둔 enum errorCode가 그대로 들어오게됨

     */

    private final ErrorCode errorCode;

    // custom msg 없는 경우
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.msg);
        this.errorCode = errorCode;
    }

    // custom msg 있는 경우
    public BusinessException(ErrorCode errorCode , String customMsg){
        super(customMsg);
        this.errorCode = errorCode;
    }

    // BusinessException errorcode
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
