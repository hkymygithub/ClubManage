package clubmanage.control;

import android.util.Base64;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clubmanage.itf.IUserManage;
import clubmanage.model.User;
import clubmanage.util.BaseException;
import clubmanage.util.DBUtil;

public class UserManage implements IUserManage {

    @Override
    public User login(String uid, String pwd) throws BaseException {
        if(uid==null||uid.equals("")) throw new BaseException("学号不能为空！");
        if(pwd==null||pwd.equals("")) throw new BaseException("密码不能为空！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from user where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next())throw new BaseException("该用户不存在！");
            if(rs.getTimestamp(10)!=null) throw new BaseException("该账号已被封号！");
            pwd=Tools.getMD5String(pwd);
            String qq=rs.getString(2);
            if(pwd.equals(rs.getString(2))==false) throw new BaseException("密码输入错误！");
            User user=new User();
            user.setUid(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setImage(Base64.encodeToString(rs.getBytes(3),Base64.DEFAULT));
            user.setName(rs.getString(4));
            user.setGender(rs.getString(5));
            user.setMajor(rs.getString(6));
            user.setMail(rs.getString(7));
            user.setPhone_number(rs.getString(8));
            user.setStart_time(rs.getTimestamp(9));
            user.setEnd_time(rs.getTimestamp(10));
            user.setUser_category(rs.getString(11));
            user.setNote(rs.getString(12));
            pst.close();
            rs.close();
            conn.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void reg(String uid, String pwd, String pwd2, String name, String mail, String phone_number) throws BaseException {
        if(uid==null) throw new BaseException("学号不能为空！");
        if(pwd==null||pwd2==null) throw new BaseException("密码不能为空！");
        if(uid.length()<1||uid.length()>20) throw new BaseException("学号长度必须为1-20！");
        if(Tools.isNumber(uid)==false) throw new BaseException("学号必须都为数字！");
        if(pwd.length()<1||pwd.length()>20||pwd2.length()<1||pwd2.length()>20) throw new BaseException("密码长度必须为1-20！");
        if(pwd.equals(pwd2)==false) throw new BaseException("两次密码输入不同！");
        if(name==null) throw new BaseException("姓名不能为空！");
        if(name.length()>100) throw new BaseException("姓名长度需小于100个字符！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select uid from user where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()) throw new BaseException("该账号已经被注册！");
            sql="insert into user(uid,pwd,name,mail,phone_number,start_time,user_category) ";
            sql=sql+" values(?,?,?,?,?,?,?) ";
            pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            pst.setString(2,Tools.getMD5String(pwd));
            pst.setString(3,name);
            pst.setString(4,mail);
            pst.setString(5,phone_number);
            pst.setTimestamp(6,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setString(7,"学生");
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
    public void logout() {
        User.currentLoginUser=null;
    }

    @Override
    public void deleteUserByID(String uid) throws BaseException {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select uid,end_time from user where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()) throw new BaseException("没有这个用户！");
            if (rs.getTimestamp(2)!=null) throw new BaseException("这个用户已被封号！");
            sql="update user set end_time=? where uid=?";
            pst=conn.prepareStatement(sql);
            pst.setTimestamp(1,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setString(2,uid);
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
    public User searchUserById(String uid) throws BaseException {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from user where uid=? ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs=pst.executeQuery();
            if(!rs.next()) throw new BaseException("没有这个用户！");
            User user=new User();
            user.setUid(rs.getString(1));
            user.setPwd(rs.getString(2));
            user.setImage(Base64.encodeToString(rs.getBytes(3),Base64.DEFAULT));
            user.setName(rs.getString(4));
            user.setGender(rs.getString(5));
            user.setMajor(rs.getString(6));
            user.setMail(rs.getString(7));
            user.setPhone_number(rs.getString(8));
            user.setStart_time(rs.getTimestamp(9));
            user.setEnd_time(rs.getTimestamp(10));
            user.setUser_category(rs.getString(11));
            user.setNote(rs.getString(12));
            pst.close();
            rs.close();
            conn.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<User> searchUserByName(String name) {
        Connection conn=null;
        List<User> result=new ArrayList<User>();
        try {
            conn= DBUtil.getConnection();
            String sql="select * from user where name like ? ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,"%"+name+"%");
            java.sql.ResultSet rs=pst.executeQuery();
            while (rs.next()){
                User user=new User();
                user.setUid(rs.getString(1));
                user.setPwd(rs.getString(2));
                user.setImage(Base64.encodeToString(rs.getBytes(3),Base64.DEFAULT));
                user.setName(rs.getString(4));
                user.setGender(rs.getString(5));
                user.setMajor(rs.getString(6));
                user.setMail(rs.getString(7));
                user.setPhone_number(rs.getString(8));
                user.setStart_time(rs.getTimestamp(9));
                user.setEnd_time(rs.getTimestamp(10));
                user.setUser_category(rs.getString(11));
                user.setNote(rs.getString(12));
                result.add(user);
            }
            pst.close();
            rs.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<User> searchAllUser(boolean includeDel) {
        List<User> result=new ArrayList<User>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from user ";
            if (includeDel==false) sql=sql+" where end_time is null ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while (rs.next()){
                User user=new User();
                user.setUid(rs.getString(1));
                user.setPwd(rs.getString(2));
                user.setImage(Base64.encodeToString(rs.getBytes(3),Base64.DEFAULT));
                user.setName(rs.getString(4));
                user.setGender(rs.getString(5));
                user.setMajor(rs.getString(6));
                user.setMail(rs.getString(7));
                user.setPhone_number(rs.getString(8));
                user.setStart_time(rs.getTimestamp(9));
                user.setEnd_time(rs.getTimestamp(10));
                user.setUser_category(rs.getString(11));
                user.setNote(rs.getString(12));
                result.add(user);
            }
            pst.close();
            rs.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<User> searchAllUser(String category, boolean includeDel) {
        List<User> result=new ArrayList<User>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from user where user_category=? ";
            if (includeDel==false) sql=sql+" and end_time is null ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,category);
            java.sql.ResultSet rs=pst.executeQuery();
            while (rs.next()){
                User user=new User();
                user.setUid(rs.getString(1));
                user.setPwd(rs.getString(2));
                user.setImage(Base64.encodeToString(rs.getBytes(3),Base64.DEFAULT));
                user.setName(rs.getString(4));
                user.setGender(rs.getString(5));
                user.setMajor(rs.getString(6));
                user.setMail(rs.getString(7));
                user.setPhone_number(rs.getString(8));
                user.setStart_time(rs.getTimestamp(9));
                user.setEnd_time(rs.getTimestamp(10));
                user.setUser_category(rs.getString(11));
                user.setNote(rs.getString(12));
                result.add(user);
            }
            pst.close();
            rs.close();
            conn.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        UserManage userManage=new UserManage();
        try {
            User user=userManage.login("31","123456");
            System.out.println(user.getUid());
        } catch (BaseException e) {
            e.printStackTrace();
        }
        //            userManage.reg("31701073","123456","123456",null,"张三","男","计算机",null,null);
//        List<User> user=userManage.searchUserByName("s");

    }
}
