package com.example.androidbarberstaffapp.Model.EventBus;

public class DismissFromBottomSheetEvent {
    private boolean isButtonClick;

    public DismissFromBottomSheetEvent(boolean isButtonClick) {
        this.isButtonClick = isButtonClick;
    }

    public boolean isButtonClick() {
        return isButtonClick;
    }

    public void setButtonClick(boolean buttonClick) {
        isButtonClick = buttonClick;
    }
}
