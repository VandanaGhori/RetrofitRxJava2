package com.example.retrofitrxjava2.Retrofit;

// for Observable we need to import this instead of java.util.Observable
import io.reactivex.Observable;
import com.example.retrofitrxjava2.Model.Post;
import java.util.List;
import retrofit2.http.GET;

public interface IMyAPI {
    @GET("posts")
    Observable<List<Post>> getPosts();
}
