package com.hospitalityhub.shared;



public class ApiURL {
    public final static String BASE_URL = "/api";
    public final static String USER_SIGN_IN = BASE_URL + "/auth/signin";
    public final static String USER_SIGN_UP = BASE_URL + "/auth/signup";
    public final static String ADMIN_SIGN_UP=BASE_URL+"/admin/signup";
    public final static String DOCTOR_LIST=BASE_URL+"/doctor/list";
    public final static String DOCTOR_UPDATE=BASE_URL+"/doctor/update";
    public final static  String PROFILE_UPDATE_WITH_PHOTO=BASE_URL+"/doctor/profile/with-photos";
    public final static String UPDATE_DOCTOR_PROFILE=BASE_URL+"/profile";
    public final static String SAVE_PATIENT_APPOINTMENT=BASE_URL+"/save/patient-appointment";
    public final static String GET_ALL_APPOINTMENT_PATIENT=BASE_URL+"/appointment-list";
    public final static String USER_PROFILE=BASE_URL+"/user/profile";
    public static final String PATIENT_UPDATE = BASE_URL+"/user/update";
    public static final String GET_USER_DETAILS = BASE_URL+"/get/user/detail";
    public static final String PATIENT_DETAIL = BASE_URL+"/patient/detail";
    public static final String APPOINTMENTS_APPROVED =BASE_URL+"/appointments/approved" ;
    public static final String CREATE_WITH_TEST=BASE_URL+"/package/with-tests";
    public static final String GET_ALL_PACKAGE=BASE_URL+"/package";
    public static final  String DELETE_PACKAGE=BASE_URL+"/delete/package";

}
