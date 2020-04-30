package clubmanage.itf;

import clubmanage.model.User;
import clubmanage.util.BaseException;

public interface IPersonalManage {
    /**
     * 改变密码：
     * 要求密码不能为空
     * 旧密码必须正确
     * 如果修改失败，则抛出异常
     * uid:学号；oldPwd：旧密码；newPwd：新密码；newPwd2:第二次输入新密码
     */
    public void changePwd(String uid, String oldPwd,String newPwd, String newPwd2)throws BaseException;
    /**
     * 改变姓名
     * 要求新名字不能为空
     * 如果修改失败，则抛出异常
     * uid:学号；newName：新名字
     */
    public void changeName(String uid, String newName)throws BaseException;
    /**
     * 改变手机号
     */
    public void changePhone_number(String uid, String number)throws BaseException;
    /**
     * 改变邮箱
     */
    public void changeMail(String uid, String mail)throws BaseException;
    /**
     * 改变专业
     */
    public void changeMajor(String uid, String major)throws BaseException;
    /**
     * 改变性别
     */
    public void changeGender(String uid, String gender)throws BaseException;
    /**
     * 改变头像
     */
    public void changeImage(String uid, byte[] image)throws BaseException;
}
