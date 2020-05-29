package clubmanage.httpInterface;

import java.util.List;

import clubmanage.message.HttpMessage;
import clubmanage.model.Club;
import clubmanage.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClubRequest {
    @GET("/club/all")
    Call<HttpMessage<List<Club>>> searchAllClub(@Query("ifdismiss") boolean ifdismiss);
    @GET("/club/searchbycategory")
    Call<HttpMessage<List<Club>>> searchClubByType(@Query("category") String category,@Query("ifdismiss") boolean ifdismiss);
    @GET("/club/searchmyclub")
    Call<HttpMessage<List<Club>>> searchMyClub(@Query("uid") String uid);
    @GET("/club/searchclubid")
    Call<HttpMessage<Integer>> searchClubIdByProprieter(@Query("uid") String uid);
    @GET("/club/searchmyclubcount")
    Call<HttpMessage<Integer>> searchMyClubCount(@Query("uid") String uid);
    @GET("/club/ifuserinclub")
    Call<HttpMessage<Boolean>> if_userInClub(@Query("uid") String uid,@Query("clubid") int clubid);
    @GET("/club/searchmember")
    Call<HttpMessage<List<User>>> searchMember(@Query("clubid") int clubid);
    @GET("/club/searchnotice")
    Call<HttpMessage<String>> searchNotice(@Query("clubid") int clubid);
    @GET("/club/searchclubbyclubid")
    Call<HttpMessage<Club>> searchClubByClubid(@Query("clubid") int clubid);
    @GET("/club/searchallclub")
    Call<HttpMessage<List<Club>>> searchAllClub(@Query("clubname") String clubname,@Query("ifdismiss") boolean ifdismiss);

    @GET("/club/searchclubbyproprieter")
    Call<HttpMessage<Club>> searchClubByProprieter(@Query("uid") String uid);
    @GET("/club/editcategory")
    Call<HttpMessage> editCategory(@Query("clubid") int club_id,@Query("category") String category);
    @GET("/club/editslogan")
    Call<HttpMessage> editSlogan(@Query("clubid") int club_id,@Query("slogan") String slogan);
    @GET("/club/editintroduction")
    Call<HttpMessage> editIntroduction(@Query("clubid") int club_id,@Query("introduction") String introduction);
    @GET("/club/editnotice")
    Call<HttpMessage> editNotice(@Query("clubid") int club_id,@Query("notice") String notice);
    @GET("/club/editlogo")
    Call<HttpMessage> editLogo(@Query("clubid") int club_id,@Query("club_icon") String club_icon);
    @GET("/club/editcover")
    Call<HttpMessage> editCover(@Query("clubid") int club_id,@Query("club_cover") String club_cover);
    @GET("/club/ifuseriscaptain")
    Call<HttpMessage<Boolean>>if_userIsCaptain(@Query("uid") String uid,@Query("clubid") int club_id);
    @GET("/club/joinclub")
    Call<HttpMessage> joinClub(@Query("uid") String uid,@Query("clubid") int club_id);
    @GET("/club/transferpresident")
    Call<HttpMessage> transferPresident(@Query("tuid") String tuid,@Query("fuid") String fuid,@Query("clubid") int club_id);
    @GET("/club/deletemember")
    Call<HttpMessage> deleteMember(@Query("uid") String uid,@Query("clubid") int club_id);
}
