/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.adp;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.example.android.adp.R;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link android.support.v4.app.Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends Activity
        //extends FragmentActivity
        {

    public static final String TAG = "MainActivity";
    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    private BluetoothAdapter mBluetoothAdapter = null;
    private ConnectionService mConnectionService = null;
    private String mConnectedDeviceName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Bluetooth is not available", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else if (mConnectionService == null) {
            setupConnection();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mConnectionService != null) {
            mConnectionService.stop();

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mConnectionService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mConnectionService.getState() == ConnectionService.STATE_NONE) {
                // Start the Bluetooth chat services
                mConnectionService.start();
            }
        }
    }

    private void setupConnection() {
        mConnectionService = new ConnectionService(getApplicationContext(),mHandler);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //FragmentActivity activity = getActivity();
            switch (msg.what) {
                case Constants.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case ConnectionService.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            //mConversationArrayAdapter.clear();
                            break;
                        case ConnectionService.STATE_CONNECTING:
                            setStatus(getString(R.string.title_connecting));
                            break;
                        case ConnectionService.STATE_LISTEN:
                        case ConnectionService.STATE_NONE:
                            setStatus(getString(R.string.title_not_connected));
                            break;
                    }
                    break;
/*                case Constants.MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;*/
                case Constants.MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer
                    String readMessage = new String(readBuf, 0, msg.arg1);
                    //Qui probabilmente andrà il parsing Json
                    try {
                        JSONObject jObj= new JSONObject(readMessage);
                        String type = jObj.getString(Constants.TYPE);
                        String content = jObj.getString(Constants.CONTENT);
                        int value = jObj.getInt(Constants.VALUE);

                    if(type.equals(Constants.ALARM)){
                        String string = Constants.ALARM_INFO + "\n" + content +" "+ value;
                        TextView txt = (TextView) findViewById(R.id.status);
                        txt.setText(string);
                    }else
                        if(type.equals(Constants.INFO)){
                            String string = Constants.SAFETY_INFO;
                            TextView txt = (TextView) findViewById(R.id.status);
                            txt.setText(string);
                        }
                    if(type.equals(Constants.TEMPERATURE)){
                        String string = type +" "+ value;
                        TextView txt = (TextView) findViewById(R.id.temperature);
                        txt.setText(string);
                    }
                    if(type.equals(Constants.ARDUINO_MEMORY)){
                        String string = "RAM "+ value;
                        TextView txt = (TextView) findViewById(R.id.ramUsage);
                        txt.setText(string);
                    }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //mConversationArrayAdapter.add(mConnectedDeviceName + ":  " + readMessage);
                    break;
                case Constants.MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(Constants.DEVICE_NAME);
                    if (null != getApplicationContext()) {
                        Toast.makeText(getApplicationContext(), "Connected to "
                                + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case Constants.MESSAGE_TOAST:
                    if (null != getApplicationContext()) {
                        Toast.makeText(getApplicationContext(), msg.getData().getString(Constants.TOAST),
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    private void setStatus(CharSequence subTitle) {
        ActionBar actionBar = getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(subTitle);
    }

    private void setStatus(int resId) {
        final ActionBar actionBar = getActionBar();
        if (null == actionBar) {
            return;
        }
        actionBar.setSubtitle(resId);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bluetooth_chat, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.secure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getApplicationContext(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            }
            case R.id.insecure_connect_scan: {
                // Launch the DeviceListActivity to see devices and do scan
                Intent serverIntent = new Intent(getApplicationContext(), DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
            }
            case R.id.discoverable: {
                // Ensure this device is discoverable by others
                ensureDiscoverable();
                return true;
            }
        }
        return false;
    }
    /**
     * Makes this device discoverable.
     */
    private void ensureDiscoverable() {
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupConnection();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Toast.makeText(getApplicationContext(), R.string.bt_not_enabled_leaving,
                            Toast.LENGTH_SHORT).show();
                }
        }
    }

    /**
     * Establish connection with other divice
     *
     * @param data   An {@link Intent} with {@link DeviceListActivity#EXTRA_DEVICE_ADDRESS} extra.
     * @param secure Socket Security type - Secure (true) , Insecure (false)
     */
    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mConnectionService.connect(device, secure);
    }

}
