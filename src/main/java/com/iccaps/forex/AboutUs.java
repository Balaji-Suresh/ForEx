package com.iccaps.forex;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.iccaps.forex.Adapter.MyPagerAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutUs extends AppCompatActivity
        implements TabLayout.OnTabSelectedListener {

    private Context mContext;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private MyPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initObjects();
        setupToolbar();
        initCallbacks();
        populateTabs();
        changeTabsFont();

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initObjects() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.pager_about_us);
        mFragmentList = new ArrayList<>();
        mContext = this;
        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mFragmentList);
    }

    private void setupToolbar() {
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("About Us");
            mToolbar.setNavigationIcon(R.drawable.ic_left_arrow_white);
            mToolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }



    private void initCallbacks() {
        mTabLayout.addOnTabSelectedListener(this);

    }

    private void populateTabs() {
        String data = "India Cements Capital Ltd (ICCL) is a part of the Chennai-based business house of The India Cements Ltd. The India Cements Ltd is a professionally managed company headed by Mr.N.Srinivasan,Vice Chairman and Managing Director. With cement plants in eight locations,the group has consolidated its position as the number one player in the southern cement market.\n" +
                "\n" +
                "\n" +
                "India Cements Capital Ltd established in 1985 with the Corporate Office at Chennai,ICCL today has a network of 26 branches which gives it a country-wide presence. The company is professionally managed and has many experienced finance professionals on its rolls.The company is one of the first to get RBI registration and complies with all regulatory norms in respect of capital adequacy and provisioning.\n" +
                "\n" +
                "\n" +
                "ICCL has the unique distinction of having a syneric mix of various fee based activities like Risk management services for importers and exporters,Full fledged money changing,Share broking under NSE,InterBank Forex broking and a Travel division with IATA accreditation.\n" +
                "\n" +
                "Our service and competitive market-driven rates stand comparison with the best in the industry.\n";
        String data1 = "Your privacy is important to ICCL Group. We respect your need to understand how information is being collected, used, disclosed, transferred and stored. Thus we have developed below Privacy policy to familiarise you with our practices. \n" + "\n" +
                "Collection of personal information\n" +
                "\n" +
                "You may be asked for personal information anytime you are in contact with ICCL Group directly or indirectly through a third party. The only way we will get any kind of personal information is if you choose to give it to us in following circumstance: \n" +
                "\n" +
                "•\tWhen you make a reservation or purchase from our website or through our customer service team - by email, letter, fax, on the phone or in physical store.\n" +
                "\n" +
                "•\tWhen you register with us, subscribe to our newsletter, enter competitions, send us queries or register for promotions. \n" +
                "\n" +
                "•\tWhen you take part in surveys or provide us with feedback\n" +
                "\n" +
                "•\tWhen you engage with us in any online or offline event, promotions, page hosted by us on a third party platform or location.\n" +
                " \n" +
                "Use of personal information\n" +
                "\n" +
                "•\tThe personal information collected is primarily used and passed on to third parties where it is necessary to process your Forex booking or enquiry. Your details may be passed to others to process and arrange for the products and services you request. These details may also pass through our online frauds detector service partner. When you submit your booking or request to us, you agree that we may use and transfer your personal information in this manner. \n" +
                "\n" +
                "The personal information we collect helps us to keep you posted about ICCL Group’s latest product announcements, offers, promotions and events. It also allows us to improve our services, content and advertising. If you wish to unsubscribe, you can choose to do so.\n" +
                "\n" +
                "We may also use the personal information to improve our product offering, develop, and deliver products, services, content and advertising..\n" +
                "\n" +
                "We may use personal information to send you important notices and communications regarding our products and services availed or changes to the terms and conditions and policies. .\n" +
                "\n" +
                "Personal information may also be used internally for research, analysis and auditing. \n" +
                "\n" +
                "We will use the information primarily for the following purposes: \n" +
                "\n" +
                "By using this website you authorize us to contact you via email or phone on your contact details provided above and offer you information and services for the product you have opted for. You  authorise us to call you for the mentioned \n" +
                "\n" +
                "purpose for a period of upto 30 to 60 days irrespective of whether you are registered with the NDNC registry. \n" +
                "\n" +
                "Allow you to access specific account information. Providing customisation:  We may use the information provided by you to customize your visit to the Web Site by displaying appropriate content at our judgment and discretion. To send you information about products and services offered by ICCL Group and its Affiliates, to contact you for payment reminder notices, and to keep you updated through our newsletters. In the event you do not wish to receive such information, you may unsubscribe through the facility in the email message you receive. \n" +
                "\n" +
                "Collection and use of non-personal information\n" +
                "\n" +
                "The data collected in non personal manner which does not identify a particular user may be used in any manner. Below are some typical examples of how we collect and use information. \n" +
                "\n" +
                "We may collect information such as browser, operating system, occupation, language, pin code, unique device identifier, location so that we can better understand customer behavior and improve our products, services, and advertising.\n" +
                "\n" +
                "We may collection information regarding customer activities on various portals of ICCL Group (India) ltd. This aggregated information is used in research, analysis, improve and monitor products and various promotional schemes. It may be shared in aggregated, non personal form with third party to enhance customer experience, products offering or services. \n" +
                "\n" +
                "Cookies and Other Technologies\n" +
                "\n" +
                "Cookies are a feature of web browser software that allows web servers to temporarily store information within your browser, which in turn allows us to recognize the computer used to access ICCL Group. Most Web browsers automatically accept cookies. Of course, by changing the options on your web browser or using certain software programs, you can control and delete how and whether cookies will be accepted by your browser. You can also edit your browser options to choose not to receive cookies in future.\n" +
                "\n" +
                "In order to help us to maintain and improve our service to you ICCL Group website, online services, applications, email messages, and advertisements may use 'cookies' to collect information about your use of the website. Cookies may also be used to carry out transactions and disabling them may affect the functionality of this website.\n" +
                "\n" +
                "ICCL Group and its partners may use cookies or other technologies to record anonymous, non personal information (not including your name, address email address or telephone number) about your visits to this and other web sites in order to measure advertising effectiveness. We may also collect non personal information about your visit to our website, based on your browsing (click stream) activities. This information may include the pages browsed and products and services viewed or booked for example. This helps us to better manage and develop our offers and to provide you with better products and services tailored to your individual interests and needs. We may use this information to measure the entry and exit points of visitors to the Site and respective numbers of visitors to various pages and sections of the Site and details of searches performed. We may also use this information to measure the usage of advertising banners, other click through from the Site.\n" +
                "\n" +
                "\n" +
                "\n" +
                "As is true of most websites, we gather some information automatically and store it in log files. This information includes Internet Protocol (IP) addresses, browser type and language, Internet service provider (ISP), referring and exit pages, operating system, date/time stamp, and clickstream data.\n" +
                "\n" +
                "We use this information to understand and analyze trends, to administer the site, to learn about user behavior on the site, and to gather demographic information about our user base as a whole. ICCL Group may use this information in our marketing and advertising services.\n" +
                "\n" +
                "In some of our email messages, we use a “click-through URL” linked to content on the ICCL Group website. When customers click one of these URLs, they pass through a separate web server before arriving at the destination page on our website. We track this click-through data to help us determine interest in particular topics and measure the effectiveness of our customer communications. If you prefer not to be tracked in this way, you should not click text or graphic links in the email messages.\n" +
                "\n" +
                "Pixel tags enable us to send email messages in a format customers can read, and they tell us whether mail has been opened. We may use this information to reduce or eliminate messages sent to customers. \n" +
                "\n" +
                "Disclosure to Third Parties\n" +
                "\n" +
                "ICCL Group may share personal information as required to fulfill the service offering and/or to make booking,  blocking and any such activity initiated by user. \n" +
                "\n" +
                "Service providers and partners\n" +
                "\n" +
                "ICCL Group may share personal information with companies who provide services such as information processing, extending credit, fulfilling customer orders, delivering products to you, managing and enhancing customer data, providing customer service, assessing your interest in our products and services, and conducting customer research or satisfaction surveys. These companies are obligated to protect your information. \n" +
                "\n" +
                "Others\n" +
                "\n" +
                "ICCL Group  may disclose Personal Information if required to do so by law or in the good-faith belief that such action is necessary to (a) conform to the edicts of the law or comply with legal process served on ICCL Group or the site; (b) protect and defend the rights or property of ICCL Group, the site or the users of ICCL Group, and (c) act under exigent circumstances to protect the personal safety of users of ICCL Group, ICCL Group, the site or the public.\n" +
                "\n" +
                "We may also disclose information about you if we determine that disclosure is reasonably necessary to enforce our terms and conditions or protect our operations or users. Additionally, in the event of a reorganisation, merger, or sale we may transfer any and all personal information we collect to the relevant third party.\n" +
                "\n" +
                "Protection & Security\n" +
                "\n" +
                "To protect your personal information and prevent unauthorized access, we have put in place appropriate security measures and certifications. We have SSL site and user should use it to protect the information transmission while transacting online. \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "While no system is full-proof, including ours, we will continue using internet security procedures to ensure your data remains safe with us.\n" +
                "\n" +
                "By opening, browsing, using this site for transactions or storing any data/information, you agree to comply with the latest revised privacy policy in effect at such time.\n" +
                "\n" +
                "If you use some social networking or other service which maintains your information, it is governed by their terms of use and privacy policy. \n" +
                "\n" +
                "While ICCL Group employs necessary security measures, it is limited by technology used for various processes by ICCL Group and/or by its partners. ICCL Group is not in any way responsible for any data loss occurring from malicious attempts to compromise its or partners/suppliers systems. Customer browsing/using/transacting on the site agrees with such limitation and exposure. \n" +
                "\n" +
                "Access to Personal Informations\n" +
                "\n" +
                "You can help ensure that your contact information and preferences are accurate, complete, and up to date by logging in to your account. For other personal information, we make good faith efforts to provide you with access so you can request that we correct the data if it is inaccurate or delete the data if ICCL Group is not required to retain it by law or for legitimate business purposes. \n" +
                "\n" +
                "We may decline to process requests that are unreasonably repetitive, require disproportionate technical effort, jeopardize the privacy of others, are extremely impractical, or for which access is not otherwise required by local law. Access, correction, or deletion requests can be made by sending an Email to the address provided in the end\n" +
                "\n" +
                "Third-Party Sites and Services\n" +
                "                                                                                                                                                                                                                                                                                                                                                          ICCL Group, its contractors, agents, owners, and employees are not responsible for the content or the privacy policies of other web sites to which this site may link to or use offers from. Please take time to learn about them as well. \n" +
                "\n" +
                "Before or at the time of collecting personal information, we will identify the purposes for which information is being collected. We will collect and use of personal information solely with the objective of fulfilling those purposes specified by us and for other compatible purposes, unless we obtain the consent of the individual concerned or as required by law. We will only retain personal information as long as necessary for the fulfillment of those purposes. We will collect personal information by lawful and fair means and, where appropriate, with the knowledge or consent of the individual concerned. Personal data should be relevant to the purposes for which it is to be used, and to the extent necessary for those purposes, should be accurate, complete, and up-to-date.We will protect personal information by reasonable security safeguards against loss or theft, as well as unauthorized access, disclosure, copying, use or modification.We are committed to conducting our business in accordance with these principles in order to ensure that the confidentiality of personal information is protected and maintained.";
        String data2 = "Terms Of Use\n" +
                "Agreement between User and ICCL Group\n" +
                "This Web site is offered to you conditioned on your acceptance without modification of the terms, conditions, and notices contained herein. Your use of this Web site constitutes your agreement to all such terms, conditions, and notices which are subject to amendment without any notice. You agree to click on the links to and familiarise yourself with the Terms of Use and other terms and guidelines found throughout this Web site and abide by them if you choose to use the sites, pages or services to which they apply. \n" +
                "Personal and non-commercial use limitation\n" +
                "\n" +
                "This Web site is for your personal and non-commercial use. You will not modify, copy, distribute, transmit, display, perform, reproduce, publish, license, create derivative works from, transfer, or sell any information, software, products or services obtained from this Web site. \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Links to third party sites\n" +
                "\n" +
                "This Web site may contain links to websites operated by parties other than ICCL Group. Such links are provided for your convenience only. ICCL Group does not control such websites, and is not responsible for their contents under any circumstances. ICCL Group's inclusion of links to such web sites does not imply any endorsement of the material on such Web sites or any association with their operators.\n" +
                "\n" +
                "Your correspondence or business dealings with or participation in activities such as but not limited to promotions found in or through such websites are solely between you and such parties. You agree that ICCL Group shall not be responsible or liable for any loss or damage of any sort incurred as a result of any such dealings or as a result of the presence of such links on ICCL Group website. \n" +
                "\n" +
                "\n" +
                "No Unlawful or Prohibited Use\n" +
                "\n" +
                "As a condition of your use of this Web site, you warrant to ICCL Group that you will not use this Web site for any purpose that is unlawful or prohibited by these terms, conditions, and notices. \n" +
                "\n" +
                "\n" +
                "Software available on this web site\n" +
                "\n" +
                "Any software that is made available to download from this Web site (\"Software\") is the copyrighted work of ICCL Group and/or its suppliers. Your use of the Software is governed by the terms of the end user license agreement, if any, which accompanies or is included with the Software (\"License Agreement\"). You will not install or use any Software that is accompanied by or includes a License Agreement unless you first agree to the License Agreement terms. \n" +
                "\n" +
                "For any Software not accompanied by a license agreement, ICCL Group hereby grants to you, the user, a personal, non-transferable license to use the Software for viewing and otherwise using this Web site in accordance with these terms and conditions, and for no other purpose provided that you keep intact all copyright and other proprietary notices. Please note that all Software, including without limitation all Source code contained in this Web site, is owned by ICCL Group and/or its suppliers and is protected by copyright laws and international treaty provisions. Any reproduction or redistribution of the Software is expressly prohibited by law, and may result in severe civil and criminal penalties.\n" +
                "\n" +
                "Violators will be prosecuted to the maximum extent possible. WITHOUT LIMITING THE FOREGOING, COPYING OR REPRODUCTION OF THE SOFTWARE TO ANY OTHER SERVER OR LOCATION FOR FURTHER REPRODUCTION OR REDISTRIBUTION IS EXPRESSLY PROHIBITED. THE SOFTWARE IS WARRANTED, IF AT ALL, ONLY ACCORDING TO THE TERMS OF THE LICENSE AGREEMENT. You acknowledge that the Software, and any accompanying documentation and/or technical information, is subject to applicable export control laws and regulations of INDIA. You agree not to export or re-export the Software, directly or indirectly, to any countries that are subject to INDIAN export restrictions. \n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "Online bookings\n" +
                "\n" +
                "ICCL Group has made this facility available to you as a value-added service. Using this service, you can make your online booking/booking request for booking various services offered by ICCL Group. Your booking request will be processed for confirmation of prices and availability of services requested by you. Only on confirmation to you about the prices and availability, ICCL Group will be bound to provide you the requested services subject however to you making full payment and complying with the relevant terms and conditions. Any correspondence with, you prior to ICCL Group aforesaid confirmation, will not be treated as any acceptance of your request. \n" +
                "\n" +
                "The online booking of the products and services made available through this website is subject to availability and solely at the discretion of ICCL Group and/or its respective suppliers. Please ensure that all information given by you while booking is correct. If any or all of these contact details are not correctly given by you, we reserve the right to cancel the transaction at your risk and cost. On Cancellation of your order the amount will be refunded to debited account within 15 working days.\n" +
                "\n" +
                "The right to access and transact on the web site is reserved as is the right to use any particular credit card on the site for payment purposes.\n";
        String data3 = "Disclaimers\n" +
                "The information, software, products, and services included on this Web site may include inaccuracies or typographical errors and ICCL Group will be entitled to rectify such inaccuracies or typographical errors. ICCL Group will not be liable / responsible for any decision that you may take based on such inaccurate information. Changes are periodically added to the information herein. ICCL Group may make improvements and/or changes in this Web site at any time. \n" +
                "\n" +
                "Advice received via this Web site should not be relied upon for personal, medical, legal or financial decisions and you should consult an appropriate professional for specific advice tailored to your situation.ICCL Group and/or its respective suppliers make no representations about the suitability, reliability, timeliness, and accuracy of the information, software, products, services, or any other items and related graphics contained on this web site for any purpose whatsoever. All such information, software, products, services and related graphics are provided \"as is\" without warranty of any kind. ICCL Group and/or its respective suppliers hereby disclaim all warranties and conditions with regard to this information, software, products, services and related graphics, including all implied warranties and conditions of merchantability, fitness for a particular purpose, title and non-infringement.\n" +
                "\n" +
                "In no event shall ICCL Group and/or its parents, subsidiaries, affiliates, officers, directors, employees, agents or suppliers be liable for any direct, indirect, punitive, incidental, special, consequential damages or any damages whatsoever including, without limitation, damages for loss of use, data or profits, arising out of or in any way connected with the use or performance of this web site, with the delay or inability to use this web site, the provision of or failure to provide services, or for any information, software, products, services and related graphics obtained through this web site, or otherwise arising out of the use of this web site, whether based on contract, tort, strict liability or otherwise, even if ICCL Group or any of its suppliers has been advised of the possibility of damages, including liability associated with any viruses which may infect a users computer equipment. If you are dissatisfied with any portion of this web site, or with any of these terms of use, your sole and exclusive remedy is to discontinue using this web site. \n" +
                "\n" +
                "\n" +
                "\n" +
                "Please ensure that all information given by you while booking is correct. For security reasons and to be able to advice you of any developments affecting your travel we need to be able to contact you by telephone and email and to have your correct address on record .If any or all of these contact details are not correctly given by you, we reserve the right to cancel the transaction at your risk and cost.\n" +
                "\n" +
                "The right to access and transact on the web site is reserved as is the right to use any particular credit card on the site for payment purposes.\n";
        mTabLayout.addTab(mTabLayout.newTab().setText("About"));
        mFragmentList.add(AboutUsFragment.newInstance(data));
        mTabLayout.addTab(mTabLayout.newTab().setText("Privacy Policy"));
        mFragmentList.add(AboutUsFragment.newInstance(data1));
        mTabLayout.addTab(mTabLayout.newTab().setText("Terms & Conditions"));
        mFragmentList.add(AboutUsFragment.newInstance(data2));
        mTabLayout.addTab(mTabLayout.newTab().setText("Disclaimer"));
        mFragmentList.add(AboutUsFragment.newInstance(data3));
        mPagerAdapter.notifyDataSetChanged();

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) mTabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(
                            Typeface.createFromAsset(getAssets(), "fonts/Nunito-Regular.ttf"),
                            Typeface.NORMAL);
                    ((TextView) tabViewChild).setTextSize(
                            getResources().getDimension(R.dimen.small_txt));
                    ((TextView) tabViewChild).setAllCaps(false);
                }
            }
        }
    }

}
