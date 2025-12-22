package com.example.backend.error;

public enum ErrorCode {
    BAD_REQUEST(400, "BAD_REQUEST", "요청이 올바르지 않습니다. (front"),
    NOT_SESSION(401, "NOT_SESSION", "세션이 만료되었습니다. 다시 로그인 해주세요") ,
    FORBIDDEN(403, "FORBIDDEN" , "권한이 없습니다."),
    POST_NOT_FOUND(404, "POST_NOT_FOUND", "게시글이 없습니다."),
    LINK_NOT_FOUND(404, "LINK_NOT_FOUND", "링크가 존재하지 않습니다."),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "사용자가 없습니다."),
    USER_LOGIN_NOT_EMAIL(404, "USER_LOGIN_NOT_EMAIL", "존재하지 않는 이메일입니다"),
    USER_LOGIN_NOT_PASSWORD(404, "USER_LOGIN_NOT_PASSWORD", "비밀번호가 일치하지 않습니다"),
    EMAIL_DUPLICATE(409,"EMAIL_DUPLICATE" , "이미 사용중인 이메일입니다"),
    INTERNAL_ERROR(500, "INTERNAL_ERROR", "서버 오류 (backend)");


    //GlobalExceptionHandler 에서 ResponseEntity 전달을 해주기위해
    // field를 생성하여 저장
    public final int status;
    public final String code;
    public final String msg;

    // ErrorCode는 BusinessException에서 생성한다
    ErrorCode(int status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }
}
