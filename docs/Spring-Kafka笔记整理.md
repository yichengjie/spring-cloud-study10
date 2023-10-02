1. 引入依赖
    ```xml
    <dependency>
        <groupId>org.springframework.kafka</groupId>
        <artifactId>spring-kafka</artifactId>
    </dependency>
    ```
2. 配置application.properties
    ```properties
    spring.kafka.bootstrap-servers=192.168.99.51:9092
    ```
3. 编写kafka的配置类
   ```java
   @Configuration
   public class KafkaConfig {
       @Value("${spring.kafka.bootstrap-servers}")
       private String bootstrapServers;
       @Bean
       public ProducerFactory<String, String> producerFactory() {
           Map<String, Object> configs = new HashMap<>();
           configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
           configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
           configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
           return new DefaultKafkaProducerFactory<>(configs);
       }
       @Bean
       public KafkaTemplate<String, String> kafkaTemplate() {
           return new KafkaTemplate<>(producerFactory());
       }
       @Bean
       public ConsumerFactory<String, String> consumerFactory() {
           Map<String, Object> props = new HashMap<>();
           props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
           props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
           props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
           props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
           return new DefaultKafkaConsumerFactory<>(props);
       }
       @Bean
       public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
           ConcurrentKafkaListenerContainerFactory<String, String> factory =
                   new ConcurrentKafkaListenerContainerFactory<>();
           // 并发数就是一个消费者实例起几个线程
           factory.setConcurrency(3);
           factory.setConsumerFactory(consumerFactory());
           return factory;
       }
   }
   ```
4. Kafka消息监听
   ```java
   @Component
   public class KafkaConsumer {
       @Autowired
       private ObjectMapper mapper;
       @KafkaListener(
           topics = {"hello-kafka-topic"},
           groupId = "hello-kafka-group",
           containerFactory = "kafkaListenerContainerFactory"
       )
       public void listener01(ConsumerRecord<String, String> record) throws Exception {
           String key = record.key();
           String value = record.value();
           HelloMessage kafkaMessage = mapper.readValue(value, HelloMessage.class);
           log.info("in listener consume kafka message: [{}], [{}]", key, mapper.writeValueAsString(kafkaMessage));
       }
   }
   ```
5. Kafka消息发送
   ```java
   @Component
   public class KafkaProducer {
       @Autowired
       private KafkaTemplate<String, String> kafkaTemplate;
       public void sendMessage(String key, String value, String topic) {
           if (StringUtils.isBlank(value) || StringUtils.isBlank(topic)) {
               throw new IllegalArgumentException("value or topic is null or empty");
           }
           ListenableFuture<SendResult<String, String>> future = StringUtils.isBlank(key) ?
                   kafkaTemplate.send(topic, value) : kafkaTemplate.send(topic, key, value);
           // 异步回调的方式获取通知
           future.addCallback(success -> {
                   assert null != success && null != success.getRecordMetadata();
                   // 发送到 kafka 的 topic
                   String _topic = success.getRecordMetadata().topic();
                   // 消息发送到的分区
                   int partition = success.getRecordMetadata().partition();
                   // 消息在分区内的 offset
                   long offset = success.getRecordMetadata().offset();
                   log.info("send kafka message success: [{}], [{}], [{}]", _topic, partition, offset);
               }, failure -> {
                   log.error("send kafka message failure: [{}], [{}], [{}]", key, value, topic);
               }
           );
       }
   }
   ```