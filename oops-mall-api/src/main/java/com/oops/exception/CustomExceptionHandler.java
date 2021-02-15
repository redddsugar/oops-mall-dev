package com.oops.exception;

import com.oops.utils.OopsResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author L-N
 * @Description TODO
 * @createTime 2021年01月31日 20:39
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public OopsResult handlerUploadException(MaxUploadSizeExceededException e) {
        return OopsResult.errorMsg("上传文件大小大于500K");
    }
}
