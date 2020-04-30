package clubmanage.itf;

import java.util.List;

import clubmanage.model.User;
import clubmanage.util.BaseException;

public interface IUserManage {
    /**
     * 登陆
     * 1、如果用户不存在或者密码错误，抛出一个异常
     * 2、如果认证成功，则返回当前用户信息
     */
    public User login(String uid, String pwd)throws BaseException;
    /**
     * 注册：
     * 要求学号不能重复，不能为空
     * 两次输入的密码必须一致，密码不能为空
     * 如果注册失败，则抛出异常
     * uid:学号；pwd：密码；pwd2:第二次输入密码；head:头像；mail：邮箱；phone_number：电话；note：备注
     */
    public void reg(String uid, String pwd,String pwd2,String name,String mail,String phone_number) throws BaseException;
    /**
     * 注销用户
     */
    public void logout();
    /**
     * 删除用户（用id）
     * 如果未找到用户则抛出异常
     * 假删除
     */
    public void deleteUserByID(String uid)throws BaseException;
    /**
     * 查找用户（用id）
     * 如果未找到用户则抛出异常
     */
    public User searchUserById(String uid)throws BaseException;
    /**
     * 查找用户（用姓名）
     * 如果未找到用户则抛出异常
     */
    public List<User> searchUserByName(String name);
    /**
     * 查找所有用户
     * includeDel:true=包含已删除用户，false=不包含
     */
    public List<User> searchAllUser(boolean includeDel);
    /**
     * 查找所有用户
     * includeDel:true=包含已删除用户，false=不包含
     * category:用户类别，学生或老师
     */
    public List<User> searchAllUser(String category,boolean includeDel);
}
