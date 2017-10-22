package org.kelex.loans.core.service;

import org.kelex.loans.core.entity.TxnSummaryEntity;
import org.kelex.loans.core.entity.TxnSummaryId;
import org.kelex.loans.core.repository.TxnSummaryRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by hechao on 2017/10/9.
 */
@Service
public class TxnSummaryService {

    @Inject
    TxnSummaryRepository txnSummaryRepository;

    public TxnSummaryEntity findOne(TxnSummaryId id){
        return null;
    }
}
