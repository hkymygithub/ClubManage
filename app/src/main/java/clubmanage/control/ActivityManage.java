package clubmanage.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clubmanage.itf.IActivityManage;
import clubmanage.model.Activity;
import clubmanage.model.Activity_picture;
import clubmanage.util.DBUtil;
import clubmanage.util.DbException;
import clubmanage.util.BaseException;

public class ActivityManage implements IActivityManage {
    @Override
    public void addActivity(int club_id, String activity_name,byte[] poster, String activity_start_time, String activity_end_time, String activity_introduce, String activity_place, String activity_attention, boolean if_public_activity, String activity_category) throws ParseException {//添加活动
        Connection conn = null;
        byte if_public=(byte)0;
        if(if_public_activity==true)
            if_public=(byte)1;
        else
            if_public=(byte)0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");//把传入的String型时间转成固定格式的时间
        Date start_time = sdf.parse(activity_start_time);
        Date end_time = sdf.parse(activity_end_time);
        try {
            conn = DBUtil.getConnection();
            String sql = "select max(activity_id) from activity";//查找最大的活动id
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            int max_id=0;
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()){
                max_id=rs.getInt(1);
            }
            sql = "select usable from area where area_name=?";//查看场地是否可用
            pst = conn.prepareStatement(sql);
            pst.setString(1,activity_place);
            rs = pst.executeQuery();
            if(rs.next()) {
                if(rs.getInt(1)==0){//如果场地不可用就抛出异常
                    throw new BaseException("场地不可用");
                }
                else {//否则在activity表中添加活动并且把该场地设为不可用
                    sql = "insert into activity(activity_id,club_id,activity_name,poster,activity_start_time,activity_end_time,activity_introduce,activity_place,activity_attention,if_public_activity,activity_category) values(?,?,?,?,?,?,?,?,?,?,?)";//把相关信息加到activity表中
                    pst = conn.prepareStatement(sql);
                    pst.setInt(1,max_id+1);
                    pst.setInt(2, club_id);
                    pst.setString(3, activity_name);
                    pst.setBytes(4,poster);
                    pst.setTimestamp(5, new Timestamp(start_time.getTime()));
                    pst.setTimestamp(6, new Timestamp(end_time.getTime()));
                    pst.setString(7,activity_introduce);
                    pst.setString(8,activity_place);
                    pst.setString(9,activity_attention);
                    pst.setByte(10,if_public);
                    pst.setString(11,activity_category);
                    pst.execute();
                    pst.close();
                    sql = "update area set usable=0 where area_name=?";
                    pst = conn.prepareStatement(sql);
                    pst.setString(1, activity_place);
                    pst.execute();
                    pst.close();
                }
            }
            rs.close();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BaseException e) {
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
    public void addActPicture(int Activity_id, String picturePath) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into activity_picture(activity_id,picture_details) values(?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, Activity_id);
            pst.setBytes(2, Tools.image2byte(picturePath));
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
    public void editActPicture(int picture_id,String picturePath) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update activity_picture set picture_details=? where picture_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setBytes(1,Tools.image2byte(picturePath));
            pst.setInt(2, picture_id);
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
    public List<Activity_picture> searchActivityPicture(int activity_id) throws BaseException{
        List<Activity_picture> result = new ArrayList<Activity_picture>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select picture_id,activity_id,picture_details from activity_picture where activity_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, activity_id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Activity_picture activity_picture = new Activity_picture();
                activity_picture.setPicture_id(rs.getInt(1));
                activity_picture.setActivity_id(rs.getInt(2));
                activity_picture.setPicture_details(rs.getBytes(3));
                result.add(activity_picture);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
        } finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }return result;
    }

    @Override
    public void editActivity(Activity activity) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update activity set club_id=?,activity_name=?,poster=?,activity_start_time=?,activity_end_time=?,activity_introduce=?,activity_place=?,activity_attention=?,if_public_activity=?,activity_category=? where activity_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, activity.getClub_id());
            pst.setString(2,activity.getActivity_name());
            pst.setBytes(3,activity.getPoster());
            pst.setTimestamp(4,activity.getActivity_start_time());
            pst.setTimestamp(5,activity.getActivity_end_time());
            pst.setString(6,activity.getActivity_introduce());
            pst.setString(7,activity.getActivity_place());
            pst.setString(8,activity.getActivity_attention());
            pst.setByte(9,activity.getIf_public_activity());
            pst.setString(10,activity.getaAtivity_category());
            pst.setInt(11,activity.getActivity_id());
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
    public List<Activity> searchAllActivity() {
        Connection conn = null;
        List<Activity> result = new ArrayList<Activity>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select activity_id,club_id,activity_name,poster,activity_start_time,activity_end_time,activity_introduce,activity_place,activity_attention,if_public_activity,activity_category from activity where if_public_activity=1";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Activity activity = new Activity();
                activity.setActivity_id(rs.getInt(1));
                activity.setClub_id(rs.getInt(2));
                activity.setActivity_name(rs.getString(3));
                activity.setPoster(rs.getBytes(4));
                activity.setActivity_start_time(rs.getTimestamp(5));
                activity.setActivity_end_time(rs.getTimestamp(6));
                activity.setActivity_introduce(rs.getString(7));
                activity.setActivity_place(rs.getString(8));
                activity.setActivity_attention(rs.getString(9));
                activity.setIf_public_activity(rs.getByte(10));
                activity.setActivity_category(rs.getString(11));
                result.add(activity);
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

    @Override
    public List<Activity> searchActivityByClubName(String club_name) {
        Connection conn = null;
        List<Activity> result = new ArrayList<Activity>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select activity_id,activity.club_id,activity_name,poster,activity_start_time,activity_end_time,activity_introduce,activity_place,activity_attention,if_public_activity,activity_category from activity,club where activity.club_id=club.club_id and club_name like '%"+club_name+"%'";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Activity activity = new Activity();
                activity.setActivity_id(rs.getInt(1));
                activity.setClub_id(rs.getInt(2));
                activity.setActivity_name(rs.getString(3));
                activity.setPoster(rs.getBytes(4));
                activity.setActivity_start_time(rs.getTimestamp(5));
                activity.setActivity_end_time(rs.getTimestamp(6));
                activity.setActivity_introduce(rs.getString(7));
                activity.setActivity_place(rs.getString(8));
                activity.setActivity_attention(rs.getString(9));
                activity.setIf_public_activity(rs.getByte(10));
                activity.setActivity_category(rs.getString(11));
                result.add(activity);
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

    @Override
    public List<Activity> searchActivityByClubId(int club_id) {
        Connection conn = null;
        List<Activity> result = new ArrayList<Activity>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select activity_id,club_id,activity_name,poster,activity_start_time,activity_end_time,activity_introduce,activity_place,activity_attention,if_public_activity,activity_category from activity where club_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,club_id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Activity activity = new Activity();
                activity.setActivity_id(rs.getInt(1));
                activity.setClub_id(rs.getInt(2));
                activity.setActivity_name(rs.getString(3));
                activity.setPoster(rs.getBytes(4));
                activity.setActivity_start_time(rs.getTimestamp(5));
                activity.setActivity_end_time(rs.getTimestamp(6));
                activity.setActivity_introduce(rs.getString(7));
                activity.setActivity_place(rs.getString(8));
                activity.setActivity_attention(rs.getString(9));
                activity.setIf_public_activity(rs.getByte(10));
                activity.setActivity_category(rs.getString(11));
                result.add(activity);
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

    @Override
    public List<Activity> searchActivityByName(String activity_name) {
        Connection conn = null;
        List<Activity> result = new ArrayList<Activity>();
        if(activity_name==null||activity_name.equals("")==true) return result;
        try {
            conn = DBUtil.getConnection();
            String sql = "select activity_id,club_id,activity_name,poster,activity_start_time,activity_end_time,activity_introduce,activity_place,activity_attention,if_public_activity,activity_category from activity where activity_name like '%"+activity_name+"%' and if_public_activity=1 ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Activity activity = new Activity();
                activity.setActivity_id(rs.getInt(1));
                activity.setClub_id(rs.getInt(2));
                activity.setActivity_name(rs.getString(3));
                activity.setPoster(rs.getBytes(4));
                activity.setActivity_start_time(rs.getTimestamp(5));
                activity.setActivity_end_time(rs.getTimestamp(6));
                activity.setActivity_introduce(rs.getString(7));
                activity.setActivity_place(rs.getString(8));
                activity.setActivity_attention(rs.getString(9));
                activity.setIf_public_activity(rs.getByte(10));
                activity.setActivity_category(rs.getString(11));
                result.add(activity);
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
    @Override
    public List<Activity> searchActivityByCategory(String category){
        Connection conn = null;
        List<Activity> result = new ArrayList<Activity>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select activity_id,club_id,activity_name,poster,activity_start_time,activity_end_time,activity_introduce,activity_place,activity_attention,if_public_activity,activity_category from activity where activity_category=? and if_public_activity=1 ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,category);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Activity activity = new Activity();
                activity.setActivity_id(rs.getInt(1));
                activity.setClub_id(rs.getInt(2));
                activity.setActivity_name(rs.getString(3));
                activity.setPoster(rs.getBytes(4));
                activity.setActivity_start_time(rs.getTimestamp(5));
                activity.setActivity_end_time(rs.getTimestamp(6));
                activity.setActivity_introduce(rs.getString(7));
                activity.setActivity_place(rs.getString(8));
                activity.setActivity_attention(rs.getString(9));
                activity.setIf_public_activity(rs.getByte(10));
                activity.setActivity_category(rs.getString(11));
                result.add(activity);
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
    public List<String> searchActivityType() throws BaseException {
        List<String> result = new ArrayList<String>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select category_name from activity_category";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String activity_category=rs.getString(1);
                result.add(activity_category);
            }
            rs.close();
            pst.close();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DbException(e);
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

    public List<Activity> searchMyActivity(String uid){//查找某用户参加的活动
        Connection conn = null;
        List<Activity> result = new ArrayList<Activity>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select a.activity_id,club_id,activity_name,poster,activity_start_time,activity_end_time,activity_introduce,activity_place,activity_attention,if_public_activity,activity_category from activity a,sign_up b where b.uid=? and a.activity_id=b.activity_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Activity activity = new Activity();
                activity.setActivity_id(rs.getInt(1));
                activity.setClub_id(rs.getInt(2));
                activity.setActivity_name(rs.getString(3));
                activity.setPoster(rs.getBytes(4));
                activity.setActivity_start_time(rs.getTimestamp(5));
                activity.setActivity_end_time(rs.getTimestamp(6));
                activity.setActivity_introduce(rs.getString(7));
                activity.setActivity_place(rs.getString(8));
                activity.setActivity_attention(rs.getString(9));
                activity.setIf_public_activity(rs.getByte(10));
                activity.setActivity_category(rs.getString(11));
                result.add(activity);
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
    public int searchMyActivityCount(String uid){//查找某用户参加的活动个数
        Connection conn = null;
        int count=0;
        try {
            conn = DBUtil.getConnection();
            String sql = "select count(*) from sign_up where uid=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,uid);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                count+=rs.getInt(1);
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
        return count;
    }

    @Override
    public boolean if_participate(String uid, int activity_id) {
        Connection conn = null;
        boolean if_pa=false;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from sign_up where uid=? and activity_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,uid);
            pst.setInt(2,activity_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                if_pa=true;
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
        return if_pa;
    }

    @Override
    public Activity searchActivityById(int activityid) {
        Connection conn = null;
        Activity activity=null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select a.activity_id,club_id,activity_name,poster,activity_start_time,activity_end_time,activity_introduce,activity_place,activity_attention,if_public_activity,activity_category from activity a where a.activity_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,activityid);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                activity = new Activity();
                activity.setActivity_id(rs.getInt(1));
                activity.setClub_id(rs.getInt(2));
                activity.setActivity_name(rs.getString(3));
                activity.setPoster(rs.getBytes(4));
                activity.setActivity_start_time(rs.getTimestamp(5));
                activity.setActivity_end_time(rs.getTimestamp(6));
                activity.setActivity_introduce(rs.getString(7));
                activity.setActivity_place(rs.getString(8));
                activity.setActivity_attention(rs.getString(9));
                activity.setIf_public_activity(rs.getByte(10));
                activity.setActivity_category(rs.getString(11));
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
        return activity;
    }

    @Override
    public String findClubNameByActivityId(int activityid) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select b.club_name from activity a,club b where a.activity_id=? and a.club_id=b.club_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,activityid);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                return rs.getString(1);
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
        return null;
    }

    @Override
    public String findProprieterNameByActivityId(int activityid) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select name from activity a,club b,user_club_role c,user d where a.activity_id=? and a.club_id=b.club_id and b.club_id=c.club_id and c.role_name like '社长' and c.uid=d.uid";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,activityid);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                return rs.getString(1);
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
        return null;
    }


    public static void main(String[] args) {
        ActivityManage am=new ActivityManage();

        List<Activity> a=am.searchMyActivity("31701013");
        for(int i=0;i<a.size();i++){
            System.out.print(a.get(i).getActivity_id());
        }
        System.out.print(am.searchMyActivityCount("31701013"));

        //添加活动
//        am.addActivity(2,"活动2","2019-11-19 11:12:13","2019-12-19 11:12:13","文105的活动","文105","",true,"体育运动");
//        am.addActivity(1,"活动3","2019-11-11 11:12:13","2019-12-19 11:12:13","文106的活动","文106","",true,"体育运动");
//        am.addActivity(1,"活动4","2019-11-11 11:12:13","2019-12-19 11:12:13","文106的活动","文106","",true,"体育运动");//会抛出异常

//        //查看所有活动
//        List<Activity> a=new ArrayList<Activity>();
//        a=am.searchAllActivity();
//        for(Activity activity:a){
//            System.out.println(activity.getActivity_id());
//        }
//        a=am.searchAllActivity();
//        for(int i=0;i<a.size();i++){
//            System.out.print(a.get(i).getActivity_name());
//        }//活动2活动3

        //根据社团id查找某社团活动
//        a=am.searchActivityByClubId(1);
//        for(int i=0;i<a.size();i++){
//            System.out.print(a.get(i).getActivity_name());
//        }//活动3

        //根据社团名查找某社团活动
//        a=am.searchActivityByClubName("棋");
//        for(int i=0;i<a.size();i++){
//            System.out.print(a.get(i).getActivity_name());
//        }//活动2

        //根据活动名查找某社团活动
//        a=am.searchActivityByName("2");
//        for(int i=0;i<a.size();i++){
//            System.out.print(a.get(i).getActivity_name());
//        }//活动2

        //按活动类别查询活动
//        a=am.searchActivityByCategory("体育运动");
//        for(int i=0;i<a.size();i++){
//            System.out.print(a.get(i).getActivity_name());
//        }//活动2活动3

        //查看所有活动类别
//        List<String> s=new ArrayList<String>();
//        s=am.searchActivityType();
//        for(int i=0;i<s.size();i++){
//            System.out.print(s.get(i));
//        }//体育运动公益服务兴趣爱好学术创新
    }
}
