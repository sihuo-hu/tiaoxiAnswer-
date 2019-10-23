package com.royal.commen.socket;


import com.royal.entity.SymbolRecord;
import com.royal.service.ISymbolRecordService;
import com.royal.task.ThreadPoolUtil;
import com.royal.util.DateUtils;
import com.royal.util.MT4Utils;
import com.royal.util.SpringContextUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

/**
 * Created by jack on 2017/10/25.
 */
public class WebsocketClient {
    private static final Logger log = LoggerFactory.getLogger (WebsocketClient.class);
    public static WebSocketClient client;
    public static int index = 1;
    public static boolean isOpen;
    public static boolean overtime;

    public static void open() {
        try {
            client = new WebSocketClient (new URI ("ws://139.159.132.117:37001"), new Draft_6455 ()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    log.info ("打开链接");
                    isOpen=true;
                }

                @Override
                public void onMessage(String s) {
                    if (!s.equals ("heartbeat")) {
                        SymbolRecord symbolRecord = MT4Utils.StringToSymbolRecord (s);
                        if (symbolRecord != null) {
//                            System.out.println (symbolRecord.toString ());
                            String saveTime = DateUtils.getFormatDateTime (symbolRecord.getMarketTime (), "yyyy-MM-dd HH:mm");
                            String date = DateUtils.getFormatDate (new Date ());
                            Date strat = DateUtils.StringToDate (date + " 06:00:00");
                            Date end = DateUtils.StringToDate (date + " 06:10:00");
                            client.send ("");
                            //6点至6.10分之间不保存任何数据
                            if (!DateUtils.belongCalendar (symbolRecord.getMarketTime (), strat, end)) {
                                ThreadPoolUtil.sendPrice (symbolRecord);
                                ThreadPoolUtil.transactionsTrigger (symbolRecord);
                                ThreadPoolUtil.maxAndMin (symbolRecord);
                                ThreadPoolUtil.entryOrdersSave (symbolRecord);
                                MT4Utils.setSymbolRecord (symbolRecord.getSymbolCode (), symbolRecord);
                                //此处需要整体改造，改成固定存在一个对象里，每分钟往数据库里存一次
                                if (!saveTime.equals (MT4Utils.saveTimeMap.get (symbolRecord.getSymbolCode ()))) {
                                    ThreadPoolUtil.open (symbolRecord);
                                    MT4Utils.saveTimeMap.put (symbolRecord.getSymbolCode (), saveTime);
                                    ISymbolRecordService symbolRecordService = (ISymbolRecordService) SpringContextUtils.getBean (
                                            "symbolRecordServiceImpl");
                                    symbolRecordService.add (symbolRecord);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    log.info ("链接已关闭");
                    client.close ();
                    isOpen=false;
                }

                @Override
                public void onError(Exception e) {
                    log.error ("发生错误，已关闭", e);
                    client.close ();
                    isOpen=false;
                }
            };
        } catch (URISyntaxException e) {
            log.error ("获取socket发生错误", e);
        }

        client.connect ();
        while (!client.getReadyState ().equals (WebSocket.READYSTATE.OPEN)&&!overtime) {
            try {
                Thread.sleep (100);
            } catch (InterruptedException e) {
                log.error("获取实时数据，暂停时间失败",e);
            }
//            System.out.println ("还没有打开"+index);
            if(index>100){
                overtime=true;
                client.close();
            }
            index++;
        }
        if(client.getReadyState ().equals (WebSocket.READYSTATE.OPEN)){
            isOpen=true;
        }
        index=0;
        overtime=false;
        log.info ("打开了?"+isOpen);

    }

    public static void main(String[] args) {
        WebsocketClient.open();
    }

}
