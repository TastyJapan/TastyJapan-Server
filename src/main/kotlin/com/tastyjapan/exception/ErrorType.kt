package com.tastyjapan.exception

import lombok.Getter
import lombok.RequiredArgsConstructor

@Getter
@RequiredArgsConstructor
enum class ErrorType(val errorCode: String, val message: String) {
    MISSING_REQUIRED_VALUE_ERROR("COMMON-001", "필수 요청값이 누락되었습니다."),
    NOT_ALLOWED_PERMISSION_ERROR("COMMON-002", "허용되지 않은 권한입니다."),
    DUPLICATED_REQUEST_ERROR("COMMON-003", "중복된 요청입니다."),
    INVALID_REQUEST_ERROR("COMMON-004", "올바르지 않은 데이터 요청입니다."),
    ASYNC_HANDLING_ERROR("COMMON-005", "비동기 처리에서 문제가 발생했습니다."),
    NETWORK_ERROR("COMMON-006", "네트워크 처리에서 문제가 발생했습니다."),

    SERVICE_BEING_CHECKED("SERVICE-001", "서비스가 점검중입니다."),


    UNEXPECTED_SERVER_ERROR("SERVER-001", "서버 관리자에게 문의하세요.");

}