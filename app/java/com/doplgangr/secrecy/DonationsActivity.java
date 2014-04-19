/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.doplgangr.secrecy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.webkit.WebView;

import org.sufficientlysecure.donations.DonationsFragment;

public class DonationsActivity extends FragmentActivity {

    /**
     * Google
     */
    private static final String GOOGLE_PUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbAMG82/KN7DaFV3lIVtQDepcEnI+N7MZJemXnus3kkSQ0vr+veE54l7w0Meq32alRaGBabgZuPZdjA7tsQJRa47IVF/ibHLzlBqAsefVNf+ulGEqvoeeU8oHJviIXZEdRRw3KfXrxepzKU75WLFXyMl1+ssQPWbhQaY6mLQebJz5cBivY67yd09zPjxz3SN844AFssj0+dh5D4YRIV1Qr5A0VgpNxWdbiGnDFk8WjLkfjbn3sdcJ2sCrB7pOUcjWbNRXp0jtFj0UQlmNisnbRPw9bPtrbXiWW7o745NmQfjMgg/35bJqRBlKOamU57LmJfbbpQwslpQVAQiv6dZWQIDAQAB";
    private static final String[] GOOGLE_CATALOG = new String[]{
            "secrecy.donation.2", "secrecy.donation.5", "secrecy.donation.10", "secrecy.donation.15",
            "secrecy.donation.20"};

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.donations_activity);
        String html = getString(R.string.donate_description);
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadData(html,"text/html", "utf-8");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DonationsFragment donationsFragment;

            donationsFragment = DonationsFragment.newInstance(BuildConfig.DEBUG, true, GOOGLE_PUBKEY, GOOGLE_CATALOG,
                    getResources().getStringArray(R.array.donation_google_catalog_values), false, null, null,
                    null, false, null, null);

        ft.replace(R.id.donations_activity_container, donationsFragment, "donationsFragment");
        ft.commit();
    }

    /**
     * Needed for Google Play In-app Billing. It uses startIntentSenderForResult(). The result is not propagated to
     * the Fragment like in startActivityForResult(). Thus we need to propagate manually to our Fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("donationsFragment");
        if (fragment != null) {
            ((DonationsFragment) fragment).onActivityResult(requestCode, resultCode, data);
        }
    }

}