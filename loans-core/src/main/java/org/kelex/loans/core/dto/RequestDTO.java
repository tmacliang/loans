package org.kelex.loans.core.dto;
import org.kelex.loans.core.context.TransactionRequestContext;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

/**
 * Created by hechao on 2017/8/29.
 */
public class RequestDTO<T> {

    private TransactionRequestContext<T> context;

    protected GenericRequest generic;

    protected T data;

    protected PageRequest paging;

    public GenericRequest getGeneric() {
        return generic;
    }

    public void setGeneric(GenericRequest generic) {
        this.generic = generic;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public PageRequest getPaging() {
        return paging;
    }

    public void setPaging(PageRequest paging) {
        this.paging = paging;
    }

    public boolean isRevised(){
        String version = getContext().getParent().getVersion();
        if(version==null || generic==null || generic.getVersion()==null){
            return false;
        }
        return version.equals(generic.getVersion());
    }

    public synchronized TransactionRequestContext<T> getContext(){
        if(context == null){
            context = new TransactionRequestContext<T>(this);
        }
        return context;
    }

    public LocalDate getBusinessDate(){
        if(generic==null){

        }
        return LocalDate.now();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RequestDTO{");
        sb.append("data=").append(data);
        sb.append(", generic=").append(generic);
        sb.append(", paging=").append(paging);
        sb.append("}");
        return sb.toString();
    }
}
