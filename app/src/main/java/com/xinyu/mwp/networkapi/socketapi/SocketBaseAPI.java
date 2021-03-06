package com.xinyu.mwp.networkapi.socketapi;

import android.os.Handler;
import android.os.Looper;

import com.xinyu.mwp.entity.BaseEntity;
import com.xinyu.mwp.listener.OnAPIListener;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPIRequestManage;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketAPIResponse;
import com.xinyu.mwp.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.xinyu.mwp.util.JSONEntityUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yaowang on 2017/2/20.
 */

public class SocketBaseAPI {

    /**
     * socket请求返回 JSONObject
     * @param socketDataPacket
     * @param listener
     */
    public void requestJsonObject(SocketDataPacket socketDataPacket, final OnAPIListener listener) {
        SocketAPIRequestManage.getInstance().startJsonRequest(socketDataPacket, new OnAPIListener<SocketAPIResponse>() {
            @Override
            public void onError(Throwable ex) {
                SocketBaseAPI.this.onError(listener,ex);
            }

            @Override
            public void onSuccess(SocketAPIResponse socketAPIResponse) {
                SocketBaseAPI.this.onSuccess(listener,socketAPIResponse.jsonObject());
            }
        });
    }

    protected void onError(final OnAPIListener listener, final Throwable ex) {
        if( listener != null ) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    listener.onError(ex);
                }
            });
        }
    }

    protected void onSuccess(final OnAPIListener listener, final Object object) {
        if( listener != null ) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    listener.onSuccess(object);
                }
            });
        }
    }

    /**
     * socket请求返回 cls Entity
     * @param socketDataPacket
     * @param cls
     * @param listener
     */
    public void requestEntity(SocketDataPacket socketDataPacket, final Class<? extends BaseEntity> cls, final OnAPIListener listener) {
        SocketAPIRequestManage.getInstance().startJsonRequest(socketDataPacket, new OnAPIListener<SocketAPIResponse>() {
            @Override
            public void onError(Throwable ex ){
                SocketBaseAPI.this.onError(listener,ex);
            }

            @Override
            public void onSuccess(SocketAPIResponse socketAPIResponse) {
                if( listener != null ) {
                    Object object = JSONEntityUtil.JSONToEntity(cls,socketAPIResponse.jsonObject());
                    SocketBaseAPI.this.onSuccess(listener,object);

                }
            }
        });
    }

    /**
     * socket请求返回 List<cls Entity?
     * @param socketDataPacket
     * @param listName 列表字段名
     * @param cls
     * @param listener
     */
    public void requestEntitys(SocketDataPacket socketDataPacket, final String listName, final Class<? extends BaseEntity> cls, final OnAPIListener listener) {
        SocketAPIRequestManage.getInstance().startJsonRequest(socketDataPacket, new OnAPIListener<SocketAPIResponse>() {
            @Override
            public void onError(Throwable ex) {
                SocketBaseAPI.this.onError(listener,ex);
            }

            @Override
            public void onSuccess(SocketAPIResponse socketAPIResponse) {
                if( listener != null ) {
                    try {
                        JSONArray jsonArray = socketAPIResponse.jsonObject().getJSONArray(listName);
                        List<BaseEntity> list = (List<BaseEntity>) JSONEntityUtil.JSONToEntitys(cls,jsonArray);
                        SocketBaseAPI.this.onSuccess(listener,list);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        onError(e);
                    }
                }
            }
        });
    }

    protected SocketDataPacket socketDataPacket(short operateCode,byte type,HashMap<String,Object> map) {
        SocketDataPacket socketDataPacket = null;
        try {
            socketDataPacket = new SocketDataPacket(operateCode, type,map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return socketDataPacket;
    }
}
