package com.example.productservice.exception.enums;

public enum FriendlyMessageCodes implements IFriendlyMessageCode {
    OK(1000),
    ERROR(1001),
    SUCCESS(1002),
    PRODUCT_NOT_CREATED_EXCEPTİON(1500),
    PRODUCT_SUCCESSFULY_CREATED(1501),
    PRODUCT_NOT_FOUND_EXCEPTION(1502);
    private final int value;

    FriendlyMessageCodes(int value){
        this.value=value;
    }

    @Override
    public int getFriendlyMessageCode() {
        return value;
    }
}
