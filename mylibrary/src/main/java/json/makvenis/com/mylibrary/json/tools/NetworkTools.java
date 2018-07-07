package json.makvenis.com.mylibrary.json.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dell on 2018/7/6.
 */

public class NetworkTools {

    public static final int UPLOAD_CALLBANK= 0x1002;
    public static final int UPLOAD_CALLBANK_DATA= 0x1003;

    //带进度条的上传
    /*文件上传*/
    /**
     * @param strPath 上传文件的路径
     * @param context
     * @param handler
     * @param servicePath 服务器地址
     */
    public static void upload(String strPath, Context context, final Handler handler, String servicePath) {
        //服务器接受文件路径
        //String mPath = Configfile.APP_NAME + "query/UploadServlet";

        //获取文件路径
        MediaType type = MediaType.parse("multipart/form-data");
        OkHttpClient http = new OkHttpClient();
        File mFile = new File(strPath);
		 /*文件后缀及其名称获取*/
        final String[] splite = strPath.split("/");
        int len = splite.length;
        String da = splite[len - 1];

        if (!mFile.exists()) {
            //System.out.println("文件不存在!");
            Toast.makeText(context,"文件不存在",Toast.LENGTH_SHORT).show();
        } else {
            RequestBody fileBody = RequestBody.create(type, mFile);
            RequestBody requestBody = new MultipartBody.Builder()
                    /**
                     * @? addFromDataPart(服务器接收的路径, 文件名, 文件的类型(提交方式));
                     * @？ da 服务器接收文件的文件名称
                     * @？ filebody 表示上传文件以及上传的文件体
                     */
                    .addFormDataPart("uploadfile", da, fileBody)
                    .build();

             /*获取文件总大小*/
            final long gone = mFile.length();
             /*子线程执行*/
            ProgressRequestBodyListener.OnProgressRequestListener p = new ProgressRequestBodyListener.OnProgressRequestListener() {
                /**
                 * @param k 当前的上传量
                 * @param m 文件的总大小 但是这里使用错误 所以在上面定义获取文件的总大小
                 * @param done bool 上传完毕则表示为true 接口定义k==m
                 */
                @Override
                public void onRequestProgress(long k, long m, boolean done) {
                    //System.out.println((100 * k) / m +  "%");
                    //System.out.println(k+"======"+m+"====="+gone);

                    int cd = (int) ((int) (k * 100) / gone);
                    //System.out.println(cd + "%");
                    /*通知主线程不停的更新ui界面*/
                    Message msg=new Message();
                    msg.what=NetworkTools.UPLOAD_CALLBANK;
                    msg.obj=cd;//int值
                    handler.sendMessage(msg);
                }
            };

			/*请求体*/
            Request requestPostFile = new Request.Builder()
                    .url(servicePath)
                    //.post(requestBody) //不带监听上传进度的方式采用
                    .post(ProgressRequestBodyListener.addProgressRequestListener(requestBody, p))
                    .build();
            http.newCall(requestPostFile).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String string = response.body().string();
                    Log.e("TAG", new Date()+" >>> (上传)服务器返回的json "+string);
                    if(string != null){
                        Message msg=new Message();
                        msg.obj=string;
                        msg.what=NetworkTools.UPLOAD_CALLBANK_DATA;
                        handler.sendMessage(msg);
                    }
                }
            });
        }
    }


}
