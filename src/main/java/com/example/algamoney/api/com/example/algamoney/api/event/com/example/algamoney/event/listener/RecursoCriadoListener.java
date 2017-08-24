package com.example.algamoney.api.com.example.algamoney.api.event.com.example.algamoney.event.listener;

import com.example.algamoney.api.com.example.algamoney.api.event.RecursoEventCriado;
import com.example.algamoney.api.model.Pessoa;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class RecursoCriadoListener implements ApplicationListener<RecursoEventCriado> {

    @Override
    public void onApplicationEvent(RecursoEventCriado recursoEventCriado) {
        HttpServletResponse response = recursoEventCriado.getResponse();
        Long codigo = recursoEventCriado.getCodigo();

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(codigo).toUri();

        response.setHeader("Location", uri.toASCIIString());


    }
}
