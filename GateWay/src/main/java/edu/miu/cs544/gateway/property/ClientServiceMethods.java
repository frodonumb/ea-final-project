package edu.miu.cs544.gateway.property;

public enum ClientServiceMethods {

    MAKE_TRANSACTION("transactions"),
    CLIENT("clients")
    ;

    public final String methodName;

    ClientServiceMethods(String methodName) {
        this.methodName = methodName;
    }
}
