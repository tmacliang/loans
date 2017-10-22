package org.kelex.loans;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by hechao on 2017/7/22.
 */
public abstract class SystemUtils {
    public static long id(String key) throws NoSuchAlgorithmException {
        MessageDigest digest=MessageDigest.getInstance("MD5");
        byte[] bytes = digest.digest((key + UUID.randomUUID().toString()).getBytes());
        String s = new BigInteger(bytes).toString(16).substring(8, 24);
        Long.valueOf(s,16);
        return 0;
    }
//
//    private final static char MINUS='-';
//
//    public final static String SHA256="SHA-256";
//
//    public final static String SHA1="SHA-1";
//
//    private static final char[] sixtyTwoCodes=new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
//            , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'i', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
//            , 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'I', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
//
//    /**
//     * 十进制数字转六十二进制
//     * @param i
//     * @return
//     */
//    public static String toSixtyTwoString(long i){
//        long a=Math.abs(i);
//        long len=sixtyTwoCodes.length;
//        long mod=0;
//        StringBuilder builder=new StringBuilder();
//        while (a>0){
//            mod=a%len;
//            builder.append(sixtyTwoCodes[(int)mod]);
//            a=(a-mod)/len;
//        }
//        if(i<0){
//            builder.append(MINUS);
//        }
//        return builder.reverse().toString();
//    }
//
//    public static String getSixtyTwoUUID(){
//        UUID uuid = UUID.randomUUID();
//
//        return "";
//    }
//
//    public static String getUUID(String algorithm) throws NoSuchAlgorithmException {
//        UUID uuid = UUID.randomUUID();
//        MessageDigest digest=MessageDigest.getInstance(algorithm);
//        BigInteger bigint=new BigInteger(digest.digest(uuid.toString().getBytes()));
//        return bigint.toString(16);
//    }
}
