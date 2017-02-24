package com.xinyu.mwp.networkapi;


import com.xinyu.mwp.networkapi.socketapi.SocketAPIFactoryImpl;

public class NetworkAPIFactoryImpl {
    private static NetworkAPIFactory networkAPIFactory = null;


    static {
        networkAPIFactory = SocketAPIFactoryImpl.getInstance();
    }


    public static void initConfig(NetworkAPIConfig config) {
        networkAPIFactory.initConfig(config);
    }

    public static NetworkAPIConfig getConfig() {
        return networkAPIFactory.getConfig();
    }

    public static UserAPI getUserAPI() {
        return networkAPIFactory.getUserAPI();
    }


    public static DealAPI getDealAPI() {
        return networkAPIFactory.getDealAPI();
    }

}
