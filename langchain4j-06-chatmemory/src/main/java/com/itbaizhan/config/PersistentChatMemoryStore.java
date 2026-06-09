package com.itbaizhan.config;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.List;
import java.util.Map;

/**
 * 自定义的持久化聊天存储器
 */
public class PersistentChatMemoryStore implements ChatMemoryStore {

    private final DB db = DBMaker.fileDB("./chat-memory.db").transactionEnable().make();
    private final Map<Integer,String> map = db.hashMap("messages", Serializer.INTEGER,Serializer.STRING).createOrOpen();

    /**
     * 获取信息
     * @param memoryId The ID of the chat memory.
     * @return
     */
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        String json = map.get((int) memoryId);
        return ChatMessageDeserializer.messagesFromJson(json);
    }

    /**
     * 更新信息
     * @param memoryId The ID of the chat memory.
     * @param messages 消息
     */
    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        String json = ChatMessageSerializer.messagesToJson(messages);
        map.put((int) memoryId, json);
        db.commit();
    }

    /**
     * 删除信息
     * @param memoryId The ID of the chat memory.
     */
    @Override
    public void deleteMessages(Object memoryId) {
        map.remove((int) memoryId);
        db.commit();
    }
}
