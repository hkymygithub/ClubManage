package clubmanage.httpInterface;

import clubmanage.message.HttpMessage;
import clubmanage.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserRequest {
    @GET("/user/login")
    Call<HttpMessage<User>> login(@Query("uid") String uid, @Query("pwd") String pwd);
}
