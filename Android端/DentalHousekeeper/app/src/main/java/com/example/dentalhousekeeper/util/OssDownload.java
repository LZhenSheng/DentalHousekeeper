package com.example.dentalhousekeeper.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class OssDownload {

    String endpoint = "endpoint";
    String accessKeyId = "accessKeyId";
    String accessKeySecret = "accessKeySecret";
    String bucketName = "bucketName";

    public void uploadFile(String name, final Context context, final String localName) {
        OSSCredentialProvider ossCredentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        OSS oss = new OSSClient(context.getApplicationContext(), endpoint, ossCredentialProvider);
        PutObjectRequest put = new PutObjectRequest(bucketName, name, localName);
        put.setProgressCallback((request, currentSize, totalSize) -> {
            Log.d("currentSize = " + currentSize, "totalSize = " + totalSize);
        });
        OSSAsyncTask ossAsyncTask= oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                if (clientException != null) {
                    Log.d("sldfj",clientException.getMessage());
                }
                if (serviceException != null) {
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());

                }
            }
        });
    }

    public void downLoadFile(String name, final Context context, final String localName) {

        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(accessKeyId, accessKeySecret);
        ClientConfiguration conf = new ClientConfiguration();
        OSS oss = new OSSClient(context, endpoint, credentialProvider, conf);

        GetObjectRequest get = new GetObjectRequest(bucketName, name);
        OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                InputStream inputStream = result.getObjectContent();
                byte[] buffer = new byte[2048];
                int len;
                File file = null;
                try {
                    //is = response.body().byteStream();
                    file = new File(context.getExternalCacheDir(), localName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(inputStream);
                    while ((len = bis.read(buffer)) != -1) {
                        // 您可以在此处编写代码来处理下载的数据
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            // GetObject请求成功，将返回GetObjectResult，其持有一个输入流的实例。返回的输入流，请自行处理。
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
        //task.cancel(); // 可以取消任务。
        //task.waitUntilFinished(); // 等待任务完成。
    }
}

