package clubmanage.httpInterface;

import java.util.List;

import clubmanage.message.HttpMessage;
import clubmanage.model.Club;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AttentionRequest {
    @GET("/attention/searchattencount")
    Call<HttpMessage<Integer>> searchAttenCount(@Query("uid") String uid);
    @GET("/attention/issubscribe")
    Call<HttpMessage<Boolean>> issubscribe(@Query("uid") String uid,@Query("clubid") int clubid);
    @GET("/attention/searchattenbyuser")
    Call<HttpMessage<List<Club>>> searchAttenByUser(@Query("uid") String uid);
    @GET("/attention/addattention")
    Call<HttpMessage> addAttention(@Query("uid") String uid,@Query("clubid")int clubid);
    @GET("/attention/deleteattention")
    Call<HttpMessage> deleteAttention(@Query("uid") String uid,@Query("clubid")int clubid);
}
