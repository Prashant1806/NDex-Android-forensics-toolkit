public class CallLogModel {
    private String callerName;
    private String phoneNumber;
    private String callType;
    private String callDuration;
    private String callTimestamp;

    public CallLogModel(String callerName, String phoneNumber, String callType, String callDuration, String callTimestamp) {
        this.callerName = callerName;
        this.phoneNumber = phoneNumber;
        this.callType = callType;
        this.callDuration = callDuration;
        this.callTimestamp = callTimestamp;
    }

    public String getCallerName() {
        return callerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCallType() {
        return callType;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public String getCallTimestamp() {
        return callTimestamp;
    }
}
