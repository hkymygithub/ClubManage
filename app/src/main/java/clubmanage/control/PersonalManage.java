package clubmanage.control;

import android.util.Base64;

import java.sql.Connection;
import java.sql.SQLException;

import clubmanage.itf.IPersonalManage;
import clubmanage.util.BaseException;
import clubmanage.util.DBUtil;

public class PersonalManage implements IPersonalManage {
    @Override
    public void changePwd(String uid, String oldPwd, String newPwd, String newPwd2) throws BaseException {
        if(oldPwd==null||newPwd==null||newPwd2==null) throw new BaseException("密码不能为空！");
        if (newPwd.length()>20||newPwd2.length()>20) throw new BaseException("密码长度不符合要求！");
        if(newPwd.equals(newPwd2)==false) throw new BaseException("两次新密码输入不同！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select uid,pwd from user where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs=pst.executeQuery();
            oldPwd=Tools.getMD5String(oldPwd);
            if(!rs.next()) throw new BaseException("登陆账号不存在!");
            if (oldPwd.equals(rs.getString(2))==false) throw new BaseException("旧密码输入错误！");
            sql="update user set pwd=? where uid=?";
            pst=conn.prepareStatement(sql);
			pst.setString(1, Tools.getMD5String(newPwd));
			pst.setString(2, uid);
            pst.execute();
			pst.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeName(String uid, String newName) throws BaseException {
        if (newName==null||newName.equals("")) throw new BaseException("新名字不能为空！");
        if (newName.length()>100) throw new BaseException("名字长度不符合要求！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update user set name=? where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,newName);
            pst.setString(2,uid);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changePhone_number(String uid, String number) throws BaseException{
        if (number==null||number.equals("")) throw new BaseException("电话不能为空！");
        if (number.length()>200) throw new BaseException("电话长度不符合要求！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update user set phone_number=? where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,number);
            pst.setString(2,uid);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeMail(String uid, String mail) throws BaseException{
        if (mail==null||mail.equals("")) throw new BaseException("邮箱不能为空！");
        if (mail.length()>200) throw new BaseException("邮箱长度不符合要求！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update user set mail=? where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,mail);
            pst.setString(2,uid);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeMajor(String uid, String major) throws BaseException{
        if (major==null||major.equals("")) throw new BaseException("专业不能为空！");
        if (major.length()>200) throw new BaseException("专业名长度不符合要求！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update user set major=? where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,major);
            pst.setString(2,uid);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeGender(String uid, String gender) throws BaseException{
        if (gender==null||gender.equals("")) throw new BaseException("性别不能为空！");
        if (gender.length()>100) throw new BaseException("性别长度不符合要求！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update user set gender=? where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,gender);
            pst.setString(2,uid);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void changeImage(String uid, String image) throws BaseException{
        if (image==null) throw new BaseException("性别不能为空！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update user set image=? where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setBytes(1, Base64.decode(image,Base64.DEFAULT));
            pst.setString(2,uid);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

    }
}
