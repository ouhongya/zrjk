package com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Controller;

import com.github.pagehelper.PageInfo;
import com.zrjk.MonsterZrjk.Common.Beans.ParamBean;
import com.zrjk.MonsterZrjk.Common.Result.CommonCode;
import com.zrjk.MonsterZrjk.Common.Result.ResponseResult;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.Account;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.AccountPower;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.ParentFunction;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Beans.Role;
import com.zrjk.MonsterZrjk.MainForce.ServerZrjk.SAccount.Service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
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
//                  不见满街漂亮妹，哪个归得程序员？

@Api(tags = "账户接口-后台")
@Log4j2
@RestController
@RequestMapping(value = "/server/account")
public class SAccountController {

    @Autowired
    private AccountService accountService;

    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:sfs");




    /**
     * 添加后台账户
     * @return
     */
    @ApiOperation(value = "添加账户",httpMethod = "POST",notes = "只需要传用户基本信息  roleId  functionIds(功能id集合)  buttonIds(按钮id集合)   其他不用传")
    @PostMapping(value = "/addAccount")
    public ResponseResult addUser(@RequestBody Account account){
        try {
            //通过正则表达式校验用户手机号
            Pattern pattern = Pattern.compile("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$");
           if(pattern.matcher(account.getPhoneNum()).matches()){
               //手机号格式正确
               Integer isSucc = accountService.addAccount(account);
               if(1==isSucc){
                   return new ResponseResult(CommonCode.SUCCESS);
               }else if(2==isSucc){
                   return new ResponseResult(CommonCode.PHONE_NUM_REPEAT);
               }else {
                   return new ResponseResult(CommonCode.USERNAME_REPEAT);
               }
           }else {
               //手机号格式错误
               return new ResponseResult(CommonCode.PHONE_NUM_ERROR);
           }

        }catch (Exception e){
            log.error("添加账户异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }


    /**
     * 添加角色
     * @param role
     * @return
     **/
    @PostMapping(value = "/addRole")
    @ApiOperation(value = "添加角色",httpMethod = "POST")
    private ResponseResult addRole(@RequestBody Role role){
        try {
            if(accountService.addRole(role)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.ROLE_NAME_REPEAT);
            }
        }catch (Exception e){
            log.error("添加角色异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }


    /**
     * 查询和搜索所有用户
     * @param paramBean
     * @return
     */
    @ApiOperation(value = "查询所有用户",httpMethod = "GET",response = Account.class)
    @GetMapping(value = "/findAllAccount")
    public ResponseResult findAllUser(ParamBean paramBean){
        try {
            PageInfo<Account> pageInfo = accountService.findAllAccount(paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询所有账户异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }


    /**
     * 通过用户id查询用户功能和按钮  id传“1” 查当前用户的权限
     * @param id
     * @param request
     * @return
     */
    @ApiOperation(value = "通过用户id查询用户功能和按钮  id传“1” 查当前用户的权限")
    @ApiImplicitParam(name = "id",value = "用户id")
    @GetMapping(value = "/findAccountFB")
    public ResponseResult findAccountFB(String id, HttpServletRequest request){
        try {
            List<ParentFunction> parentFunctions = accountService.findAccountFBByUid(id,request);
            return new ResponseResult(CommonCode.SUCCESS,parentFunctions);
        }catch (Exception e){
            log.error("通过用户id查询用户功能和按钮异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    /**
     * 查询所有角色
     * @param paramBean
     * @return
     */
    @GetMapping(value = "/findAllRole")
    @ApiOperation(value = "查询所有角色",httpMethod = "GET")
    public ResponseResult findAllAndFunctionAndButton(ParamBean paramBean){
        try {
            PageInfo<Role> pageInfo = accountService.findAllRole(paramBean);
            return new ResponseResult(CommonCode.SUCCESS,pageInfo);
        }catch (Exception e){
            log.error("查询所有角色 功能 按钮 异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    /**
     * 查询所有功能 按钮
     * @return
     */
    @GetMapping(value = "/findAllFB")
    @ApiOperation(value = "查询所有功能和按钮",httpMethod = "GET")
    public ResponseResult findAllFB(){
        try {
            List<ParentFunction> parentFunctions=accountService.findAllFB();
            return new ResponseResult(CommonCode.SUCCESS,parentFunctions);
        }catch (Exception e){
            log.error("查询所有功能按钮异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    /**
     * 编辑用户
     * @param account
     * @return
     */
    @PostMapping(value = "/updateAccount")
    @ApiOperation(value = "编辑用户",httpMethod = "POST")
    public ResponseResult updateAccount(@RequestBody Account account){
        try {
            Pattern pattern = Pattern.compile("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$");
            if(pattern.matcher(account.getPhoneNum()).matches()){
                //手机格式正确
                accountService.updateAccount(account);
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                //手机格式错误
                return new ResponseResult(CommonCode.PHONE_NUM_ERROR);
            }

        }catch (Exception e){
            log.error("编辑账户异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return  new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    /**
     * 编辑角色
     * @param role
     * @return
     */
    @PostMapping(value = "/updateRole")
    @ApiOperation(value = "编辑角色",httpMethod = "POST")
    public ResponseResult updateRole(@RequestBody Role role){
        try {
            if(accountService.updateRole(role)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.ROLE_NAME_REPEAT);
            }
        }catch (Exception e){
            log.error("编辑角色异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @GetMapping(value = "/deleteAccount")
    @ApiOperation(value = "删除账户",httpMethod = "GET")
    @ApiImplicitParam(name = "uid",value = "用户id")
    public ResponseResult deleteAccount(String uid){
        try {
            if(accountService.deleteAccount(uid)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.CANNOT_DELETE_ACCOUNT);
            }
        }catch (Exception e){
            log.error("删除用户异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }


    @GetMapping(value = "/deleteRole")
    @ApiOperation(value = "删除角色",httpMethod = "GET")
    @ApiImplicitParam(name = "id",value = "角色id")
    public ResponseResult deleteRole(String id){
        try {
            if(accountService.deleteRole(id)){
                return new ResponseResult(CommonCode.SUCCESS);
            }else {
                return new ResponseResult(CommonCode.CANNOT_DELETE_ROLE);
            }
        }catch (Exception e){
            log.error("删除角色异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @GetMapping(value = "/findRoleById")
    @ApiOperation(value = "通过角色id查询功能按钮")
    @ApiImplicitParam(name = "id",value = "角色id")
    public ResponseResult findRoleById(String id){
        try {
            List<ParentFunction> parentFunctions=accountService.findFBByRoleId(id);
            return new ResponseResult(CommonCode.SUCCESS,parentFunctions);
        }catch (Exception e){
            log.error("通过角色id查询功能按钮异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }


    @ApiOperation(value = "查询账户权限",httpMethod = "GET")
    @GetMapping(value = "/findAccountPower")
    public ResponseResult findAccountPower(HttpServletRequest request){
        try {
            List<AccountPower> accountPowers=accountService.findAccountPower(request);
            return new ResponseResult(CommonCode.SUCCESS,accountPowers);
        }catch (Exception e){
            log.error("查询账户权限异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }
    @GetMapping(value = "/adminR")
    public ResponseResult admin(){
        try {
            accountService.adminR();
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error(e);
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @GetMapping(value = "/adminU")
    public ResponseResult adminU(String roleId){
        try {
            accountService.adminU(roleId);
            return new ResponseResult(CommonCode.SUCCESS);
        }catch (Exception e){
            log.error(e);
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }

    @ApiOperation(value = "查询当前登录用户的基本信息",httpMethod = "GET")
    @GetMapping(value = "/findNowUser")
    public ResponseResult findNowUser(HttpServletRequest request){
        try {
            Account account = accountService.findNowUser(request);
            return new ResponseResult(CommonCode.SUCCESS,account);
        }catch (Exception e){
            log.error("查询当前登录用户的基本信息异常");
            log.error(e);
            log.error(sf.format(new Date()));
            return new ResponseResult(CommonCode.SYSTEMBUSY);
        }
    }






}
