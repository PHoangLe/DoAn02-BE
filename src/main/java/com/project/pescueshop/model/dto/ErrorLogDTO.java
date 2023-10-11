package com.project.pescueshop.model.dto;

import com.project.pescueshop.model.general.ErrorLog;
import com.project.pescueshop.model.annotation.Name;
import lombok.*;

import java.util.Date;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Name(noun = "error", pluralNoun = "errorList")
public class ErrorLogDTO {
    private String errorLogId;
    private String message;
    private String stackTrace;
    private String client;
    private String url;
    private String locale;
    private String email;
    private Date date;

    public ErrorLogDTO(ErrorLog log){
        this.errorLogId = log.getErrorLogId();
        this.message = log.getMessage();
        this.stackTrace = log.getStackTrace().substring(0, 150);
        this.client = log.getClient();
        this.url = log.getUrl();
        this.locale = log.getLocale();
        this.email = log.getEmail();
        this.date = log.getDate();
    }
}
