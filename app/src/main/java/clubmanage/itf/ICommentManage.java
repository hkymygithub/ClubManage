package clubmanage.itf;

import java.util.List;

import clubmanage.model.Comment;
import clubmanage.util.BaseException;

public interface ICommentManage {
    /**
     * 添加评论
     */
    public void addComment(int activity_id,String uid,String comment_details) throws BaseException;
    /**
     * 按用户查评论
     */
    public List<Comment> searchComByUser(String uid)throws BaseException;
    /**
     * 按活动查评论
     */
    public List<Comment> searchComByAct(int activit_id)throws BaseException;
    /**
     * 按用活动和用户查评论
     */
//    public List<Comment> searchComByUserAndAct(String uid,int activit_id)throws BaseException;
}
