package com.example.boot3.https.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.lang.NonNull;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.HttpURLConnection;
import java.security.cert.X509Certificate;

/**
 * 兼容调Https接口
 * <p>
 *     设置 RestTemplate 对https的支持
 *     <code>
 *          RestTemplate restTemplateForIP() {
 *              return new RestTemplate(new HttpsClientRequestFactory());
 *          }
 *     </code>
 * @author caimeng
 * @date 2021/12/10 11:05
 */
@Slf4j
public class HttpsClientRequestFactory extends SimpleClientHttpRequestFactory {

    /**
     * 跳过证书效验的 SSLContext
     *
     * @return SSLContext
     * @throws Exception Exception
     */
    public static SSLContext createIgnoreVerifySSL() throws Exception {
        SSLContext sc = SSLContext.getInstance("TLS");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    @Override
    protected void prepareConnection(@NonNull HttpURLConnection connection, @NonNull String httpMethod) {
        try {
            // https协议，修改协议版本
            if (connection instanceof HttpsURLConnection httpsConnection) {
                log.info("++++++++++ https ++++++++++++");
                httpsConnection.setHostnameVerifier((cert, s) -> true);
                SSLContext ctx = createIgnoreVerifySSL();
                httpsConnection.setSSLSocketFactory(ctx.getSocketFactory());
                super.prepareConnection(httpsConnection, httpMethod);
            }
            super.prepareConnection(connection, httpMethod);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
