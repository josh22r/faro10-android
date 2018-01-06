package com.kartum.utils;

public class URLs {


    private static String MAIN_URL = "https://www.faro10.com";

    public static String REGISTER() {
        return MAIN_URL + "/api/users";
    }

    public static String LOGIN() {
        return MAIN_URL + "/api/session";
    }

    public static String FORGETPIN() {
        return MAIN_URL + "/api/passwords";
    }

    public static String ADD_ENTRY() {
        return MAIN_URL + "/api/entries";
    }

    public static String GET_SYMPTOMS() {
        return MAIN_URL + "/api/symptoms";
    }

//    public static String GET_SYMPTOMS_TRACKED() {
//        return MAIN_URL + "/api/symptoms/tracked";
//    }

    public static String GET_SYMPTOMS_TRACKED() {
        return MAIN_URL + "/api/tracked_symptoms";
    }

//    public static String ADD_SYMPTOMS_UPDATE() {
//        return MAIN_URL + "/api/symptoms/";
//    }

//    public static String ADD_SYMPTOMS_UPDATE() {
//        return MAIN_URL + "/api/";
//    }

    public static String ADD_SYMPTOMS_UPDATE() {
        return MAIN_URL + "/api/tracked_symptoms";
    }

    public static String PUT_ADD_CUSTOM_SYMPTOM() {
        return MAIN_URL + "/api/symptoms";
    }

    public static String LOGOUT() {
        return MAIN_URL + "/api/session";
    }

    public static String CHART() {
        return MAIN_URL + "/api/aggregate";
    }

    public static String GET_USER() {
        return MAIN_URL + "/api/user";
    }

    public static String GET_PRESCRIPTION() {
        return MAIN_URL + "/api/prescriptions";
    }

    public static String PUT_PRESCRIPTION() {
        return MAIN_URL + "/api/prescriptions/";
    }

    public static String GET_DRUGS() {
        return MAIN_URL + "/api/drugs";
    }

    public static String GET_MESSAGES() {
        return MAIN_URL + "/api/messages";
    }

    public static String READ_MESSAGE() {
        return MAIN_URL + "/api/messages/";
    }

    public static String UNREAD_MESSAGE() {
        return MAIN_URL + "/api/messages/";
    }

    public static String DEL_MESSAGE() {
        return MAIN_URL + "/api/messages/";
    }

    public static String GET_GOALCENTER() {
        return MAIN_URL + "/api/goal_center";
    }

    public static String GET_CLINICIANS() {
        return MAIN_URL + "/api/memberships";
    }

    public static String GET_DASHBOARD() {
        return MAIN_URL + "/api/dashboard";
    }

    public static String GET_OBSERVEES() {
        return MAIN_URL + "/api/observations_of_others/people";
    }

    public static String ADD_OBSERVERS() {
        return MAIN_URL + "/api/observers/";
    }

    public static String GET_OBSERVERS() {
        return MAIN_URL + "/api/observations_on_me";
    }

    public static String EDIT_OBSERVERS() {
        return MAIN_URL + "/api/observations_on_me/";
    }

    public static String DELETE_OBSERVER() {
        return MAIN_URL + "/api/observers/";
    }

    public static String SAVE_OBSERVATION() {
        return MAIN_URL + "/api/observer_entries";
    }

    public static String GET_OBSERVER() {
        return MAIN_URL + "/api/observations_of_others";
    }

    public static String GET_HISTORY() {
        return MAIN_URL + "/api/observations_of_others/entries";
    }

    public static String GET_OBS_PRES_HIST() {
        return MAIN_URL + "/api/observations_of_others/prescriptions";
    }

    public static String GET_OBS_PRES_GRAPH() {
        return MAIN_URL + "/api/observations_of_others/consistency_chart";
    }

    public static String GET_CARE_TEAM() {
        return MAIN_URL + "/api/members/";
    }

    public static String GET_JOURNALS() {
        return MAIN_URL + "/api/journals";
    }

    public static String APPROVE_CLINICIAN() {
        return MAIN_URL + "/api/members/";
    }

    public static String DELETE_CLINICIAN() {
        return MAIN_URL + "/api/members/";
    }

    public static String GET_TERM_AND_CONDITION() {
        return MAIN_URL + "/about";
    }

    public static String GET_USER_TIMEZONE() {
        return MAIN_URL + "/api/users/time_zones";
    }

//    https://www.faro10.com/about

}
