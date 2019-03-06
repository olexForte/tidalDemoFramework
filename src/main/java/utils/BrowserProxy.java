package utils;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.*;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


public class BrowserProxy {
    private static BrowserProxy instance = null;
    public BrowserMobProxy proxyServer;
    private boolean isServerStarted;
    public String currentHarName;
    public FileWriter fos = null;
    private static final Logger LOGGER = LoggerFactory.getLogger(BrowserProxy.class);

    public static BrowserProxy getInstance() {
        if (instance == null)
            instance = new BrowserProxy();
        return instance;
    }

    /**
     * Start proxy
     * @param capabilities
     */
    public void startServer(DesiredCapabilities capabilities) {
        proxyServer = new BrowserMobProxyServer();
        try {

            proxyServer.setTrustAllServers(true);
            proxyServer.start();
            isServerStarted = true;
        } catch (Exception e) {
            throw new RuntimeException("Cant start proxy-server on port: " + proxyServer.getPort(), e);
        }

        Proxy proxy = null;
        try {
            proxy = createHttpProxy(proxyServer.getPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // createNewHar();
        // configure it as a desired capability
        capabilities.setCapability(CapabilityType.PROXY, proxy);
    }

    /**
     * create proxy
     * @param port
     * @return
     * @throws UnknownHostException
     */
    private Proxy createHttpProxy(int port) throws UnknownHostException {
        //Proxy proxy = new Proxy();
        Proxy proxy = ClientUtil.createSeleniumProxy(proxyServer);

        String hostIp = Inet4Address.getLocalHost().getHostAddress();
        proxy.setHttpProxy(hostIp+":" + proxyServer.getPort());
        proxy.setSslProxy(hostIp+":" + proxyServer.getPort());

//        proxy.setProxyType(Proxy.ProxyType.MANUAL);
//        String proxyStr = "localhost:" + port;  //String.format("%s:%d", InetAddress.getLocalHost().getCanonicalHostName(),  port);
//        proxy.setHttpProxy(proxyStr);
//        proxy.setSslProxy(proxyStr);
        return proxy;
    }

    /**
     * create new HAR
     * @param name
     */
    public void createNewHar(String name){
        currentHarName = name;
        proxyServer.newHar();
 /*       //HAR
        Har har = proxyServer.getHar();

        try {
            fos = new FileWriter(new File(currentHarName));
            har.writeTo(fos);
        } catch (FileNotFoundException e) {
            System.out.println("HAR file creation failed");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("HAR file update failed");
            e.printStackTrace();
        }
*/
    }

    /**
     * stop PROXY
     */
    public void stopServer() {
        if (isServerStarted) {
            try {
                proxyServer.stop();
            } catch (Exception e) {
                throw new RuntimeException("Cant stop proxy server", e);
            }
        }
    }

    public Har getHar() {
        return proxyServer.getHar();
    }

    /**
     * get nuber of substrings in request
     * @param typeOfRequest
     * @param partOfURL
     * @param expectedTextInContent
     * @return
     */
    public int getNumberOfSubstringsInRequest(String typeOfRequest, String partOfURL, String expectedTextInContent) {
        boolean tooLate = false;
        boolean wasFound = false;
        int maxAttempts = 30;
        Har har;
        for ( int curAttempt = 0; curAttempt < maxAttempts; curAttempt++ ) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            har = proxyServer.getHar();
            try {
                for (HarEntry entry : har.getLog().getEntries())
                    if (entry.getRequest().getUrl().contains(partOfURL) && entry.getRequest().getMethod().contains(typeOfRequest))
                        LOGGER.info(entry.getRequest().getPostData().getText());
//                        return entry.getRequest().getPostData().getText().split(expectedTextInContent).length - 1;
            } catch (Exception e) {
                // nothing was found
            }
        }
        return 0;
    }


    /**
     * write HAR file to default file
     * @return
     */
    public String writeHarToFile() {
        String filename = currentHarName;
        try {
            Har har = proxyServer.getHar();
            if (har != null){
                if(har.getLog().getEntries().size() > 0) {
                    FileWriter wr = new FileWriter(new File(filename));
                    wr.write(packEntryToString(har));
                    wr.close();
                } else
                    filename = null;
            } else
                filename = null;
        } catch (Exception e) {
            filename = null;
            e.printStackTrace();
        }
        return filename;
    }

    /**
     * transform HAR entry to String
     * @param har
     * @return
     */
    public String packEntryToString(Har har){
        StringBuilder sb = new StringBuilder();
        int numberOfRequests = 0;
        for (HarEntry entry : har.getLog().getEntries()){
            numberOfRequests++;
            String url =entry.getRequest().getUrl();
            if (    !url.contains(".woff") &&
                    !url.contains(".png") &&
                    !url.contains(".gif") &&
                    !url.contains(".ttf") &&
                    !url.contains(".css") &&
                    !url.contains(".ico") &&
                    !url.contains("fonts.googleapis.com") &&
                    !url.contains("gstatic.com") &&
                    !url.contains("accounts.google.com") &&
                    !url.contains(".js") ){

                sb.append("==================== " + numberOfRequests + " ==================== ");
                sb.append(entry.getServerIPAddress());
                sb.append("\n");
                sb.append("Request method and URL: " + entry.getRequest().getMethod() + " " + entry.getRequest().getUrl());
                Iterator<HarNameValuePair> iterator = entry.getRequest().getQueryString().iterator();
                while (iterator.hasNext()) {
                    HarNameValuePair item = iterator.next();
                    sb.append(item.getName() + " = " + item.getValue() + "\n");
                }
/*
                sb.append("\n== cookies \n");
                Iterator<HarCookie> iterCookies = entry.getRequest().getCookies().iterator();
                while (iterCookies.hasNext()) {
                    HarCookie cookie = iterCookies.next();
                    sb.append(cookie.getName() + " = " + cookie.getValue() + "\n");
                }
*/
                if (entry.getRequest().getPostData() != null)
                    sb.append(entry.getRequest().getPostData().getText());
                sb.append("\n");
                sb.append("\n");
                sb.append(entry.getResponse().getStatusText());
                sb.append("\n");
                sb.append("Status code: " + entry.getResponse().getStatus());
                sb.append("\n");
                if (entry.getResponse().getContent() != null)
                    sb.append(entry.getResponse().getContent().getText());
/*
                sb.append("\n== cookies \n");
                iterCookies = entry.getResponse().getCookies().iterator();
                while (iterCookies.hasNext()) {
                    HarCookie cookie = iterCookies.next();
                    sb.append(cookie.getName() + " = " + cookie.getValue() + "\n");
                }
               */
                sb.append("\n\n");
            }
        }
        return sb.toString();
    }

    /**
     * write dump to default har file
     * @return
     */
    public String dumpHARFileFromProxy() {
        String fileName = null;
        fileName = writeHarToFile();
        return fileName;
    }

    /**
     * save HAR file to output dir
     * @param testName
     */
    public String createHARFileFromProxy(String testName) {
        String filename = FileIO.OUTPUT_DIR + new SimpleDateFormat("yyMMdd_HHmmss").format(new Date()) + "_" + String.valueOf(System.currentTimeMillis()).substring(9) + "_" + testName.replaceAll("\\W","") + ".txt";
        createNewHar(filename);
        return filename;
    }
}
