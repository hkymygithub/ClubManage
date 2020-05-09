package clubmanage.httpInterface;

import java.util.List;

import clubmanage.message.HttpMessage;
import clubmanage.model.Activity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ActivityRequest {
    @GET("/activity/all")
    Call<HttpMessage<List<Activity>>> searchAllActivity();
    @GET("/activity/searchbycategory")
    Call<HttpMessage<List<Activity>>> searchActivityByCategory(@Query("category") String category);
    @GET("/activity/searchmyactivity")
    Call<HttpMessage<List<Activity>>> searchMyActivity(@Query("uid") String uid);
    @GET("/activity/searchmyactivitycount")
    Call<HttpMessage<Integer>> searchMyActivityCount(@Query("uid") String uid);
    @GET("/activity/ifparticipate")
    Call<HttpMessage<Boolean>> ifparticipate(@Query("uid") String uid,@Query("activityid")int activityid);
    @GET("/activity/searchactivitybyid")
    Call<HttpMessage<Activity>> searchActivityById(@Query("activityid")int activityid);
    @GET("/activity/findclubnamebyactivityid")
    Call<HttpMessage<String>> findClubNameByActivityId(@Query("activityid")int activityid);
    @GET("/activity/findproprieternamebyactivityId")
    Call<HttpMessage<String>> findProprieterNameByActivityId(@Query("activityid")int activityid);
    @GET("/activity/searchactivitybyclubId")
    Call<HttpMessage<List<Activity>>> searchActivityByClubId(@Query("clubid")int clubid);
    @GET("/activity/searchactivitybyname")
    Call<HttpMessage<List<Activity>>> searchActivityByName(@Query("actname")String actname);
}
