package clubmanage.httpInterface;

import clubmanage.message.HttpMessage;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PersonalRequest {
    @GET("/personal/changepwd")
    Call<HttpMessage> changePwd(@Query("uid") String uid,@Query("oldpwd") String oldpwd,@Query("newpwd") String newpwd,@Query("newpwd2") String newpwd2);
    @GET("/personal/changename")
    Call<HttpMessage> changeName(@Query("uid") String uid,@Query("msg") String msg);
    @GET("/personal/changegender")
    Call<HttpMessage> changeGender(@Query("uid") String uid,@Query("msg") String msg);
    @GET("/personal/changephone")
    Call<HttpMessage> changePhone_number(@Query("uid") String uid,@Query("msg") String msg);
    @GET("/personal/changemail")
    Call<HttpMessage> changeMail(@Query("uid") String uid,@Query("msg") String msg);
    @GET("/personal/changemajor")
    Call<HttpMessage> changeMajor(@Query("uid") String uid,@Query("msg") String msg);
    @GET("/personal/changeimage")
    Call<HttpMessage> changeImage(@Query("uid") String uid,@Query("msg") String msg);
}
