package com.github.fish56.tutorialsmall.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Service 层返回数据的统一接口
 *
 * @param <T> 正常数据的类型
 */
@Data
@Accessors(chain = true)
public class ServiceResponse<T> {
    /**
     * 发生异常时错误信息和HTTP错误状态码由service层判断
     */
    private Integer errorStatusCode;
    private String errorMessage;

    /**
     * 正常情况下的真实数据
     *   HTTP状态码由Controller层判断
     */
    private T data;

    public boolean isSuccess(){
        return errorMessage == null;
    }
    public boolean hasError(){
        return errorMessage != null;
    }
}
