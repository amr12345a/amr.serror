public void Start(View view) throws NoSuchFieldException,
ClassNotFoundException, IllegalAccessException, NoSuchMethodException
{

        textView.setText("active");
        textView.setTextColor(Color.GREEN);
        TelephonyManager telephonyManager = (TelephonyManager)
getSystemService(Context.TELEPHONY_SERVICE);
        final ConnectivityManager conman = (ConnectivityManager)
getSystemService(Context.CONNECTIVITY_SERVICE);
        Class conmanClass;

        assert conman != null;
        conmanClass = Class.forName(conman.getClass().getName());

        Field iConnectivityManagerField = null;

            if (conmanClass != null) {
                iConnectivityManagerField =
conmanClass.getDeclaredField("mService");
            }

        assert iConnectivityManagerField != null;
        iConnectivityManagerField.setAccessible(true);
        final Object iConnectivityManager;
            iConnectivityManager = iConnectivityManagerField.get(conman);

        final Class iConnectivityManagerClass;
            iConnectivityManagerClass =
Class.forName(iConnectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod;
            setMobileDataEnabledMethod =
iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled",
Boolean.TYPE);
        Handler handler;

        assert telephonyManager != null;
        switch (telephonyManager.getDataState()) {
            case TelephonyManager.DATA_CONNECTED: //TODO
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                            try {

setMobileDataEnabledMethod.invoke(iConnectivityManager, false);
                            } catch (IllegalAccessException e) {

                                textView.setText(e.getMessage());

                            } catch (InvocationTargetException e) {

                                textView.setText(e.getMessage());

                            }
                        // Actions to do after 10 seconds
                    }
                }, 7000);
            case TelephonyManager.DATA_DISCONNECTED:
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        try {

setMobileDataEnabledMethod.invoke(iConnectivityManager, true);
                        } catch (IllegalAccessException e) {

                        } catch (InvocationTargetException e) {

                        }
                        // Actions to do after 10 seconds
                    }
                }, 7000);
        }


        Button stop = (Button) findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                active = false;
                textView.setTextColor(Color.RED);
                textView.setText("unactive");
            }
        });

    }
