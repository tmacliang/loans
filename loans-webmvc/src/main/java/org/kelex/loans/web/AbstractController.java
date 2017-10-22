package org.kelex.loans.web;

import org.kelex.loans.core.dto.ResponseDTO;

/**
 * Created by hechao on 2017/8/29.
 * @author  hechao
 */
public abstract class AbstractController {
    public <T> ResponseDTO<T> createResponseDTO(T a){
        return new ResponseDTO<T>();
    }
}
