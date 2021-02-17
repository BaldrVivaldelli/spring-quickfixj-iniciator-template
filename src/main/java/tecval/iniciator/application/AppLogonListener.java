package tecval.iniciator.application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import quickfix.ConfigError;
import quickfix.Session;
import quickfix.SessionID;
import quickfix.ThreadedSocketInitiator;

@Slf4j
@Component
public class AppLogonListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ThreadedSocketInitiator threadedSocketInitiator;

	private boolean initiatorStarted = false;

	/**
	 * Esto es la magia que hace que se ejecute cuando se levanta el context de
	 * spring boot
	 * 
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		startFixInitiator();
	}
	private void startFixInitiator() {
			if (!initiatorStarted) {
				try {
					threadedSocketInitiator.start();
					log.info("--------- ThreadedSocketInitiator started ---------");
					initiatorStarted = true;
				} catch (ConfigError configError) {
					configError.printStackTrace();
					log.error("--------- ThreadedSocketInitiator ran into an error ---------");
				}
			} else {
				logon();
			}
	}

	private void logon() {
		if (threadedSocketInitiator.getSessions() != null && threadedSocketInitiator.getSessions().size() > 0) {
			for (SessionID sessionID : threadedSocketInitiator.getSessions()) {
				Session.lookupSession(sessionID).logon();
			}
			log.info("--------- ThreadedSocketInitiator logged on to sessions. Size: "
					+ threadedSocketInitiator.getSessions().size() + " ---------");
		}
	}

	@Scheduled(fixedRate = 5000)
	public void clientStatus() {
		log.info("Client Status | Logged on: {}. Current Time: {}", threadedSocketInitiator.isLoggedOn(),LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
	}
}
