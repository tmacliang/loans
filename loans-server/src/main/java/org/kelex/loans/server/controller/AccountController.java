package org.kelex.loans.server.controller;

import org.kelex.loans.bean.OpenAccountDTO;
import org.kelex.loans.core.service.OpenAccountService;
import org.kelex.loans.web.AbstractController;
import org.kelex.loans.web.HttpRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by hechao on 2017/8/31.
 * @author  hechao
 */
@RestController
@RequestMapping("/service/account")
public class AccountController extends AbstractController {

    @Inject
    private OpenAccountService accountService;

    @PostMapping("open")
    public void open(@RequestBody HttpRequestDTO<OpenAccountDTO> requestDTO) throws Exception {

        accountService.process(requestDTO.getContext());

        this.hashCode();
    }
}