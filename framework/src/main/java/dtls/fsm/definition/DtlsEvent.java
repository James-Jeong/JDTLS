package dtls.fsm.definition;

public enum DtlsEvent {

    BUFFER_NEXT_FLIGHT,
    SEND_FLIGHT_1,
    SEND_FLIGHT_2,                // Must set retransmit timer
    RECEIVE_NEXT_FLIGHT,
    RECEIVE_LAST_FLIGHT,
    READ_RETRANSMIT,
    SEND_HELLO_REQUEST,
    RECEIVE_HELLO_REQUEST,
    SEND_CLIENT_HELLO,
    TIMER_EXPIRES,
    RETRANSMIT_LAST_FLIGHT

}
