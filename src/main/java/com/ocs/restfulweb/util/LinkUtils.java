package com.ocs.restfulweb.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@UtilityClass
public class LinkUtils {


    /**
     * Construye la URI para el recurso recién creado (POST).
     *
     * @param pathVariable ID del recurso.
     * @return URI completa.
     */
    public static URI buildCreatedLocation(Object pathVariable) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pathVariable)
                .toUri();
    }

    /**
     * Construye la URI para el recurso actualizado (PUT/PATCH).
     * Usa la misma URL actual.
     *
     * @return URI completa.
     */
    public static URI buildCurrentLocation() {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();
    }

    /**
     * Construye URI dinámica con múltiples variables.
     *
     * @param variables Mapa de variables para expandir en la ruta.
     * @return URI completa.
     */
    public static URI buildLocation(Map<String, Object> variables) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(variables)
                .toUri();
    }

}
