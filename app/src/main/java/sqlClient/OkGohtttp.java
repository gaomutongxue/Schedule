package sqlClient;

import android.telecom.Call;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * Created by xiaowentao85336773 on 2018/2/26.
 */

public class OkGohtttp {
    public static void getaffair(String url){
        OkGo.<String>get(url)     // 请求方式和请求url
                .tag(1)                       // 请求的 tag, 主要用于取消对应的请求
                .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                       Log.d("net==",response.body().toString()) ;
                    }
                    public void onError(Response<String> response){

                    }
                });
    }
}
