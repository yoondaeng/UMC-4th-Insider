package com.example.umc_insider.config;

public enum BaseResponseStatus {
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false, 2003, "권한이 없는 유저의 접근입니다."),
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    USERS_EXISTS_USER_ID(false, 2011, "중복된 아이디입니다."),
    USERS_EXISTS_NICKNAME(false, 2012, "중복된 닉네임입니다."),
    USERS_FAILED_TO_SIGN_UP(false, 2013, "회원 가입에 실패하였습니다."),
    USER_NOT_FOUND(false, 2014, "유저를 찾을 수 없습니다."),


    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false, 2017, "중복된 이메일입니다."),

    POST_USERS_EMPTY_PW(false, 2030, "비밀버호를 입력해주세요."),
    POST_USERS_INVALID_PW(false, 2031, "비밀번호를 확인해주세요."),

    POST_BOARDS_EMPTY_TITLE(false, 2018, "제목은 두 글자 이상으로 작성해주세요."),
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false, 3014, "없는 아이디거나 비밀번호가 틀렸습니다."),

    FAILED_TO_SIGNUP(false, 3015, "회원가입에 실패하였습니다."),

    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    DATABASE_EXIST(false, 4000, "이미 존재하는 데이터입니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),
    MODIFY_FAIL_USERNAME(false, 4014, "유저네임 수정 실패"),
    MODIFY_FAIL_BOARDTITLE(false, 4015, "게시글 제목 수정 실패"),
    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),
    CHATROOM_NOT_FOUND(false, 5001, "채팅방을 찾을 수 없습니다."),
    CHATROOM_NOT_SOLD(false, 5002, "상품이 판매되지 않았습니다."),
    CHATROOM_ID_NOT_PROVIDED(false, 5003, "채팅방 ID가 제공되지 않았습니다."),
    GOODS_NOT_FOUND_IN_CHATROOM(false, 5004, "채팅방에서 상품을 찾을 수 없습니다."),
    DUPLICATE_USER_ID(false, 5005, "DB상에 동일한 userID가 존재합니다."),
    USERS_DUPLICATE_EMAIL(false, 2015, "중복된 이메일입니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
