package com.example.algamoney.api.com.example.algamoney.api.event;

import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class RecursoEventCriado extends ApplicationEvent {

    private static final Long serialVersionUID = 1L;

    private HttpServletResponse response;
    private Long codigo;

    public RecursoEventCriado(Object source, HttpServletResponse response, Long codigo) {
        super(source);
        this.response = response;
        this.codigo = codigo;
    }

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}
