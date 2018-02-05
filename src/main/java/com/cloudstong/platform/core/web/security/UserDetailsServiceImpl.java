package com.cloudstong.platform.core.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cloudstong.platform.core.common.Constants;
import com.cloudstong.platform.core.util.AppUtil;
import com.cloudstong.platform.core.util.BeanUtils;
import com.cloudstong.platform.system.dao.SysRoleDao;
import com.cloudstong.platform.system.model.SysRole;
import com.cloudstong.platform.system.model.SysUser;
import com.cloudstong.platform.system.service.IUserService;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    IUserService userService;
    @Resource
    SysRoleDao sysRoleDao;

    public UserDetailsServiceImpl() {
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IUserService userService = (IUserService)AppUtil.getBean(IUserService.class);
        SysUser user = userService.getUserByAccount(username);
        if (BeanUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("用户：" + username + "不存在");
        } else if (!user.isEnabled()) {
            throw new DisabledException("用户：" + username + "已经被禁用");
        } else {
            Collection<GrantedAuthority> collection = new ArrayList();
            if (user.isSuper()) {
                collection.add(Constants.ROLE_GRANT_SUPER);
            } else {
                List<SysRole> userRoleList = this.sysRoleDao.getByUserId(user.getId());
                Iterator var7 = userRoleList.iterator();

                while(var7.hasNext()) {
                	SysRole sysRole = (SysRole)var7.next();
                    GrantedAuthority role = new SimpleGrantedAuthority(sysRole.getAlias());
                    collection.add(role);
                }
            }

            UserDetails userDetails = this.getDefault(user, collection);
            return userDetails;
        }
    }

    private UserDetails getDefault(SysUser user, Collection<GrantedAuthority> collection) {
        return new User(user.getUsername(), user.getPassword(), collection);
    }

}
