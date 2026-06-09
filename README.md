# medical-rag

基于 **Spring Boot 3.3.4** + **LangChain4j 0.35.0** 的医疗 RAG 智能问答系统。

## 架构概览

```
用户上传文档 → Tika 解析 → 分块 → ZhipuAI Embedding → Qdrant 向量库
                                                           ↓
用户提问 → QueryTransformer 改写 → 向量检索 + Tavily 网络搜索
                                       ↓
                              Jina ReRanking 重排序
                                       ↓
                              Qwen-max 流式生成回答
```

## 核心模块

### langchain4j-14-tika — 文档解析 + 基础 RAG

- **文档解析**：Apache Tika 自动识别并解析 PDF、Word、CSV 等多种格式
- **Query 压缩**：`CompressingQueryTransformer` 结合历史对话压缩用户问题，提升检索精度
- **Embedding**：智谱 AI `ZhipuAiEmbeddingModel`
- **向量存储**：Qdrant 向量数据库
- **对话记忆**：`MessageWindowChatMemory` 保留最近 10 轮对话，按 userId 隔离

### langchain4j-15-medical — 医疗 RAG 主应用

- **流式对话**：Qwen-max + Reactor Flux，SSE 流式输出
- **多路检索**：
  - 向量检索：Qdrant 中查找相似医疗知识片段（top 3，minScore 0.75）
  - 网络检索：Tavily 搜索引擎补充最新医学信息
- **ReRanking**：Jina Reranker v2 对多路召回结果重排序
- **知识库管理**：
  - 文件上传 → MySQL 记录元数据 → 定时任务异步向量化 → 写入 Qdrant
  - 文件状态流转：`PENDING → PROCESSING → COMPLETED`
- **前端界面**：Thymeleaf 模板 + 知识库管理页面

##  技术栈

| 层 | 技术 |
|------|------|
| LLM | 阿里百炼 Qwen-max (OpenAI 兼容接口) |
| Embedding | 智谱 AI Embedding |
| 向量数据库 | Qdrant |
| 关系数据库 | MySQL + MyBatis-Plus |
| 文档解析 | Apache Tika |
| 重排序 | Jina Reranker v2 |
| 网络搜索 | Tavily Search API |
| 前端 | Thymeleaf + HTML/CSS |
| 异步 | Spring @Async + CompletableFuture |

## 环境变量

```bash
DASHSCOPE_API_KEY    # 阿里百炼 API Key
ZHIPU_API_KEY        # 智谱 AI API Key
JINA_API_KEY         # Jina Reranker API Key
TAVILY_API_KEY       # Tavily 搜索 API Key
DB_PASSWORD          # MySQL 密码
```

## 运行

```bash
# 1. 确保 Qdrant 和 MySQL 已启动

# 2. 设置环境变量
export DASHSCOPE_API_KEY=sk-xxx
export ZHIPU_API_KEY=xxx
export JINA_API_KEY=jina_xxx
export TAVILY_API_KEY=tvly-xxx
export DB_PASSWORD=your_password

# 3. 编译
mvn clean package -DskipTests

# 4. 启动医疗 RAG 应用
cd langchain4j-15-medical
mvn spring-boot:run

# 5. 访问 http://localhost:8080
```

## 模块列表

| 模块 | 功能 |
|------|------|
| langchain4j-01-demo | 入门示例 |
| langchain4j-02-chat | 基础对话 |
| langchain4j-03-streaming | 流式对话 |
| langchain4j-04-image | 图片理解 |
| langchain4j-05-generateimage | 图片生成 |
| langchain4j-06-chatmemory | 对话记忆 |
| langchain4j-07-prompt | Prompt 模板 |
| langchain4j-08-structure | 结构化输出 |
| langchain4j-09-funcation | 函数调用 |
| langchain4j-10-search | 搜索引擎集成 |
| langchain4j-11-qdrant | Qdrant 向量库 |
| langchain4j-12-sensitiveword | 敏感词过滤 |
| langchain4j-13-rag | RAG 基础 |
| langchain4j-14-tika | Tika 文档解析 + RAG |
| langchain4j-15-medical | **医疗 RAG 主应用** |
