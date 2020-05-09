package clubmanage.httpInterface;

import clubmanage.message.HttpMessage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AttentionRequest {
    @GET("/attention/searchattencount")
    Call<HttpMessage<Integer>> searchAttenCount(@Query("uid") String uid);
    @GET("/attention/issubscribe")
    Call<HttpMessage<Boolean>> issubscribe(@Query("uid") String uid,@Query("clubid") int clubid);

}
