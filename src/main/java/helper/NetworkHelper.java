package helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class NetworkHelper {

    public NetworkHelper() {

    }

    public boolean verify(String response, String ip) throws UnsupportedEncodingException, IOException {

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            // TODO: Finish setting reCAPTCHA up.
            HttpPost httpPost = new HttpPost("https://www.google.com/recaptcha/api/siteverify");

            List<BasicNameValuePair> params = new ArrayList<>();

            params.add(new BasicNameValuePair("secret", System.getenv("g-recaptcha-secret")));
            params.add(new BasicNameValuePair("response", response));
            params.add(new BasicNameValuePair("remoteip", ip));

            httpPost.setEntity(new UrlEncodedFormEntity(params));

            CloseableHttpResponse closeableHttpResponse = client.execute(httpPost);

            return false;
        }
    }

}
