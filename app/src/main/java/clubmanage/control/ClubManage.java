package clubmanage.control;

import android.util.Base64;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import clubmanage.itf.IClubManage;
import clubmanage.model.Club;
import clubmanage.model.Club_category;
import clubmanage.model.User;
import clubmanage.util.BaseException;
import clubmanage.util.DBUtil;
import clubmanage.util.DbException;

public class ClubManage implements IClubManage {
    @Override
    public List<Club> searchAllClub(boolean ifdismiss) throws BaseException {
        Connection conn = null;
        List<Club> result = new ArrayList<Club>();
        try {
            conn = DBUtil.getConnection();
            String str="";
            if(ifdismiss==false)
                str+=" where if_club_end=0";
            String sql = "select club_id,category_name,club_icon,club_cover,club_name,club_introduce,slogan,club_place,member_number,if_club_end,intendant from club "+str;
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
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

    @Override
    public List<Club> searchAllClub(String club_name, boolean ifdismiss) throws BaseException {//按名字查找社团，true表示包含所有社团，false不包含废弃社团
        Connection conn = null;
        List<Club> result = new ArrayList<Club>();
        if(club_name==null||club_name.equals("")==true) return result;
        try {
            conn = DBUtil.getConnection();
            String str="";
            if(ifdismiss==false)
                str+=" and if_club_end=0";
            String sql = "select club_id,category_name,club_icon,club_cover,club_name,club_introduce,slogan,club_place,member_number,if_club_end,intendant from club where club_name like '%"+club_name+"%'"+str;
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
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

    @Override
    public List<Club> searchClubByType(String typename, boolean ifdismiss) {
        Connection conn = null;
        List<Club> result = new ArrayList<Club>();
        try {
            conn = DBUtil.getConnection();
            String str="";
            if(ifdismiss==false)
                str+=" and if_club_end=0";
            String sql = "select club_id,category_name,club_icon,club_cover,club_name,club_introduce,slogan,club_place,member_number,if_club_end,intendant from club where category_name like '"+typename+"'"+str;
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
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

    @Override
    public Club searchClubByClubid(int clubid) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql="select a.club_id,category_name,club_icon,club_cover,club_name,club_introduce,slogan," +
                    " club_place,member_number,if_club_end,intendant from club a where " +
                    " club_id=? ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, clubid);
            java.sql.ResultSet rs = pst.executeQuery();
            Club club=new Club();
            if(rs.next()){
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
                return club;
            }
            rs.close();
            pst.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
    public List<User> searchMember(int club_id) throws BaseException {//返回所有该社团社员（包含社长，不包含社团管理员）
        List<User> result = new ArrayList<User>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select user.uid,pwd,image,name,gender,mail,major,phone_number,start_time,end_time,user_category,note from user,user_club_role where user.uid=user_club_role.uid and club_id=? and role_name!='社团管理员'";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, club_id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getString(1));
                user.setPwd(rs.getString(2));
                user.setImage(rs.getBytes(3));
                user.setName(rs.getString(4));
                user.setGender(rs.getString(5));
                user.setMail(rs.getString(6));
                user.setMajor(rs.getString(7));
                user.setPhone_number(rs.getString(8));
                user.setStart_time(rs.getTimestamp(9));
                user.setEnd_time(rs.getTimestamp(10));
                user.setUser_category(rs.getString(11));
                user.setNote(rs.getString(12));
                result.add(user);
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

    @Override
    public Club searchClubByProprieter(String uid) {//根据社长查社团
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql="select a.club_id,category_name,club_icon,club_cover,club_name,club_introduce,slogan," +
                    " club_place,member_number,if_club_end,intendant from club a,user_club_role b where " +
                    " b.uid=? and b.role_name like '社长' and a.club_id=b.club_id ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            java.sql.ResultSet rs = pst.executeQuery();
            Club club=new Club();
            if(rs.next()){
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
                return club;
            }
            rs.close();
            pst.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
    public Integer searchClubIdByProprieter(String uid) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql="select a.club_id from club a,user_club_role b where " +
                    " b.uid=? and b.role_name like '社长' and a.club_id=b.club_id ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            java.sql.ResultSet rs = pst.executeQuery();
            Integer clubid=null;
            if(rs.next()){
                clubid=rs.getInt(1);
                return clubid;
            }
            rs.close();
            pst.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    public List<Club> searchMyClub(String uid){//查询我的社团
        Connection conn = null;
        List<Club> result = new ArrayList<Club>();
        try {
            conn = DBUtil.getConnection();
            String sql="select a.club_id,category_name,club_icon,club_cover,club_name,club_introduce,slogan," +
                    " club_place,member_number,if_club_end,intendant from club a,user_club_role b where " +
                    " b.uid=? and  (b.role_name like '社长' or b.role_name like '社员') and a.club_id=b.club_id ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Club club=new Club();
                club.setClub_id(rs.getInt(1));
                club.setCategory_name(rs.getString(2));
                if (rs.getBytes(3)==null) club.setClub_icon(null);
                else club.setClub_icon(Base64.encodeToString(rs.getBytes(3),Base64.DEFAULT));
                if (rs.getBytes(3)==null) club.setClub_cover(null);
                else club.setClub_cover(Base64.encodeToString(rs.getBytes(4),Base64.DEFAULT));
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
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (conn != null)
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }
    public int searchMyClubCount(String uid){//查询我的社团的数量
        Connection conn = null;
        int count=0;
        try {
            conn = DBUtil.getConnection();
            String sql="select count(*) from user_club_role where uid=? and (role_name like '社长' or role_name like '社员')";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()){
                count+=rs.getInt(1);
            }
            rs.close();
            pst.close();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
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
    public List<Club_category> searchClubType() throws BaseException{//列出所有社团的种类
        List<Club_category> result = new ArrayList<Club_category>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select category_name from club_category";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Club_category club_category=new Club_category();
                club_category.setCategory_name(rs.getString(1));
                result.add(club_category);
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

    @Override
    public void addClub(String club_name, String category_name, String captain_id,String club_introduce, String club_place, String intendant_id) {//新建社团
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select max(club_id) from club";//找到目前存在的最大的社团id，从而确定新建社团的id为最大id+1
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            int max_id=0;
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()){
                max_id=rs.getInt(1);
            }
            sql = "insert into club(club_id,club_name,category_name,club_introduce,club_place,intendant,member_number,if_club_end) values(?,?,?,?,?,?,?,?)";//在club表中添加相关内容
            pst = conn.prepareStatement(sql);
            pst.setInt(1,max_id+1);
            pst.setString(2, club_name);
            pst.setString(3, category_name);
            pst.setString(4, club_introduce);
            pst.setString(5, club_place);
            pst.setString(6,intendant_id);
            pst.setInt(7,1);
            pst.setByte(8,(byte)0);
            pst.execute();
            pst.close();
            sql = "select club_id from club where club_name=?";//查看当前社团id
            pst = conn.prepareStatement(sql);
            pst.setString(1, club_name);
            rs = pst.executeQuery();
            int club_id=0;
            if(rs.next()) {
                club_id=rs.getInt(1);
            }else
                throw new BaseException("添加错误");
            rs.close();
            pst.close();
            sql = "insert into user_club_role(uid,club_id,role_name) values(?,?,?)";//在user_club_role身份表中添加社长的信息
            pst = conn.prepareStatement(sql);
            pst.setString(1, captain_id);
            pst.setInt(2, club_id);
            pst.setString(3,"社长");
            pst.execute();
            pst.close();
            sql = "insert into user_club_role(uid,club_id,role_name) values(?,?,?)";//在user_club_role身份表中添加社团管理员的信息
            pst = conn.prepareStatement(sql);
            pst.setString(1, intendant_id);
            pst.setInt(2, club_id);
            pst.setString(3,"社团管理员");
            pst.execute();
            pst.close();
            sql = "update area set specialties=1 where area_name=?";//把社团的地点设为私有
            pst = conn.prepareStatement(sql);
            pst.setString(1, club_place);
            pst.execute();
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
    public void deleteClub(int club_id) {//删除社团
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update club set if_club_end=1 where club_id=?";//把社团的if_club_end设为1表示该社团已被删除
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, club_id);
            pst.execute();
            pst.close();
            sql = "delete from user_club_role where club_id=?";//把user_club_role表中该社团的所有信息删除
            pst = conn.prepareStatement(sql);
            pst.setInt(1, club_id);
            pst.execute();
            pst.close();
            sql = "select club_place from club where club_id=?";//查找社团管理员的uid
            pst = conn.prepareStatement(sql);
            pst.setInt(1, club_id);
            java.sql.ResultSet rs = pst.executeQuery();
            String club_place="";
            if(rs.next()) {
                club_place = rs.getString(1);
            }
            sql = "update area set specialties=0 where area_name=?";//把社团的地点设为公有
            pst = conn.prepareStatement(sql);
            pst.setString(1, club_place);
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
    public void editCategory(int club_id, String category) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update club set category_name=? where club_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,category);
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
    public void editIntroduction(int club_id,String introduction) {//编辑社团介绍
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update club set club_introduce=? where club_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,introduction);
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

//    @Override
//    public void addSlogan(int club_id) {
//
//    }

    @Override
    public void editSlogan(int club_id,String slogan) {//编辑宣传语
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update club set slogan=? where club_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,slogan);
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

//    @Override
//    public void addLogo(int club_id) {
//
//    }

    @Override
    public void editLogo(int club_id,String club_icon) {//编辑图标
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update club set club_icon=? where club_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setBytes(1,Base64.decode(club_icon,Base64.DEFAULT));
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
    public void editCover(int club_id,String club_cover) {//编辑封面
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update club set club_cover=? where club_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setBytes(1,Base64.decode(club_cover,Base64.DEFAULT));
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
    public void joinClub(String uid, int club_id) {//加入社团
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select member_number from club where club_id=?";//查找当前该社团的成员数
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, club_id);
            int member_number=0;
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()){
                member_number=rs.getInt(1);
            }
            sql = "update club set member_number=? where club_id=?";//更新成员数为原来+1
            pst = conn.prepareStatement(sql);
            pst.setInt(1,member_number+1);
            pst.setInt(2,club_id);
            pst.execute();
            pst.close();
            sql = "insert into user_club_role(uid,club_id,role_name) values(?,?,?)";//在user_club_role表中添加该成员的信息
            pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            pst.setInt(2, club_id);
            pst.setString(3,"社员");
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
    public void secedeClub(String uid, int club_id) {//退出社团
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select member_number from club where club_id=?";//查找该社团当前成员数
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, club_id);
            int member_number=0;
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()){
                member_number=rs.getInt(1);
            }
            sql = "update club set member_number=? where club_id=?";//设置该社团的成员数-1
            pst = conn.prepareStatement(sql);
            pst.setInt(1,member_number-1);
            pst.setInt(2,club_id);
            pst.execute();
            pst.close();
            sql = "delete from user_club_role where uid=? and club_id=?";//删除user_club_role表中该同学的相关记录
            pst = conn.prepareStatement(sql);
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
    public String searchNotice(int club_id) {//查找该社团的告示
        Connection conn = null;
        String str = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select notice_details from notice where club_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,club_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                str=rs.getString(1);
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
        return str;
    }

    @Override
    public void editNotice(int club_id,String notice_details){//编辑或添加告示（存在就编辑，不存在就添加）
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            if(searchNotice(club_id)==null){
                String sql = "insert into notice(club_id,notice_details,notice_time) values(?,?,?)";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, club_id);
                pst.setString(2, notice_details);
                pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                pst.execute();
                pst.close();
            }else{
                String sql = "update notice set notice_details=? where club_id=?";
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1,notice_details);
                pst.setInt(2, club_id);
                pst.execute();
                pst.close();
            }

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
    public boolean if_userInClub(String uid, int club_id) {
        Connection conn = null;
        boolean if_in=false;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user_club_role where uid=? and club_id=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,uid);
            pst.setInt(2,club_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                if_in=true;
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
        return if_in;
    }

    @Override
    public boolean if_userIsCaptain(String uid, int club_id) {
        Connection conn = null;
        boolean if_in=false;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from user_club_role where uid=? and club_id=? and role_name='社长'";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,uid);
            pst.setInt(2,club_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                if_in=true;
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
        return if_in;
    }

    @Override
    public void transferPresident(String uid,String puid,int clubid){
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "update user_club_role set role_name='社长' where club_id=? and uid=? ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,clubid);
            pst.setString(2,uid);
            pst.execute();
            sql = "update user_club_role set role_name='社员' where club_id=? and uid=? ";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,clubid);
            pst.setString(2,puid);
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
    public void deleteMember(String uid,int clubid){
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "delete from user_club_role where club_id=? and uid=? and role_name like '社员' ";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1,clubid);
            pst.setString(2,uid);
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

    public static void main(String[] args) throws BaseException {
        ClubManage cm=new ClubManage();
        cm.searchMyClub("31701073");
//        cm.joinClub("31701015",1);
//        List<Club> club=cm.searchMyClub("31701015");
//        for(int i=0;i<club.size();i++){
//           System.out.println(club.get(i).getClub_id());
//       }
//        System.out.println(cm.searchNotice(1));

//        System.out.println(cm.searchClubByProprieter("31701073").getClub_name());
//        System.out.println(cm.searchMember(1).size());
//        List<Club> club=cm.searchAllClub(false);
//        for(Club c:club){
//            System.out.println(c.getClub_name());
//        }
//        cm.addClub("棋社","兴趣爱好","学生1","欢迎","文102","管理员1");
//        List<ClubItem> res=cm.searchAllClub("棋",true);
//        for(int i=0;i<res.size();i++){
//           System.out.print(res.get(i).getClub_id());
//           System.out.print(res.get(i).getClub_name());
//       }//返回2棋社

//        List<User> us=cm.searchMember(2);
//        for(int i=0;i<us.size();i++){
//            System.out.print(us.get(i).getName());
//            System.out.print(us.get(i).getUid());
//        }//返回学生131701011

//        List<ClubItem> res=cm.searchAllClub("音",true);
//        for(int i=0;i<res.size();i++){
//            System.out.print(res.get(i).getClub_id());
//            System.out.print(res.get(i).getClub_name());
//        }
//        List<Club_category> cc=cm.searchClubType();
//        for(int i=0;i<cc.size();i++){
//            System.out.print(cc.get(i).getCategory_name());
//        }
//        cm.addClub("轮滑社","体育运动","学生2","好","文103","管理员1");
//        List<ClubItem> res=cm.searchAllClub("",true);
//        for(int i=0;i<res.size();i++){
//           System.out.print(res.get(i).getClub_id());
//           System.out.print(res.get(i).getClub_name());
//       }//返回所有三个1音悦2棋社3轮滑社

        //编辑宣传语
//        cm.editSlogan(3,"快来玩！");

        //加入社团
//        cm.joinClub("31701073",3);
//        List<User> us=cm.searchMember(3);
//        for(int i=0;i<us.size();i++){
//            System.out.print(us.get(i).getName());
//            System.out.print(us.get(i).getUid());
//        }//学生231701012李四31701073管理员1t001

        //退出社团
//        cm.secedeClub("31701073",3);
//        List<User> us=cm.searchMember(3);
//        for(int i=0;i<us.size();i++){
//            System.out.print(us.get(i).getName());
//            System.out.print(us.get(i).getUid());
//        }//学生231701012管理员1t001

        //编辑或添加告示（存在就编辑，不存在就添加）
//        cm.editNotice(3,"abc");
//        String no=cm.searchNotice(3);
//        System.out.print(no);
//        cm.editNotice(3,"ac");
//        no=cm.searchNotice(3);
//        System.out.print(no);

        //删除社团
//        cm.deleteClub(3);
    }
}
