package dtls;

import JFSM.JFSM.src.main.java.com.fsm.StateManager;
import dtls.fsm.definition.DtlsState;
import dtls.unit.DtlsUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Made by a singleton pattern
 */
public class DtlsHandshakeManager {

    /**
     * @ Reference : https://tools.ietf.org/id/draft-ietf-tls-dtls13-02.html
     *
     * 3.1. Packet Loss
     *      DTLS uses a simple retransmission timer to handle packet loss.
     *      Figure 1 demonstrates the basic concept, using the first phase of the DTLS handshake:
     *
     *          Client                                   Server
     *          ------                                   ------
     *          ClientHello           ------>
     *
     *                                  X<-- HelloRetryRequest
     *                                                   (lost)
     *
     *          [Timer Expires]
     *
     *          ClientHello           ------>
     *          (retransmit)
     *
     *          Figure 1: DTLS Retransmission Example.
     *
     *      Once the client has transmitted the ClientHello message, it expects to see a HelloRetryRequest from the server.
     *      However, if the server’s message is lost,
     *      the client knows that either the ClientHello or the HelloRetryRequest has been lost and retransmits.
     *      When the server receives the retransmission, it knows to retransmit.
     *
     *      The server also maintains a retransmission timer and retransmits when that timer expires.
     *      Note that timeout and retransmission do not apply to the HelloRetryRequest since this would require creating state on the server.
     *      The HelloRetryRequest is designed to be small enough that it will not itself be fragmented,
     *      thus avoiding concerns about interleaving multiple HelloRetryRequests.
     *
     * 3.1.1. Reordering
     *      In DTLS, each handshake message is assigned a specific sequence number within that handshake.
     *      When a peer receives a handshake message, it can quickly determine whether that message is the next message it expects.
     *      If it is, then it processes it. If not, it queues it for future handling once all previous messages have been received.
     *
     * 3.1.2. Message Size
     *      TLS and DTLS handshake messages can be quite large (in theory up to 2^24-1 bytes, in practice many kilobytes).
     *      By contrast, UDP datagrams are often limited to less than 1500 bytes if IP fragmentation is not desired.
     *      In order to compensate for this limitation, each DTLS handshake message may be fragmented over several DTLS records,
     *      each of which is intended to fit in a single IP datagram.
     *      Each DTLS handshake message contains both a fragment offset and a fragment length.
     *      Thus, a recipient in possession of all bytes of a handshake message can reassemble the original unfragmented message.
     *
     * 3.2. Replay Detection
     *      DTLS optionally supports record replay detection.
     *      The technique used is the same as in IPsec AH/ESP, by maintaining a bitmap window of received records.
     *      Records that are too old to fit in the window and records that have previously been received are silently discarded.
     *      The replay detection feature is optional, since packet duplication is not always malicious, but can also occur due to routing errors.
     *      Applications may conceivably detect duplicate packets and accordingly modify their data transmission strategy.
     *
     */

    private static final Logger logger = LoggerFactory.getLogger(DtlsHandshakeManager.class);
    
    ////////////////////////////////////////////////////////////
    // VARIABLES
    private static DtlsHandshakeManager dtlsHandshakeManager = null;

    private final HashMap<String, DtlsUnit> dtlsUnitHashMap = new HashMap<>();
    private final ReentrantLock dtlsUnitHashMapLock = new ReentrantLock();
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    // CONSTRUCTORs
    public DtlsHandshakeManager() {}
    ////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////
    // FUNCTIONS
    public static DtlsHandshakeManager getInstance() {
        if (dtlsHandshakeManager == null) {
            dtlsHandshakeManager = new DtlsHandshakeManager();
        }

        return dtlsHandshakeManager;
    }
    ////////////////////////////////////////////////////////////

    public void addDtlsUnit(String key) {
        if (key == null) {
            logger.warn("Fail to add a new dtls unit. Argument error is occurred. (key={})", key);
            return;
        }

        dtlsUnitHashMapLock.lock();
        try {
            ////////////////////////////
            DtlsUnit dtlsUnit = new DtlsUnit(key);
            ////////////////////////////

            ////////////////////////////
            StateManager stateManager = dtlsUnit.getDtlsFsmManager().getStateManager();
            stateManager.addStateUnit(
                    dtlsUnit.getDtlsStateUnitName(),
                    DtlsState.NAME,
                    DtlsState.DTLS_STATE_PREPARING,
                    dtlsUnit
            );
            ////////////////////////////

            dtlsUnitHashMap.putIfAbsent(key, dtlsUnit);
            logger.warn("Success to add a new dtls unit. (key={})", key);
        } catch (Exception e) {
            logger.warn("DtlsHandshakeManager.addDtlsUnit.Exception", e);
        } finally {
            dtlsUnitHashMapLock.unlock();
        }
    }

    public void deleteDtlsUnit(String key) {
        if (key == null) { return; }

        dtlsUnitHashMapLock.lock();
        try {
            dtlsUnitHashMap.remove(key);
        } catch (Exception e) {
            logger.warn("DtlsHandshakeManager.deleteDtlsUnit.Exception", e);
        } finally {
            dtlsUnitHashMapLock.unlock();
        }
    }

    public DtlsUnit getDtlsUnit(String key) {
        return dtlsUnitHashMap.get(key);
    }

    public int getDtlsUnitMapSize() {
        return dtlsUnitHashMap.size();
    }

}
