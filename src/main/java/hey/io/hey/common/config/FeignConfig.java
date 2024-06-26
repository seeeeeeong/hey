package hey.io.hey.common.config;

import feign.codec.Decoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Logger;
import feign.Logger.Level;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;

@Configuration
@EnableFeignClients(basePackages = {"hey.io.hey"})
public class FeignConfig {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    public FeignConfig(final ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Level.FULL;
    }

    @Bean
    public FormEncoder feignFormEncoder() {
        return new FormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    public Decoder feignDecoder() {
        MappingJackson2CborHttpMessageConverter cborConverter = new MappingJackson2CborHttpMessageConverter();
        HttpMessageConverters converters = new HttpMessageConverters(cborConverter);
        ObjectFactory<HttpMessageConverters> objectFactory = () -> converters;
        return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
    }
}
