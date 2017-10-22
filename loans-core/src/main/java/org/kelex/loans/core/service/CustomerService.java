package org.kelex.loans.core.service;

import org.kelex.loans.core.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * Created by hechao on 2017/8/10.
 */
@Service
@Transactional
public class CustomerService {
    @Inject
    CustomerRepository customerRepository;
}
