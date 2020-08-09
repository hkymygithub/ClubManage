package clubmanage.httpInterface;

import java.util.List;

import clubmanage.message.HttpMessage;
import clubmanage.model.Create;
import clubmanage.model.Create_activity;
import clubmanage.model.Create_club;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApplicationRequest {
    @GET("/application/ifhaveclubappli")
    Call<HttpMessage<Boolean>> ifHaveClubAppli(@Query("uid") String uid);
    @GET("/application/signupactivity")
    Call<HttpMessage> signupActivity(@Query("uid") String uid,@Query("activityid")int activityid);
    @GET("/application/searchcreateactivityappli")
    Call<HttpMessage<List<Create_activity>>> searchCreateActivityAppli();
    @GET("/application/searchcreateclubappli")
    Call<HttpMessage<List<Create_club>>> searchCreateClubAppli();
    @GET("/application/searchcreateappli")
    Call<HttpMessage<List<Create>>> searchCreateAppli(@Query("uid") String uid);
    @GET("/application/feedbackclubappli")
    Call<HttpMessage> feedbackClubAppli(@Query("applyid") int applyid,@Query("state") int state,@Query("uid") String uid,@Query("suggestion") String suggestion);
    @GET("/application/searchcreateclubapplibyid")
    Call<HttpMessage<Create_club>> searchCreateClubAppliByID(@Query("applyid") int applyid);
    @GET("/application/feedbackactivityappli")
    Call<HttpMessage> feedbackActivityAppli(@Query("applyid") int applyid,@Query("state") int state,@Query("uid") String uid,@Query("suggestion") String suggestion);
    @GET("/application/searchcreateactivityapplibyid")
    Call<HttpMessage<Create_activity>> searchCreateActivityAppliByID(@Query("applyid") int applyid);

    @POST("/application/addqctivityqppli")
    Call<HttpMessage> addActivityAppli(@Body Create_activity create_activity);
    @POST("/application/addclubappli")
    Call<HttpMessage> addClubAppli(@Body Create_club create_club);
    @POST("/application/editactivityqppli")
    Call<HttpMessage> editActivityAppli(@Body Create_activity create_activity);

    @GET("/application/searchcreateactivityapplibyclub")
    Call<HttpMessage<List<Create_activity>>> searchCreateActivityAppliByClub(@Query("clubid") int clubid);

}
