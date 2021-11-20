package com.example.demo.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeneralResponse<T> {

    private Boolean success;
    private String code;
    private String message;
    private T payload;

    public GeneralResponse<T> generateError(String code) {
        this.setSuccess(false);
        this.setCode(code);
        this.setMessage(this.generateMessage(code));
        this.setPayload(null);
        return this;
    }

    public GeneralResponse<T> generateErrorWithPayload(String code, T payload) {
        this.setSuccess(false);
        this.setCode(code);
        this.setMessage(this.generateMessage(code));
        this.setPayload(payload);
        return this;
    }

    public GeneralResponse<T> generateSuccess(String code, T payload) {
        this.setSuccess(true);
        this.setCode(code);
        this.setMessage(this.generateMessage(code));
        this.setPayload(payload);
        return this;
    }

    private String generateMessage(String code) {
        String message;
        switch (code) {
            case "404":
                message = "Data not found!";
                break;
            case "400":
                message = "Bad Request!";
                break;
            case "200":
                message = "Success";
                break;
            default:
                message = "Something went wrong!";
                break;
        }
        return message;
    }
}
