package clubmanage.httpInterface;

import java.util.List;

import clubmanage.message.HttpMessage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PersonalRequest {
    @GET("/personal/changepwd")
    Call<HttpMessage> changePwd(@Query("uid") String uid,@Query("oldpwd") String oldpwd,@Query("newpwd") String newpwd,@Query("newpwd2") String newpwd2);
}
