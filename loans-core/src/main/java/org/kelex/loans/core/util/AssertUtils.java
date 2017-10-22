package org.kelex.loans.core.util;

import org.kelex.loans.ArgumentMessageEnum;
import org.kelex.loans.core.ServerRuntimeException;
import org.kelex.loans.core.context.ApplicationContextLoader;
import org.kelex.loans.core.context.ServerApplicationContext;
import org.slf4j.helpers.MessageFormatter;

/**
 * Created by hechao on 2017/9/1.
 */
public abstract class AssertUtils {

    private static Object[] EMPTY_MESSAGE_ARGS = new Object[0];

    private static ServerApplicationContext getContext(){
        return ApplicationContextLoader.getCurrentServerApplicationContext();
    }

    public static String getMessage(boolean isCode, String msg, Object... args){
        if(isCode){
            return getContext().getMessage(msg, args);
        }
        return MessageFormatter.format(msg, args).getMessage();
    }

    public static void notNull(Object object, ArgumentMessageEnum message){
        if(object==null){
            throw new ServerRuntimeException(message.ERROR_CODE, getMessage(true, message.MESSAGE_CODE));
        }
    }

    public static void isNull(Object object, ArgumentMessageEnum message){
        if(object!=null){
            throw new ServerRuntimeException(message.ERROR_CODE, getMessage(true, message.MESSAGE_CODE));
        }
    }


    public static void size(Integer size, Integer min, Integer max, ArgumentMessageEnum message){
        if(size.compareTo(min)<0 || size.compareTo(max)>0){
            throw new ServerRuntimeException(message.ERROR_CODE, getMessage(true, message.MESSAGE_CODE));
        }
    }
    /**
     * >=
     */
    public static void notLessThan(int size, int min, ArgumentMessageEnum message){

    }

    /**
     *  <=
     */
    public static void notMoreThan(int size, int max, ArgumentMessageEnum message){

    }
}