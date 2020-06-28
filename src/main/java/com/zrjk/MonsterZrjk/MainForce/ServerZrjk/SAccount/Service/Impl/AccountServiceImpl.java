package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.Common.Utils.TokenUtil;
import com.zrjk.MonsterZrjk.Common.Utils.UUIDGenerator;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.*;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Mapper.AccountMapper;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

//                            _ooOoo_
//                           o8888888o
//                           88" . "88
//                           (| -_- |)
//                            O\ = /O
//                        ____/`---'\____
//                      .   ' \\| |// `.
//                       / \\||| : |||// \
//                     / _||||| -:- |||||- \
//                       | | \\\ - /// | |
//                     | \_| ''\---/'' | |
//                      \ .-\__ `-` ___/-. /
//                   ___`. .' /--.--\ `. . __
//                ."" '< `.___\_<|>_/___.' >'"".
//               | | : `- \`.;`\ _ /`;.`/ - ` : | |
//                 \ \ `-. \_ __\ /__ _/ .-` / /
//         ======`-.____`-.___\_____/___.-`____.-'======
//                            `=---='
//
//         .............................................
//                  佛祖保佑             永无BUG
//          佛曰:
//                  写字楼里写字间，写字间里程序员；
//                  程序人员写程序，又拿程序换酒钱。
//                  酒醒只在网上坐，酒醉还来网下眠；
//                  酒醉酒醒日复日，网上网下年复年。
//                  但愿老死电脑间，不愿鞠躬老板前；
//                  奔驰宝马贵者趣，公交自行程序员。
//                  别人笑我忒疯癫，我笑自己命太贱；
//
//                  不见满街漂亮妹，哪个归得程序员？

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private TokenUtil tokenUtil;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    //token过期时间
    @Value("${tokenTime}")
    private Long tokenTime;

    @Value("${powerId}")
    private String powerId;

    @Autowired
    private AccountMapper accountMapper;



    /**
     * 添加用户
     * @param account
     * @return
     */
    @Override
    public Integer addAccount(Account account) throws Exception {
        //查询用户名是否重复
        Account account1=accountMapper.findAccountByUsername(account.getUsername());
        if(account1==null){
            //查询用户手机号是否重复
            Account account2=accountMapper.findAccountByPhoneNum(account.getPhoneNum());
            if(account2==null){
                account.setId(UUIDGenerator.uuid());
                account.setCreatedTime(new Date());
                //将密码加密
                account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
                //可以添加
                accountMapper.addAccount(account);
                //添加用户功能 和 按钮
                accountMapper.addAccountButton(account.getId(),account.getButtonIds());
                accountMapper.addAccountFunction(account.getId(),account.getFunctionIds());
                //如果有健康咨询权限  添加医生与账户关系
                for (String functionId : account.getFunctionIds()) {
                        if(powerId.equals(functionId)){
                            accountMapper.addAccountDoctor(account.getId(),account.getDoctorIds());
                            break;
                        }
                }
                return 1;
            }else {
                return 2;
            }
        }else {
            return 3;
        }
    }

    /**
     * 添加角色
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public boolean addRole(Role role) throws Exception {
        //查询角色名是否重复
        Role role1=accountMapper.findRoleByName(role.getRoleName());
        if(role1==null){
            //可以添加
            role.setId(UUIDGenerator.uuid());
            role.setCreatedTime(new Date());
            //添加角色基本信息
            accountMapper.addRole(role);
            //添加角色功能
            accountMapper.addRoleFunction(role.getId(),role.getFunctionIds());
            //添加角色按钮
            accountMapper.addRoleButton(role.getId(),role.getButtonIds());
            return true;
        }else {
            return false;
        }
    }


    /**
     * 查询所有用户
     * @param paramBean
     * @return
     * @throws Exception
     */
    @Override
    public  PageInfo<Account> findAllAccount(ParamBean paramBean) throws Exception {
            String orderBy=paramBean.getSortField()+" "+paramBean.getSortWay();
            PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),orderBy);
            List<Account> accounts=accountMapper.findAllAccount(paramBean.getSearch());
            PageInfo<Account> pageInfo = new PageInfo<>(accounts);
            return pageInfo;
    }


    /**
     * 通过id获取用户拥有的功能和按钮
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public List<ParentFunction> findAccountFBByUid(String id, HttpServletRequest request) throws Exception{
            if("1".equals(id)){
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if("guess".equals(cookie.getName())){
                        String uid = tokenUtil.getUidFromToken(cookie.getValue());
                        id=uid;
                    }else {
                        return null;
                    }
                }
            }
            //通过uid查询用户功能
            List<ParentFunction> parentFunctions=accountMapper.findAccountFB(id);

            parentFunctions=addDoctorPower(parentFunctions,id);
        return parentFunctions;
    }


    private List<ParentFunction> addDoctorPower(List<ParentFunction> parentFunctions,String uid){
        //如果用户拥有健康咨询权限  查询账户能代表的医生
        for (ParentFunction parentFunction : parentFunctions) {
            for (Function function : parentFunction.getFunctions()) {
                if(powerId.equals(function.getId())){
                    //查询医生
                    parentFunction.setDoctors(accountMapper.findDoctorByUid(uid));
                    break;
                }
            }
        }
        return parentFunctions;
    }
    /**
     * 查询所有角色
     * @return
     */
    @Override
    public PageInfo<Role> findAllRole(ParamBean paramBean) throws Exception {
        //分页和排序
        PageHelper.startPage(paramBean.getPage(),paramBean.getSize(),paramBean.getSortField()+" "+paramBean.getSortWay());
        List<Role> roles = accountMapper.findAllRole(paramBean.getSearch());
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        return pageInfo;
    }

    /**
     * 查询所有功能 按钮
     * @return
     * @throws Exception
     */
    @Override
    public List<ParentFunction> findAllFB() throws Exception {
        List<ParentFunction> parentFunctions=accountMapper.findAllFB();
        return parentFunctions;
    }


    /**
     *编辑账户
     * @param account
     */
    @Override
    public void updateAccount(Account account) throws Exception {
        //修改用户基本信息
        accountMapper.updateAccount(account);
        //删除用户的功能
        accountMapper.deleteFucntionToAccount(account.getId());
        //添加用户新功能
        if(account.getFunctionIds()!=null&&account.getFunctionIds().size()>0){
            accountMapper.addAccountFunction(account.getId(),account.getFunctionIds());
        }
        //删除用户按钮
        accountMapper.deleteButtonToAccount(account.getId());
        //添加用户新按钮
        if(account.getButtonIds()!=null&&account.getButtonIds().size()>0){
            accountMapper.addAccountButton(account.getId(),account.getButtonIds());
        }
        //如果拥有健康咨询权限  编辑可代表的医生
        for (String functionId : account.getFunctionIds()) {
            if(powerId.equals(functionId)){
                //删除原来的医生id
                accountMapper.deleteDoctorToAccount(account.getId());
                //添加新的医生
                accountMapper.addAccountDoctor(account.getId(),account.getDoctorIds());
            }
        }
    }


    /**
     * 编辑角色
     * @param role
     */
    @Override
    public Boolean updateRole(Role role) {
        //检查角色名称是否相同
        Role role2 = accountMapper.findRoleByName(role.getRoleName());
        if(role2==null||role2.getId().equals(role.getId())){
            //修改角色名称
            accountMapper.updateRoleNameById(role.getId(),role.getRoleName());
            //删除角色旧功能
            accountMapper.deleteFunctionToRole(role.getId());
            //添加角色新功能
            if(role.getFunctionIds()!=null&&role.getFunctionIds().size()>0){
                accountMapper.addRoleFunction(role.getId(),role.getFunctionIds());
            }
            //删除角色旧按钮
            accountMapper.deleteButtonToRole(role.getId());
            //添加角色新按钮
            if(role.getButtonIds()!=null&&role.getButtonIds().size()>0){
                accountMapper.addRoleButton(role.getId(),role.getButtonIds());
            }
            return true;
        }else {
            return false;
        }
    }


    /**
     * 删除账户
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteAccount(String uid) throws Exception {
        //查询该账户是否绑定了功能
        List<String> functionids=accountMapper.findFunctionIdByUid(uid);

        if(functionids==null||functionids.size()<=0){
            //没有绑定功能  将用户置位-1  删除全部按钮
            accountMapper.updateAccountStatus(uid,-1);

            accountMapper.deleteButtonToAccount(uid);
            return true;
        }else {
            return false;
        }
    }


    /**
     * 删除角色
     * @param id
     * @return
     */
    @Override
    public boolean deleteRole(String id) throws Exception {

        //检查该角色是否绑定了账户
        List<Account> accounts=accountMapper.findAccountByRid(id);

        if(accounts==null||accounts.size()<=0){
            //可以删除  将角色置位-1  删除全部按钮
            accountMapper.updateRoleStatus(id,-1);

            accountMapper.deleteButtonToRole(id);

            return true;
        }
        return false;
    }

    @Override
    public List<ParentFunction> findFBByRoleId(String id) throws Exception {

        List<ParentFunction> parentFunctions=accountMapper.findFBByRoleId(id);

        return parentFunctions;
    }

    @Override
    public List<AccountPower> findAccountPower(HttpServletRequest request) throws Exception {
        String token="";

        //从Request中获取cookie
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if("token".equals(cookie.getName())){
                token=cookie.getValue();
            }
        }

        //取出token中的uid
        String uid = tokenUtil.getUidFromToken(token);

        List<AccountPower> accountPowers = accountMapper.findAccountPower(uid);
        for (AccountPower accountPower : accountPowers) {
            accountPower.setPath("/"+accountPower.getName());
        }
        return accountPowers;
    }

    @Override
    public void adminR() throws Exception {
        List<String> fids=accountMapper.findFids();
        List<String> bids=accountMapper.findBids();

        Set<String> fset = new HashSet<>();

        for (String fid : fids) {
            fset.add(fid);
        }

        Set<String> bset = new HashSet<>();

        for (String bid : bids) {
            bset.add(bid);
        }

        Role role = new Role();
        role.setRoleName("超级管理员");
        role.setFunctionIds(fset);
        role.setButtonIds(bset);
        addRole(role);
    }

    @Override
    public void adminU(String roleId) throws Exception {
        List<String> fids = accountMapper.findFids();
        List<String> bids = accountMapper.findBids();

        Account account = new Account();
        account.setPhoneNum("13350756337");
        account.setFunctionIds(fids);
        account.setButtonIds(bids);
        account.setUsername("admin");
        account.setPassword("1122334455");
        account.setRoleId(roleId);
        account.setTrueName("周杰伦");
        addAccount(account);
    }


    /**
     * 查询当前登录用户基本信息
     * @param request
     * @return
     */
    @Override
    public Account findNowUser(HttpServletRequest request) {
       //从cookie中获取token
        String token=null;
        Cookie[] cookies = request.getCookies();

        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if("token".equals(cookie.getName())){
                    token=cookie.getValue();
                }
            }
        }

        if(token!=null){
            String uid = tokenUtil.getUidFromToken(token);

            //通过uid查询用户基本信息
            Account account = accountMapper.findAccountById(uid);
            return account;
        }

        return null;
    }
}
