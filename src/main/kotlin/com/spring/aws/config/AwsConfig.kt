package com.spring.aws.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.sqs.AmazonSQSAsync
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder
import com.spring.aws.commons.EnvCommons
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
class AwsConfig {

    @Value(EnvCommons.AWS_ACCESS_KEY)
    private val awsAccessKey: String = ""

    @Value(EnvCommons.AWS_SECRET_KEY)
    private val awsSecretKey: String = ""

    @Value(EnvCommons.AWS_DEFAULT_REGION)
    private val defaultRegion: String = ""

    fun getAWSCredentials() : AWSStaticCredentialsProvider{
        return AWSStaticCredentialsProvider(BasicAWSCredentials(awsAccessKey, awsSecretKey))
    }

    @Bean
    fun queueMessagingTemplate(): QueueMessagingTemplate? {
        return QueueMessagingTemplate(amazonSQSAsync())
    }

    @Bean
    @Primary
    fun amazonSQSAsync(): AmazonSQSAsync? {
        return AmazonSQSAsyncClientBuilder
            .standard()
            .withRegion(Regions.valueOf(defaultRegion))
            .withCredentials(
                getAWSCredentials()
            )
            .build()
    }

}