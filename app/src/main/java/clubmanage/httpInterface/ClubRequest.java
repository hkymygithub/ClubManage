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
}
