package com.caidonglun.realm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.caidonglun.entity.User;

public class MyRealm extends AuthorizingRealm {

	/**
	 * 权限认证、角色认证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//		这里获取的是用户名
		Object userName = principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
		Set<String> roles=new HashSet<String>();
		
		String RoleSql="select * from users u,t_role r where u.roleId=r.id and username=?";
		   String url = "jdbc:mysql://localhost:3306/test" ;    
		     String username = "root" ;   
		     String password = "123456" ;   
		     try{   
		    	 Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
		    Connection con =DriverManager.getConnection(url , username , password ) ;   
		    PreparedStatement preparedStatement=con.prepareStatement(RoleSql);
		    preparedStatement.setString(1,userName.toString());
		    ResultSet executeQuery = preparedStatement.executeQuery();
		    if(executeQuery.next()) {
		    	roles.add(executeQuery.getString("roleName"));
		    }
		    con.close();
		    simpleAuthorizationInfo.setRoles(roles);
		     }catch(Exception se){   
		    System.out.println("数据库连接失败！");   
		    se.printStackTrace() ;   
		     }   
		
		     String permissonSql="select * from users u,t_role r,t_permission p where u.roleId=r.id and p.roleId=r.id and username=?";
		     Set<String> permissions=new HashSet<String>();
			     try{   
			    	 Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
			    Connection con =DriverManager.getConnection(url , username , password ) ;   
			    PreparedStatement preparedStatement=con.prepareStatement(permissonSql);
			    preparedStatement.setString(1,userName.toString());
			    ResultSet executeQuery = preparedStatement.executeQuery();
			    if(executeQuery.next()) {
			    	permissions.add(executeQuery.getString("permissionName"));
			    }
			    con.close();
			    simpleAuthorizationInfo.setStringPermissions(permissions);
		     
			     }catch(Exception se){   
					    System.out.println("数据库连接失败！");   
					    se.printStackTrace() ;   
					     }
		     return simpleAuthorizationInfo;
	}

	/**
	 * 身份认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		Object userName = token.getPrincipal();
		System.out.println("username=====>"+userName.toString());
		User user=null;
		String sql="select * from users where username=?";
		   String url = "jdbc:mysql://localhost:3306/test" ;    
		     String username = "root" ;   
		     String password = "123456" ;   
		     try{   
		    	 Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
		    Connection con =DriverManager.getConnection(url , username , password ) ;   
		    PreparedStatement preparedStatement=con.prepareStatement(sql);
		    preparedStatement.setString(1,userName.toString() );
		    ResultSet executeQuery = preparedStatement.executeQuery();
		    if(executeQuery.next()) {
		    	user=new User();
		    	user.setId(executeQuery.getInt("id"));
		    	user.setUsername(executeQuery.getString("username"));
		    	user.setPassword(executeQuery.getString("password"));
		    }
		    		if(user!=null) {
		    			con.close();
		    			return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),getClass().getName());
		    		}else {
		    			return null;
		    		}     
		     }catch(Exception se){   
		    System.out.println("数据库连接失败！");   
		    se.printStackTrace() ;   
		     }   
		return null;
	}

}
