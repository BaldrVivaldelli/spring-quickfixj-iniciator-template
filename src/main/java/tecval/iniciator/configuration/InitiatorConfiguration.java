package tecval.iniciator.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FileLogFactory;
import quickfix.FileStoreFactory;
import quickfix.LogFactory;
import quickfix.MessageFactory;
import quickfix.MessageStoreFactory;
import quickfix.SessionSettings;
import quickfix.ThreadedSocketInitiator;
import tecval.iniciator.application.ClientFixApp;


@Slf4j
@Configuration
public class InitiatorConfiguration {
	

	@Value("${quickfixj.client.config}")
	private String fileName;
	
    @Autowired
    private ClientFixApp application;

    @Bean
    public ThreadedSocketInitiator threadedSocketInitiator(){
        ThreadedSocketInitiator threadedSocketInitiator = null;

        try {
            SessionSettings settings = new SessionSettings(new FileInputStream(fileName));
            MessageStoreFactory storeFactory = new FileStoreFactory(settings);
            LogFactory logFactory = new FileLogFactory(settings);
            MessageFactory messageFactory = new DefaultMessageFactory();
            threadedSocketInitiator = new ThreadedSocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
        } catch (ConfigError configError) {
            configError.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return threadedSocketInitiator;
    }

}
