package com.example.app.global.swagger;

public class SchemaDescriptionUtils {
    public static class User{
        public static final String name = "사용자 닉네임";
        public static final String id = "사용자 식별자";
    }
    public static class Group{
        public static final String name = "그룹명";
        public static final String id = "그룹 식별자";
    }

    public static class Membership{
        public static final String id = "멤버십 식별자";
    }
    public static class Response{
        public static final String status = "API 성공 여부";
        public static final String errorCode = "에러 코드";
    }
}
