package com.yukoon.bargain.realms;

import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.utils.EncodeUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class UserRealm extends AuthorizingRealm {
	//认证的方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//		AuthenticationException//1. 把 AuthenticationToken 转换为 UsernamePasswordToken
//		UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
//		//2. 从 UsernamePasswordToken 中获取 username,password构建user
//		String username = upToken.getUsername();
//		User user = new User().setUsername(username).setPassword(EncodeUtil.encodePassword(String.valueOf(upToken.getPassword()),username));
//		//3. 从数据库获取User准备进行比对
//		User user_temp = userService.login(user);
//		//4. 异常用户抛出异常
//		if (user_temp == null) {
//			throw new UnknownAccountException("用户不存在!");
//		}
//		//5. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回.
//		Object principal = user_temp.getId();	//这里principal传入ID比较方便
//		ByteSource credentialsSalt = ByteSource.Util.bytes(user_temp.getUsername());
//		Object credentials = new SimpleHash("MD5", upToken.getCredentials(),credentialsSalt, 1024);
//		String realmName = getName();
//		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
//		return info;
		return null;
	}

	//授权方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		return null;
	}


}
