package com.foxconn.crd.common.Utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 获取webservice返回信息
 * Created by Jaime on 16/2/1,09:15.
 */
public class WebServiceUtil {

    private final String TAG = getClass().getSimpleName();

    private static WebServiceUtil dataProviderInstance;

    private String mNamespace = "http://statistics.efoxconn.org/";

    private String mDataUrl = "http://10.120.81.36:5959/StatisticsService/statisticsService?wsdl";

    private HttpTransportSE httpTransportSE;
    private SoapSerializationEnvelope serializationEnvelope;

    public WebServiceUtil setNamespace(String namespace) {
        this.mNamespace = namespace;
        return getInstance();
    }

    public WebServiceUtil setUrl(String dataUrl) {
        this.mDataUrl = dataUrl;
        return getInstance();
    }

    private WebServiceUtil() {
    }

    public static synchronized WebServiceUtil getInstance() {
        if (dataProviderInstance == null) {
            synchronized (WebServiceUtil.class) {
                if (dataProviderInstance == null) {
                    dataProviderInstance = new WebServiceUtil();
                }
            }
        }
        return dataProviderInstance;
    }

    /**
     * 获取web service返回信息,该方法会阻塞UI线程.如果获取信息失败的话,如果获取返回值失败的话,该方法会访问三次server
     *
     * @param methodName     调用的web service方法名
     * @param paramNameList  参数民列表
     * @param paramValueList 对应的参数值列表
     * @return 方法返回信息
     */
    public SoapObject getResponse(String methodName, ArrayList<String> paramNameList, ArrayList<Object> paramValueList) throws XmlPullParserException, IOException {
        return getResponse(mNamespace, mDataUrl, methodName, paramNameList, paramValueList);
    }

    private SoapObject getResponse(final String namespace,
                                   final String serverUrl,
                                   String methodName,
                                   ArrayList<String> paramNameList,
                                   ArrayList<Object> paramValueList) throws IOException, XmlPullParserException {
        int failCount = 1;
        initWebserviceClient(namespace, serverUrl, methodName, paramNameList, paramValueList);
        while (failCount <= 3) {
            SoapObject returnObject = execute();
            L.i(TAG, methodName + (returnObject == null ? " fail" : " success")
                    + " to get response in the " + failCount + " times");
            if (returnObject != null) {
                return returnObject;
            } else {
                failCount++;
            }
        }
        L.i(TAG, methodName + " return null.");
        return null;
    }

    private SoapObject execute() throws XmlPullParserException, IOException {
        httpTransportSE.call(null, serializationEnvelope);
        Object object = serializationEnvelope.getResponse();
        if (object != null) {
            return (SoapObject) serializationEnvelope.bodyIn;
        } else {
            return null;
        }
    }

    private void initWebserviceClient(String namespace, String serverUrl, String methodName,
                                      ArrayList<String> paramNameList,
                                      ArrayList<Object> paramValueList) {
        if (httpTransportSE == null) {
            httpTransportSE = new HttpTransportSE(serverUrl, 15000);
            L.i(TAG, "New a HttpTransportSE instance:" + httpTransportSE.toString());
        } else {
            httpTransportSE.reset();
            L.i(TAG, "Reset HttpTransportSE instance");
        }
        if (serializationEnvelope == null) {
            serializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            serializationEnvelope.encodingStyle = "UTF-8";
            L.i(TAG, "New a SerializationEnvelope instance with \" UTF-8 \" "
                    + httpTransportSE.toString());
            serializationEnvelope.bodyOut = null;
            serializationEnvelope.bodyIn = null;
        } else {
            L.i(TAG, "The SerializationEnvelope instance has been created");
        }
        //add header info for verify
            /*serializationEnvelope.headerOut = new Element[1];
            serializationEnvelope.headerOut[0] = buildAuthHeader(mNamespace);*/
        SoapObject soapObject = null;
        if (paramValueList != null) {
            soapObject = new SoapObject(namespace, methodName);
            L.i(TAG, methodName + " mParams is not null, data:" + paramValueList.toString());
            for (int index = 0; index < paramValueList.size(); index++) {
                soapObject.addProperty(paramNameList.get(index), paramValueList.get(index));
            }
        } else {
            L.i(TAG, methodName + "'s params is  null");
        }
        serializationEnvelope.bodyOut = soapObject;
    }

    /**
     * 增加头部验证信息
     *
     * @return 封装头部信息的Element
     */
    private Element buildAuthHeader(String namespace) {
        Element hElement = new Element().createElement(namespace, "authHead");
        Element userId = new Element().createElement(namespace, "userId");
        userId.setAttribute(namespace, "userId", "root");
        Element passwordElement = new Element().createElement(namespace, "userPass");
        passwordElement.setAttribute(namespace, "userPass", "123456");
        hElement.addChild(Node.ELEMENT, userId);
        hElement.addChild(Node.ELEMENT, passwordElement);

        return hElement;
    }

}
