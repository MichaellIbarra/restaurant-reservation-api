package dev.matichelo.restaurantreservationapi.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
// @Order es una anotación que se utiliza para definir el orden de los componentes o beans.
// Ordered.HIGHEST_PRECEDENCE es una constante que se utiliza para definir el orden más alto posible.
@Order(Ordered.HIGHEST_PRECEDENCE)
// Filter es una interfaz que se utiliza para definir un filtro que se puede utilizar para realizar operaciones de filtrado en las solicitudes y respuestas HTTP.
public class CORSConfig implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // HttpServletResponse es una interfaz que se utiliza para proporcionar funcionalidades específicas del protocolo HTTP.
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        // setHeader es un método que se utiliza para establecer el valor de la cabecera de respuesta con el nombre y el valor especificados.
        // Access-Control-Allow-Origin es una cabecera que se utiliza para permitir que un recurso sea compartido por un origen diferente al del recurso en sí.
        res.setHeader("Access-Control-Allow-Origin", "*");
        // OPTIONS es un método HTTP que se utiliza para describir las opciones de comunicación para el recurso de destino.
        res.setHeader("Access-Control-Allow-Methods", "DELETE, GET, OPTIONS, PATCH, POST, PUT");
        // Access-Control-Max-Age es una cabecera que se utiliza para especificar cuánto tiempo, en segundos, se puede almacenar en caché la respuesta a una solicitud previa.
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with, authorization, Content-Type, Authorization, credential, X-XSRF-TOKEN");
        //  Access-Control-Max-Age es una cabecera que se utiliza para especificar cuánto tiempo, en segundos, se puede almacenar en caché la respuesta a una solicitud previa.
        // nos permite especificar cuánto tiempo, en segundos, se puede almacenar en caché la respuesta a una solicitud previa.
        // para que el navegador no tenga que enviar otra solicitud OPTIONS.
        res.setHeader("Access-Control-Max-Age", "3600");
        // se debe crear una condición para que el método OPTIONS no se ejecute en cada solicitud.
        // porque el método OPTIONS se utiliza para describir las opciones de comunicación para el recurso de destino y no se debe ejecutar en cada solicitud.
        if("OPTIONS".equals(req.getMethod())){
            // SC_OK es una constante que se utiliza para indicar que la solicitud se ha completado con éxito.
            res.setStatus(HttpServletResponse.SC_OK);
        }else{
            // doFilter es un método que se utiliza para pasar la solicitud y la respuesta a la siguiente entidad en la cadena de filtros.
            // chan es un objeto que se utiliza para pasar la solicitud y la respuesta a la siguiente entidad en la cadena de filtros.
            chain.doFilter(request, response);
        }
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
