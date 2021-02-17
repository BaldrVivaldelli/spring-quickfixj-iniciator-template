package tecval.iniciator.application;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import quickfix.Application;
import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;


@Slf4j
@Component
public class ClientFixApp implements Application {

    @Override
    public void onCreate(SessionID sessionID) {
        log.info("--------- onCreate ---------");
    }

    @Override
    public void onLogon(SessionID sessionID) {
        log.info("--------- onLogon ---------");
    }

    @Override
    public void onLogout(SessionID sessionID) {
        log.info("--------- onLogout ---------");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        log.info("--------- toAdmin ---------");
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.info("--------- fromAdmin ---------");
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        log.info("--------- toApp ---------");
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        log.info("--------- fromApp ---------");
    }
}
