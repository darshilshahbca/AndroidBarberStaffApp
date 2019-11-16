package com.example.androidbarberstaffapp.Retrofit;

import com.example.androidbarberstaffapp.Model.FCMResponse;
import com.example.androidbarberstaffapp.Model.FCMSendData;
import io.reactivex.Observable;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAl0AwVsA:APA91bHrx9-SGwU_pES02DMK4Vfre98wdL4esuJPQsLJoYQph85MC-XUDiDFhLManQjx39-PK51-j_voE03IQE-sdQTVhAzOm6ANqtur3_vvQn_-MkhQdLooj_jvRZHlFPlbTDveO4Ik"
    })
    @POST("fcm/send")
    Observable<FCMResponse> sendNotification (@Body FCMSendData body);

}
