package dtls.fsm;

import JFSM.JFSM.src.main.java.com.fsm.StateManager;
import JFSM.JFSM.src.main.java.com.fsm.module.StateHandler;
import dtls.fsm.definition.DtlsEvent;
import dtls.fsm.definition.DtlsState;
import dtls.fsm.definition.callback.DtlsRetransmitTimerMaker;
import dtls.unit.DtlsUnit;

public class DtlsFsmManager {

    /////////////////////////////////////////////////////
    private final StateManager stateManager = new StateManager(10);
    /////////////////////////////////////////////////////

    /////////////////////////////////////////////////////
    public DtlsFsmManager() {
        // Nothing
    }
    /////////////////////////////////////////////////////

    /////////////////////////////////////////////////////
    public StateManager getStateManager() {
        return stateManager;
    }
    /////////////////////////////////////////////////////

    /////////////////////////////////////////////////////
    public void init (DtlsUnit dtlsUnit) {
        if (dtlsUnit == null) { return; }

        ////////////////////////////
        // 1) INITIALIZE DTLS STATE HANDLER
        stateManager.addStateHandler(DtlsState.NAME);
        StateHandler dtlsStateHandler = stateManager.getStateHandler(DtlsState.NAME);
        ////////////////////////////

        ////////////////////////////
        // 2) ADD EVENT

        /////////////
        // 2-1) BUFFER_NEXT_FLIGHT
        dtlsStateHandler.addState(
                DtlsEvent.BUFFER_NEXT_FLIGHT.name(),
                DtlsState.DTLS_STATE_PREPARING, DtlsState.DTLS_STATE_SENDING,
                null, null,
                null, 0, 0
        );
        /////////////

        /////////////
        // 2-2) SEND_FLIGHT_1
        /**
         * Once the messages have been sent, the
         *    implementation then enters the FINISHED state if this is the last
         *    flight in the handshake.
         */
        dtlsStateHandler.addState(
                DtlsEvent.SEND_FLIGHT_1.name(),
                DtlsState.DTLS_STATE_SENDING, DtlsState.DTLS_STATE_FINISHED,
                null, null,
                null, 0, 0
        );
        /////////////

        /////////////
        // 2-3) SEND_FLIGHT_2
        /**
         * Or, if the implementation expects to
         *    receive more messages, it sets a retransmit timer and then enters the
         *    WAITING state.
         */
        dtlsStateHandler.addState(
                DtlsEvent.SEND_FLIGHT_2.name(), // > Set retransmit timer
                DtlsState.DTLS_STATE_SENDING, DtlsState.DTLS_STATE_WAITING,
                new DtlsRetransmitTimerMaker(
                        stateManager,
                        DtlsRetransmitTimerMaker.class.getSimpleName(),
                        dtlsUnit,
                        /**
                         * Implementations SHOULD use an initial timer value
                         *    of 1 second (the minimum defined in RFC 2988 [RFC2988]) and double
                         *    the value at each retransmission, up to no less than the RFC 2988
                         *    maximum of 60 seconds.
                         *
                         *    Note that we recommend a 1-second timer
                         *    rather than the 3-second RFC 2988 default in order to improve latency
                         *    for time-sensitive applications.  Because DTLS only uses
                         *    retransmission for handshake and not dataflow, the effect on
                         *    congestion should be minimal.
                         */
                        60000
                ),
                null,
                null, 0, 0
        );
        /////////////

        /////////////
        // 2-4) TIMER_EXPIRES
        /**
         * The retransmit timer expires: the implementation transitions to
         *       the SENDING state, where it retransmits the flight, resets the
         *       retransmit timer, and returns to the WAITING state.
         */
        dtlsStateHandler.addState(
                DtlsEvent.TIMER_EXPIRES.name(),
                DtlsState.DTLS_STATE_WAITING, DtlsState.DTLS_STATE_SENDING,
                null, null,
                null, 0, 0
        );
        /////////////

        /////////////
        // 2-5) READ_RETRANSMIT
        /**
         * The implementation reads a retransmitted flight from the peer:
         *       the implementation transitions to the SENDING state, where it
         *       retransmits the flight, resets the retransmit timer, and returns
         *       to the WAITING state.  The rationale here is that the receipt of a
         *       duplicate message is the likely result of timer expiry on the peer
         *       and therefore suggests that part of one's previous flight was
         *       lost.
         */
        dtlsStateHandler.addState(
                DtlsEvent.READ_RETRANSMIT.name(),
                DtlsState.DTLS_STATE_WAITING, DtlsState.DTLS_STATE_SENDING,
                null, null,
                null, 0, 0
        );
        /////////////

        /////////////
        // 2-6) RECEIVE_NEXT_FLIGHT
        /**
         * The implementation receives the next flight of messages:  if
         *       this is the final flight of messages, the implementation
         *       transitions to FINISHED.  If the implementation needs to send a
         *       new flight, it transitions to the PREPARING state.  Partial reads
         *       (whether partial messages or only some of the messages in the
         *       flight) do not cause state transitions or timer resets.
         */
        dtlsStateHandler.addState(
                DtlsEvent.RECEIVE_NEXT_FLIGHT.name(),
                DtlsState.DTLS_STATE_WAITING, DtlsState.DTLS_STATE_PREPARING,
                null, null,
                null, 0, 0
        );
        /////////////

        /////////////
        // 2-7) RECEIVE_LAST_FLIGHT
        dtlsStateHandler.addState(
                DtlsEvent.RECEIVE_LAST_FLIGHT.name(),
                DtlsState.DTLS_STATE_WAITING, DtlsState.DTLS_STATE_FINISHED,
                null, null,
                null, 0, 0
        );
        /////////////

        /////////////
        // 2-8) RECEIVE_LAST_FLIGHT
        dtlsStateHandler.addState(
                DtlsEvent.RECEIVE_LAST_FLIGHT.name(),
                DtlsState.DTLS_STATE_WAITING, DtlsState.DTLS_STATE_FINISHED,
                null, null,
                null, 0, 0
        );
        /////////////

        /////////////
        // 2-9) SEND_HELLO_REQUEST
        /**
         * When the server desires a rehandshake, it transitions from the
         *    FINISHED state to the PREPARING state to transmit the HelloRequest.
         */
        dtlsStateHandler.addState(
                DtlsEvent.SEND_HELLO_REQUEST.name(),
                DtlsState.DTLS_STATE_FINISHED, DtlsState.DTLS_STATE_PREPARING,
                null, null,
                null, 0, 0
        );
        /////////////

        /////////////
        // 2-10) RECEIVE_HELLO_REQUEST
        /**
         * When the client receives a HelloRequest it transitions from FINISHED
         *    to PREPARING to transmit the ClientHello.
         */
        dtlsStateHandler.addState(
                DtlsEvent.RECEIVE_HELLO_REQUEST.name(),
                DtlsState.DTLS_STATE_FINISHED, DtlsState.DTLS_STATE_PREPARING,
                null, null,
                null, 0, 0
        );
        /////////////

        /////////////
        // 2-11) SEND_CLIENT_HELLO
        dtlsStateHandler.addState(
                DtlsEvent.SEND_CLIENT_HELLO.name(),
                DtlsState.DTLS_STATE_FINISHED, DtlsState.DTLS_STATE_PREPARING,
                null, null,
                null, 0, 0
        );
        /////////////

        ////////////////////////////
    }
    /////////////////////////////////////////////////////

}
