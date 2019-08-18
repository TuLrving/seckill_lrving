package com.show.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Auther: 涂成
 * @Date: 2019/6/19 08:58
 * @Description: 两次md5加密用户密码
 */
public class MD5Util {

    private static final String salt = "14s56g4asdgaw3q329r8tsgasg1e3sj901*(&(^$%%$%^(&(";

    private static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 将用户输入的密码第一次md5
     *
     * @param inputPass
     * @return
     */
    public static String inputPassToFormPass(String inputPass) {
        String pass = inputPass + salt;
        return md5(pass);
    }

    /**
     * 将用户输入的密码第二次md5
     *
     * @param formPass
     * @param DBsalt
     * @return
     */
    public static String formPassToDBPass(String formPass, String DBsalt) {
        String pass = formPass + DBsalt;
        return md5(pass);
    }

    /**
     * 执行两次md5
     *
     * @param inputPass
     * @param DBSalt
     * @return
     */
    public static String inputPassTODBPass(String inputPass, String DBSalt) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, DBSalt);
        return dbPass;
    }

    public static void main(String[] args) {
//        System.out.println(inputPassToFormPass("15070833417"));//834b84a3528e1447f688f35e8379e235
        System.out.println(formPassToDBPass("834b84a3528e1447f688f35e8379e235", "123456"));
        String dbPassword = inputPassTODBPass("15070833417", "123456");//65167f430a424984d1116b1a45fbe702
        System.out.println(dbPassword);
    }


}
