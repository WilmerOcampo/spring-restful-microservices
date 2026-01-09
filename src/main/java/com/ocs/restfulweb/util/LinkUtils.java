package com.ocs.restfulweb.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@UtilityClass
public class LinkUtils {

    /**
     * Construye la URI dinámica para Location header.
     *
     * @param pathVariable Valor del path variable (ID del recurso).
     * @return URI completa.
     */
    public static URI buildLocation(Object pathVariable) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(pathVariable)
                .toUri();
    }
}
