package io.svinoczar.api.errorHandling;

import io.jsonwebtoken.MalformedJwtException;
import io.svinoczar.api.exception.ApiException;
import io.svinoczar.api.exception.AuthException;
import io.svinoczar.api.exception.UnauthorizedException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.security.SignatureException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class AppErrorAttributes extends DefaultErrorAttributes {
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public AppErrorAttributes() {super();}

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        Throwable error = getError(request);

        ArrayList<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
//        error instanceof ExpiredJwtException todo: check if this exception could be handled! (for this moment ExpiredJwtException not exist in io.jsonwebtoken)

        if (error instanceof AuthException || error instanceof UnauthorizedException
                || error instanceof SignatureException || error instanceof MalformedJwtException) {
            status = HttpStatus.UNAUTHORIZED;
            LinkedHashMap<String, Object> errorMap = new LinkedHashMap<String, Object>();
            errorMap.put("code", ((ApiException) error).getErrorCode());
            errorMap.put("message", error.getMessage());
            errorList.add(errorMap);
        } else if (error instanceof ApiException) {
            status = HttpStatus.BAD_REQUEST;
            LinkedHashMap<String, Object> errorMap = new LinkedHashMap<String, Object>();
            errorMap.put("code", ((ApiException) error).getErrorCode());
            errorMap.put("message", error.getMessage());
            errorList.add(errorMap);
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            String message = error.getMessage();
            if (message == null) message = error.getClass().getName();
            LinkedHashMap<String, Object> errorMap = new LinkedHashMap<String, Object>();
            errorMap.put("code", "INTERNAL_ERROR");
            errorMap.put("message", message);
            errorList.add(errorMap);
        }
        HashMap<String, Object> errors = new HashMap<String, Object>();
        errors.put("errors", errorList);
        errorAttributes.put("status", status.value());
        errorAttributes.put("errors", errors);

        return errorAttributes;
    }
}
