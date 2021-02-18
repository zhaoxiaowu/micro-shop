package com.zxw.common.web.handler;

import com.zxw.common.web.exception.BusinessException;
import com.zxw.common.web.web.ApiErrorCode;
import com.zxw.common.web.web.JSONResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ServerWebInputException;

import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 全局异常处理
 *
 * @author wuhongyun
 * @date 2020/5/25 17:59
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONResponse> handleCustomException(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            log.warn("请求访问参数错误!!", e);
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setErrorCode(ApiErrorCode.input_params_error.value);
            BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            if (fieldErrors == null || fieldErrors.size() == 0) {
                jsonResponse.setMessage(ApiErrorCode.input_params_error.getName());
            } else {
                jsonResponse.setMessage(fieldErrors.get(0).getDefaultMessage());
            }
            return ResponseEntity.ok(jsonResponse);
        } else if (e instanceof WebExchangeBindException) {
            log.warn("请求访问参数错误!!", e);
            WebExchangeBindException e1 = (WebExchangeBindException) e;
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setErrorCode(ApiErrorCode.input_params_error.value);
            FieldError fieldError = e1.getFieldError();
            if (fieldError == null) {
                jsonResponse.setMessage(ApiErrorCode.input_params_error.getName());
            } else {
                jsonResponse.setMessage(fieldError.getDefaultMessage());
            }
            return ResponseEntity.ok(jsonResponse);
        } else if (e instanceof ConstraintViolationException
                || e instanceof javax.validation.ConstraintDeclarationException) {
            log.warn("请求访问参数错误!!", e);
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setErrorCode(ApiErrorCode.input_params_error.value);
            jsonResponse.setMessage(e.getMessage());
            return ResponseEntity.ok(jsonResponse);
        } else if (e instanceof ServerWebInputException || e instanceof HttpMessageNotReadableException) {
            log.warn("请求访问参数错误!!", e);
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setErrorCode(ApiErrorCode.input_params_error.value);
            jsonResponse.setMessage(ApiErrorCode.input_params_error.getName());
            return ResponseEntity.ok(jsonResponse);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            log.warn("请求访问方法错误!!", e);
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setErrorCode(ApiErrorCode.http_request_method_not_supported.value);
            jsonResponse.setMessage(ApiErrorCode.http_request_method_not_supported.getName());
            return ResponseEntity.ok(jsonResponse);
        } else if (e instanceof MaxUploadSizeExceededException) {
            log.warn("长度超限错误!!", e);
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setErrorCode(ApiErrorCode.maximum_upload_size_exceeded.value);
            jsonResponse.setMessage(ApiErrorCode.maximum_upload_size_exceeded.getName());
            return ResponseEntity.ok(jsonResponse);
        } else if (e instanceof BusinessException) {
            log.warn("出现业务错误!!", e);
            BusinessException businessException = (BusinessException) e;
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setErrorCode(businessException.getCode());
            jsonResponse.setMessage(businessException.getMessage());
            return ResponseEntity.ok(jsonResponse);
        } else {
            log.error("出错啦!!", e);
            JSONResponse jsonResponse = new JSONResponse();
            jsonResponse.setErrorCode(ApiErrorCode.internal_server_error.value);
            jsonResponse.setMessage(ApiErrorCode.internal_server_error.getName());
            return ResponseEntity.ok(jsonResponse);
        }

    }
}