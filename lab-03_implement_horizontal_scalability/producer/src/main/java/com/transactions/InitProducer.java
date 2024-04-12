package com.transactions;

import com.transactions.producer.dto.TransactionDto;
import com.transactions.producer.enums.Descriptions;
import com.transactions.producer.enums.TransactionTypeEnum;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.sql.Timestamp;
import java.util.random.RandomGenerator;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class InitProducer {

    Logger logger = LoggerFactory.getLogger(InitProducer.class);

    @Value("${consumer.uri}")
    private String uri;

    @Value("${consumer.port}")
    private String port;
    private static RandomGenerator gen = RandomGenerator.of("L128X256MixRandom");

    @PostConstruct
    public void init() throws InterruptedException {
        RestClient restClient = RestClient.create();

        while (true) {

            TransactionDto transaction = TransactionDto.builder()
                    .transactionId(generateTransactionId())
                    .amount(generateAmount())
                    .transactionTimestamp(generateCurrentTimestamp())
                    .description(generateDescription())
                    .transactionType(generateTransactionType())
                    .build();

            String response = restClient
                    .post()
                    .uri(uri + ":" + port + "/transactions")
                    .contentType(APPLICATION_JSON)
                    .body(transaction)
                    .retrieve()
                            .body(String.class);

            logger.info("Data Sent: {}", transaction.toString());
            logger.info("Response: {}", response);

            //Thread.sleep(1000);
        }

    }

    private static Integer generateTransactionType(){
        int max = 4;
        int min = 1;
        return TransactionTypeEnum.values()[(int) ((Math.random() * (max - min)) + min)].id;
    }
    private static String generateDescription() {
        int max = 2;
        int min = 0;

        return Descriptions.values()[(int) ((Math.random() * (max - min)) + min)].value;

    }

    private static Timestamp generateCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    private static String generateTransactionId() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

    private static Float generateAmount() {
        return gen.nextFloat() * gen.nextInt();
    }
}
