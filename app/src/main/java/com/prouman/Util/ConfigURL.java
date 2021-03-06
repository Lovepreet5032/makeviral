package com.prouman.Util;

public class ConfigURL {
  //  public static final String Test_URL ="http://api.apps.eabbigliamento.it";// "http://eabbigliamento.it/api";
  //  public static final String PROD_URL = "http://moneysite.net/api";
    public static final String PROD_URL1 = "https://mobileapi.prouman.network";//"https://api.apps.makeviral.net";
    public static final String BASE_URL = PROD_URL1;
    public static final String GUEST_URL = BASE_URL+"/authenticateGuest";
    public static final String MEMBER_URL = BASE_URL+"/authenticateMember";
    public static final String PHOTO_URL = BASE_URL+"/uploads/profile_image/";
    public static final String SHARED_PREF_NAME = "Pref_Name";
    public static final String Video_URL = BASE_URL+"/getVideos";
    public static final String Video_Directory_URL = BASE_URL+"/getVideosDirectory";
    public static final String Video_Hub_URL = BASE_URL+"/getVideosHub";
    public static final String getRecievedNotification = BASE_URL+"/getReceivedNotification";
    public static final String registerDevice=BASE_URL+"/registerDevice";
    public static final String getAdminNotification = BASE_URL+"/getAdminNotification";
    public static final String SentNotification_URL = BASE_URL+"/getSentNotification";
    public static final String SeenNotification_URL = BASE_URL+"/seenNotification";
    public static final String SendPushNotification_URL = BASE_URL+"/sendPushNotification";
    public static final String COPY_RIGHT_URL =BASE_URL+"/auth_public/view_terms_conditions";
    public static final String GET_FORM = BASE_URL+"/getForms";
    public static final String SUBMIT_FORM = BASE_URL+"/submitForm";
    public static final String SUBMIT_HITS = BASE_URL+"/hitForm";
    public static final String GETHOME_Forms = BASE_URL+"/getHomeForms";
    public static final String GET_HOMESCREEN_FORM = BASE_URL+"/getHomeForms";
    public static final String GET_EMAILTEMPLATE = BASE_URL+"/getEmailTemplates";
    public static final String GET_EMAILCAMPAIGNS = BASE_URL+"/getEmailCampaigns";
    public static final String GET_MESSAGE_LANGUAGE =BASE_URL+"/getMessageLanguage";
    public static final String INSERT_FORM =BASE_URL+"/insertProspect";
    public static String SMS_TEMPLATE=BASE_URL+"/getSMSTemplates";
    public static String SMS_CAMPAIGNS=BASE_URL+"/getSMSCampaigns";
    public static String GET_WEBINAR=BASE_URL+"/getWebinar";
    public static final String DATA_URL = BASE_URL+"/getForms";
    public static final String LINK_PAGES = BASE_URL+"/getLinkPages";
  //public static final String CAPTURE_PAGES = BASE_URL+"/getCapturePages";
 // public static final String VIDEO_LINK_PAGES = BASE_URL+"/getVideoLinkPages";
  //public static final String VIDEO_CAPTURE_PAGES = BASE_URL+"/getVideoCapturePages";
 // public static final String OMS_PAGES = BASE_URL+"/getOMSPages";
  public static final String IMPORT_CONTACTS = BASE_URL+"/importContacts";
  public static final String GETCONTACT=BASE_URL+"/getProspectslist";
  public static final String GETGROUPS=BASE_URL+"/getProspectRelatedlist";
  public static final String GETGROUPSLIST=BASE_URL+"/getProspectGroupsList";
  public static final String ADDPROTOCAMPAIGN=BASE_URL+"/addProspectToCampaign";
  public static final String ADDPROTOGROUP=BASE_URL+"/addProspectToGroup";
  public static final String ADDPROTOBROADCAST=BASE_URL+"/addProspectToBroadcast";
  public static final String EDITPROSPECT=BASE_URL+"/doEditProspect";
  public static final String DELETEPROSPECT=BASE_URL+"/doDeleteProspect";
  public static final String TRANSFERPROSPECT=BASE_URL+"/doTransferProspect";
  public static final String GETTAGS=BASE_URL+"/getProspectTagsList";
  public static final String GETPAGESECTION=BASE_URL+"/getPageSections";
  public static final String GETPAGES=BASE_URL+"/getPages";
//  public static final String ADDPROTOCAMPAIGN=BASE_URL+"/addProspectToCampaign";
    //Tags used in the JSON String
    public static final String TAG_USERNAME = "username";
    public static final String TAG_NAME = "name";
    public static final String TAG_COURSE = "course";
    public static final String TAG_SESSION = "session";
  public static final String GET_TEMPLATE_CAMPIGNS = BASE_URL+"/getAllTemplatesCampaigns";
    //JSON array name
    public static final String JSON_ARRAY = "form_templates";
    public static String INSERTFORM="http://eabbigliamento.it/api/insertProspect";
}
