package com.llkj.system.customer;

import com.llkj.common.util.MD5;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @ClassName CustomMd5PasswordEncoder
 * @Description TODO
 * @Author qing
 * @Date 2022/12/27 13:59
 * @Version 1.0
 */
@Component //必须添加注解，会被扫描，创建实例，并放入IoC容器，SpringSecurity就可以使用
//不需要开发者自己来使用它，SpringSecurity的流程中进行密码比较时会自动调用
public class CustomMd5PasswordEncoder implements PasswordEncoder {
    /**
     *
     * @param charSequence 明文
     * @return  密文
     */
    @Override
    public String encode(CharSequence charSequence) {
        return MD5.encrypt(charSequence.toString());
    }

    /**
     *
     * @param rawPassword 用户输入的密码 明文11111
     * @param encodedPassword  从数据库中查询出来的密码 密文 96e79218965eb72c92a549dd5a330112
     * @return
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {

        return encodedPassword.equals(MD5.encrypt(rawPassword.toString()));
        //return encodedPassword.equals(encode(rawPassword));
    }
}
