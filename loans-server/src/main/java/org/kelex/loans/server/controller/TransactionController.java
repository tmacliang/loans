package org.kelex.loans.server.controller;

import org.kelex.loans.bean.PaymentOrderDTO;
import org.kelex.loans.bean.RetailDTO;
import org.kelex.loans.core.service.PaymentOrderService;
import org.kelex.loans.core.service.RetailService;
import org.kelex.loans.web.AbstractController;
import org.kelex.loans.web.HttpRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

/**
 * Created by hechao on 2017/10/18.
 */
@RestController
@RequestMapping("/service/transaction")
public class TransactionController extends AbstractController {

    @Inject
    RetailService retailService;

    @Inject
    private PaymentOrderService paymentOrderService;

    @PostMapping("retail")
    public void retail(@RequestBody HttpRequestDTO<RetailDTO> requestDTO) throws Exception {
        retailService.process(requestDTO.getContext());
    }

    @PostMapping("paymentOrder")
    public void paymentOrder(@RequestBody HttpRequestDTO<PaymentOrderDTO> requestDTO) throws Exception {
        paymentOrderService.process(requestDTO.getContext());
    }
}
