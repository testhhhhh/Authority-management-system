package com.lear.service;

import com.lear.domain.Permission;
import com.lear.domain.Role;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface IRoleService {

    List<Role> findAll() throws Exception;

    void save(Role role) throws Exception;

    Role findById(String roleId) throws Exception;

    void deleteRoleById(String roleId) throws Exception;

    List<Permission> findOtherPermissions(String roleId) throws Exception;

    void addPermissionToRole(String roleId, String[] permissionId) throws Exception;
}
