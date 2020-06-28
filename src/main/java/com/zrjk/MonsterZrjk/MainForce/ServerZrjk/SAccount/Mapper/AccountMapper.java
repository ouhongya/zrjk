package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Mapper;

import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.Account;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.AccountPower;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.ParentFunction;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.Role;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SHealth.Beans.Doctor;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface AccountMapper {
    Account findAccountByUsername(String username);

    void addAccount(Account account);

    void addAccountButton(@Param("id") String id, @Param("buttonIds") List<String> buttonIds);

    void addAccountFunction(@Param("id") String id, @Param("functionIds") List<String> functionIds);

    Role findRoleByName(String roleName);

    void addRole(@Param("role") Role role);

    void addRoleFunction(@Param("id") String id, @Param("functionIds") Set<String> functionIds);

    List<Account> findAllAccount(@Param("search") String search);

    List<ParentFunction> findAccountFB(String uid);

    List<Role> findAllRole(@Param("search") String search);

    void addRoleButton(@Param("id") String id, @Param("buttonIds") Set<String> buttonIds);

    List<ParentFunction> findAllFB();

    void updateAccount(@Param("account") Account account);

    void deleteFucntionToAccount(String id);

    void deleteButtonToAccount(String id);

    void updateRoleNameById(@Param("rid") String id, @Param("rname") String roleName);

    void deleteFunctionToRole(String id);

    void deleteButtonToRole(String id);

    List<String> findFunctionIdByUid(String uid);

    void updateAccountStatus(@Param("uid") String uid, @Param("status") int i);

    List<String> findFunctionIdsByRId(String id);

    void updateRoleStatus(@Param("rid") String id, @Param("status") int i);

    List<ParentFunction> findFBByRoleId(String id);

    Account findAccountByPhoneNum(String phoneNum);

    List<Account> findAccountByRid(String rid);

    List<AccountPower> findAccountPower(String uid);

    String findRoleNameById(String roleId);

    List<String> findFids();

    List<String> findBids();

    List<String> findButtonUrlById(String id);

    Account findAccountById(String uid);

    List<Doctor> findDoctorByUid(String id);

    void addAccountDoctor(@Param("uid") String id,@Param("dids") List<String> doctorIds);

    void deleteDoctorToAccount(String id);

    List<String> findDoctorIdByUid(String uid);

    // List<ParentFunction> findFunctionByRoleId(String roleId);
}
