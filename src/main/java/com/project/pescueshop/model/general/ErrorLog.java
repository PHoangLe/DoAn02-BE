package com.project.pescueshop.model.general;

import com.project.pescueshop.dto.ErrorLogDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ERROR_LOG")
@Entity
@Builder
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer errorLogId;
    @Column(columnDefinition = "TEXT")
    private String message;
    @Column(columnDefinition = "TEXT")
    private String stackTrace;
    private String client;
    private String url;
    private String locale;
    private Date date;

    public ErrorLog(ErrorLogDTO dto){
        this.message = dto.getMessage();
        this.stackTrace = dto.getStackTrace();
        this.client = dto.getClient();
        this.url = dto.getUrl();
        this.locale = dto.getUrl();
        this.date = dto.getDate();
    }
}
