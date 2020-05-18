package clubmanage.httpInterface;

import clubmanage.message.HttpMessage;
import clubmanage.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserRequest {
    @GET("/user/login")
    Call<HttpMessage<User>> login(@Query("uid") String uid, @Query("pwd") String pwd);
    @GET("/user/register")
    Call<HttpMessage> reg(@Query("uid") String uid,
                          @Query("uid") String pwd1,
                          @Query("uid") String pwd2,
                          @Query("uid") String name,
                          @Query("uid") String mail,
                          @Query("uid") String phone);

}
