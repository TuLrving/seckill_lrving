package com.show.seckill.utils;

import com.show.seckill.redis.RedisService;

public class UserUtil {


    private static void createUser(int count) throws Exception {
        RedisService redisService = new RedisService();
//        List<MiaoshaUser> users = new ArrayList<>(count);
//        //生成用户
//        for (int i = 0; i < count; i++) {
//            MiaoshaUser user = new MiaoshaUser();
//            user.setId(13000000000L + i);
//            user.setLoginCount(1);
//            user.setNickname("user" + i);
//            user.setRegisterDate(new Date());
//            user.setSalt("123456");
//            user.setPassword(MD5Util.inputPassTODBPass("123456", user.getSalt()));
//            users.add(user);
//        }
//        System.out.println("create user:" + users.size());
//        //插入数据库
//        Connection conn = DBUtil.getConn();
//        String sql = "insert into miaosha_user(login_count, nick_name, register_date, salt, password, id)values(?,?,?,?,?,?)";
//        PreparedStatement pstmt = conn.prepareStatement(sql);
//        for (int i = 0; i < users.size(); i++) {
//            MiaoshaUser user = users.get(i);
//            pstmt.setInt(1, user.getLoginCount());
//            pstmt.setString(2, user.getNickname());
//            pstmt.setTimestamp(3, new Timestamp(user.getRegisterDate().getTime()));
//            pstmt.setString(4, user.getSalt());
//            pstmt.setString(5, user.getPassword());
//            pstmt.setLong(6, user.getId());
//            pstmt.addBatch();
//        }
//        pstmt.executeBatch();
//        pstmt.close();
//        conn.close();
//        System.out.println("insert to db");
//        //登录，生成token
//        for (MiaoshaUser user : users) {
//            String token = UUIDUtil.uuid();
//            redisService.set(MiaoshaUserKey.token,token,user);
//            System.out.println(user.getId() + "," + token);
//        }
//        System.out.println("over");
    }

    public static void main(String[] args) throws Exception {
        createUser(5000);
    }

}
