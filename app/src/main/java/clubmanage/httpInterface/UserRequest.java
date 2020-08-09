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
                          @Query("pwd1") String pwd1,
                          @Query("pwd2") String pwd2,
                          @Query("name") String name,
                          @Query("mail") String mail,
                          @Query("phone") String phone);

}
