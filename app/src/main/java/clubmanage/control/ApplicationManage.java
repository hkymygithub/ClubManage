package clubmanage.control;

import android.util.Base64;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import clubmanage.itf.IApplicationManage;
import clubmanage.model.Create_activity;
import clubmanage.model.Create_club;
import clubmanage.model.Dismiss_club;
import clubmanage.model.Join_club;
import clubmanage.util.BaseException;
import clubmanage.util.ClubManageUtil;
import clubmanage.util.DBUtil;

public class ApplicationManage implements IApplicationManage {
    @Override
    public void addClubAppli(String area_name, String uid, String applican_name, String club_name, String club_category,
                             String introduce, String reason) throws BaseException {
        if (area_name==null||uid==null||applican_name==null||club_name==null||club_category==null
                ||introduce==null||reason==null) throw new BaseException("请完善所有信息！");
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select uid,name from user where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs=pst.executeQuery();
            if (!rs.next()) throw new BaseException("申请人信息有误！");
            if (rs.getString(2).equals(applican_name)==false) throw new BaseException("申请人信息有误！");
            sql="select club_name from create_club where state=2 and club_name=? ";
            pst=conn.prepareStatement(sql);
            pst.setString(1,club_name);
            rs=pst.executeQuery();
            if(rs.next())throw new BaseException("社团名称已被使用！");
            sql="select club_name from club where if_club_end=0 and club_name=? ";
            pst=conn.prepareStatement(sql);
            pst.setString(1,club_name);
            rs=pst.executeQuery();
            if(rs.next())throw new BaseException("社团名称已被使用！");
            sql="insert into create_club(area_name,uid,applican_name,club_name,club_category,introduce,reason,propose_time,state) values(?,?,?,?,?,?,?,?,?) ";
            pst=conn.prepareStatement(sql);
            pst.setString(1,area_name);
            pst.setString(2,uid);
            pst.setString(3,applican_name);
            pst.setString(4,club_name);
            pst.setString(5,club_category);
            pst.setString(6,introduce);
            pst.setString(7,reason);
            pst.setTimestamp(8,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setInt(9,2);
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
    public void feedbackClubAppli(int applyclub_formid, int state, String handle_people_id,String suggestion) {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update create_club set state=? ,suggestion=? , handle_people_id=? where applyclub_formid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,state);
            pst.setString(2,suggestion);
            pst.setString(3,handle_people_id);
            pst.setInt(4,applyclub_formid);
            pst.execute();
            if (state==1){
                sql="select * from create_club where applyclub_formid=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,applyclub_formid);
                java.sql.ResultSet rs=pst.executeQuery();
                if(rs.next()){
                    ClubManageUtil.clubManage.addClub(rs.getString(5),rs.getString(6),rs.getString(3),
                            rs.getString(7),rs.getString(2),rs.getString(11));
                }
                rs.close();
            }
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
    public void addActivityAppli(int club_id,String poster,String activity_name, String area_name, String activity_owner_id, String activity_owner_name,
                                 String activity_start_time, String activity_end_time, String activity_details,String attention,String activity_category, boolean if_public_activity, String reason) throws BaseException{
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select uid,name from user where uid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,activity_owner_id);
            java.sql.ResultSet rs=pst.executeQuery();
            if (!rs.next()) throw new BaseException("申请人信息有误！");
            if (rs.getString(2).equals(activity_owner_name)==false) throw new BaseException("申请人信息有误！");
            sql="insert into create_activity(club_id,activity_name,poster,area_name,activity_owner_id,activity_owner_name,activity_start_time,activity_end_time,activity_details,activity_attention,activity_category,if_public_activity,reason,propose_time,state) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst=conn.prepareStatement(sql);
            pst.setInt(1,club_id);
            pst.setString(2,activity_name);
            pst.setBytes(3,Base64.decode(poster,Base64.DEFAULT));
            pst.setString(4,area_name);
            pst.setString(5,activity_owner_id);
            pst.setString(6,activity_owner_name);
            pst.setTimestamp(7, Timestamp.valueOf(activity_start_time));
            pst.setTimestamp(8,Timestamp.valueOf(activity_end_time));
            pst.setString(9,activity_details);
            pst.setString(10,attention);
            pst.setString(11,activity_category);
            pst.setBoolean(12,if_public_activity);
            pst.setString(13,reason);
            pst.setTimestamp(14,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setInt(15,2);
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
    public void feedbackActivityAppli(int activity_approval_id, int state,String suggest, String handle_people_id) {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update create_activity set state=? , suggestion=? , handle_people_id=? where activity_approval_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,state);
            pst.setString(2,suggest);
            pst.setString(3,handle_people_id);
            pst.setInt(4,activity_approval_id);
            pst.execute();
            if(state==1){
                sql="select * from create_activity where activity_approval_id=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,activity_approval_id);
                java.sql.ResultSet rs=pst.executeQuery();
                boolean ispublic=true;
                if(rs.next()){
                    if (rs.getByte(13)==0) ispublic=false;
                    else ispublic=true;
                    ClubManageUtil.activityManage.addActivity(rs.getInt(2),rs.getString(3),Base64.encodeToString(rs.getBytes(4),Base64.DEFAULT),
                    rs.getTimestamp(8).toString(),rs.getTimestamp(9).toString(),rs.getString(10),rs.getString(5),
                            rs.getString(11),ispublic,rs.getString(12));
                }
                rs.close();
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void joinClubAppli(int club_id, String uid, String join_name, String reason) {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="insert into join_club(club_id,uid,join_name,reason,propose_time,state) values(?,?,?,?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,club_id);
            pst.setString(2,uid);
            pst.setString(3,join_name);
            pst.setString(4,reason);
            pst.setTimestamp(5,new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setInt(6,2);
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
    public void feedbackJoinClubAppli(int joinclub_formid, int state, String handle_people_id) {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update join_club set state=? , handle_people_id=? where joinclub_formid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,state);
            pst.setString(2,handle_people_id);
            pst.setInt(3,joinclub_formid);
            pst.execute();
            if(state==1){
                sql="select uid,club_id from join_club where joinclub_formid=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,joinclub_formid);
                java.sql.ResultSet rs=pst.executeQuery();
                if(rs.next()){
                    ClubManageUtil.clubManage.joinClub(rs.getString(2),rs.getInt(1));
                }
                rs.close();
            }
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
    public void dismissClubAppli(String uid, String dismiss_name,int club_id, String club_name, String reason) {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="insert into dismiss_club(uid,dismiss_name,club_id,club_name,reason) values(?,?,?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            pst.setString(2,dismiss_name);
            pst.setInt(3,club_id);
            pst.setString(4,club_name);
            pst.setString(5,reason);
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
    public void feedbackDismissClub(int dismissclub_formid, int state, String handle_people_id) {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="update dismiss_club set state=? , handle_people_id=? where dismissclub_formid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,state);
            pst.setString(2,handle_people_id);
            pst.setInt(3,dismissclub_formid);
            pst.execute();
            if(state==1){
                sql="select club_id from dismiss_club where dismissclub_formid=?";
                pst=conn.prepareStatement(sql);
                pst.setInt(1,dismissclub_formid);
                java.sql.ResultSet rs=pst.executeQuery();
                if (rs.next()){
                    ClubManageUtil.clubManage.deleteClub(rs.getInt(1));
                }
                rs.close();
            }
            pst.close();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
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
    public void signupActivity(String uid, int activity_id) {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="insert into sign_up(uid,activity_id,propose_time) values(?,?,?)";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            pst.setInt(2,activity_id);
            pst.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
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
    public List<Create_club> searchCreateClubAppli() {
        List<Create_club> result=new ArrayList<Create_club>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from create_club where state=2 order by state desc";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                Create_club create_club=new Create_club();
                create_club.setApplyclub_formid(rs.getInt(1));
                create_club.setArea_name(rs.getString(2));
                create_club.setUid(rs.getString(3));
                create_club.setApplican_name(rs.getString(4));
                create_club.setClub_name(rs.getString(5));
                create_club.setClub_category(rs.getString(6));
                create_club.setIntroduce(rs.getString(7));
                create_club.setReason(rs.getString(8));
                create_club.setPropose_time(rs.getTimestamp(9));
                create_club.setState(rs.getInt(10));
                create_club.setHandle_people_id(rs.getString(11));
                create_club.setSuggestion(rs.getString(12));
                result.add(create_club);
            }
            pst.close();
            rs.close();
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
    public Create_club searchCreateClubAppliByID(int id) {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from create_club where state=2 and applyclub_formid=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs=pst.executeQuery();
            Create_club create_club=new Create_club();
            while(rs.next()){
                create_club.setApplyclub_formid(rs.getInt(1));
                create_club.setArea_name(rs.getString(2));
                create_club.setUid(rs.getString(3));
                create_club.setApplican_name(rs.getString(4));
                create_club.setClub_name(rs.getString(5));
                create_club.setClub_category(rs.getString(6));
                create_club.setIntroduce(rs.getString(7));
                create_club.setReason(rs.getString(8));
                create_club.setPropose_time(rs.getTimestamp(9));
                create_club.setState(rs.getInt(10));
                create_club.setHandle_people_id(rs.getString(11));
                create_club.setSuggestion(rs.getString(12));
            }
            pst.close();
            rs.close();
            return create_club;
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

    public List<Create_activity> searchCreateActivityAppli() {
        List<Create_activity> result=new ArrayList<Create_activity>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from create_activity where state=2 order by state desc";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                Create_activity create_activity=new Create_activity();
                create_activity.setActivity_approval_id(rs.getInt(1));
                create_activity.setClub_id(rs.getInt(2));
                create_activity.setActivity_name(rs.getString(3));
                create_activity.setPoster(Base64.encodeToString(rs.getBytes(4),Base64.DEFAULT));
                create_activity.setArea_name(rs.getString(5));
                create_activity.setActivity_owner_id(rs.getString(6));
                create_activity.setActivity_owner_name(rs.getString(7));
                create_activity.setActivity_start_time(rs.getTimestamp(8));
                create_activity.setActivity_end_time(rs.getTimestamp(9));
                create_activity.setActivity_details(rs.getString(10));
                create_activity.setActivity_attention(rs.getString(11));
                create_activity.setActivity_category(rs.getString(12));
                create_activity.setIf_public_activity(rs.getByte(13));
                create_activity.setReason(rs.getString(14));
                create_activity.setPropose_time(rs.getTimestamp(15));
                create_activity.setState(rs.getInt(16));
                create_activity.setHandle_people_id(rs.getString(17));
                result.add(create_activity);
            }
            pst.close();
            rs.close();
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
    public Create_activity searchCreateActivityAppliByID(int id) {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from create_activity where state=2 and activity_approval_id=?";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setInt(1,id);
            java.sql.ResultSet rs=pst.executeQuery();
            Create_activity create_activity=new Create_activity();
            while(rs.next()){
                create_activity.setActivity_approval_id(rs.getInt(1));
                create_activity.setClub_id(rs.getInt(2));
                create_activity.setActivity_name(rs.getString(3));
                create_activity.setPoster(Base64.encodeToString(rs.getBytes(4),Base64.DEFAULT));
                create_activity.setArea_name(rs.getString(5));
                create_activity.setActivity_owner_id(rs.getString(6));
                create_activity.setActivity_owner_name(rs.getString(7));
                create_activity.setActivity_start_time(rs.getTimestamp(8));
                create_activity.setActivity_end_time(rs.getTimestamp(9));
                create_activity.setActivity_details(rs.getString(10));
                create_activity.setActivity_attention(rs.getString(11));
                create_activity.setActivity_category(rs.getString(12));
                create_activity.setIf_public_activity(rs.getByte(13));
                create_activity.setReason(rs.getString(14));
                create_activity.setPropose_time(rs.getTimestamp(15));
                create_activity.setState(rs.getInt(16));
                create_activity.setHandle_people_id(rs.getString(17));
            }
            pst.close();
            rs.close();
            return create_activity;
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
    public List<Join_club> searchJoinClubAppli() {
        List<Join_club> result=new ArrayList<Join_club>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from join_club order by state desc";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                Join_club join_club=new Join_club();
                join_club.setJoinclub_formid(rs.getInt(1));
                join_club.setClub_id(rs.getInt(2));
                join_club.setUid(rs.getString(3));
                join_club.setJoin_name(rs.getString(4));
                join_club.setReason(rs.getString(5));
                join_club.setPropose_time(rs.getTimestamp(6));
                join_club.setState(rs.getInt(7));
                join_club.setHandle_people_id(rs.getString(8));
                result.add(join_club);
            }
            pst.close();
            rs.close();
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
    public List<Dismiss_club> searchDismissClubAppli() {
        List<Dismiss_club> result=new ArrayList<Dismiss_club>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from dismiss_club order by state desc";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                Dismiss_club dismiss_club=new Dismiss_club();
                dismiss_club.setDismissclub_formid(rs.getInt(1));
                dismiss_club.setUid(rs.getString(2));
                dismiss_club.setDismiss_name(rs.getString(3));
                dismiss_club.setClub_id(rs.getInt(4));
                dismiss_club.setClub_name(rs.getString(5));
                dismiss_club.setReason(rs.getString(6));
                dismiss_club.setPropose_time(rs.getTimestamp(7));
                dismiss_club.setState(rs.getInt(8));
                dismiss_club.setHandle_people_id(rs.getString(9));
                result.add(dismiss_club);
            }
            pst.close();
            rs.close();
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

    public List<Create_activity> searchCreateActivityByUser(String uid){//查找某位用户创建活动的申请
        List<Create_activity> result=new ArrayList<Create_activity>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from create_activity where activity_owner_id = ? order by state desc";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                Create_activity create_activity=new Create_activity();
                create_activity.setActivity_approval_id(rs.getInt(1));
                create_activity.setClub_id(rs.getInt(2));
                create_activity.setActivity_name(rs.getString(3));
                create_activity.setPoster(Base64.encodeToString(rs.getBytes(4),Base64.DEFAULT));
                create_activity.setArea_name(rs.getString(5));
                create_activity.setActivity_owner_id(rs.getString(6));
                create_activity.setActivity_owner_name(rs.getString(7));
                create_activity.setActivity_start_time(rs.getTimestamp(8));
                create_activity.setActivity_end_time(rs.getTimestamp(9));
                create_activity.setActivity_details(rs.getString(10));
                create_activity.setActivity_attention(rs.getString(11));
                create_activity.setActivity_category(rs.getString(12));
                create_activity.setIf_public_activity(rs.getByte(13));
                create_activity.setReason(rs.getString(14));
                create_activity.setPropose_time(rs.getTimestamp(15));
                create_activity.setState(rs.getInt(16));
                create_activity.setSuggestion(rs.getString(17));
                create_activity.setHandle_people_id(rs.getString(18));
                result.add(create_activity);
            }
            pst.close();
            rs.close();
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
    public List<Create_club> searchCreateClubByUser(String uid){//查找某位用户创建社团的申请
        List<Create_club> result=new ArrayList<Create_club>();
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select * from create_club where uid=? order by state desc";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs=pst.executeQuery();
            while(rs.next()){
                Create_club create_club=new Create_club();
                create_club.setApplyclub_formid(rs.getInt(1));
                create_club.setArea_name(rs.getString(2));
                create_club.setUid(rs.getString(3));
                create_club.setApplican_name(rs.getString(4));
                create_club.setClub_name(rs.getString(5));
                create_club.setClub_category(rs.getString(6));
                create_club.setIntroduce(rs.getString(7));
                create_club.setReason(rs.getString(8));
                create_club.setPropose_time(rs.getTimestamp(9));
                create_club.setState(rs.getInt(10));
                create_club.setHandle_people_id(rs.getString(11));
                result.add(create_club);
            }
            pst.close();
            rs.close();
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
    public boolean ifHaveClubAppli(String uid) {
        Connection conn=null;
        try {
            conn= DBUtil.getConnection();
            String sql="select uid from create_club where uid=? and state=2 ";
            java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs=pst.executeQuery();
            if(rs.next()){
                return true;
            }
            pst.close();
            rs.close();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(!conn.isClosed()) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ApplicationManage am=new ApplicationManage();
//        am.signupActivity("31701013",1);
//        am.signupActivity("31701013",3);
//        am.signupActivity("31701013",5);
//        am.signupActivity("31701014",1);
//        am.signupActivity("31701014",4);
//        am.signupActivity("31701015",3);
//        am.feedbackActivityAppli(1,1,"可以","t001");
        System.out.println(am.ifHaveClubAppli("31701012"));
    }
}
