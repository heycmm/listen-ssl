package czx.me.app.service.tasks;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * @author：czx.me 2020/10/20
 */
public class SSLCertExpiry {

    public static int getRemainingDay(String cn) throws IOException {
        int day = 999;
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {

                    public X509Certificate[] getAcceptedIssuers() {
                        // TODO Auto-generated method stub
                        return null;
                    }

                    public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                            throws CertificateException {
                        // TODO Auto-generated method stub

                    }

                    public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                            throws CertificateException {
                        // TODO Auto-generated method stub

                    }
                }
        };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            System.out.println("could not install trust manager.. continuing here; it may not be necessary");
        }


        URL url = new URL("https://" + cn);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.connect();
        Certificate[] certs = conn.getServerCertificates();
        for (Certificate c : certs) {
            X509Certificate xc = (X509Certificate) c; // we should really check the type beore doing this typecast..
            String dn = xc.getSubjectDN().getName();
            if (dn.contains(cn)) {
                Date expiresOn = xc.getNotAfter();
                Date now = new Date();
                System.out.println(cn + " certificate expires on :" + expiresOn + ".. only " +
                        (expiresOn.getTime() - now.getTime()) / (1000 * 60 * 60 * 24) + " days to go");
                day = (int) ((expiresOn.getTime() - now.getTime()) / (1000 * 60 * 60 * 24));
            }
        }
        return day;
    }


}
