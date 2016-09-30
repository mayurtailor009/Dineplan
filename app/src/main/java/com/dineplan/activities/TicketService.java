package com.dineplan.activities;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.dineplan.dbHandler.DbHandler;
import com.dineplan.model.User;
import com.dineplan.rest.Constant;
import com.dineplan.rest.RequestCall;
import com.dineplan.rest.listener.AsyncTaskCompleteListener;
import com.dineplan.utility.Constants;
import com.dineplan.utility.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TicketService extends IntentService implements AsyncTaskCompleteListener<String> {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.dineplan.activities.action.FOO";
    private static final String ACTION_BAZ = "com.dineplan.activities.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.dineplan.activities.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.dineplan.activities.extra.PARAM2";

    public TicketService() {
        super("TicketService");
    }


    private String json;
    private int ticketNumber;
    private final int REQ_GENERATE_TICKET=1;
    private DbHandler dbHandler;

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, TicketService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, int param2) {
        Intent intent = new Intent(context, TicketService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            JSONObject json = null;
            try {
                dbHandler=new DbHandler(this);
                json = new JSONObject(intent.getStringExtra(EXTRA_PARAM1));
                ticketNumber = intent.getIntExtra(EXTRA_PARAM2,0);
                SharedPreferences preferences = preferences = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
                User user = new Gson().fromJson(preferences.getString("user", "{}"), User.class);
                new RequestCall(preferences.getString("url", Constant.BASE_URL) + "api/services/app/ticket/CreateOrUpdateTicket",
                        this, json, PaymentCashActivity.class.getName(), this, REQ_GENERATE_TICKET, true, Utils.getHeader(user));
                } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onTaskComplete(String result, int fromCalling) {
        switch (fromCalling){
            case REQ_GENERATE_TICKET:
                if(result!=null){
                    try{
                        JSONObject jsonObject=new JSONObject(result);
                        if(jsonObject.getBoolean("success")){
                            dbHandler.updateTicketId(jsonObject.getJSONObject("result").getInt("ticket"),ticketNumber);
                        }else{
                      /*      Utils.showOkDialog(this,getResources().getString(R.string.app_name),
                                    jsonObject.getJSONObject("error").getString("message"));
*/                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
