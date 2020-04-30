package clubmanage.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import clubmanage.itf.ICommentManage;
import clubmanage.model.Comment;
import clubmanage.util.BaseException;
import clubmanage.util.DBUtil;
import clubmanage.util.DbException;

public class CommentManage implements ICommentManage {
    @Override
    public void addComment(int activity_id, String uid, String comment_details) throws BaseException {
        if (comment_details == null || "".equals(comment_details))
            throw new BaseException("评论不能为空");
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "insert into comment(activity_id,uid,comment_details,comment_time) values(?,?,?,?)";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, activity_id);
            pst.setString(2, uid);
            pst.setString(3, comment_details);
            pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.execute();
            pst.close();
//            JOptionPane.showMessageDialog(null, "已添加科目 " + sub_name + " ，id为" + sub_id, "提示",
//                    JOptionPane.PLAIN_MESSAGE);
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
    public List<Comment> searchComByUser(String uid) throws BaseException {
        List<Comment> result = new ArrayList<Comment>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select comment_id,activity_id,uid,comment_details,comment_time from comment where uid=? order by comment_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, uid);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setComment_id(rs.getInt(1));
                comment.setActivity_id(rs.getInt(2));
                comment.setUid(rs.getString(3));
                comment.setComment_details(rs.getString(4));
                comment.setComment_time(rs.getTimestamp(5));
                result.add(comment);
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
    public List<Comment> searchComByAct(int activit_id) throws BaseException {
        List<Comment> result = new ArrayList<Comment>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select comment_id,activity_id,uid,comment_details,comment_time from comment where activity_id=? order by comment_id";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, activit_id);
            java.sql.ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setComment_id(rs.getInt(1));
                comment.setActivity_id(rs.getInt(2));
                comment.setUid(rs.getString(3));
                comment.setComment_details(rs.getString(4));
                comment.setComment_time(rs.getTimestamp(5));
                result.add(comment);
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

//    @Override
//    public List<Comment> searchComByUserAndAct(String uid, int activit_id)throws BaseException {
//        return null;
//    }
public static void main(String[] args) throws BaseException {
    CommentManage cm=new CommentManage();
    //添加评论
//    cm.addComment(2,"31701073","aaa");
//    cm.addComment(1,"31701011","bbb");

    //按用户查评论
    List<Comment> co=new ArrayList<Comment>();
//    co=cm.searchComByUser("31701011");
//    for(int i=0;i<co.size();i++){
//            System.out.print(co.get(i).getComment_details());
//        }//bbb

    //按活动查评论
    co=cm.searchComByAct(2);
    for(int i=0;i<co.size();i++){
            System.out.print(co.get(i).getComment_details());
        }//aaa
}
}
