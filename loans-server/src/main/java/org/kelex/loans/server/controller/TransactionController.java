package org.kelex.loans.server.controller;

import org.kelex.loans.bean.*;
import org.kelex.loans.core.service.*;
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
    private RetailService retailService;

    @Inject
    private PaymentOrderService paymentOrderService;

    @Inject
    private PrePaymentService prePaymentService;

    @Inject
    private ChangeLimitService changeLimitService;

    @Inject
    private RiskControlService riskControlService;

    @Inject
    private RefundService refundService;

    @PostMapping("retail")
    public void retail(@RequestBody HttpRequestDTO<RetailDTO> requestDTO) throws Exception {
        retailService.process(requestDTO.getContext());
    }

    @PostMapping("paymentOrder")
    public void paymentOrder(@RequestBody HttpRequestDTO<PaymentOrderDTO> requestDTO) throws Exception {
        paymentOrderService.process(requestDTO.getContext());
    }

    @PostMapping("prePayment")
    public void prePayment(@RequestBody HttpRequestDTO<PrePaymentDTO> requestDTO) throws Exception {
        prePaymentService.process(requestDTO.getContext());
    }

    @PostMapping("changeLimit")
    public void changeLimit(@RequestBody HttpRequestDTO<ChangeLimitDTO> requestDTO) throws Exception{
        changeLimitService.process(requestDTO.getContext());
    }

    @PostMapping("riskControl")
    public void riskControl(@RequestBody HttpRequestDTO<RiskControlDTO> requestDTO) throws Exception{
        riskControlService.process(requestDTO.getContext());
    }

    @PostMapping("refund")
    public void refund(@RequestBody HttpRequestDTO<RefundRequest> requestDTO) throws Exception{
        refundService.process(requestDTO.getContext());
    }

}
