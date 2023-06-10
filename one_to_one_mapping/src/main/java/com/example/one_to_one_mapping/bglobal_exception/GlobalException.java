package com.example.one_to_one_mapping.bglobal_exception;

import com.example.one_to_one_mapping.api_Response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalException {
    @Autowired
    private ApiResponse apiResponse;
    public ApiResponse handleGlobalException(MethodArgumentNotValidException e){
        HashMap<String,Object> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((error) ->{
            String fieldError = error.getField();
            String message=error.getDefaultMessage();
            errors.put(fieldError,message);
            apiResponse.setData(errors);
            apiResponse.setMessage("Something Went To Wrong");
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        });
        return apiResponse;
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse handleIllegalAuthException(IllegalArgumentException e){
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        apiResponse.setData(null);
        apiResponse.setMessage("Unauthorized Access");
        return apiResponse;
    }
//    @ExceptionHandler(NoSuchNoTFieldException.class)
//    public ApiResponse handleNoSuchNoTFieldException(NoSuchNoTFieldException e){
//        apiResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
//        apiResponse.setData(null);
//        apiResponse.setMessage("Unauthorized Token Access");
//        return apiResponse;
//    }
}
