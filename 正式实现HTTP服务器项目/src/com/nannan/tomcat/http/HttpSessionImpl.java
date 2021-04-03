package com.nannan.tomcat.http;

import com.nannan.standard.http.HttpSession;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpSessionImpl implements HttpSession {
    public final String sessionId;
    private final Map<String, Object> sessionData;

    @Override
    public String toString() {
        return "HttpSessionImpl{" +
                "sessionId='" + sessionId + '\'' +
                ", sessionData=" + sessionData +
                '}';
    }

    // 没有从 cookie 中拿到 session-id 时构建对象使用
    public HttpSessionImpl() {
        sessionId = UUID.randomUUID().toString();
        sessionData = new HashMap<>();
    }

    // 从 cookie 中拿到 session-id 时构建对象使用
    public HttpSessionImpl(String sessionId) throws IOException, ClassNotFoundException {
        this.sessionId = sessionId;
        sessionData = loadSessionData(sessionId);
    }

    private static final String SESSION_BASE = "C:\\Users\\86187\\Java\\正式实现HTTP服务器项目\\sessions";
    // 文件名：<session-id>.session
    private Map<String, Object> loadSessionData(String sessionId) throws IOException, ClassNotFoundException {
        String sessionFilename = String.format("%s\\%s.session", SESSION_BASE, sessionId);
        File sessionFile = new File(sessionFilename);
        if (!sessionFile.exists()) {
            //如果sessionFile 文件不存在（可能传的是错误sessionId，或者因为过期被清除了）
            //返回一个空 HashMap
            return new HashMap<>();
        }

        try (InputStream is = new FileInputStream(sessionFile)) {
            // ObjectInputStream 进行对象读取
            try (ObjectInputStream objectInputStream = new ObjectInputStream(is)) {
                return (Map<String, Object>) objectInputStream.readObject();
            }
        }
    }
    //保存 session 数据。在发送完响应，调完service之后，如果用户有 session 要种进去，写到map里头去，我们就是把map返回到 sessionFile文件中
    public void saveSessionData() throws IOException {
        if (sessionData.isEmpty()) {
            return;
        }
        ;
        String sessionFilename = String.format("%s\\%s.session", SESSION_BASE, sessionId);
        //这个时候就不需要再判断文件是否为空了，因为我们是要种session进去
        try (OutputStream os = new FileOutputStream(sessionFilename)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(os)) {
                objectOutputStream.writeObject(sessionData);
                objectOutputStream.flush();
            }
        }

    }

    @Override
    public Object getAttribute(String name) {
        return sessionData.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        sessionData.remove(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        sessionData.put(name, value);
    }
}
