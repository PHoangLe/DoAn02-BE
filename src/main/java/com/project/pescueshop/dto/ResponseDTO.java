package com.project.pescueshop.dto;

import com.project.pescueshop.util.constant.EnumResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T>{
    public String statusCode;
    public String message;
    public T data;

    public void setMeta(EnumResponseCode enumResponseCode){
        this.statusCode = enumResponseCode.statusCode;
        this.message = enumResponseCode.message;
    }

    public ResponseDTO(EnumResponseCode enumResponseCode, T data){
        this.setMeta(enumResponseCode);
        this.data = data;
    }
    public ResponseDTO(EnumResponseCode enumResponseCode){
        this.setMeta(enumResponseCode);
    }
}
