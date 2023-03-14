package com.it.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.it.vo.LoginUserVO;
import com.it.entity.Permission;
import com.it.entity.User;
import com.it.service.PermissionService;
import com.it.service.RoleService;
import com.it.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 自定义Realm
 */
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    /**
     * AccountRealm的域名
     * @return
     */
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 认证方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //获取当前登录主体
        String userName = (String) token.getPrincipal();
        try {
            //根据用户名查询用户信息的方法
            User user = userService.findUserByUserName(userName);

            //对象不为空
            if(user!=null){
                //创建当前登录用户对象
                if (user.getAvailable()==0){
                    return null;
                }
                //创建登录用户对象，传入用户信息，角色列表，权限列表
               LoginUserVO loginUserVO = new LoginUserVO(user,null,null);
                //创建条件构造器对象
                QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("type","permission");//只查权限不查菜单

                //根据当前登录用户ID查询该用户拥有的角色列表
                Set<Integer> currentUserRoleIds = userService.findRoleByUserId(user.getUid());
                //创建集合保存每个角色下拥有的权限菜单ID
                Set<Integer> pids = new HashSet<>();
                //循环遍历当前用户拥有的角色列表
                for (Integer roleId : currentUserRoleIds) {
                    //4.根据角色ID查询每个角色下拥有的权限菜单
                    Set<Integer> permissionIds = roleService.findRolePermissionByRoleId(roleId);
                    //5.将查询出来的权限id放到集合中
                    pids.addAll(permissionIds);
                }
                //创建集合保存权限
                List<Permission> list = new ArrayList<>();
                //判断pids集合是否有值
                if(pids!=null && pids.size()>0){
                    queryWrapper.in("id",pids);
                    //执行查询
                    list = permissionService.list(queryWrapper);
                }
                //查询权限编码
                Set<String> perCodes = new HashSet<>();
                //循环每一个菜单
                for (Permission permission : list) {
                    perCodes.add(permission.getPercode());
                }
                //给权限集合赋值
                loginUserVO.setPermissions(perCodes);
                //创建盐值(以用户名作为盐值)
                ByteSource salt = ByteSource.Util.bytes(user.getSalt());
                //创建身份验证对象
                //参数1：当前登录对象  参数2：密码  参数3：盐值 参数4：域名
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUserVO,user.getPassword(),salt,getName());
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 授权方法
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //创建授权对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        LoginUserVO loginUserVO = (LoginUserVO) principals.getPrimaryPrincipal();
        Set<String> permissions = loginUserVO.getPermissions();
        //3.判断当前登录用户是否是超级管理员
        if(loginUserVO.getUser().getType()== 0){
            //超级管理员拥有所有操作权限
            simpleAuthorizationInfo.addStringPermission("*:*");
        }else{//普通用户
            //判断权限集合是否有数据
            if(permissions!=null && permissions.size()>0){
                //非超级管理员需要根据自己拥有的角色进行授权
                simpleAuthorizationInfo.addStringPermissions(permissions);
            }
        }
        return simpleAuthorizationInfo;
    }


}
