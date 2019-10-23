package com.royal.commen.socket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

//import com.royal.commen.Logger;
import com.royal.entity.SymbolRecord;
import com.royal.util.JSONUtils;
import com.royal.util.WebSocketUtil;
import com.royal.commen.Logger;;
import org.springframework.stereotype.Component;


@ServerEndpoint(value = "/websocket/{sign}")
@Component
public class WebSocketServer {
    Logger logger = Logger.getLogger(this.getClass());
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<WebSocketServer> connectSet = new CopyOnWriteArraySet<WebSocketServer>();
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "sign") String sign) {
        this.session = session;
        if (!WebSocketUtil.verificationSign(sign)) {
            try {
                session.close();
                return;
            } catch (IOException e) {
                logger.error("session未通过验证,关闭session时出错,sign:" + sign, e);
            }
        } else {
            connectSet.add(this);
            addOnlineCount();
        }
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        connectSet.remove(this);
        subOnlineCount();
        logger.info("这个是关闭的方法");
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("来自客户端的消息:" + message);
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("这个是异常的方法");
        logger.error("发生错误", error);
    }

    /**
     * 发送消息
     *
     * @param message
     * @throws IOException
     */
    public synchronized void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            logger.error("发送失败：", e);
            this.onClose();
        }
    }

    public static void sendPrice(SymbolRecord symbolRecord) {
        if (connectSet.size() > 0) {
            for (WebSocketServer webSocketServer : connectSet) {
                webSocketServer.sendMessage(JSONUtils.toJSONObject(symbolRecord).toString());
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

}
