package com.example.statemachine.engine;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.example.statemachine.data.model.Command;
import com.example.statemachine.data.model.State;
import com.example.statemachine.ui.activities.AmountActivity;
import com.example.statemachine.ui.activities.AuthActivity;
import com.example.statemachine.ui.activities.CardActivity;
import com.example.statemachine.ui.activities.CompleteActivity;
import com.example.statemachine.ui.activities.DeclineActivity;
import com.example.statemachine.ui.activities.FailActivity;
import com.example.statemachine.ui.main.MainActivity;

public class TransactionEngine {

    private Command command;
    private State currentState;
    private final Activity activity;
    private final ActivityResultLauncher<Intent> launcher;

    // Init engine
    public TransactionEngine(Activity activity) {
        this.activity = activity;
        launcher = ((androidx.activity.ComponentActivity) activity).registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        String activityResult = result.getData().getStringExtra("result");
                        handleResult(activityResult);
                    }
                }
        );
    }

    // Starting point of a Command, launches the initial state
    public void start(Command command) {
        this.command = command;
        goToState(command.getInitialState());
    }

    // Gets the state by name and calls the launching function
    private void goToState(String stateName) {
        if (stateName == null) {
            Log.d("Engine", "No next state. aborting");
            abort();
        }

        State state = command.findState(stateName);
        if (state == null) {
            Log.e("Engine", "Unknown state: " + stateName);
            return;
        }

        Log.d("Engine", "Entering state: " + state.getName());
        launchActivityForState(state);
    }

    // Launching the activities from the states
    private void launchActivityForState(State state) {
        Class<? extends Activity> target = getFunctionActivity(state.getFunction());
        if (target == null) {
            Log.e("Engine", "No Activity mapped for function: " + state.getFunction());
            goToState(state.getErrorState());
            return;
        }
        currentState = state;
        Intent intent = new Intent(activity, target);
        launcher.launch(intent);
    }

    // Mapping of function strings with their respective Activity classes
    Class<? extends Activity> getFunctionActivity(String function) {
        Class<? extends Activity> target = null;

        switch (function) {
            case "enterAmount":
                target = AmountActivity.class;
                break;
            case "readCard":
                target = CardActivity.class;
                break;
            case "authorizePayment":
                target = AuthActivity.class;
                break;
            case "complete":
                target = CompleteActivity.class;
                break;
            case "decline":
                target = DeclineActivity.class;
                break;
            case "fail":
                target = FailActivity.class;
                break;
        }

        return target;
    }

    // Abort function used to go back to main menu, used when no next step is defined or when transaction is canceled
    private void abort() {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        launcher.launch(intent);
    }

    // Handles the result from called activities
    private void handleResult(String result) {
        Log.d("Engine", "Result from " + currentState.getName() + ": result=" + result);
        if ("success".equals(result))
            goToState(currentState.getNextState());
        else if ("fail".equals(result))
            goToState(currentState.getErrorState());
        else if ("cancel".equals(result))
            abort();
    }

}
