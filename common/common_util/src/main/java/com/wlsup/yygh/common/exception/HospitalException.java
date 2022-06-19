package com.wlsup.yygh.common.exception;

import com.wlsup.yygh.common.result.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 自定义全局异常类
 *
 * @author qy
 */
@Data
@ApiModel(value = "自定义全局异常类")
public class HospitalException extends RuntimeException {

    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public HospitalException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象  因为你throw new HospitalException()的时候
     * 里面的参数填ResultCodeEnum所拥有的更加规范
     * @param resultCodeEnum
     */
    public HospitalException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }


    @Override
    public String toString() {
        return "HospitalException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
