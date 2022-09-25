package edu.miu.cs544.gateway.property;

public enum AccountServiceMethods {

    GET_BALANCE("account/balance");

    public final String methodName;

    AccountServiceMethods(String methodName) {
        this.methodName = methodName;
    }
}
