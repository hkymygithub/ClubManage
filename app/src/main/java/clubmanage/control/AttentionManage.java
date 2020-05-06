package clubmanage.control;

import android.util.Base64;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clubmanage.itf.IAttentionManage;
import clubmanage.model.Club;
import clubmanage.util.BaseException;
import clubmanage.util.DBUtil;
import clubmanage.util.DbException;

public class AttentionManage implements IAttentionManage {
    @Override
    public void addAttention(String uid, int club_id) {//添加关注
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into attention(uid,club_id) values(?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            pst.setInt(2, club_id);
            pst.execute();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public void deleteAttention(String uid,int club_id) throws BaseException{//删除关注
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "delete from attention where uid=? and club_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            pst.setInt(2,club_id);
            pst.execute();
            pst.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }

    @Override
    public List<Club> searchAttenByUser(String uid){//查找某用户关注的所有社团
        List<Club> result = new ArrayList<Club>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select club.club_id,category_name,club_icon,club_cover,club_name,club_introduce,slogan,club_place,member_number,if_club_end,intendant from club,attention where club.club_id=attention.club_id and uid=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Club club = new Club();
                club.setClub_id(rs.getInt(1));
                club.setCategory_name(rs.getString(2));
                club.setClub_icon(Base64.encodeToString(rs.getBytes(3),Base64.DEFAULT));
                club.setClub_cover(Base64.encodeToString(rs.getBytes(4),Base64.DEFAULT));
                club.setClub_name(rs.getString(5));
                club.setClub_introduce(rs.getString(6));
                club.setSlogan(rs.getString(7));
                club.setClub_place(rs.getString(8));
                club.setMember_number(rs.getInt(9));
                club.setIf_club_end(rs.getByte(10));
                club.setIntendant(rs.getString(11));
                result.add(club);
            }
            rs.close();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return result;
    }
    public int searchAttenCount(String uid){//查询用户关注的社团数
        Connection conn = null;
        int count=0;
        try {
            conn = DBUtil.getConnection();
            String sql = "select count(*) from attention where uid=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            java.sql.ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                count+=rs.getInt(1);
            }
            rs.close();
            pst.close();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return count;
    }

    public boolean issubscribe(String uid,int club_id) {//查询某用户是否关注某社团
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);
            String sql = "select count(*) from attention where uid=? and club_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            pst.setInt(2,club_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                if (rs.getInt(1)==0)
                    return false;
                else
                    return true;
            }
            rs.close();
            pst.close();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return true;
    }
    public static void main(String[] args) throws BaseException {
        AttentionManage am=new AttentionManage();
        //添加关注并查看
//        am.addAttention("31701015",4);
//        am.addAttention("31701015",6);
//        am.addAttention("31701015",7);
        List<Club> cl =am.searchAttenByUser("31701073");
//        for(int i=0;i<cl.size();i++){
//            System.out.println(cl.get(i).getClub_id());
//            System.out.println(cl.get(i).getClub_name());
//        }
        System.out.println(cl.size());


//        am.addAttention("31701073",2);
//        List<ClubItem> cl =am.searchAttenByUser("31701073");
//        for(int i=0;i<cl.size();i++){
//            System.out.println(cl.get(i).getClub_id());
//            System.out.println(cl.get(i).getClub_name());
//        }
//        System.out.print(am.issubscribe("31701073",2));//2棋社true

//        am.addAttention("31701073",1);
//        List<ClubItem> cl =am.searchAttenByUser("31701073");
//        for(int i=0;i<cl.size();i++){
//            System.out.println(cl.get(i).getClub_id());
//            System.out.println(cl.get(i).getClub_name());
//        }
//        System.out.print(am.issubscribe("31701073",1));//2棋社1音悦true

        //取消关注并查看
//        am.deleteAttention("31701073",2);
//        List<ClubItem> cl =am.searchAttenByUser("31701073");
//        for(int i=0;i<cl.size();i++){
//            System.out.println(cl.get(i).getClub_id());
//            System.out.println(cl.get(i).getClub_name());
//        }
//        System.out.print(am.issubscribe("31701073",2));//1音悦false
    }
}
