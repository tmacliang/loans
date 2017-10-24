package org.kelex.loans.core.service;

import org.kelex.loans.core.cache.ActiveCache;
import org.kelex.loans.core.entity.*;
import org.kelex.loans.core.repository.*;
import org.kelex.loans.enumeration.CurrencyCodeEnum;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by hechao on 2017/9/4.
 */
@Service
public class EntryService {

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ActProcCtrlRepository actProcCtrlRepository;

    @Inject
    private PlanProcessCtrlRepository planProcessCtrlRepository;

    @Inject
    private CurrProcessCtrlRepository currProcessCtrlRepository;

    @Inject
    private PlanProfileRepository planProfileRepository;

    @Inject
    private StatusCodeRepository statusCodeRepository;

    @Inject
    private TxnProfileRepository txnProfileRepository;

    @Inject
    private TxnProcessCtrlRepository txnProcessCtrlRepository;

    @Inject
    private BalProcessCtrlRepository balProcessCtrlRepository;

    @Inject
    private BalCompProfileRepository balCompProfileRepository;

    @Inject
    private PaymentProcessCtrlRepository paymentProcessCtrlRepository;

    public static String getKey(BaseEntity entity, String... strings) {
        return getKey(entity.getClass(), strings);
    }

    public static String getKey(Class cls, String... strings) {
        StringBuilder builder = new StringBuilder(cls.getName()).append('@');
        for (String str : strings) {
            builder.append(str);
            builder.append('-');
        }
        return builder.toString();
    }

    public ActiveCache getCache(RepositoryProxy repository) {
        if (repository == null) {
            return null;
        }
        return repository.getCache();
    }

    /**
     * 从指定的数据仓库代理获取数据，如果 <code>repository.getCache()</code> 不为 <code>null</code>。
     *
     * @param repository 数据仓库代理
     * @param key        键值
     * @param cls        数据类
     * @return
     */
    private <T> T fromCache(Object key, Class<T> cls, RepositoryProxy repository) {
        ActiveCache cache = getCache(repository);
        if (cache != null && cache.isEnabled()) {
            return cache.get(key, cls);
        }
        return null;
    }

    public ProductEntity findOneProduct(String productId, String actTypeId) {
        return this.findOneProduct(productId, actTypeId, null);
    }

    /**
     * 将数据添加到缓存，如果 <code>repository.getCache()</code> 不为 <code>null</code>。
     *
     * @param repository
     * @param mapper
     * @param data
     * @param <T>
     */
    private <T> T toCache(Object mapper, T data, RepositoryProxy repository) {
        ActiveCache cache = getCache(repository);
        if (cache != null && cache.isEnabled()) {
            cache.put(mapper, data);
        }
        return data;
    }

    /**
     * 根据指定的产品代码、模块代码及数据仓库代理进行数据查找。如果数据仓库代理参数 <code>repository.getCache()</code> 不为 <code>null</code>，优先在代理的缓存中进行查找，否则直接查询数据库。
     *
     * @param productId  产品代码
     * @param actTypeId  模块代码
     * @param repository 数据仓库代理
     * @return 产品实例
     */
    public ProductEntity findOneProduct(String productId, String actTypeId, RepositoryProxy repository) {
        String key = getKey(ProductEntity.class, productId, actTypeId);

        ProductEntity product = fromCache(key, ProductEntity.class, repository);
        if (product != null) {
            return product;
        }

        //TODO:扩展Repository.findOne()方法支持多参数
        ProductId embeddedId = new ProductId();
        embeddedId.setProductId(productId);
        embeddedId.setActTypeId(actTypeId);
        product = productRepository.findOne(embeddedId);
        return toCache(key, product, repository);
    }

    /**
     * @param productId
     * @param actTypeId
     * @param repository
     * @return
     */
    public ActProcessCtrlEntity findOneActProcessCtrl(String productId, String actTypeId, RepositoryProxy repository) {
        String key = getKey(ActProcessCtrlEntity.class, productId, actTypeId);
        ActProcessCtrlEntity entity = fromCache(key, ActProcessCtrlEntity.class, repository);
        if (entity != null) {
            return entity;
        }

        //TODO:扩展Repository.findOne()方法支持多参数
        ProductId embeddedId = new ProductId();
        embeddedId.setProductId(productId);
        embeddedId.setActTypeId(actTypeId);
        entity = actProcCtrlRepository.findOne(embeddedId);
        return toCache(key, entity, repository);
    }

    public ActProcessCtrlEntity findOneActProcessCtrl(String productId, String moduleCode) {
        return this.findOneActProcessCtrl(productId, moduleCode, null);
    }

    /**
     * @param planId
     * @param repository
     * @return
     */
    public PlanProfileEntity findOnePlanProfile(String planId, RepositoryProxy repository) {
        String key = getKey(PlanProfileEntity.class, planId);
        PlanProfileEntity entity = fromCache(key, PlanProfileEntity.class, repository);
        if (entity != null) {
            return entity;
        }

        entity = planProfileRepository.findOne(planId);
        return toCache(key, entity, repository);
    }

    /**
     * @param productId  产品ID
     * @param actTypeId  账户类型ID
     * @param planId     计划ID
     * @param repository 仓库
     * @return
     */
    public PlanProcessCtrlEntity findOnePlanProcessCtrl(String productId, String actTypeId, String planId, RepositoryProxy repository) {
        String key = getKey(PlanProcessCtrlEntity.class, productId, actTypeId, planId);
        PlanProcessCtrlEntity entity = fromCache(key, PlanProcessCtrlEntity.class, repository);
        if (entity != null) {
            return entity;
        }
        entity = planProcessCtrlRepository.findOne(productId, actTypeId, planId);
        return toCache(key, entity, repository);
    }

    public PlanProcessCtrlEntity findOnePlanProcessCtrl(String productId, String actTypeId, String planId) {
        return findOnePlanProcessCtrl(productId, actTypeId, planId, null);
    }

    /**
     * @param repository
     * @return
     */
    public CurrProcessCtrlEntity findOneCurrProcessCtrl(CurrencyCodeEnum code, String unit, RepositoryProxy repository) {
        String key = getKey(CurrProcessCtrlEntity.class, code.name(), unit);
        CurrProcessCtrlEntity entity = fromCache(key, CurrProcessCtrlEntity.class, repository);
        if (entity != null) {
            return entity;
        }

        //TODO:扩展Repository.findOne()方法支持多参数
        CurrProcessCtrlId ctrlId = new CurrProcessCtrlId();
        ctrlId.setCurrencyCodeEnum(code);
        ctrlId.setUnit(unit);
        entity = currProcessCtrlRepository.findOne(ctrlId);
        return toCache(key, entity, repository);
    }

    /**
     * @param code
     * @param type
     * @param repository
     * @return
     */
    public StatusCodeEntity findOneStatusCode(String code, String type, RepositoryProxy repository) {
        String key = getKey(StatusCodeEntity.class, code, type);
        StatusCodeEntity entity = fromCache(key, StatusCodeEntity.class, repository);
        if (entity != null) {
            return entity;
        }

        //TODO:扩展Repository.findOne()方法支持多参数
        StatusCodeId id = new StatusCodeId();
        id.setStatusCode(code);
        id.setStatusType(type);
        entity = statusCodeRepository.findOne(id);
        return toCache(key, entity, repository);
    }

    /**
     * @param txnCode
     * @param repository
     * @return
     */
    public TxnProfileEntity findOneTxnProfile(String txnCode, RepositoryProxy repository) {
        String key = getKey(TxnProfileEntity.class, txnCode);
        TxnProfileEntity entity = fromCache(key, TxnProfileEntity.class, repository);
        if (entity != null) {
            return entity;
        }

        entity = txnProfileRepository.findOne(txnCode);
        return toCache(key, entity, repository);
    }

    /**
     * @param repository
     * @return
     */
    public TxnProcessCtrlEntity findOneTxnProcessCtrl(String productId, String actTypeId, String txnCode, RepositoryProxy repository) {
        String key = getKey(TxnProcessCtrlEntity.class, productId, actTypeId, txnCode);
        TxnProcessCtrlEntity entity = fromCache(key, TxnProcessCtrlEntity.class, repository);
        if (entity != null) {
            return entity;
        }

        //TODO:扩展Repository.findOne()方法支持多参数
        TxnProcessCtrlId ctrlId = new TxnProcessCtrlId();
        ctrlId.setProductId(productId);
        ctrlId.setActTypeId(actTypeId);
        ctrlId.setTxnCode(txnCode);
        entity = txnProcessCtrlRepository.findOne(ctrlId);
        return toCache(key, entity, repository);
    }

    /**
     * @param productId
     * @param actTypeId
     * @param bcpId
     * @param repository
     * @return
     */
    public BalProcessCtrlEntity findOneBalProcessCtrl(String productId, String actTypeId, String bcpId, RepositoryProxy repository) {
        String key = getKey(BalProcessCtrlEntity.class, productId, actTypeId, bcpId);
        BalProcessCtrlEntity entity = fromCache(key, BalProcessCtrlEntity.class, repository);
        if (entity != null) {
            return entity;
        }

        //TODO:扩展Repository.findOne()方法支持多参数
        BalProcessCtrlId ctrlId = new BalProcessCtrlId();
        ctrlId.setProductId(productId);
        ctrlId.setActTypeId(actTypeId);
        ctrlId.setBcpId(bcpId);
        entity = balProcessCtrlRepository.findOne(ctrlId);
        return toCache(key, entity, repository);
    }

    /**
     * @param id
     * @param repository
     * @return
     */
    public BalCompProfileEntity findOneBalCompProfile(String id, RepositoryProxy repository) {
        String key = getKey(BalCompProfileEntity.class, id);
        BalCompProfileEntity entity = fromCache(key, BalCompProfileEntity.class, repository);
        if (entity != null) {
            return entity;
        }
        entity = balCompProfileRepository.findOne(id);
        return toCache(key, entity, repository);
    }

    /**
     * 查找指定的还款控制
     *
     * @param productId
     * @param actTypeId
     * @param method
     * @param repository
     * @return
     */
    public PaymentProcessCtrlEntity findOnePaymentProcessCtrl(String productId, String actTypeId, String method, RepositoryProxy repository) {
        String key = getKey(PaymentProcessCtrlEntity.class, productId, actTypeId, method);
        PaymentProcessCtrlEntity entity = fromCache(key, PaymentProcessCtrlEntity.class, repository);
        if (entity != null) {
            return entity;
        }
        PaymentProcessCtrlId ctrlId = new PaymentProcessCtrlId();
        ctrlId.setProductId(productId);
        ctrlId.setActTypeId(actTypeId);
        ctrlId.setPaymentMethod(method);
        entity = paymentProcessCtrlRepository.findOne(ctrlId);
        return toCache(key, entity, repository);
    }
}
