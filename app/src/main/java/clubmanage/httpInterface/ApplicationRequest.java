package clubmanage.httpInterface;

import java.util.List;

import clubmanage.message.HttpMessage;
import clubmanage.model.Create;
import clubmanage.model.Create_activity;
import clubmanage.model.Create_club;
import retrofit2.Call;
import retrofit2.http.GET;
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
}
